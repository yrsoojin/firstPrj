package project_controller;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
//import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import project_constructor.Menu;
import project_constructor.Reserve;
import project_dao.MenuDao;
import project_dao.ReserveDao;


public class UserReserveListController extends UserController implements Initializable{
   
   @FXML private TableView<Reserve> reserveTableView;
   @FXML private TableColumn<Reserve,String> dateResTable;

   @FXML private TableColumn<Reserve,String> tableResTable;

   @FXML private TableColumn<Reserve,String> startTimeResTable;
   @FXML private TableColumn<Reserve,String> getTimeResTable;
   @FXML private TableColumn<Reserve,String> orderMenuResTable;
   @FXML private TableColumn<Reserve,String> priceResTable;

   @FXML private DatePicker datePicker;
   @FXML private Button searchReserve;
   @FXML private Button backBtn;
   @FXML private Button tableReserveBtn;
   @FXML private Button reserveListBtn;
   @FXML private Button userUpdate;
   String dataStr;
   @FXML private ImageView imageView;
   @FXML private ImageView iconView;
   
   MenuDao mDao = new MenuDao();
   ReserveDao rDao = new ReserveDao();
   @FXML private ListView<String> listView;
   
   ObservableList<String> Ordersheet = FXCollections.observableArrayList();
   ObservableList<Menu> Orderlist = FXCollections.observableArrayList();
   
   
   @FXML private TextArea textArea;
   
   static int Mcount;
   static int total=0;
   @Override
   public void initialize(URL location, ResourceBundle resources) {
    
      tableReserveBtn.setDisable(true);
      userUpdate.setDisable(true);
      
      //행의 값을 Reserve필드를 PropertyValueFactory를 이용해 매핑한다.
      dateResTable.setCellValueFactory(cellData -> cellData.getValue().getNowDateProperty());
      dateResTable.setStyle("-fx-alignment : CENTER");

      
      tableResTable.setCellValueFactory(cellData -> cellData.getValue().getTableNoProperty());
      tableResTable.setStyle("-fx-alignment : CENTER");
      
   
      startTimeResTable.setCellValueFactory(cellData -> cellData.getValue().getStartTimeProperty());
      startTimeResTable.setStyle("-fx-alignment : CENTER");
      
      getTimeResTable.setCellValueFactory(cellData -> cellData.getValue().getGetTimeProperty());
      getTimeResTable.setStyle("-fx-alignment : CENTER");
      
      priceResTable.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
      priceResTable.setStyle("-fx-alignment : CENTER");
      
      
      //reserveTableView.setItems(rDao.selectOne(id));
      
  
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

          
      reserveTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Reserve>() {
         
         @Override
         public void changed(ObservableValue<? extends Reserve> observable, Reserve oldValue, Reserve newValue) {
            System.out.println("이미지뷰");   
           // System.out.println(getClass().getResource("../images/" + newValue.getImage()).toString());
            if(newValue != null) {
               imageView.setImage(new Image(getClass().getResource("../images/" + newValue.getImage()).toString()));
               
                  
//               StringTokenizer st = new StringTokenizer(newValue.getReserveMenu(), ",");
//               while(st.hasMoreTokens()) {
//                  Ordersheet.add(st.nextToken().replace(" ",""));  //1개씩 observable list에 넣어주기 //정렬을위해 띄어쓰기 없애기
//               }
//               listView.setItems(Ordersheet); //listView에 넣어줄 목록완성후(ordersheet) 한꺼번에 넣기 
               
               StringTokenizer st = new StringTokenizer(newValue.getReserveMenu(), ",");
               textArea.clear();
               String[] str1 = new String[st.countTokens()];
               int count = 0;
               
               while(st.hasMoreTokens()) {
                  
                  String str = st.nextToken().replace(" ","");
                 // textArea.appendText(str+ "\n");
                  
                  str1[count] = str;
                  count++;
               }
               
           Orderlist = mDao.selectAll();
             for(int i=0; i<Orderlist.size(); i++) {
                for(int j=0; j<str1.length; j++) {
                   if(Orderlist.get(i).getMenuname().equals(str1[j])) {
                      Mcount++;
                   }
                }
                if(Mcount!=0) {
                textArea.appendText(Orderlist.get(i).getMenuname() +"\t x "+ Mcount + "\t\t" +
                                    Integer.parseInt(Orderlist.get(i).getPrice())*Mcount + "\n");
                total += Integer.parseInt(Orderlist.get(i).getPrice());
                Mcount=0;
                
                }
             }
            textArea.appendText("\n\t\t total : "+ String.valueOf(total));
        }
   
     }
       });
      
      
   }
   

   
   public void reserveList(ActionEvent event) {
      reserveTableView.setItems(rDao.selectOne(id));
   }
   
   public void back(ActionEvent e) {
      Stage root = (Stage)backBtn.getScene().getWindow();
      try {
         Parent root1 = FXMLLoader.load(getClass().getResource("../project_fxml/user.fxml"));
         Scene scene = new Scene(root1);
         scene.getStylesheets().add(getClass().getResource("Order.css").toString());
         root.setScene(scene);
         root.setResizable(false);
         root.show();
         root.setTitle("사용자 모드");
      } catch (Exception e2) {e2.printStackTrace();
      }
   }
   
}