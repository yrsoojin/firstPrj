package Test;

//JAVA Bean이라고 함.
public class Board {
	private String id;
    private String pw;
    private String name;
    private String email;
    private String combosex;
	
    public Board() {
	
	}
    
    public Board(String id, String pw, String name, String email, String combosex) {
		super();		
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.combosex = combosex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCombosex() {
		return combosex;
	}

	public void setCombosex(String combosex) {
		this.combosex = combosex;
	}

}
