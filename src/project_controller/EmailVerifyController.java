package project_controller;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EmailVerifyController extends RegisterController implements Initializable {
	
	@FXML private Button emailVerifyBtn;
	@FXML private Label emailVerifyLabel;
	@FXML private TextField emailVerifyField;
	
	Alert alert = new Alert(Alert.AlertType.INFORMATION);

	
	int random = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		emailVerifyLabel.setText(emailFieldText);
		String host = "smtp.naver.com"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정 
	    String user = "lounge_cu@naver.com";  //보내는 사람의 메일 계정
	    String password = "gksdldma!!";   // 패스워드
	      //SMTP 서버 정보를 설정한다. 
	    Properties props = new Properties(); 
	    props.put("mail.smtp.host", host); 
	    props.put("mail.smtp.port", 587); 
	    props.put("mail.smtp.auth", "true"); 
	      
	    Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	       protected PasswordAuthentication getPasswordAuthentication() { 
	           return new PasswordAuthentication(user, password); 
	          } 
	       }); 
	    
	    try { 
	       random = (int)(Math.random()*10000);
	       MimeMessage message = new MimeMessage(session);
	       //보내는 사람 설정
	       message.setFrom(new InternetAddress(user)); 
	       //받는 사람 설정          
	       message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailFieldText));         
	       // 메일 제목
	       message.setSubject("회원가입 이메일 인증");
	       // 메일 내용 
	       message.setText("oo카페 예약 서비스 회원가입 \n\n"+nameFieldText+" 회원님의 이메일이 맞는지 확인해 주세요.\n\n가입하신 아이디는 "+idFieldText+" 입니다.\n\n"
	    		   			+nameFieldText+" 회원님이 맞으면 아래의 인증번호를 인증번호 입력창에 입력해 주세요.\n\n인증번호 : " + random);
	       
	       Transport.send(message); 
	       emailsend = true;
	       System.out.println("메일 보내기 성공"); 
	       
	       } catch (MessagingException e1) { e1.printStackTrace(); } 
	}
	
	public void verifyOnAction(ActionEvent event) {
		try {
			if(random == Integer.parseInt(emailVerifyField.getText())) {
				Stage root = (Stage)emailVerifyBtn.getScene().getWindow();
				root.close();
				alert.setTitle("이메일 인증");
			    alert.setHeaderText("이메일 인증 완료");
			    alert.show();
			}
			else {
				alert.setTitle("이메일 인증");
			    alert.setHeaderText("인증번호를 다시 확인해 주세요.");
			    alert.show();
			}
		} catch (Exception e) {
			
		}
	}
}