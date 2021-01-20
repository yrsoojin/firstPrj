package project_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDao {
	private Connection conn;    //DB 커넥션(연결) 객체
    private static final String USERNAME = "root";   //DB 접속시 ID
    private static final String PASSWORD = "0824";	 //DB 접속시 패스워드
    private static final String URL = "jdbc:mysql://localhost:3306/iddb";
    public UserDao() {
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
    
    ObservableList<project_constructor.User> userList = null;
    
    //아이디 생성
    public void idInsert(project_constructor.User uDao) {
    	String sql = "insert into id values(?,?,?,?,?,?,?);";
    	PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uDao.getId());
            pstmt.setString(2, uDao.getPw());
            pstmt.setString(3, uDao.getName());
            pstmt.setString(4, uDao.getEmail());
            pstmt.setString(5, uDao.getPhone());
            pstmt.setString(6, uDao.getCombosex());
            Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd/HHmmss");
            pstmt.setString(7, sdf.format(date));
            
            int result = pstmt.executeUpdate();
            if(result == 1) {
            	System.out.println("ID 생성 성공!");
            	System.out.println(uDao);
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

    public String bringID() {  
    	String sql = "select * from saveID;";
    	PreparedStatement pstmt = null;
    	try {
    		 pstmt = conn.prepareStatement(sql);    		 
             									// SELECT문은 executeUpdate명령으로 호출시 에러발생합니다.
             ResultSet rs = pstmt.executeQuery();
             if (rs.next()) { //id가 db에 있을 때 
            	 return rs.getString("id");
             }
             else {
             	return null;  //id가 db에 없을 때 
             }
         } catch (SQLException e) {
        	 e.printStackTrace();
         	return "Exception";
         } finally {
             try {
                 if (pstmt != null && !pstmt.isClosed())
                     pstmt.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
     }
    
    public int saveIDdelete() {  //저장된 id 모두 지우기 
    	String sql = "delete from saveID;";
    	PreparedStatement pstmt = null;
    	try {
            pstmt = conn.prepareStatement(sql);
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
    
    public void saveIDinsert(project_constructor.User uDao) {
    	String sql = "insert into saveID values(?);";
    	PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uDao.getId());
            int result = pstmt.executeUpdate(); //db실행문
            if(result==1) { //저장되면 1
            	System.out.println("아이디 저장 성공 ");
            }
        }catch(Exception e) {
        	e.printStackTrace();
        }finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getName(String id) {
    	String sql = "select username from id where id=?;";
    	PreparedStatement pstmt = null;
    	try {
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();
    		 if (rs.next()) {
             	return rs.getString("username");
             }
             else {
             	return null;
             }
    	}catch(Exception e) {
    			e.printStackTrace();
    	}
		return null;   	
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
            	return 0;  //id가 db에 없을 때 
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
    
    public int emailVerify(String email) {
        String sql = "select * from id where email = ?;";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
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
    public String idLoad(String id) {
   	 String sql = "select * from saveID where id = ?;";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
           	 	return rs.getString("id");
           	 }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        return "";
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
    
    public ObservableList<project_constructor.User> selectAll() {
        userList = FXCollections.observableArrayList();
    	String sql = "select * from id;";
    	PreparedStatement pstmt = null;
    	try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				project_constructor.User user = new project_constructor.User(rs.getString("id"), rs.getString("username"), rs.getString("email"), rs.getString("phone"),rs.getString("reservedate"));
		    	System.out.println(rs.getString("id"));
		    	System.out.println(rs.getString("username"));
		    	System.out.println(rs.getString("email"));
				userList.add(user);
			}
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null&&!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
    	return userList;
    }
    
    public ObservableList<project_constructor.User> selectOne(String id) {
        userList = FXCollections.observableArrayList();
    	String sql = "select * from id where id = ?;";
    	PreparedStatement pstmt = null;
    	try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				project_constructor.User user = new project_constructor.User(rs.getString("id"), rs.getString("username"), rs.getString("email"), rs.getString("phone"), rs.getString("reservedate"));
		    	System.out.println(rs.getString("id"));
		    	System.out.println(rs.getString("username"));
		    	System.out.println(rs.getString("email"));
				userList.add(user);
			}
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null&&!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
    	return userList;
    }
    
}