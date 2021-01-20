package project_constructor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Menu {
   private SimpleStringProperty menuname;
    private SimpleStringProperty price;
    private SimpleStringProperty count;
   
    
    public Menu() {
       
   }
    
    public Menu(String menuname, String price, String count) {
      this.menuname = new SimpleStringProperty(menuname);
      this.price = new SimpleStringProperty(price);
      this.count = new SimpleStringProperty(count);
   }

   public Menu(String menuname, String price) {
      super();
      this.menuname = new SimpleStringProperty(menuname);
      this.price = new SimpleStringProperty(price);
   }

   public StringProperty getMenunameProperty() {
       return menuname;
    }
   
   public StringProperty getPriceProperty() {
       return price;
    }

   public String getMenuname() {
      return menuname.get();
   }
   
   
   public StringProperty getCount() {
      return count;
   }

   public void setMenuname(String menuname) {
      this.menuname.set(menuname);
   }

   public String getPrice() {
      return price.get();
   }

   public void setPrice(String price) {
      this.price.set(price);
   }
}