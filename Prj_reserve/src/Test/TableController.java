package Test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TableController implements Initializable{

	@FXML Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("������");
		btn1.setOnAction(e -> handleBtn1Action(e));
		btn2.setOnAction(e -> handleBtn2Action(e));
		btn3.setOnAction(e -> handleBtn3Action(e));
		btn4.setOnAction(e -> handleBtn4Action(e));
		btn5.setOnAction(e -> handleBtn5Action(e));
		btn6.setOnAction(e -> handleBtn6Action(e));
		btn7.setOnAction(e -> handleBtn7Action(e));
		
	}
	
	
	public void handleBtn1Action(ActionEvent e) {
		System.out.println("��ư1Ŭ��");
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("seat.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Table_View");
			stage.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		 
	}
	public void handleBtn2Action(ActionEvent e) {
		System.out.println("��ư2Ŭ��");
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("seat2.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Table_View");
			stage.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	public void handleBtn3Action(ActionEvent e) {
		System.out.println("��ư3Ŭ��");
		
	}
	public void handleBtn4Action(ActionEvent e) {
		System.out.println("��ư4Ŭ��");
		
	}
	public void handleBtn5Action(ActionEvent e) {
		System.out.println("��ư5Ŭ��");
		
	}
	public void handleBtn6Action(ActionEvent e) {
		System.out.println("��ư6Ŭ��");
		
	}
	public void handleBtn7Action(ActionEvent e) {
		System.out.println("��ư7Ŭ��");
		
	}
	
	
		
}
