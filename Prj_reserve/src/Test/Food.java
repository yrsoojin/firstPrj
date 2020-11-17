package Test;

import javafx.beans.property.SimpleStringProperty;

public class Food {

	private SimpleStringProperty Food;
	private SimpleStringProperty Price;
	private SimpleStringProperty image;

	
	public Food(String food, String price, String image) {
		super();
		this.Food = new SimpleStringProperty(food);
		this.Price = new SimpleStringProperty(price);
		this.image = new SimpleStringProperty(image);
	}


	public String getFood() {
		return Food.get();
	}
	public void setFood(String food) {
		this.Food.set(food);
	}
	public String getPrice() {
		return Price.get();
	}
	public void setPrice(String price) {
		this.Price.set(price);
	}
	public String getImage() {
		return image.get();
	}
	public void setImage(String image) {
		this.image.set(image);
	}
	
	
	
	
	
}
