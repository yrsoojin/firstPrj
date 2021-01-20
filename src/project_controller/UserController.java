package project_controller;

import java.io.IOException;
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

public class UserController extends LoginController implements Initializable{

	@FXML private Button tableReserveBtn;
	@FXML private Button reserveListBtn;
	@FXML private Button userUpdateBtn;
	@FXML private Button logoutBtn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}	
	
	public void logout(ActionEvent event) {
		
		Stage root = (Stage)logoutBtn.getScene().getWindow();
		root.close();
		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("../project_fxml/login.fxml"));
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("../project/Main.css").toString());
			root.setScene(scene);
			root.setResizable(false);
			root.show();
			root.setTitle("로그인");
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	
	
	public void reserveTable(ActionEvent event) {
		Stage root = (Stage)tableReserveBtn.getScene().getWindow();
		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("../project_fxml/order.fxml"));
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("Order.css").toString());
			root.setScene(scene);
			root.setResizable(false);
			root.show();
			root.setTitle("테이블 예약 - " + id);
		} catch (IOException e) { e.printStackTrace(); }
		}

	
	
	public void showReservation(ActionEvent event) {
		Stage root = (Stage)reserveListBtn.getScene().getWindow();
		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("../project_fxml/userReserveList.fxml"));
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("Reservelist.css").toString());
			root.setScene(scene);
			root.setResizable(false);
			root.show();
			root.setTitle("예약 내역 조회 - "+id);
		} catch (IOException e) { e.printStackTrace(); }
	}

	public void userUpdate(ActionEvent event) {
		Stage root = (Stage)userUpdateBtn.getScene().getWindow();
		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("../project_fxml/userUpdate.fxml"));
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("UserUpdate.css").toString());
			root.setScene(scene);
			root.setResizable(false);
			root.show();
			root.setTitle("회원 정보 수정 - "+id);
		} catch (IOException e) { e.printStackTrace(); }
	}
}