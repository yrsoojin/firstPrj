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
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("��� �׸��� �Է��� �ּ���.");
		    alert.show();
		}
		//���̵�,�н����尡 admin�̸� �����ڸ��� ����
		else if (idField.getText().equals("admin") && pwField.getText().equals("admin")) {
			Stage stage = new Stage();
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("admin.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
				stage.setTitle("������ ���");	
			} catch (IOException e) { e.printStackTrace(); }
		}		
		else if (bDao.idVerify(idField.getText())==0) {
			alert.setTitle("���̵� Ȯ��");
		    alert.setHeaderText("���� �����Դϴ�.\n�ٽ� Ȯ���� �ּ���.");
		    alert.show();
		}
		else if(bDao.idLoad(idField.getText(), pwField.getText())==0) {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� Ʋ���ϴ�.\n�ٽ� Ȯ���� �ּ���.");
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
				stage.setTitle("����� ��� : " + id + "�� �α���");
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
			stage.setTitle("ȸ������");	
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void cancle(ActionEvent event) {
		Platform.exit();
	}
	
}