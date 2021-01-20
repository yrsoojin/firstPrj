package project_constructor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//JAVA Bean이라고 함.
public class User {
	private StringProperty id;
    private String pw;
    private StringProperty name;
    private StringProperty email;
    private String combosex;
    private StringProperty reservedate;
    private StringProperty phone;
	
    public User() {
		
	}
    public User(String id) {
    	this.id = new SimpleStringProperty(id);
	}
    
    public User(String id, String name, String email, String phone, String reservedate) {
    	this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.email = new SimpleStringProperty(email);
		this.phone = new SimpleStringProperty(phone);
		this.reservedate = new SimpleStringProperty(reservedate);
	}
    
    public User(String id, String pw, String name, String email, String phone, String combosex) {
		super();		
		this.id = new SimpleStringProperty(id);
		this.pw = pw;
		this.name = new SimpleStringProperty(name);
		this.email = new SimpleStringProperty(email);
		this.phone = new SimpleStringProperty(phone);
		this.combosex = combosex;
	}
    
    public StringProperty getReservDateProperty() {
    	return reservedate;
    }
    
    public String getReservDate() {
		return reservedate.get();
	}
    
	public void setReservDate(String reservdate) {
		this.reservedate.set(reservdate);
	}
	
	public StringProperty getPhoneProperty() {
    	return phone;
    }
    
    public String getPhone() {
		return phone.get();
	}
    
	public void setPhone(String phone) {
		this.phone.set(phone);
	}
	
	

    
    public StringProperty getNameProperty() {
    	return name;
    }
    
    public StringProperty getIdProperty() {
    	return id;
    }
    
    public StringProperty getEmailProperty() {
    	return email;
    }
    
	public String getName() {
		return name.get();
	}


	public void setName(String name) {
		this.name.set(name);
	}
	public void setEmail(String email) {
		this.email.set(email);
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}
	

	
	

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email.get();
	}



	public String getCombosex() {
		return combosex;
	}

	public void setCombosex(String combosex) {
		this.combosex = combosex;
	}

}
