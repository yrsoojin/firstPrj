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
	static boolean checkbox; //üũ���¸� ��´�
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(uDao.bringID()!=null) {  //����� ID���� ��� set
			System.out.println("bring the id");
			idField.setText(uDao.bringID());
			saveID.setSelected(true);
		}
		//����ġ�� ���� 
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
	public void saveIDBtnChecked(ActionEvent e) { //üũ�ڽ� ��üũ�� id����
		if(!saveID.isSelected()) {
			uDao.saveIDdelete();
		}
	}
	
	public void login(ActionEvent event) {
		
		if(idField.getText().equals("") || pwField.getText().equals("")) {
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("��� �׸��� �Է��� �ּ���.");
		    alert.show();
		}
		//���̵�,�н����尡 admin�̸� �����ڸ��� ����
		else if (idField.getText().equals("admin") && pwField.getText().equals("admin")) {
			Stage root = (Stage)logBtn.getScene().getWindow();
			try {
				Parent root1 = FXMLLoader.load(getClass().getResource("../project_fxml/admin.fxml"));
				Scene scene = new Scene(root1);
				root.setScene(scene);
				root.setResizable(false);
				root.show();
				root.setTitle("������ ���");
			} catch (Exception e) {
			}
			
		}		
		else if (uDao.idVerify(idField.getText())==0) {
			alert.setTitle("���̵� Ȯ��");
		    alert.setHeaderText("���� �����Դϴ�.\n�ٽ� Ȯ���� �ּ���.");
		    alert.show();
		}
		else if(uDao.idLoad(idField.getText(), pwField.getText())==0) {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� Ʋ���ϴ�.\n�ٽ� Ȯ���� �ּ���.");
		    alert.show();
		}
		else {
			id = idField.getText();
			pw = pwField.getText();
			
			if(saveID.isSelected()) {  //üũ�ڽ� Ȯ�� , id����			
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
				root.setTitle("����� ��� : " + id + "�� �α���");
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
			stage.setTitle("ȸ������");	
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void cancel(ActionEvent event) {
		Platform.exit();
	}
}