package project_constructor;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//JAVA Bean이라고 함.
public class Reserve {
   private StringProperty nowDate;
   private StringProperty id;
   private StringProperty username;
   private StringProperty tableNo;
   private StringProperty payTime;
   private StringProperty startTime;
   private StringProperty getTime;
   private StringProperty reserveMenu;
   private StringProperty price;
   private StringProperty image;
   private static int total;

   StringProperty var = new SimpleStringProperty((String.valueOf(total)));
   
    public String getImage() {
      return image.get();
   }

   public void setImage(String image) {
      this.image.set(image);
   }

     
   public String getTotal() {
	return String.valueOf(total);
   }
   
   public StringProperty getVar() {
	   return var;
   }
    

	public Reserve() {
	      
	   }
	public Reserve(String nowDate) {
	      this.nowDate = new SimpleStringProperty(nowDate);
	      
	   }
 
	
	
   
    public Reserve(String nowDate, String id, String username, String tableNo, String payTime,
          String startTime, String getTime, String reserveMenu, String price, String image) {
      super();
      this.nowDate = new SimpleStringProperty(nowDate);
      this.id = new SimpleStringProperty(id);
      this.username = new SimpleStringProperty(username);
      this.tableNo = new SimpleStringProperty(tableNo);
      this.payTime = new SimpleStringProperty(payTime);
      this.startTime = new SimpleStringProperty(startTime);
      this.getTime = new SimpleStringProperty(getTime);
      this.reserveMenu = new SimpleStringProperty(reserveMenu);
      this.price = new SimpleStringProperty(price);
      this.image = new SimpleStringProperty(image);
      this.total += Integer.parseInt(price);
      
   }
    
    public StringProperty getNowDateProperty() {
       return nowDate;
    }
    
    public String getNowDate() {
      return nowDate.get();
   }
    
   public void setNowDate(String nowDate) {
      this.nowDate.set(nowDate);
   }

    
   public StringProperty getIdProperty() {
       return id;
    }
    
    public String getId() {
      return id.get();
   }
    
   public void setId(String id) {
      this.id.set(id);
   }

   public StringProperty getUsernameProperty() {
       return username;
    }
    
    public String getUsername() {
      return username.get();
   }
    
   public void setUsername(String username) {
      this.username.set(username);
   }

   public StringProperty getTableNoProperty() {
       return tableNo;
    }
    
    public String getTableNo() {
      return tableNo.get();
   }
    
   public void setTableNo(String tableNo) {
      this.tableNo.set(tableNo);
   }

   public StringProperty getPayTimeProperty() {
       return payTime;
    }
    
    public String getPayTime() {
      return payTime.get();
   }
    
   public void setPayTime(String payTime) {
      this.payTime.set(payTime);
   }

   public StringProperty getStartTimeProperty() {
       return startTime;
    }
    
    public String getStartTime() {
      return startTime.get();
   }
    
   public void setStartTime(String startTime) {
      this.startTime.set(startTime);
   }

   public StringProperty getGetTimeProperty() {
       return getTime;
    }
    
    public String getGetTime() {
      return getTime.get();
   }
    
   public void setGetTime(String getTime) {
      this.getTime.set(getTime);
   }

   public StringProperty getReserveMenuProperty() {
       return reserveMenu;
    }
    
    public String getReserveMenu() {
      return reserveMenu.get();
   }
    
   public void setReserveMenu(String reserveMenu) {
      this.reserveMenu.set(reserveMenu);
   }

   public StringProperty getPriceProperty() {
       return price;
    }
    
    public String getPrice() {
      return price.get();
   }
    
   public void setPrice(String price) {
      this.price.set(price);
   }


}