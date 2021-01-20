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
		String host = "smtp.naver.com"; // ���̹��� ��� ���̹� ����, gmail��� gmail ���� 
	    String user = "lounge_cu@naver.com";  //������ ����� ���� ����
	    String password = "gksdldma!!";   // �н�����
	      //SMTP ���� ������ �����Ѵ�. 
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
	       //������ ��� ����
	       message.setFrom(new InternetAddress(user)); 
	       //�޴� ��� ����          
	       message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailFieldText));         
	       // ���� ����
	       message.setSubject("ȸ������ �̸��� ����");
	       // ���� ���� 
	       message.setText("ooī�� ���� ���� ȸ������ \n\n"+nameFieldText+" ȸ������ �̸����� �´��� Ȯ���� �ּ���.\n\n�����Ͻ� ���̵�� "+idFieldText+" �Դϴ�.\n\n"
	    		   			+nameFieldText+" ȸ������ ������ �Ʒ��� ������ȣ�� ������ȣ �Է�â�� �Է��� �ּ���.\n\n������ȣ : " + random);
	       
	       Transport.send(message); 
	       emailsend = true;
	       System.out.println("���� ������ ����"); 
	       
	       } catch (MessagingException e1) { e1.printStackTrace(); } 
	}
	
	public void verifyOnAction(ActionEvent event) {
		try {
			if(random == Integer.parseInt(emailVerifyField.getText())) {
				Stage root = (Stage)emailVerifyBtn.getScene().getWindow();
				root.close();
				alert.setTitle("�̸��� ����");
			    alert.setHeaderText("�̸��� ���� �Ϸ�");
			    alert.show();
			}
			else {
				alert.setTitle("�̸��� ����");
			    alert.setHeaderText("������ȣ�� �ٽ� Ȯ���� �ּ���.");
			    alert.show();
			}
		} catch (Exception e) {
			
		}
	}
}