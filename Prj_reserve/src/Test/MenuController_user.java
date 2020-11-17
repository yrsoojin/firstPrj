package Test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class MenuController_user implements Initializable{
	
	  @FXML private TableView<Food> tableView;
	  @FXML private ImageView imageView;
	  @FXML private Button btnPay;
	  @FXML private Button btnCancel; 
	  @FXML private Button btnAdd;
	  @FXML private TextArea addlist;	
	  @FXML private Label lbTotal;
	  @FXML private TextField tf;
	  Alert alert = new Alert(Alert.AlertType.INFORMATION);
	  
	  private static int total;
	  private int count = 10000;  //�̿밡�ɽð��������ִ� �ݾ�
	
		  
	public static int getTotal() {
		return total;
	}

	public static void setTotal(int total) {
		MenuController_user.total = total;
	}
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		ObservableList<Food> foodlist = FXCollections.observableArrayList(			
				new Food("Coffee", "5000", "coffee.jpg"),
				new Food("Toast", "4000", "toast.jpg"),
				new Food("Salad", "6500", "salad.jpg")						
		);
		
		TableColumn<Food, ?> tcFood = tableView.getColumns().get(0);
		tcFood.setCellValueFactory(new PropertyValueFactory<>("food"));
		
		TableColumn<Food, ?> tcPrice = tableView.getColumns().get(1);
		tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

		tableView.setItems(foodlist);
		
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Food>() {			
			@Override
			public void changed(ObservableValue<? extends Food> observable, Food oldValue, Food newValue) {
				System.out.println("�̹��� ��Ÿ���� ");
				if(newValue != null) {
					imageView.setImage(new Image(getClass().getResource("images/" + newValue.getImage()).toString()));
				}
			}
			
		});
		
		//��ư �̺�Ʈ ó�� 
		btnPay.setOnAction(e -> btnPayHandler(e));
		btnCancel.setOnAction(e -> btnCancelHandler(e));
		btnAdd.setOnAction(e -> btnAddHandler(e));
	}
	
	public void handleTime() {
		System.out.println("�ݾ״� ���� �ð� ��Ÿ����");
		
		if(total>=(count*5)) {
			tf.setText("");
			tf.appendText("�ð��� ������� �̿밡���մϴ�.");			
		}else if(total>=(count*4) ) {
			tf.setText("");
			tf.appendText("�̿밡�ɽð� : 4�ð�+");
		}else if(total>=(count*3)) {
			tf.setText("");
			tf.appendText("�̿밡�ɽð� : 3�ð�+");
		}else if(total>=(count*2)) {
			tf.setText("");
			tf.appendText("�̿밡�ɽð� : 2�ð�+");
		}else if(total>=(count)) {
			tf.setText("");
			tf.appendText("�̿밡�ɽð� : 1�ð�+");
		}	
	}
	
	public void btnAddHandler(ActionEvent e) {
		Food item = tableView.getSelectionModel().getSelectedItem();
		System.out.println("���õ� �޴� : " + item.getFood());
		addlist.appendText(item.getFood().toString() + " " + item.getPrice().toString() + "\n");  //addlist�� ��Ÿ��
		total += Integer.valueOf(item.getPrice());  //total�� ������
		lbTotal.setText(total+"");
		this.handleTime();
	}
	
	
	
	public void btnPayHandler(ActionEvent e) {
		System.out.println("����");
		if(addlist.getText().equals("")) {
			alert.setTitle("");
			alert.setHeaderText("���Ÿ���� Ȯ�����ּ���.");
			alert.show();
		}
		else {
		Stage stg = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("pay.fxml"));
			Scene scene = new Scene(root);
			stg.setScene(scene);
			stg.setTitle("����Ȯ��");
			stg.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		}
	}
	public void btnCancelHandler(ActionEvent e) {
		System.out.println("���");
		if(addlist.getText().isEmpty()) {
			Stage stage = (Stage)btnCancel.getScene().getWindow();
			stage.close();
			System.out.println("���");
		}
		
		tf.setText("");
		addlist.setText("");
		lbTotal.setText("0");
		
	}
	
}
