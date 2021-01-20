package project_controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import project_constructor.Reserve;
import project_dao.ReserveDao;
import project_dao.UserDao;


public class OrderConfirmController extends OrderController implements Initializable {
   
   @FXML private Label timeReserve;
   @FXML private Label priceReserve;
   @FXML private Button reserveBtn;
   @FXML private TextField moneyField;
   @FXML private ImageView imageView;
   ReserveDao rDao = new ReserveDao();
   UserDao uDao = new UserDao();
   Alert alert = new Alert(Alert.AlertType.INFORMATION);
   
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      timeReserve.setText("이용 시작 시간 : " + selTime+ ":00");
      priceReserve.setText("결제금액 : "+totalPrice+"원");
   }   
   

  
   
   
   //확인 누를시 ReserveDao를 통해 예약내용이 저장됨
   public void handleBtnReserveAction(ActionEvent event) {
	  if(moneyField.getText().equals(String.valueOf(totalPrice))) {
	      SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMdd");
	      Date date = new Date();
	      SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
	      String nowDate = sdf1.format(date);
	      String timeNow = sdf.format(date);
	      String startTime = String.valueOf(selTime)+":00";
	      String getTime = String.valueOf(totalTime)+"시간";
	      String reserveMenu = "";
	      
	      String image = "seat" + tableNo + ".jpg";
	      
	      for(int i=0; i<ordersheet.size(); i++) {
	    	  if(i==ordersheet.size()-1) {
	    		  reserveMenu += ordersheet.get(i).getMenuname();
	    	  }
	    	  else 
	    		  reserveMenu += ordersheet.get(i).getMenuname()+", ";
	      }
	      String price = String.valueOf(totalPrice);
	      
	      
	      String username = uDao.getName(id);
	      
	      Reserve reserve = new Reserve(nowDate, id, username, String.valueOf(tableNo), timeNow, 
	            startTime, getTime, reserveMenu, price, image);
	      rDao.reserveInsert(reserve);
	      
	      Stage root = (Stage)reserveBtn.getScene().getWindow();
	      root.close();
	      
	      alert.setTitle("예약 완료");
	       alert.setHeaderText("예약 완료");
	       alert.show();
	  }
   
	  else {
			alert.setTitle("결제 확인");
		    alert.setHeaderText("결제 금액을 확인해 주세요.");
		    alert.show();
	  }
   }
}