package Test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
//import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class RegisterController implements Initializable {
	
	@FXML private TextField idField;	
	@FXML private PasswordField pwField;
	@FXML private PasswordField pwField1;
	@FXML private TextField nameField;
	@FXML private TextField emailField; 
	@FXML private TextField textField;
	@FXML private ComboBox<String> comboSex; 
//	@FXML private DatePicker dateExit;				//��¥ ���� 
//	@FXML private TextArea txtContent;				//�����Է�


	BoardDao bDao = new BoardDao();
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}	

	//���̵� ������ư
	public void handleBtnRegAction(ActionEvent event) {
		//�� �׸� ���
		if(idField.getText().equals("") || pwField.getText().equals("") || nameField.getText().equals("") ||
		   emailField.getText().equals("")) {
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("��� �׸��� �Է��� �ּ���.");
		    alert.show();
		}
		else if(idField.getText().length()<=4 || idField.getText().length()>=15) {
			alert.setTitle("���̵� Ȯ��");
		    alert.setHeaderText("���̵�� 5~14���ڷ� �Է����ּ���.");
		    alert.show();
		}
		else if(pwField.getText().length()<=4 || pwField.getText().length()>=15) {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� 5~14���ڷ� �Է����ּ���.");
		    alert.show();
		}
		//admin ���̵� ���� ����
		else if(idField.getText().equals("admin")) {
			alert.setTitle("���̵� Ȯ��");
		    alert.setHeaderText("����� �� ���� ���̵��Դϴ�.\n�ٽ� Ȯ���� �ּ���.");
		    alert.show();
		}
		else {
			String id = idField.getText();
			String pw = pwField.getText();
			String name = nameField.getText();
			//�������� ����
			String regExp = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
			if(Pattern.matches(regExp, emailField.getText())==false) {
				alert.setTitle("�̸��� ����");
			    alert.setHeaderText("�̸��� ������ Ȯ���� �ּ���.\n(abcd@mail.com)");         
			    alert.show();
			}
			else {
				String email = emailField.getText();
				String combosex = comboSex.getValue();
				//���̵� �ߺ�, ��й�ȣ �ٸ� Ȯ��
				if (bDao.idVerify(idField.getText())==1) {
					alert.setTitle("���̵� �ߺ�Ȯ��");
				    alert.setHeaderText("��� ���� ���̵��Դϴ�.\n���̵� �ٽ� Ȯ���� �ּ���.");
				    alert.show(); 
				}
				else if(!pwField.getText().equals(pwField1.getText())) {
					alert.setTitle("��й�ȣ Ȯ��");
				    alert.setHeaderText("��й�ȣ�� ���� �ٸ��ϴ�.\n��й�ȣ�� �ٽ� Ȯ���� �ּ���.");
				    alert.show(); 
				}
				else {
					Board board = new Board(id, pw, name, email, combosex);
					bDao.idInsert(board);
					alert.setTitle("���ԿϷ�");
				    alert.setHeaderText("���̵� ���� �Ϸ�");         
				    alert.show();
					idField.setText("");
					pwField.setText("");
					pwField1.setText("");
					nameField.setText("");
					emailField.setText("");
					comboSex.setValue("����");
				}
			}
		}
	}
	
	//���̵� �ߺ�Ȯ��
	public void idVerify(ActionEvent event) {
		if (bDao.idVerify(idField.getText())==1) {
			alert.setTitle("���̵� �ߺ�Ȯ��");
		    alert.setHeaderText("��� ���� ���̵��Դϴ�.");
		    alert.show(); 
		}
		//admin ���̵� ���� ����
		else if(idField.getText().equals("admin")) {
			alert.setTitle("���̵� Ȯ��");
		    alert.setHeaderText("����� �� ���� ���̵��Դϴ�.\n�ٽ� Ȯ���� �ּ���.");
		    alert.show();
		}
		else if(idField.getText().length()<=4 || idField.getText().length()>=15) {
			alert.setTitle("���̵� Ȯ��");
		    alert.setHeaderText("���̵�� 5~14���ڷ� �Է����ּ���.");
		    alert.show();
		}
		else {
			alert.setTitle("���̵� �ߺ�Ȯ��");
		    alert.setHeaderText("��� ������ ���̵��Դϴ�.");         
		    alert.show(); 
		}
	}
	
	//��й�ȣ ������ Ȯ��
	public void pwVerify(ActionEvent event) {
		if (pwField.getText().equals(pwField1.getText())) {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� �����ϴ�.");
		    alert.show(); 
		}
		else if(pwField.getText().length()<=4 || pwField.getText().length()>=15) {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� 5~14���ڷ� �Է����ּ���.");
		    alert.show();
		}
		else {
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� �ٸ��ϴ�.");         
		    alert.show();
		}
	}
	
	public void handleBtnClear(ActionEvent event) {
		idField.setText("");
		pwField.setText("");
		pwField1.setText("");
		nameField.setText("");
		emailField.setText("");
		comboSex.setValue("����");
	}	
}