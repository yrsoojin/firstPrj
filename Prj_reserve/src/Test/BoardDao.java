package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardDao {
	private Connection conn;    //DB 커넥션(연결) 객체
    private static final String USERNAME = "root";   //DB 접속시 ID
    private static final String PASSWORD = "1234";	 //DB 접속시 패스워드
    private static final String URL = "jdbc:mysql://localhost:3306/iddb";
    public BoardDao() {
        try {
            System.out.println("생성자");
            Class.forName("com.mysql.jdbc.Driver"); 
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("드라이버 로딩 성공!!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("드라이버 로드 실패!!");
        }
    }    

    //아이디 생성
    public void idInsert(Board board) {
    	String sql = "insert into id values(?,?,?,?,?,?);";
    	PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getId());
            pstmt.setString(2, board.getPw());
            pstmt.setString(3, board.getName());
            pstmt.setString(4, board.getEmail());
            pstmt.setString(5, board.getCombosex());
            Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd/HHmmss");
            pstmt.setString(6, sdf.format(date));

            int result = pstmt.executeUpdate();
            if(result == 1) {
            	System.out.println("ID 생성 성공!");
            	System.out.println(board);
            }           
        } catch (SQLException e) {            
        	System.out.println("ID 생성 실패!");
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

    //아이디 중복확인, 로그인 아이디 확인
    public int idVerify(String id) {
        String sql = "select * from id where id = ?;";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
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
    
    public int idLoad(String id, String pw) {
    	 String sql = "select * from id where id = ?;";
         PreparedStatement pstmt = null;
         try {
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, id);
             ResultSet rs = pstmt.executeQuery();
             if (rs.next()) {
            	 if(rs.getString("pw").equals(pw)) {
            		 return 1;
            	 }
            	 else {
            		 return 0;
            	 }
             }
             else {
             	return 0;
             }
         } catch (SQLException e) {
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
    
    public int pwUpdate(String id, String pw) {
    	String sql = "update id set pw = ? where id = ?;";
    	PreparedStatement pstmt = null;
    	try {
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, pw);
            pstmt.setString(2, id);
            int result = pstmt.executeUpdate();
            if (result == 1) {
            	return 1;
            }
            else {
            	return 0;
            }
        } catch (SQLException e) {
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
    
    public int idDelete(String id) {
    	String sql = "delete from id where id= ? ;";
    	PreparedStatement pstmt = null;
    	try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            int result = pstmt.executeUpdate();
            if (result == 1) {
            	return 1;
            }
            else {
            	return 0;
            }
        } catch (SQLException e) {
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
}