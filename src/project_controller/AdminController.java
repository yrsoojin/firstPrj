package project_controller;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import net.halowd.saveImg.SaveImg;
import project_constructor.Menu;
import project_constructor.Reserve;
import project_constructor.User;
import project_dao.MenuDao;
import project_dao.ReserveDao;
import project_dao.UserDao;

public class AdminController implements Initializable {
   
   //���̺� ȯ�漳��
   @FXML private ImageView imageView;
   @FXML private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
   @FXML private Button changePic;

   
   //ȸ������
   @FXML private TableColumn<User,String> idTable;
   @FXML private TableColumn<User,String> nameTable;
   @FXML private TableColumn<User,String> emailTable;
   @FXML private TableColumn<User,String> reservDate;
   @FXML private TableColumn<User,String> phoneTable;
   @FXML private Button search;
   @FXML private TableView<User> table;
   @FXML private Button searchOne;
   @FXML private TextField searchId;
   
   //�޴�����
   @FXML private TableColumn<Menu,String> menunameTable;
   @FXML private TableColumn<Menu,String> priceTable;
   @FXML private TableColumn<Menu,String> ordernameTable;
   @FXML private TableColumn<Menu,String> ordernumTable;
   @FXML private TableColumn<Menu,String> orderpriceTable;
   @FXML private ImageView menuimage;
   @FXML private TableView<Menu> menulist;
   @FXML private TableView<Menu> orderlist;
   @FXML private Button addBtn;
   @FXML private Button cancleBtn;
   @FXML private Button payBtn;
   @FXML private Button backBtn;
   @FXML private Label total;
   @FXML private Label time;
   
   //�������
   @FXML private TableView<Reserve> reserveTableView;
   @FXML private TableColumn<Reserve,String> dateResTable;
   @FXML private TableColumn<Reserve,String> idResTable;
   @FXML private TableColumn<Reserve,String> nameResTable;
   @FXML private TableColumn<Reserve,String> tableResTable;
   @FXML private TableColumn<Reserve,String> reserveTimeResTable;
   @FXML private TableColumn<Reserve,String> startTimeResTable;
   @FXML private TableColumn<Reserve,String> getTimeResTable;
   @FXML private TableColumn<Reserve,String> orderMenuResTable;
   @FXML private TableColumn<Reserve,String> priceResTable;
   @FXML private Button searchReserve;
   @FXML private Button refreshBtn;
   @FXML private DatePicker datePicker;
   
   //�������
   @FXML private TableView<project_constructor.total> totalTableView;
   @FXML private TableColumn<project_constructor.total, ?> dateCol;
   @FXML private TableColumn<project_constructor.total, ?> totalCol;
	   
   @FXML private DatePicker datePick;
   @FXML private LineChart<String, Number> lineChart;
   @FXML private LineChart<String, Number> lineChart1;
   @FXML private CategoryAxis xAxis;
   @FXML private CategoryAxis xAxis2;
    XYChart.Series<String, Number> series = null;
    XYChart.Series<String, Number> series1 = null;
    ObservableList<String> xLables = FXCollections.observableArrayList();
   
   
   Alert alert = new Alert(Alert.AlertType.INFORMATION);
   MenuDao mDao = new MenuDao();
   UserDao bDao = new UserDao();
   ReserveDao rDao = new ReserveDao();
   String orderMenu;
   String orderPrice;
   static String selectedMenu;
   
   
   String orderNum;
   int selectNum;
   Menu menu;
   ObservableList<Menu> orderList = FXCollections.observableArrayList();
   
   Date date;
   String seatName;
   String dataStr;
   String dataStr1;
   
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      //���̺����
      btn1.setOnAction(e -> handleBtn1Action(e));
      btn2.setOnAction(e -> handleBtn2Action(e));
      btn3.setOnAction(e -> handleBtn3Action(e));
//      btn4.setOnAction(e -> handleBtn4Action(e));
//      btn5.setOnAction(e -> handleBtn5Action(e));
//      btn6.setOnAction(e -> handleBtn6Action(e));
//      btn7.setOnAction(e -> handleBtn7Action(e));
      
      refreshBtn.setOnAction(e -> handleRfresh(e));
      
      
      //���⳻������
    
      //System.out.println(rDao.profit("201207"));
            
      datePick.valueProperty().addListener(new ChangeListener<LocalDate>() {
         @Override
         public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
               LocalDate newValue) {
         
            dataStr = datePick.getValue().toString();
            dataStr = dataStr.replaceAll("-", "");
            dataStr = dataStr.substring(2, 8);
           
            drawChart();
         }
      });
      
     
      //ȸ������
      idTable.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
      nameTable.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
      emailTable.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
      phoneTable.setCellValueFactory(cellData -> cellData.getValue().getPhoneProperty());
      phoneTable.setStyle("-fx-alignment : CENTER");
      reservDate.setCellValueFactory(cellData -> cellData.getValue().getReservDateProperty());
      reservDate.setStyle("-fx-alignment : CENTER");
      
      //�޴�����
      menuimage.setImage(new Image(getClass().getResource("../images/null.png").toString()));
      menunameTable.setCellValueFactory(cellData -> cellData.getValue().getMenunameProperty());
      priceTable.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
      priceTable.setStyle("-fx-alignment : CENTER");
      menulist.setItems(mDao.selectAll());

      menulist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Menu>() {
         @Override
         public void changed(ObservableValue<? extends Menu> observable, Menu oldValue, Menu newValue) {
            if(newValue!=null) {
               selectedMenu = menulist.getSelectionModel().getSelectedItem().getMenuname();
               menuimage.setImage(new Image(getClass().getResource("../images/"+ newValue.getMenuname() +".jpg").toString()));
            }
            
         }
      });
      
      //���೻����ȸ
      dateResTable.setCellValueFactory(cellData -> cellData.getValue().getNowDateProperty());
      dateResTable.setStyle("-fx-alignment : CENTER");
      idResTable.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
      nameResTable.setCellValueFactory(cellData -> cellData.getValue().getUsernameProperty());
      tableResTable.setCellValueFactory(cellData -> cellData.getValue().getTableNoProperty());
      tableResTable.setStyle("-fx-alignment : CENTER");
      reserveTimeResTable.setCellValueFactory(cellData -> cellData.getValue().getPayTimeProperty());
      reserveTimeResTable.setStyle("-fx-alignment : CENTER");
      startTimeResTable.setCellValueFactory(cellData -> cellData.getValue().getStartTimeProperty());
      startTimeResTable.setStyle("-fx-alignment : CENTER");
      getTimeResTable.setCellValueFactory(cellData -> cellData.getValue().getGetTimeProperty());
      getTimeResTable.setStyle("-fx-alignment : CENTER");
      orderMenuResTable.setCellValueFactory(cellData -> cellData.getValue().getReserveMenuProperty());
      priceResTable.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
      priceResTable.setStyle("-fx-alignment : CENTER");
      
      datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
         @Override
         public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
               LocalDate newValue) {
            dataStr = datePicker.getValue().toString();
            dataStr = dataStr.replaceAll("-", "");
            dataStr = dataStr.substring(2, 8);
            reserveTableView.setItems(rDao.selectOneDate(dataStr));
         }
      });
   }
   
   public void drawChart() {
      series = new XYChart.Series<String, Number>();
      lineChart.getData().clear();
      
      SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
       Date date = new Date();
       System.out.println(dataStr);
       date.setDate(Integer.parseInt(dataStr.substring(4, 6)));
       date.setMonth(Integer.parseInt(dataStr.substring(2, 4))-1);
       date.setYear(Integer.parseInt(dataStr.substring(0, 2)));
       
       String nowDate = sdf.format(date);
       System.out.println(nowDate);
      
      Date date_1 = new Date();  //�Ϸ��� 
       date_1.setDate(date.getDate()-1);
       String nowDate_1 = sdf.format(date_1);
               
      Date date_2 = new Date();   //2���� 
       date_2.setDate(date.getDate()-2);
       String nowDate_2 = sdf.format(date_2);
         
         Date date_3 = new Date();
         date_3.setDate(date.getDate()-3);
         String nowDate_3 = sdf.format(date_3);
                  
         Date date_4 = new Date();
         date_4.setDate(date.getDate()-4);
         String nowDate_4 = sdf.format(date_4);
               
         Date date_5 = new Date();
         date_5.setDate(date.getDate()-5);
         String nowDate_5 = sdf.format(date_5);
                  
         Date date_6 = new Date();
         date_6.setDate(date.getDate()-6);
         String nowDate_6 = sdf.format(date_6);
      
         series.getData().add(new XYChart.Data<String, Number> (nowDate_6, Integer.parseInt(rDao.profit(nowDate_6)))); //6����//������ 
         series.getData().add(new XYChart.Data<String, Number> (nowDate_5, Integer.parseInt(rDao.profit(nowDate_5)))); //5���� 
         series.getData().add(new XYChart.Data<String, Number> (nowDate_4, Integer.parseInt(rDao.profit(nowDate_4)))); //4����
         series.getData().add(new XYChart.Data<String, Number> (nowDate_3, Integer.parseInt(rDao.profit(nowDate_3)))); //3���� 
         series.getData().add(new XYChart.Data<String, Number> (nowDate_2, Integer.parseInt(rDao.profit(nowDate_2))));  //2���� 
         series.getData().add(new XYChart.Data<String, Number> (nowDate_1, Integer.parseInt(rDao.profit(nowDate_1)))); //1���� 
         series.getData().add(new XYChart.Data<String, Number> (nowDate, Integer.parseInt(rDao.profit(nowDate)))); //���� ��¥ 
      
         
         ObservableList<project_constructor.total> stringList = FXCollections.observableArrayList(
 				new project_constructor.total(nowDate_6, rDao.profit(nowDate_6)),
 				new project_constructor.total(nowDate_5, rDao.profit(nowDate_5)),
 				new project_constructor.total(nowDate_4, rDao.profit(nowDate_4)),
 				new project_constructor.total(nowDate_3, rDao.profit(nowDate_3)),
 				new project_constructor.total(nowDate_2, rDao.profit(nowDate_2)),
 				new project_constructor.total(nowDate_1, rDao.profit(nowDate_1)),
 				new project_constructor.total(nowDate, rDao.profit(nowDate))
 				);
 		
 		dateCol = totalTableView.getColumns().get(0);
 		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
 		dateCol.setStyle("-fx-alignment : CENTER");
 		totalCol = totalTableView.getColumns().get(1);
 		totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
 		totalCol.setStyle("-fx-alignment : CENTER");
 		totalTableView.setItems(stringList);
 		                       
      series.setName("�ֺ� �����");
      lineChart.getData().add(series);   
      //-------------------------------------------------------------���� �����, �ֺ� ����� �׷���+���̺�� 
      
      series1 = new XYChart.Series<String, Number>();
      lineChart1.getData().clear();
      
      Calendar today = Calendar.getInstance();
      System.out.println(" �⵵ : " + today.get(Calendar.YEAR));
      String year = String.valueOf(today.get(Calendar.YEAR)).substring(2, 4);
      System.out.println(year);
      
         series1.getData().add(new XYChart.Data<String, Number> ("1��", Integer.parseInt(rDao.profitMonth(year+"01")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("2��", Integer.parseInt(rDao.profitMonth(year+"02"))));  //
         series1.getData().add(new XYChart.Data<String, Number> ("3��", Integer.parseInt(rDao.profitMonth(year+"03")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("4��", Integer.parseInt(rDao.profitMonth(year+"04")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("5��", Integer.parseInt(rDao.profitMonth(year+"05"))));  //
         series1.getData().add(new XYChart.Data<String, Number> ("6��", Integer.parseInt(rDao.profitMonth(year+"06")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("7��", Integer.parseInt(rDao.profitMonth(year+"07")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("8��", Integer.parseInt(rDao.profitMonth(year+"08"))));  //
         series1.getData().add(new XYChart.Data<String, Number> ("9��", Integer.parseInt(rDao.profitMonth(year+"09")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("10��", Integer.parseInt(rDao.profitMonth(year+"10")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("11��", Integer.parseInt(rDao.profitMonth(year+"11")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("12��", Integer.parseInt(rDao.profitMonth(year+"12")))); //12�� 
         
        
      series1.setName("���� �����");
      lineChart1.getData().add(series1);
      //---------------------------------------------------------------���� ����� 
   }
   
   
   
   
   public void fileChoose(ActionEvent e) throws Exception{
      FileChooser fc = new FileChooser();
        fc.setTitle("�̹��� ����");
        fc.setInitialDirectory(new File("C:/"));
        ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg", "*.gif", "*.png");
        fc.getExtensionFilters().add(imgType);
        File selectedFile =  fc.showOpenDialog(null); //������ ����
       
        String str = seatName + ".jpg";
        
        if(selectedFile!=null) {
           File name = new File("../Project/src/images/" + str );          
           name.delete();
           
           SaveImg saveImg = new SaveImg();  //URL�� ���� �̹����� �����ϱ� ���� SaveImg
         
           String file = selectedFile.toURI().toString();           
           String path = "../Project/src/images";
         
         try {
            int result = saveImg.saveImgFromUrl(file, path); //������ file�� path�� �ְڴ�
            
            if (result == 1) {
               
               System.out.println("����Ȱ�� : " + saveImg.getPath());
               System.out.println("����������̸� : " + saveImg.getSavedFileName());
               Thread.sleep(3000);
               imageView.setImage(new Image(getClass().getResource("../images/" + seatName + ".jpg").toString()));
            }
         } catch (IOException e2) {
            e2.printStackTrace();
         }
           }
        
   
   }

   public void handleRfresh(ActionEvent event) {
      System.out.println("���ΰ�ħ");
      imageView.setImage(new Image(getClass().getResource("../images/" + seatName + ".jpg").toString()));
   }
   
   public void handleBtn1Action(ActionEvent event) {
      seatName = "seat1";
      System.out.println("seatName : "  + seatName);
      imageView.setImage(new Image(getClass().getResource("../images/seat1.jpg").toString()));
      
   }
   public void handleBtn2Action(ActionEvent event) {
      seatName = "seat2";
      System.out.println("seatName : " + seatName);
      imageView.setImage(new Image(getClass().getResource("../images/seat2.jpg").toString()));
      
   }
   public void handleBtn3Action(ActionEvent event) {
      seatName = "seat3";
      System.out.println("seatName : " + seatName);
      imageView.setImage(new Image(getClass().getResource("../images/seat3.jpg").toString()));
      
   }

   public void reserveList(ActionEvent event) {
      reserveTableView.setItems(rDao.selectAll());
   }
   
   public void userList(ActionEvent event) {
      table.setItems(bDao.selectAll());
   }

   public void userOneList(ActionEvent event) {
      table.setItems(bDao.selectOne(searchId.getText()));
   }
   
   public void menuRefresh(ActionEvent event) {
      menulist.setItems(mDao.selectAll());
   }
   
   public void setMenu(ActionEvent event) {
      Stage stage = new Stage();
      Parent root;
      try {
         root = FXMLLoader.load(getClass().getResource("../project_fxml/menuAdd.fxml"));
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.setResizable(false);
         stage.show();
         stage.setTitle("�޴� �߰�");   
      } catch (IOException e) { e.printStackTrace(); }
   }
   
   public void deleteMenu(ActionEvent event) {
      String menuName = menulist.getSelectionModel().getSelectedItem().getMenuname();
      File file = new File("../Project/src/images/"+menuName+".jpg");
      file.delete();
      mDao.menuDelete(menuName);
      
      menulist.setItems(mDao.selectAll());
      menuimage.setImage(new Image(getClass().getResource("../images/null.png").toString()));
      alert.setTitle("�޴� ����");
       alert.setHeaderText(menuName+" �޴� ���� �Ϸ�");         
       alert.show();
   }
   
   public void updateMenu(ActionEvent event) {
      Stage stage = new Stage();
      Parent root;
      try {
         root = FXMLLoader.load(getClass().getResource("../project_fxml/menuUpdate.fxml"));
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.setResizable(false);
         stage.show();
         stage.setTitle("�޴� ����");   
      } catch (IOException e) { e.printStackTrace(); }
   }
   
   
}