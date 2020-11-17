package Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
//import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

@SuppressWarnings("unused")
public class UserController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}	
	
	public void reserveTable(ActionEvent event) {
		Stage stage = new Stage();		
		Parent root;
		try {	
			root = FXMLLoader.load(getClass().getResource("table.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			stage.setTitle("테이블");	
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	
	public void showReservation(ActionEvent event) {

	}
	
	public void userUpdate(ActionEvent event) {
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("userUpdate.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			stage.setTitle("회원 정보 수정");	
		} catch (IOException e) { e.printStackTrace(); }
	}

}