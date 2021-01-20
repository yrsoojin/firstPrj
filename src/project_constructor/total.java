package project_constructor;

import javafx.beans.property.SimpleStringProperty;

public class total {
	//simplestringproperty 속성감시에 유용
	private SimpleStringProperty date;
	private SimpleStringProperty total;
	
	public total(String date, String total) {
		super();
		this.date = new SimpleStringProperty(date);
		this.total = new SimpleStringProperty(total);
	}

	public String getDate() {
		return date.get();
	}

	public String getTotal() {
		return total.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	public void setTotal(String total) {
		this.total.set(total);
	}
	
	
}
