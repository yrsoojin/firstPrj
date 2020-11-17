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
//	@FXML private DatePicker dateExit;				//날짜 선택 
//	@FXML private TextArea txtContent;				//내용입력


	BoardDao bDao = new BoardDao();
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
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
		else {
			String id = idField.getText();
			String pw = pwField.getText();
			String name = nameField.getText();
			//메일형식 지정
			String regExp = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
			if(Pattern.matches(regExp, emailField.getText())==false) {
				alert.setTitle("이메일 형식");
			    alert.setHeaderText("이메일 형식을 확인해 주세요.\n(abcd@mail.com)");         
			    alert.show();
			}
			else {
				String email = emailField.getText();
				String combosex = comboSex.getValue();
				//아이디 중복, 비밀번호 다름 확인
				if (bDao.idVerify(idField.getText())==1) {
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
					Board board = new Board(id, pw, name, email, combosex);
					bDao.idInsert(board);
					alert.setTitle("가입완료");
				    alert.setHeaderText("아이디 생성 완료");         
				    alert.show();
					idField.setText("");
					pwField.setText("");
					pwField1.setText("");
					nameField.setText("");
					emailField.setText("");
					comboSex.setValue("선택");
				}
			}
		}
	}
	
	//아이디 중복확인
	public void idVerify(ActionEvent event) {
		if (bDao.idVerify(idField.getText())==1) {
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
	public void pwVerify(ActionEvent event) {
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
			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호가 다릅니다.");         
		    alert.show();
		}
	}
	
	public void handleBtnClear(ActionEvent event) {
		idField.setText("");
		pwField.setText("");
		pwField1.setText("");
		nameField.setText("");
		emailField.setText("");
		comboSex.setValue("선택");
	}	
}