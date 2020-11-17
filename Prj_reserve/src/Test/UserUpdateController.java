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
			alert.setTitle("빈 항목");
		    alert.setHeaderText("모든 항목을 입력해 주세요.");
		    alert.show();
		}
		else if(!newPwField.getText().equals(newPwVerifyField.getText())) {
			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호가 서로 다릅니다.\n비밀번호를 다시 확인해 주세요.");
		    alert.show(); 
		}
		else if(newPwField.getText().length()<=4 || newPwField.getText().length()>=15) {
			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호는 5~14글자로 입력해주세요.");
		    alert.show();
		}
		
		else if(bDao.idLoad(idField.getText(), pwField.getText())==1) {
			if(bDao.pwUpdate(idField.getText(), newPwField.getText()) == 1) {
				alert.setTitle("비밀번호 변경");
			    alert.setHeaderText("비밀번호 변경 완료");         
			    alert.show();
			    idField.setText("");
			    pwField.setText("");
			    newPwField.setText("");
			    newPwVerifyField.setText("");
			}
			else {
				alert.setTitle("비밀번호 변경 ");
			    alert.setHeaderText("비밀번호 변경 실패 \n아이디 또는 비밀번호를 다시 확인해 주세요.");
			    alert.show(); 
			}
		}
		else {
			alert.setTitle("비밀번호 변경 ");
		    alert.setHeaderText("비밀번호 변경 실패 \n아이디 또는 비밀번호를 다시 확인해 주세요.");
		    alert.show(); 
		}
	}
	
	public void pwVerify(ActionEvent event) {
		if(newPwField.getText().equals("") || newPwVerifyField.getText().equals("")) {
			alert.setTitle("빈 항목");
		    alert.setHeaderText("모든 항목을 입력해 주세요.");
		    alert.show();
		}
		else if(newPwField.getText().length()<=4 || newPwField.getText().length()>=15) {
			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호는 5~14글자로 입력해주세요.");
		    alert.show();
		}
		else if (newPwField.getText().equals(newPwVerifyField.getText())) {
			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호가 같습니다.");
		    alert.show(); 
		}
		else {
			alert.setTitle("비밀번호 확인");
		    alert.setHeaderText("비밀번호가 다릅니다.");         
		    alert.show();
		}
	}
	
	public void idDelete(ActionEvent event) {
		if(idField.getText().equals("")) {
			alert.setTitle("빈 항목");
		    alert.setHeaderText("아이디를 입력해 주세요.");
		    alert.show();
		}
		
		else if(bDao.idLoad(idField.getText(), pwField.getText())==1) {
			if(bDao.idDelete(idField.getText())==1) {
				alert.setTitle("아이디 삭제");
			    alert.setHeaderText("회원탈퇴 완료\n아이디가 삭제되었습니다.");
			    alert.show();
			    idField.setText("");
			    pwField.setText("");
			    newPwField.setText("");
			    newPwVerifyField.setText("");
			}
			else {
				alert.setTitle("아이디 삭제");
			    alert.setHeaderText("아이디 삭제 실패\n아이디를 다시 확인해 주세요.");
			    alert.show(); 
			}
		}
		else {
			alert.setTitle("아이디 삭제");
		    alert.setHeaderText("아이디 삭제 실패\n아이디를 다시 확인해 주세요.");
		    alert.show();
		}
	}
}