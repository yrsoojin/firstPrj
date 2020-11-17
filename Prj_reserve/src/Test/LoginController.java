package Test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
//import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	@FXML private TextField idField;	
	@FXML private PasswordField pwField;

	BoardDao bDao = new BoardDao();
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	String id;
	String pw;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}	

	public void login(ActionEvent event) {
		if(idField.getText().equals("") || pwField.getText().equals("")) {
			alert.setTitle("빈 항목");
		    alert.setHeaderText("모든 항목을 입력해 주세요.");
		    alert.show();
		}
		//아이디,패스워드가 admin이면 관리자모드로 실행
		else if (idField.getText().equals("admin") && pwField.getText().equals("admin")) {
			Stage stage = new Stage();
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("admin.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
				stage.setTitle("관리자 모드");	
			} catch (IOException e) { e.printStackTrace(); }
		}		
		else if (bDao.idVerify(idField.getText())==0) {
			alert.setTitle("아이디 확인");
		    alert.setHeaderText("없는 계정입니다.\n다시 확인해 주세요.");
		    alert.show();
		}
		else if(bDao.idLoad(idField.getText(), pwField.getText())==0) {
			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호가 틀립니다.\n다시 확인해 주세요.");
		    alert.show();
		}
		else {
			id = idField.getText();
			pw = pwField.getText();
			Stage stage = new Stage();
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("user.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
				stage.setTitle("사용자 모드 : " + id + "님 로그인");
			} catch (IOException e) { e.printStackTrace(); }
			}
	}
	
	public void register(ActionEvent event) {
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("register.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			stage.setTitle("회원가입");	
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void cancle(ActionEvent event) {
		Platform.exit();
	}
	
}