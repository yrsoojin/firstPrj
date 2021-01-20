package project_controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project_constructor.User;


public class RegisterController implements Initializable {
	
	@FXML public TextField idField;	
	@FXML private PasswordField pwField;
	@FXML private PasswordField pwField1;
	@FXML public TextField nameField;
	@FXML public TextField emailField; 
	@FXML private TextField textField;
	@FXML private TextField phoneField;
	@FXML private ComboBox<String> comboSex;
	@FXML private Button regBtn;
	@FXML private Button idBtn;
	@FXML private Button pwBtn;
	@FXML private Button emailBtn;
	@FXML private Label pwVerifyLabel;



	project_dao.UserDao uDao = new project_dao.UserDao();
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	static Boolean emailsend = false;
	String id;
	String name;
	
	static String emailFieldText;
	static String nameFieldText;
	static String idFieldText;
	
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
		else if(emailsend!=true) {
			alert.setTitle("�̸��� ����");
		    alert.setHeaderText("�̸��� ������ �ʿ��մϴ�.");
		    alert.show();
		}
		else {
			id = idField.getText();
			String pw = pwField.getText();
			name = nameField.getText();
			//�������� ����
			String regExp = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
			if(Pattern.matches(regExp, emailField.getText())==false) {
				alert.setTitle("�̸��� ����");
			    alert.setHeaderText("�̸��� ������ Ȯ���� �ּ���.\n(abcd@mail.com)");         
			    alert.show();
			}
			else {  //��� �������׿� ������ ���� DB���� �ߺ��� ���� �� ������ ���̵� �����ϱ� 
				String email = emailField.getText();
				String phone = phoneField.getText();
				String combosex = comboSex.getValue();
				//���̵� �ߺ�, ��й�ȣ �ٸ� Ȯ��
				if (uDao.idVerify(idField.getText())==1) {
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
					User board = new User(id, pw, name, email, phone, combosex);
					uDao.idInsert(board);
					alert.setTitle("���ԿϷ�");
				    alert.setHeaderText("���̵� ���� �Ϸ�");         
				    alert.show();
					Stage root = (Stage)regBtn.getScene().getWindow();
					root.close();
				}
			}
		}
	}
	
	//���̵� �ߺ�Ȯ��
	public void idVerify(ActionEvent event) {
		if (uDao.idVerify(idField.getText())==1) {
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
	public void pwVerify(ActionEvent e) {
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
			pwVerifyLabel.setText("��й�ȣ�� �ٸ��ϴ�.");
			alert.setTitle("��й�ȣ Ȯ��");
		    alert.setHeaderText("��й�ȣ�� �ٸ��ϴ�.");         
		    alert.show();
		}
	}
	
	public void mailSend(ActionEvent e) {
		emailFieldText = emailField.getText();
		nameFieldText = nameField.getText();
		idFieldText = idField.getText();
		
		String regExp = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
		if(Pattern.matches(regExp, emailField.getText())==false) {
			alert.setTitle("�̸��� ����");
		    alert.setHeaderText("�̸��� ������ Ȯ���� �ּ���.\n(abcd@mail.com)");         
		    alert.show();
		}
		else {
			if(uDao.emailVerify(emailField.getText())==1) {
				alert.setTitle("�̸��� �ߺ�");
			    alert.setHeaderText("�̹� ���� �� �̸����Դϴ�.\n�ٽ� Ȯ���� �ּ���.");         
			    alert.show();
			}
			else {
				Stage stage = new Stage();
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("../project_fxml/emailVerify.fxml"));
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setResizable(false);
					stage.show();
					stage.setTitle("�̸��� ����");	
				} catch (IOException e2) { e2.printStackTrace(); }
			}
		}
	}
	
	public void handleBtnClear(ActionEvent event) {
		idField.setText("");
		pwField.setText("");
		pwField1.setText("");
		nameField.setText("");
		emailField.setText("");
		comboSex.setValue("����");
		idBtn.setDisable(false);
		pwBtn.setDisable(false);
		emailBtn.setDisable(false);
	}	
}