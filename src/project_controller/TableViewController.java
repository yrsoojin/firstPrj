package project_controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TableViewController extends OrderController implements Initializable{

	@FXML Button btnReserve, btnCancel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnReserve.setOnAction(e ->btnReserve(e));
		btnCancel.setOnAction(e ->handleXBtnAction(e));
			
	}
	

	public void btnReserve(ActionEvent e) {
			
		for(int i=1; i<8; i++) {  //���̺� �ѹ� ���� ������ �ٲ��ֱ� 
			if(tableConNo==i) {
				tableNo=i;
				setTotalPrice(0);
				//System.out.println(getTotalPrice()); totalPrice�� ����� 0�̵ƴ��� Ȯ��
				
			}
		}
		Stage root = (Stage)btnCancel.getScene().getWindow();
		root.close();
	}
		
	public void handleXBtnAction(ActionEvent e) {
		Stage root = (Stage)btnCancel.getScene().getWindow();
		root.close();
	}
}



