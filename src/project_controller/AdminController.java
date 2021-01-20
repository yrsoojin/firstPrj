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
   
   //테이블 환경설정
   @FXML private ImageView imageView;
   @FXML private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
   @FXML private Button changePic;

   
   //회원정보
   @FXML private TableColumn<User,String> idTable;
   @FXML private TableColumn<User,String> nameTable;
   @FXML private TableColumn<User,String> emailTable;
   @FXML private TableColumn<User,String> reservDate;
   @FXML private TableColumn<User,String> phoneTable;
   @FXML private Button search;
   @FXML private TableView<User> table;
   @FXML private Button searchOne;
   @FXML private TextField searchId;
   
   //메뉴관리
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
   
   //예약관리
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
   
   //매출관리
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
      //테이블관리
      btn1.setOnAction(e -> handleBtn1Action(e));
      btn2.setOnAction(e -> handleBtn2Action(e));
      btn3.setOnAction(e -> handleBtn3Action(e));
//      btn4.setOnAction(e -> handleBtn4Action(e));
//      btn5.setOnAction(e -> handleBtn5Action(e));
//      btn6.setOnAction(e -> handleBtn6Action(e));
//      btn7.setOnAction(e -> handleBtn7Action(e));
      
      refreshBtn.setOnAction(e -> handleRfresh(e));
      
      
      //매출내역관리
    
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
      
     
      //회원정보
      idTable.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
      nameTable.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
      emailTable.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
      phoneTable.setCellValueFactory(cellData -> cellData.getValue().getPhoneProperty());
      phoneTable.setStyle("-fx-alignment : CENTER");
      reservDate.setCellValueFactory(cellData -> cellData.getValue().getReservDateProperty());
      reservDate.setStyle("-fx-alignment : CENTER");
      
      //메뉴관리
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
      
      //예약내역조회
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
      
      Date date_1 = new Date();  //하루전 
       date_1.setDate(date.getDate()-1);
       String nowDate_1 = sdf.format(date_1);
               
      Date date_2 = new Date();   //2일전 
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
      
         series.getData().add(new XYChart.Data<String, Number> (nowDate_6, Integer.parseInt(rDao.profit(nowDate_6)))); //6일전//일주일 
         series.getData().add(new XYChart.Data<String, Number> (nowDate_5, Integer.parseInt(rDao.profit(nowDate_5)))); //5일전 
         series.getData().add(new XYChart.Data<String, Number> (nowDate_4, Integer.parseInt(rDao.profit(nowDate_4)))); //4일전
         series.getData().add(new XYChart.Data<String, Number> (nowDate_3, Integer.parseInt(rDao.profit(nowDate_3)))); //3일전 
         series.getData().add(new XYChart.Data<String, Number> (nowDate_2, Integer.parseInt(rDao.profit(nowDate_2))));  //2일전 
         series.getData().add(new XYChart.Data<String, Number> (nowDate_1, Integer.parseInt(rDao.profit(nowDate_1)))); //1일전 
         series.getData().add(new XYChart.Data<String, Number> (nowDate, Integer.parseInt(rDao.profit(nowDate)))); //오늘 날짜 
      
         
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
 		                       
      series.setName("주별 매출액");
      lineChart.getData().add(series);   
      //-------------------------------------------------------------일일 매출액, 주별 매출액 그래프+테이블뷰 
      
      series1 = new XYChart.Series<String, Number>();
      lineChart1.getData().clear();
      
      Calendar today = Calendar.getInstance();
      System.out.println(" 년도 : " + today.get(Calendar.YEAR));
      String year = String.valueOf(today.get(Calendar.YEAR)).substring(2, 4);
      System.out.println(year);
      
         series1.getData().add(new XYChart.Data<String, Number> ("1월", Integer.parseInt(rDao.profitMonth(year+"01")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("2월", Integer.parseInt(rDao.profitMonth(year+"02"))));  //
         series1.getData().add(new XYChart.Data<String, Number> ("3월", Integer.parseInt(rDao.profitMonth(year+"03")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("4월", Integer.parseInt(rDao.profitMonth(year+"04")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("5월", Integer.parseInt(rDao.profitMonth(year+"05"))));  //
         series1.getData().add(new XYChart.Data<String, Number> ("6월", Integer.parseInt(rDao.profitMonth(year+"06")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("7월", Integer.parseInt(rDao.profitMonth(year+"07")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("8월", Integer.parseInt(rDao.profitMonth(year+"08"))));  //
         series1.getData().add(new XYChart.Data<String, Number> ("9월", Integer.parseInt(rDao.profitMonth(year+"09")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("10월", Integer.parseInt(rDao.profitMonth(year+"10")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("11월", Integer.parseInt(rDao.profitMonth(year+"11")))); //
         series1.getData().add(new XYChart.Data<String, Number> ("12월", Integer.parseInt(rDao.profitMonth(year+"12")))); //12뤟 
         
        
      series1.setName("월별 매출액");
      lineChart1.getData().add(series1);
      //---------------------------------------------------------------월별 매출액 
   }
   
   
   
   
   public void fileChoose(ActionEvent e) throws Exception{
      FileChooser fc = new FileChooser();
        fc.setTitle("이미지 선택");
        fc.setInitialDirectory(new File("C:/"));
        ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg", "*.gif", "*.png");
        fc.getExtensionFilters().add(imgType);
        File selectedFile =  fc.showOpenDialog(null); //선택한 파일
       
        String str = seatName + ".jpg";
        
        if(selectedFile!=null) {
           File name = new File("../Project/src/images/" + str );          
           name.delete();
           
           SaveImg saveImg = new SaveImg();  //URL을 통해 이미지를 저장하기 위한 SaveImg
         
           String file = selectedFile.toURI().toString();           
           String path = "../Project/src/images";
         
         try {
            int result = saveImg.saveImgFromUrl(file, path); //선택한 file을 path에 넣겠다
            
            if (result == 1) {
               
               System.out.println("저장된경로 : " + saveImg.getPath());
               System.out.println("저장된파일이름 : " + saveImg.getSavedFileName());
               Thread.sleep(3000);
               imageView.setImage(new Image(getClass().getResource("../images/" + seatName + ".jpg").toString()));
            }
         } catch (IOException e2) {
            e2.printStackTrace();
         }
           }
        
   
   }

   public void handleRfresh(ActionEvent event) {
      System.out.println("새로고침");
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
         stage.setTitle("메뉴 추가");   
      } catch (IOException e) { e.printStackTrace(); }
   }
   
   public void deleteMenu(ActionEvent event) {
      String menuName = menulist.getSelectionModel().getSelectedItem().getMenuname();
      File file = new File("../Project/src/images/"+menuName+".jpg");
      file.delete();
      mDao.menuDelete(menuName);
      
      menulist.setItems(mDao.selectAll());
      menuimage.setImage(new Image(getClass().getResource("../images/null.png").toString()));
      alert.setTitle("메뉴 삭제");
       alert.setHeaderText(menuName+" 메뉴 삭제 완료");         
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
         stage.setTitle("메뉴 수정");   
      } catch (IOException e) { e.printStackTrace(); }
   }
   
   
}