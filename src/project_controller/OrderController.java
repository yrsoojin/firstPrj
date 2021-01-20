package project_controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import project_constructor.Menu;
import project_dao.MenuDao;
import project_dao.ReserveDao;
import project_dao.UserDao;

public class OrderController extends UserController implements Initializable{
	
	//메뉴주문
	@FXML private TableColumn<Menu,String> menunameTable;
	@FXML private TableColumn<Menu,String> priceTable;
	@FXML private TableColumn<Menu,String> ordernameTable;
	@FXML private TableColumn<Menu,String> ordernumTable;
	@FXML private TableColumn<Menu,String> orderpriceTable;
	@FXML private TableColumn<Menu,String> countTable;
	
	@FXML private ImageView menuimage;
	@FXML private TableView<Menu> menulist;
	@FXML private TableView<Menu> orderlist;
	@FXML private Button addBtn;
	@FXML private Button cancleBtn;
	@FXML private Button payBtn;
	@FXML private Button backBtn;
	@FXML private Label total;
	@FXML private Label time;
	@FXML private Label endTime;
	@FXML private ChoiceBox<String> selectTime;
	
	@FXML private Label selectedTableLabel;
	@FXML private Label selectedMenuLabel;
	@FXML private Label exceptionLabel;
	@FXML private Button tableReserveBtn;
	@FXML private Button reserveListBtn;
	@FXML private Button userUpdate;
	

	public static int getTotalPrice() {
		return totalPrice;
	}



	public static void setTotalPrice(int totalPrice) {
		OrderController.totalPrice = totalPrice;
	}

	//테이블예약
	@FXML Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
	public static int tableNo = 0;
	public static int tableConNo = 0;
	
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	MenuDao mDao = new MenuDao();
	ReserveDao rDao = new ReserveDao();
	UserDao bDao = new UserDao();
	String orderMenu;
	String orderPrice;
	
	static int totalPrice = 0;
	static int confirm = 0;
	
	String selectedMenu;
	String orderNum;
	int selectNum;
	Menu menu;
	static int totalTime;
	static int selTime;
	static ObservableList<Menu> ordersheet = FXCollections.observableArrayList();
	static ObservableList<Menu> emptyList = FXCollections.observableArrayList();
	static ObservableList<Menu> newList = FXCollections.observableArrayList();
	static ObservableList<Menu> Orderlist1 = FXCollections.observableArrayList();
	int count;
	
	ObservableList<String> selectedTime = FXCollections.observableArrayList(
			"10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reserveListBtn.setDisable(true);
		userUpdate.setDisable(true);
		
		btn1.setOnAction(e -> handleBtn1Action(e));
		btn2.setOnAction(e -> handleBtn2Action(e));
		btn3.setOnAction(e -> handleBtn3Action(e));
		btn4.setOnAction(e -> handleBtn4Action(e));
		btn5.setOnAction(e -> handleBtn5Action(e));
		btn6.setOnAction(e -> handleBtn6Action(e));
		btn7.setOnAction(e -> handleBtn7Action(e));
		
		emptyList.add(new Menu()); //빈 리스트 생성
		
		
		menunameTable.setCellValueFactory(cellData -> cellData.getValue().getMenunameProperty());
		priceTable.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
		
		priceTable.setStyle("-fx-alignment : CENTER");
		menulist.setItems(mDao.selectAll());
		
		ordernameTable.setCellValueFactory(cellData -> cellData.getValue().getMenunameProperty());
		orderpriceTable.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
		countTable.setCellValueFactory(cellData -> cellData.getValue().getCount());
		
		orderlist.setItems(emptyList);  //빈테이블
		//orderlist.setItems(orderList);
		
		menulist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Menu>() {
			@Override
			public void changed(ObservableValue<? extends Menu> observable, Menu oldValue, Menu newValue) {
				if(newValue!=null) {				
					menuimage.setImage(new Image(getClass().getResource("../images/"+ newValue.getMenuname() +".jpg").toString()));
					orderMenu = newValue.getMenuname();
					orderPrice = newValue.getPrice();
				}			
			}
		});
		
		
		
		orderlist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Menu>() {
			@Override
			public void changed(ObservableValue<? extends Menu> observable, Menu oldValue, Menu newValue) {
				if(newValue!=null) {
					selectNum = orderlist.getSelectionModel().getSelectedIndex();
					selectedMenu = orderlist.getSelectionModel().getSelectedItem().getMenuname(); 
				}
			}
		});
		
		
		timeVerify();  //불가능한 시간 빼주기 
		selectTime.setItems(selectedTime); //remove된 시간표 다시 choiceBox에 넣어줌 
		
	selectTime.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {  //한정된 시간예약가능 알림문구띄우기
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		if(newValue!=null) {
			timeControl();
			try {
				
	if(totalTime != 1) {
		for(int i=1; i<totalTime; i++) {
			if(Integer.parseInt(selectedTime.get(selectTime.getSelectionModel().getSelectedIndex()).substring(0, 2))+i 
					!= Integer.parseInt(selectedTime.get(selectTime.getSelectionModel().getSelectedIndex()+i).substring(0, 2))) {
				exceptionLabel.setText("우선 예약으로 인해 "
				+ (Integer.parseInt(selectedTime.get(selectTime.getSelectionModel().getSelectedIndex()).substring(0, 2))+i 
							+ ":00 까지 이용가능"));
				break;
			}
			else if(Integer.parseInt(selectedTime.get(selectTime.getSelectionModel().getSelectedIndex()+i).substring(0, 2))>20) {
				exceptionLabel.setText("");
			}
			else {
				exceptionLabel.setText("");
			}
				}
			}
			else {
				exceptionLabel.setText("");
			}
		} catch (Exception e2) {
			exceptionLabel.setText("");
		}
	}
}
		});
	}
	
	
	
	public void handleBtn1Action(ActionEvent e) {

		ordersheet.clear();
		System.out.println("버튼1클릭!!");
		tableConNo=1;
		
		timeVerify();
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../project_fxml/table1.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Table_View");
			stage.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void handleBtn2Action(ActionEvent e) {
		
		ordersheet.clear();
		System.out.println("버튼2클릭");
		tableConNo=2;
		timeVerify();
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../project_fxml/table2.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Table_View");
			stage.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	public void handleBtn3Action(ActionEvent e) {
		ordersheet.clear();
		System.out.println("버튼3클릭");
		tableConNo=3;
		timeVerify();
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../project_fxml/table3.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Table_View");
			stage.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}
	public void handleBtn4Action(ActionEvent e) {
		System.out.println("버튼4클릭");
		
	}
	public void handleBtn5Action(ActionEvent e) {
		System.out.println("버튼5클릭");
		
	}
	public void handleBtn6Action(ActionEvent e) {
		System.out.println("버튼6클릭");
		
	}
	public void handleBtn7Action(ActionEvent e) {
		System.out.println("버튼7클릭");
		
	}
	
	public void timeVerify() {
		
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		System.out.println(rDao.reserveVerify(sdf.format(date), String.valueOf(tableNo)));
		StringTokenizer st = new StringTokenizer(rDao.reserveVerify(sdf.format(date), String.valueOf(tableNo)), "/");
		String[] strStringArr = new String[st.countTokens()];
		for(int i=0; i<strStringArr.length; i++) {
			strStringArr[i] = st.nextToken();
		}
		System.out.println(Arrays.toString(strStringArr));
		
		String[] startArr = new String[strStringArr.length];
		String[] endArr = new String[strStringArr.length];
		
		for(int j=0; j<strStringArr.length; j++) {
			StringTokenizer arrSt = new StringTokenizer(strStringArr[j], "~");
			startArr[j] = arrSt.nextToken();
			endArr[j] = arrSt.nextToken();
		}
		
		System.out.println(Arrays.toString(startArr));
		System.out.println(Arrays.toString(endArr));
		
		
		
		for(int i=0; i<selectedTime.size(); i++) {  //ChoiceBox에서 불가능한 시간 제거하기 
			for(int j=0; j<startArr.length; j++) {
				if(selectedTime.get(i).equals(startArr[j])) {
					selectedTime.remove(i);
					try {
						if(endArr[j].equals("2시간")) {
							selectedTime.remove(i);
						}
						else if(endArr[j].equals("3시간")) {
							selectedTime.remove(i);
							selectedTime.remove(i);
						}
						else if(endArr[j].equals("4시간")) {
							selectedTime.remove(i);
							selectedTime.remove(i);
							selectedTime.remove(i);
						}
						else if(endArr[j].equals("5시간")) {
							selectedTime.remove(i);
							selectedTime.remove(i);
							selectedTime.remove(i);
							selectedTime.remove(i);
						}
					} catch (Exception e) {}
				}
			}
		}
		if(selectedTime.isEmpty()) {
			selectedTime.add("예약 마감               ");
			payBtn.setDisable(true);
		}
		
	}
	
	public void addOrder(ActionEvent e) {    //주문하기 
	      
	      menu = new Menu(orderMenu, orderPrice);
	      
	      ordersheet.add(menu);   //주문을 orderlist에 추가
	      orderlist.setItems(ordersheet); //추가한 주문내역 테이블에 set
	      
	      Orderlist1 = mDao.selectAll();
	      
	      int price =0;
	      newList.clear();
	      for(int i=0; i<Orderlist1.size(); i++) {
	         for(int j=0; j<ordersheet.size(); j++) {         
	            if(Orderlist1.get(i).getMenuname().equals(ordersheet.get(j).getMenuname())) {
	               count++;
	            }
	         }
	         if(count!=0) {
	            price = Integer.parseInt(Orderlist1.get(i).getPrice())*count;
	            Menu menu = new Menu(Orderlist1.get(i).getMenuname(), String.valueOf(price) , String.valueOf(count));
	            try {
	               newList.add(menu);
	               count=0;
	               }catch(Exception e2) {
	                  e2.printStackTrace();
	               }
	         }
	      }
	      orderlist.setItems(newList);
	      
	      
	      totalPrice += Integer.parseInt(orderPrice);
	      total.setText("합계 : " + totalPrice + "원");
	      if(totalPrice >= 4000 && totalPrice < 8000) {
	         time.setText("이용 가능 시간  : 1시간");
	         totalTime=1;
	         if(selectTime.getValue()!=null)
	            timeControl();

	      }
	      else if(totalPrice >= 8000 && totalPrice < 12000) {
	         time.setText("이용 가능 시간  : 2시간");
	         totalTime=2;
	         if(selectTime.getValue()!=null)
	            timeControl();
	      }
	      else if(totalPrice >= 12000 && totalPrice < 16000) {
	         time.setText("이용 가능 시간  : 3시간");
	         totalTime=3;
	         if(selectTime.getValue()!=null)
	            timeControl();
	      }
	      else if(totalPrice >= 16000 && totalPrice < 20000) {
	         time.setText("이용 가능 시간  : 4시간");
	         totalTime=4;
	         if(selectTime.getValue()!=null)
	            timeControl();
	      }
	      else if(totalPrice >= 20000) {
	         time.setText("이용 가능 시간  : 5시간");
	         totalTime=5;
	         if(selectTime.getValue()!=null)
	            timeControl();
	      }
	   }
	   
	   public void delOrder(ActionEvent e) {  //주문메뉴 취소하기 
	      
	      try {
	            for(int i=0; i<ordersheet.size(); i++) {            
	               if(selectedMenu.equals(ordersheet.get(i).getMenuname())){ //메뉴이름을 저장해놓은 String(selectedMenu)을 이용해 주문서의 메뉴이름과 비교
	                  totalPrice -= Integer.parseInt(ordersheet.get(i).getPrice());  
	                  ordersheet.remove(i);
	                  break;
	               }         
	            }
	            Orderlist1 = mDao.selectAll();  //메뉴판에 있는 메뉴, 커피,토스트,샐러드 등.
	            
	            int price =0;
	            newList.clear();
	            for(int i=0; i<Orderlist1.size(); i++) {  //메뉴와 주문서를 비교해서 주문한 만큼 count를 올려줌
	               for(int j=0; j<ordersheet.size(); j++) {         
	                  if(Orderlist1.get(i).getMenuname().equals(ordersheet.get(j).getMenuname())) {
	                     count++;
	                  }
	               }
	               if(count!=0) {
	                  price = Integer.parseInt(Orderlist1.get(i).getPrice())*count;
	                  Menu menu = new Menu(Orderlist1.get(i).getMenuname(), String.valueOf(price) , String.valueOf(count));
	                  try {
	                     newList.add(menu);  //count를 추가한 리스트를 만듬 
	                     count=0;
	                     }catch(Exception e2) {
	                        e2.printStackTrace();
	                     }
	               }
	            }
	            orderlist.setItems(newList);  //테이블 뷰에 새로운 리스트를 넣어줌 
	            
	            if(newList.size()==0) {
	               orderlist.setItems(emptyList); //빈리스트 보여주기 
	            }
	            
	            
	            
	            total.setText("합계 : " + totalPrice + "원");
	            if(totalPrice >= 4000 && totalPrice < 8000) {
	               time.setText("이용 가능 시간  : 1시간");
	               totalTime=1;
	               if(selectTime.getValue()!=null)
	                  timeControl();
	            }
	            else if(totalPrice >= 8000 && totalPrice < 12000) {
	               time.setText("이용 가능 시간  : 2시간");
	               totalTime=2;
	               if(selectTime.getValue()!=null)
	                  timeControl();
	            }
	            else if(totalPrice >= 12000 && totalPrice < 16000) {
	               time.setText("이용 가능 시간  : 3시간");
	               totalTime=3;
	               if(selectTime.getValue()!=null)
	                  timeControl();
	            }
	            else if(totalPrice >= 16000 && totalPrice < 20000) {
	               time.setText("이용 가능 시간  : 4시간");
	               totalTime=4;
	               if(selectTime.getValue()!=null)
	                  timeControl();
	            }
	            else if(totalPrice >= 20000) {
	               time.setText("이용 가능 시간  : 5시간");
	               totalTime=5;
	               if(selectTime.getValue()!=null)
	                  timeControl();
	            }
	            if(ordersheet.isEmpty()) {
	               time.setText("이용 가능 시간  : 0시간");
	               endTime.setText("종료 시간 : 0시");
	            }
	         
	      }catch(Exception e2) {
	         e2.printStackTrace();
	      }
	      
	   }
	   
	public void timeControl() {		//ChoiceBox //사용자가 선택하는 시간을 바탕으로 totalTime을 체크해서 종료시간setText
		if(selectTime.getValue().equals("10:00")) {
			selTime=10;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 11시");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 12시");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 13시");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 14시");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 15시");
			}
		}
		
		else if(selectTime.getValue().equals("11:00")) {
			selTime=11;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 12시");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 13시");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 14시");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 15시");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 16시");
			}
		}
		
		else if(selectTime.getValue().equals("12:00")) {
			selTime=12;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 13시");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 14시");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 15시");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 16시");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 17시");
			}
		}
		
		else if(selectTime.getValue().equals("13:00")) {
			selTime=13;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 14시");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 15시");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 16시");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 17시");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 18시");
			}
		}
		
		else if(selectTime.getValue().equals("14:00")) {
			selTime=14;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 15시");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 16시");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 17시");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 18시");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 19시");
			}
		}
		
		else if(selectTime.getValue().equals("15:00")) {
			selTime=15;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 16시");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 17시");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 18시");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 19시");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 20시");
			}
		}
		
		else if(selectTime.getValue().equals("16:00")) {
			selTime=16;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 17시");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 18시");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 19시");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 20시");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
		}
		
		else if(selectTime.getValue().equals("17:00")) {
			selTime=17;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 18시");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 19시");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 20시");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
		}
		
		else if(selectTime.getValue().equals("18:00")) {
			selTime=18;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 19시");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 20시");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
		}
		
		else if(selectTime.getValue().equals("19:00")) {
			selTime=19;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 20시");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
		}
		
		else if(selectTime.getValue().equals("20:00")) {
			selTime=20;
			if(totalTime==1) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
			else if(totalTime==2) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
			else if(totalTime==3) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
			else if(totalTime==4) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
			else if(totalTime==5) {
				endTime.setText("종료 시간 : 영업마감까지");
			}
		}
	}
	
	public void pay(ActionEvent e) {   //결제 
		
			Stage stage = new Stage();
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../project_fxml/orderConfirm.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
				stage.setTitle("예약 완료 및 결제");	
			} catch (IOException e2) { e2.printStackTrace(); }
		}
		
		
		
	
	
	public void back(ActionEvent e) {    //돌아가기
		
		ordersheet.clear();
		totalPrice = 0;
		Stage root = (Stage)backBtn.getScene().getWindow();
		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("../project_fxml/user.fxml"));
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("User.css").toString());
			root.setScene(scene);
			root.setResizable(false);
			root.show();
			root.setTitle("사용자 모드");
		} catch (Exception e2) {e2.printStackTrace();
		}
	}
}