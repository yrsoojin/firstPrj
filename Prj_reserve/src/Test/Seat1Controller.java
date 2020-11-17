package Test;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.corba.se.pept.transport.EventHandler;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Seat1Controller implements Initializable{

	@FXML Button btnReserve, btnCancel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnReserve.setOnAction(e ->handleBtnReserveAction(e));
		btnCancel.setOnAction(e ->handleXBtnAction(e));		
	}
	
	Stage stage = new Stage();
	public void handleBtnReserveAction(ActionEvent e) {
		System.out.println("�����ư Ŭ��");
		MenuController_user mcu = new MenuController_user();
		
		Parent root;
		try {
			MenuController_user.setTotal(0);
			root = FXMLLoader.load(getClass().getResource("menu.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Menu&Order");
			stage.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}
	public void handleXBtnAction(ActionEvent e) {
		System.out.println("�ڷΰ��� Ŭ��");
		Stage stage = (Stage)btnCancel.getScene().getWindow();
		stage.close();
	}
}
