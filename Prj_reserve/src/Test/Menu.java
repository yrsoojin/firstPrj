package Test;

public class Menu {
	private String menuname;
    private String price;
	
    public Menu() {
	
	}

	public Menu(String menuname, String price) {
		super();
		this.menuname = menuname;
		this.price = price;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
    
    

}
