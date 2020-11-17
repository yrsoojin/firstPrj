package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuDao {
	private Connection conn;    //DB Ŀ�ؼ�(����) ��ü
    private static final String USERNAME = "root";   //DB ���ӽ� ID
    private static final String PASSWORD = "1234";	 //DB ���ӽ� �н�����
    private static final String URL = "jdbc:mysql://localhost:3306/iddb";
    public MenuDao() {
        try {
            System.out.println("������");
            Class.forName("com.mysql.jdbc.Driver"); 
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("����̹� �ε� ����!!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("����̹� �ε� ����!!");
        }
    }    

    public void menuInsert(Menu menu) {
    	String sql = "insert into menu values(?,?);";
    	PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, menu.getMenuname());
            pstmt.setString(2, menu.getPrice());

            int result = pstmt.executeUpdate();
            if(result == 1) {
            	System.out.println("�޴� �߰� ����!");
            }           
        } catch (SQLException e) {            
        	System.out.println("�޴� �߰� ����!");
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {                
                e.printStackTrace();
            }
        }
    }
    
    public int menuDelete(String menuname) {
    	System.out.println(menuname);
    	String sql = "delete from menu where menuname = ?;";
    	PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, menuname);

            int result = pstmt.executeUpdate();
            if(result == 1) {
            	return 1;
            }           
            else {
            	return 0;
            }
        } catch (SQLException e) {            
        	System.out.println("�޴� ���� ����!");
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {                
                e.printStackTrace();
            }
        }
    }
    
    public int menuVerify(String menuname) {
        String sql = "select * from menu where menuname = ?;";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, menuname);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	return 1;
            }
            else {
            	return 0;
            }
        } catch (SQLException e) {
        	return 0;
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public int menuUpdate(String menuname, String newMenuname, String newMenuprice) {
    	String sql = "update menu set menuname = ?, price = ? where menuname = ?;";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newMenuname);
            pstmt.setString(2, newMenuprice);
            pstmt.setString(3, menuname);
            int result = pstmt.executeUpdate();
            if (result == 1) {
            	return 1;
            }
            else {
            	return 0;
            }
        } catch (SQLException e) {
        	return 0;
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}