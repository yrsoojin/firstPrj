package Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

@SuppressWarnings("unused")
public class UserUpdateController implements Initializable {
	
	@FXML private TextField idField;
	@FXML private PasswordField pwField;
	@FXML private PasswordField newPwField;
	@FXML private PasswordField newPwVerifyField;


	BoardDao bDao = new BoardDao();
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
		
		else if(bDao.idLoad(idField.getText(), pwField.getText())==1) {
			if(bDao.pwUpdate(idField.getText(), newPwField.getText()) == 1) {
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
		
		else if(bDao.idLoad(idField.getText(), pwField.getText())==1) {
			if(bDao.idDelete(idField.getText())==1) {
				alert.setTitle("���̵� ����");
			    alert.setHeaderText("ȸ��Ż�� �Ϸ�\n���̵� �����Ǿ����ϴ�.");
			    alert.show();
			    idField.setText("");
			    pwField.setText("");
			    newPwField.setText("");
			    newPwVerifyField.setText("");
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
}