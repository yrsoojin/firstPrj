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

	//아이디 생성버튼
	public void handleBtnRegAction(ActionEvent event) {
		//빈 항목 경고
		if(idField.getText().equals("") || pwField.getText().equals("") || nameField.getText().equals("") ||
		   emailField.getText().equals("")) {
			alert.setTitle("빈 항목");
		    alert.setHeaderText("모든 항목을 입력해 주세요.");
		    alert.show();
		}
		else if(idField.getText().length()<=4 || idField.getText().length()>=15) {
			alert.setTitle("아이디 확인");
		    alert.setHeaderText("아이디는 5~14글자로 입력해주세요.");
		    alert.show();
		}
		else if(pwField.getText().length()<=4 || pwField.getText().length()>=15) {
			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호는 5~14글자로 입력해주세요.");
		    alert.show();
		}
		//admin 아이디 생성 금지
		else if(idField.getText().equals("admin")) {
			alert.setTitle("아이디 확인");
		    alert.setHeaderText("사용할 수 없는 아이디입니다.\n다시 확인해 주세요.");
		    alert.show();
		}
		else if(emailsend!=true) {
			alert.setTitle("이메일 인증");
		    alert.setHeaderText("이메일 인증이 필요합니다.");
		    alert.show();
		}
		else {
			id = idField.getText();
			String pw = pwField.getText();
			name = nameField.getText();
			//메일형식 지정
			String regExp = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
			if(Pattern.matches(regExp, emailField.getText())==false) {
				alert.setTitle("이메일 형식");
			    alert.setHeaderText("이메일 형식을 확인해 주세요.\n(abcd@mail.com)");         
			    alert.show();
			}
			else {  //모든 인적사항에 문제가 없고 DB와의 중복이 없을 시 보통의 아이디 생성하기 
				String email = emailField.getText();
				String phone = phoneField.getText();
				String combosex = comboSex.getValue();
				//아이디 중복, 비밀번호 다름 확인
				if (uDao.idVerify(idField.getText())==1) {
					alert.setTitle("아이디 중복확인");
				    alert.setHeaderText("사용 중인 아이디입니다.\n아이디를 다시 확인해 주세요.");
				    alert.show(); 
				}
				else if(!pwField.getText().equals(pwField1.getText())) {
					alert.setTitle("비밀번호 확인");
				    alert.setHeaderText("비밀번호가 서로 다릅니다.\n비밀번호를 다시 확인해 주세요.");
				    alert.show(); 
				}
				else {
					User board = new User(id, pw, name, email, phone, combosex);
					uDao.idInsert(board);
					alert.setTitle("가입완료");
				    alert.setHeaderText("아이디 생성 완료");         
				    alert.show();
					Stage root = (Stage)regBtn.getScene().getWindow();
					root.close();
				}
			}
		}
	}
	
	//아이디 중복확인
	public void idVerify(ActionEvent event) {
		if (uDao.idVerify(idField.getText())==1) {
			alert.setTitle("아이디 중복확인");
		    alert.setHeaderText("사용 중인 아이디입니다.");
		    alert.show(); 
		}
		//admin 아이디 생성 금지
		else if(idField.getText().equals("admin")) {
			alert.setTitle("아이디 확인");
		    alert.setHeaderText("사용할 수 없는 아이디입니다.\n다시 확인해 주세요.");
		    alert.show();
		}
		else if(idField.getText().length()<=4 || idField.getText().length()>=15) {
			alert.setTitle("아이디 확인");
		    alert.setHeaderText("아이디는 5~14글자로 입력해주세요.");
		    alert.show();
		}
		else {
			alert.setTitle("아이디 중복확인");
		    alert.setHeaderText("사용 가능한 아이디입니다.");         
		    alert.show(); 
		}
	}
	
	//비밀번호 같은지 확인
	public void pwVerify(ActionEvent e) {
		if (pwField.getText().equals(pwField1.getText())) {

			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호가 같습니다.");
		    alert.show();
		}
		else if(pwField.getText().length()<=4 || pwField.getText().length()>=15) {

			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호는 5~14글자로 입력해주세요.");
		    alert.show();
		}
		else {
			pwVerifyLabel.setText("비밀번호가 다릅니다.");
			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호가 다릅니다.");         
		    alert.show();
		}
	}
	
	public void mailSend(ActionEvent e) {
		emailFieldText = emailField.getText();
		nameFieldText = nameField.getText();
		idFieldText = idField.getText();
		
		String regExp = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
		if(Pattern.matches(regExp, emailField.getText())==false) {
			alert.setTitle("이메일 형식");
		    alert.setHeaderText("이메일 형식을 확인해 주세요.\n(abcd@mail.com)");         
		    alert.show();
		}
		else {
			if(uDao.emailVerify(emailField.getText())==1) {
				alert.setTitle("이메일 중복");
			    alert.setHeaderText("이미 가입 된 이메일입니다.\n다시 확인해 주세요.");         
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
					stage.setTitle("이메일 인증");	
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
		comboSex.setValue("선택");
		idBtn.setDisable(false);
		pwBtn.setDisable(false);
		emailBtn.setDisable(false);
	}	
}