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
	
	//�޴��ֹ�
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

	//���̺���
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
		
		emptyList.add(new Menu()); //�� ����Ʈ ����
		
		
		menunameTable.setCellValueFactory(cellData -> cellData.getValue().getMenunameProperty());
		priceTable.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
		
		priceTable.setStyle("-fx-alignment : CENTER");
		menulist.setItems(mDao.selectAll());
		
		ordernameTable.setCellValueFactory(cellData -> cellData.getValue().getMenunameProperty());
		orderpriceTable.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
		countTable.setCellValueFactory(cellData -> cellData.getValue().getCount());
		
		orderlist.setItems(emptyList);  //�����̺�
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
		
		
		timeVerify();  //�Ұ����� �ð� ���ֱ� 
		selectTime.setItems(selectedTime); //remove�� �ð�ǥ �ٽ� choiceBox�� �־��� 
		
	selectTime.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {  //������ �ð����డ�� �˸���������
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		if(newValue!=null) {
			timeControl();
			try {
				
	if(totalTime != 1) {
		for(int i=1; i<totalTime; i++) {
			if(Integer.parseInt(selectedTime.get(selectTime.getSelectionModel().getSelectedIndex()).substring(0, 2))+i 
					!= Integer.parseInt(selectedTime.get(selectTime.getSelectionModel().getSelectedIndex()+i).substring(0, 2))) {
				exceptionLabel.setText("�켱 �������� ���� "
				+ (Integer.parseInt(selectedTime.get(selectTime.getSelectionModel().getSelectedIndex()).substring(0, 2))+i 
							+ ":00 ���� �̿밡��"));
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
		System.out.println("��ư1Ŭ��!!");
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
		System.out.println("��ư2Ŭ��");
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
		System.out.println("��ư3Ŭ��");
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
		
		
		
		for(int i=0; i<selectedTime.size(); i++) {  //ChoiceBox���� �Ұ����� �ð� �����ϱ� 
			for(int j=0; j<startArr.length; j++) {
				if(selectedTime.get(i).equals(startArr[j])) {
					selectedTime.remove(i);
					try {
						if(endArr[j].equals("2�ð�")) {
							selectedTime.remove(i);
						}
						else if(endArr[j].equals("3�ð�")) {
							selectedTime.remove(i);
							selectedTime.remove(i);
						}
						else if(endArr[j].equals("4�ð�")) {
							selectedTime.remove(i);
							selectedTime.remove(i);
							selectedTime.remove(i);
						}
						else if(endArr[j].equals("5�ð�")) {
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
			selectedTime.add("���� ����               ");
			payBtn.setDisable(true);
		}
		
	}
	
	public void addOrder(ActionEvent e) {    //�ֹ��ϱ� 
	      
	      menu = new Menu(orderMenu, orderPrice);
	      
	      ordersheet.add(menu);   //�ֹ��� orderlist�� �߰�
	      orderlist.setItems(ordersheet); //�߰��� �ֹ����� ���̺� set
	      
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
	      total.setText("�հ� : " + totalPrice + "��");
	      if(totalPrice >= 4000 && totalPrice < 8000) {
	         time.setText("�̿� ���� �ð�  : 1�ð�");
	         totalTime=1;
	         if(selectTime.getValue()!=null)
	            timeControl();

	      }
	      else if(totalPrice >= 8000 && totalPrice < 12000) {
	         time.setText("�̿� ���� �ð�  : 2�ð�");
	         totalTime=2;
	         if(selectTime.getValue()!=null)
	            timeControl();
	      }
	      else if(totalPrice >= 12000 && totalPrice < 16000) {
	         time.setText("�̿� ���� �ð�  : 3�ð�");
	         totalTime=3;
	         if(selectTime.getValue()!=null)
	            timeControl();
	      }
	      else if(totalPrice >= 16000 && totalPrice < 20000) {
	         time.setText("�̿� ���� �ð�  : 4�ð�");
	         totalTime=4;
	         if(selectTime.getValue()!=null)
	            timeControl();
	      }
	      else if(totalPrice >= 20000) {
	         time.setText("�̿� ���� �ð�  : 5�ð�");
	         totalTime=5;
	         if(selectTime.getValue()!=null)
	            timeControl();
	      }
	   }
	   
	   public void delOrder(ActionEvent e) {  //�ֹ��޴� ����ϱ� 
	      
	      try {
	            for(int i=0; i<ordersheet.size(); i++) {            
	               if(selectedMenu.equals(ordersheet.get(i).getMenuname())){ //�޴��̸��� �����س��� String(selectedMenu)�� �̿��� �ֹ����� �޴��̸��� ��
	                  totalPrice -= Integer.parseInt(ordersheet.get(i).getPrice());  
	                  ordersheet.remove(i);
	                  break;
	               }         
	            }
	            Orderlist1 = mDao.selectAll();  //�޴��ǿ� �ִ� �޴�, Ŀ��,�佺Ʈ,������ ��.
	            
	            int price =0;
	            newList.clear();
	            for(int i=0; i<Orderlist1.size(); i++) {  //�޴��� �ֹ����� ���ؼ� �ֹ��� ��ŭ count�� �÷���
	               for(int j=0; j<ordersheet.size(); j++) {         
	                  if(Orderlist1.get(i).getMenuname().equals(ordersheet.get(j).getMenuname())) {
	                     count++;
	                  }
	               }
	               if(count!=0) {
	                  price = Integer.parseInt(Orderlist1.get(i).getPrice())*count;
	                  Menu menu = new Menu(Orderlist1.get(i).getMenuname(), String.valueOf(price) , String.valueOf(count));
	                  try {
	                     newList.add(menu);  //count�� �߰��� ����Ʈ�� ���� 
	                     count=0;
	                     }catch(Exception e2) {
	                        e2.printStackTrace();
	                     }
	               }
	            }
	            orderlist.setItems(newList);  //���̺� �信 ���ο� ����Ʈ�� �־��� 
	            
	            if(newList.size()==0) {
	               orderlist.setItems(emptyList); //�󸮽�Ʈ �����ֱ� 
	            }
	            
	            
	            
	            total.setText("�հ� : " + totalPrice + "��");
	            if(totalPrice >= 4000 && totalPrice < 8000) {
	               time.setText("�̿� ���� �ð�  : 1�ð�");
	               totalTime=1;
	               if(selectTime.getValue()!=null)
	                  timeControl();
	            }
	            else if(totalPrice >= 8000 && totalPrice < 12000) {
	               time.setText("�̿� ���� �ð�  : 2�ð�");
	               totalTime=2;
	               if(selectTime.getValue()!=null)
	                  timeControl();
	            }
	            else if(totalPrice >= 12000 && totalPrice < 16000) {
	               time.setText("�̿� ���� �ð�  : 3�ð�");
	               totalTime=3;
	               if(selectTime.getValue()!=null)
	                  timeControl();
	            }
	            else if(totalPrice >= 16000 && totalPrice < 20000) {
	               time.setText("�̿� ���� �ð�  : 4�ð�");
	               totalTime=4;
	               if(selectTime.getValue()!=null)
	                  timeControl();
	            }
	            else if(totalPrice >= 20000) {
	               time.setText("�̿� ���� �ð�  : 5�ð�");
	               totalTime=5;
	               if(selectTime.getValue()!=null)
	                  timeControl();
	            }
	            if(ordersheet.isEmpty()) {
	               time.setText("�̿� ���� �ð�  : 0�ð�");
	               endTime.setText("���� �ð� : 0��");
	            }
	         
	      }catch(Exception e2) {
	         e2.printStackTrace();
	      }
	      
	   }
	   
	public void timeControl() {		//ChoiceBox //����ڰ� �����ϴ� �ð��� �������� totalTime�� üũ�ؼ� ����ð�setText
		if(selectTime.getValue().equals("10:00")) {
			selTime=10;
			if(totalTime==1) {
				endTime.setText("���� �ð� : 11��");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : 12��");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : 13��");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : 14��");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : 15��");
			}
		}
		
		else if(selectTime.getValue().equals("11:00")) {
			selTime=11;
			if(totalTime==1) {
				endTime.setText("���� �ð� : 12��");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : 13��");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : 14��");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : 15��");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : 16��");
			}
		}
		
		else if(selectTime.getValue().equals("12:00")) {
			selTime=12;
			if(totalTime==1) {
				endTime.setText("���� �ð� : 13��");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : 14��");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : 15��");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : 16��");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : 17��");
			}
		}
		
		else if(selectTime.getValue().equals("13:00")) {
			selTime=13;
			if(totalTime==1) {
				endTime.setText("���� �ð� : 14��");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : 15��");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : 16��");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : 17��");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : 18��");
			}
		}
		
		else if(selectTime.getValue().equals("14:00")) {
			selTime=14;
			if(totalTime==1) {
				endTime.setText("���� �ð� : 15��");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : 16��");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : 17��");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : 18��");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : 19��");
			}
		}
		
		else if(selectTime.getValue().equals("15:00")) {
			selTime=15;
			if(totalTime==1) {
				endTime.setText("���� �ð� : 16��");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : 17��");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : 18��");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : 19��");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : 20��");
			}
		}
		
		else if(selectTime.getValue().equals("16:00")) {
			selTime=16;
			if(totalTime==1) {
				endTime.setText("���� �ð� : 17��");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : 18��");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : 19��");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : 20��");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : ������������");
			}
		}
		
		else if(selectTime.getValue().equals("17:00")) {
			selTime=17;
			if(totalTime==1) {
				endTime.setText("���� �ð� : 18��");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : 19��");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : 20��");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : ������������");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : ������������");
			}
		}
		
		else if(selectTime.getValue().equals("18:00")) {
			selTime=18;
			if(totalTime==1) {
				endTime.setText("���� �ð� : 19��");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : 20��");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : ������������");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : ������������");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : ������������");
			}
		}
		
		else if(selectTime.getValue().equals("19:00")) {
			selTime=19;
			if(totalTime==1) {
				endTime.setText("���� �ð� : 20��");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : ������������");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : ������������");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : ������������");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : ������������");
			}
		}
		
		else if(selectTime.getValue().equals("20:00")) {
			selTime=20;
			if(totalTime==1) {
				endTime.setText("���� �ð� : ������������");
			}
			else if(totalTime==2) {
				endTime.setText("���� �ð� : ������������");
			}
			else if(totalTime==3) {
				endTime.setText("���� �ð� : ������������");
			}
			else if(totalTime==4) {
				endTime.setText("���� �ð� : ������������");
			}
			else if(totalTime==5) {
				endTime.setText("���� �ð� : ������������");
			}
		}
	}
	
	public void pay(ActionEvent e) {   //���� 
		
			Stage stage = new Stage();
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../project_fxml/orderConfirm.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
				stage.setTitle("���� �Ϸ� �� ����");	
			} catch (IOException e2) { e2.printStackTrace(); }
		}
		
		
		
	
	
	public void back(ActionEvent e) {    //���ư���
		
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
			root.setTitle("����� ���");
		} catch (Exception e2) {e2.printStackTrace();
		}
	}
}