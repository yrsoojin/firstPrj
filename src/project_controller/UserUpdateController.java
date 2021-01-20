package project_controller;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project_dao.UserDao;

public class UserUpdateController extends UserController implements Initializable{
	

	@FXML private TextField idField;
	@FXML private PasswordField pwField;
	@FXML private PasswordField newPwField;
	@FXML private PasswordField newPwVerifyField;
	
	@FXML private Button tableReserveBtn;
	@FXML private Button reserveListBtn;
	@FXML private Button userUpdateBtn;
	@FXML private Button idDeleteBtn;
	@FXML private Button backBtn;
	
	UserDao uDao = new UserDao();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reserveListBtn.setDisable(true);
		tableReserveBtn.setDisable(true);
	}
	
	
	public void pwUpdate(ActionEvent event) {
		if(idField.getText().equals("") || pwField.getText().equals("") ||newPwField.getText().equals("") || newPwVerifyField.getText().equals("")) {
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("��� �׸��� �Է��� �ּ���.");
		    alert.show();
		}
		else if(!newPwField.getText().equals(newPwVerifyField.getText())) {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� ���� �ٸ��ϴ�.\n��й�ȣ�� �ٽ� Ȯ���� �ּ���.");
		    alert.show(); 
		}
		else if(newPwField.getText().length()<=4 || newPwField.getText().length()>=15) {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� 5~14���ڷ� �Է����ּ���.");
		    alert.show();
		}
		
		else if(uDao.idLoad(idField.getText(), pwField.getText())==1) {
			if(uDao.pwUpdate(idField.getText(), newPwField.getText()) == 1) {
				alert.setTitle("��й�ȣ ����");
			    alert.setHeaderText("��й�ȣ ���� �Ϸ�");         
			    alert.show();
			    idField.setText("");
			    pwField.setText("");
			    newPwField.setText("");
			    newPwVerifyField.setText("");
			}
			else {
				alert.setTitle("��й�ȣ ���� ");
			    alert.setHeaderText("��й�ȣ ���� ���� \n���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ���� �ּ���.");
			    alert.show(); 
			}
		}
		else {
			alert.setTitle("��й�ȣ ���� ");
		    alert.setHeaderText("��й�ȣ ���� ���� \n���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ���� �ּ���.");
		    alert.show(); 
		}
	}
	
	public void pwVerify(ActionEvent event) {
		if(newPwField.getText().equals("") || newPwVerifyField.getText().equals("")) {
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("��� �׸��� �Է��� �ּ���.");
		    alert.show();
		}
		else if(newPwField.getText().length()<=4 || newPwField.getText().length()>=15) {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� 5~14���ڷ� �Է����ּ���.");
		    alert.show();
		}
		else if (newPwField.getText().equals(newPwVerifyField.getText())) {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� �����ϴ�.");
		    alert.show(); 
		}
		else {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� �ٸ��ϴ�.");         
		    alert.show();
		}
	}
	
	public void idDelete(ActionEvent event) {
		if(idField.getText().equals("")) {
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("���̵� �Է��� �ּ���.");
		    alert.show();
		}
		
		else if(uDao.idLoad(idField.getText(), pwField.getText())==1) {
			if(uDao.idDelete(idField.getText())==1) {
				alert.setTitle("���̵� ����");
			    alert.setHeaderText("ȸ��Ż�� �Ϸ�\n���̵� �����Ǿ����ϴ�.");
			    alert.show();
			    Stage root = (Stage)idDeleteBtn.getScene().getWindow();
				root.close();
			}
			else {
				alert.setTitle("���̵� ����");
			    alert.setHeaderText("���̵� ���� ����\n���̵� �ٽ� Ȯ���� �ּ���.");
			    alert.show(); 
			}
		}
		else {
			alert.setTitle("���̵� ����");
		    alert.setHeaderText("���̵� ���� ����\n���̵� �ٽ� Ȯ���� �ּ���.");
		    alert.show();
		}
	}
	
	public void back(ActionEvent e) {
		Stage root = (Stage)backBtn.getScene().getWindow();
		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("../project_fxml/user.fxml"));
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("User.css").toString());
			root.setScene(scene);
			root.setResizable(false);
			root.show();
			root.setTitle("����� ���");
		} catch (Exception e2) {e2.printStackTrace();
		}
	}
}