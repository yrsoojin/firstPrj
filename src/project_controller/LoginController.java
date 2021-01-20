package project_controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import project_constructor.User;

public class LoginController implements Initializable {
	
	@FXML private TextField idField;	
	@FXML private PasswordField pwField;
	@FXML private Button logBtn;
	@FXML private ImageView imageView;
	@FXML private CheckBox saveID;
	
	project_dao.UserDao uDao = new project_dao.UserDao();
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	public static String id;
	String pw;
	static boolean checkbox; //체크상태를 얻는다
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(uDao.bringID()!=null) {  //저장된 ID있을 경우 set
			System.out.println("bring the id");
			idField.setText(uDao.bringID());
			saveID.setSelected(true);
		}
		//엔터치고 들어가기 
		pwField.setOnKeyPressed(new EventHandler<KeyEvent>() {
	         @Override
	         public void handle(KeyEvent event) {
	            if(event.getCode().equals(KeyCode.ENTER)) {
	               login(null);
	            }
	         }
	      });
			
		saveID.setOnAction(e -> saveIDBtnChecked(e));
		
			
		
	}	
	public void saveIDBtnChecked(ActionEvent e) { //체크박스 언체크시 id삭제
		if(!saveID.isSelected()) {
			uDao.saveIDdelete();
		}
	}
	
	public void login(ActionEvent event) {
		
		if(idField.getText().equals("") || pwField.getText().equals("")) {
			alert.setTitle("빈 항목");
		    alert.setHeaderText("모든 항목을 입력해 주세요.");
		    alert.show();
		}
		//아이디,패스워드가 admin이면 관리자모드로 실행
		else if (idField.getText().equals("admin") && pwField.getText().equals("admin")) {
			Stage root = (Stage)logBtn.getScene().getWindow();
			try {
				Parent root1 = FXMLLoader.load(getClass().getResource("../project_fxml/admin.fxml"));
				Scene scene = new Scene(root1);
				root.setScene(scene);
				root.setResizable(false);
				root.show();
				root.setTitle("관리자 모드");
			} catch (Exception e) {
			}
			
		}		
		else if (uDao.idVerify(idField.getText())==0) {
			alert.setTitle("아이디 확인");
		    alert.setHeaderText("없는 계정입니다.\n다시 확인해 주세요.");
		    alert.show();
		}
		else if(uDao.idLoad(idField.getText(), pwField.getText())==0) {
			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호가 틀립니다.\n다시 확인해 주세요.");
		    alert.show();
		}
		else {
			id = idField.getText();
			pw = pwField.getText();
			
			if(saveID.isSelected()) {  //체크박스 확인 , id저장			
				User save = new User(id);
				uDao.saveIDinsert(save);
			}
			Stage root = (Stage)logBtn.getScene().getWindow();
			try {
				Parent root1 = FXMLLoader.load(getClass().getResource("../project_fxml/user.fxml"));
				Scene scene = new Scene(root1);
				scene.getStylesheets().add(getClass().getResource("User.css").toString());
				root.setScene(scene);
				root.setResizable(false);
				root.show();
				root.setTitle("사용자 모드 : " + id + "님 로그인");
			} catch (IOException e) { e.printStackTrace(); }
			}
	}
	
	public void register(ActionEvent event) {
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../project_fxml/register.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("Register.css").toString());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			stage.setTitle("회원가입");	
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void cancel(ActionEvent event) {
		Platform.exit();
	}
}