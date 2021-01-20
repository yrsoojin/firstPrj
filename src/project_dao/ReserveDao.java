package project_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project_constructor.Reserve;

public class ReserveDao {
   private Connection conn;    //DB 커넥션(연결) 객체
    private static final String USERNAME = "root";   //DB 접속시 ID
    private static final String PASSWORD = "0824";    //DB 접속시 패스워드
    private static final String URL = "jdbc:mysql://localhost:3306/iddb";
    public ReserveDao() {
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
    
    ObservableList<Reserve> reserveList = null;
    ObservableList<String> reserveList1 = null;
    
    //예약내용 삽입  //ReserveVerifyController.java 클래스에서 사용 
    public void reserveInsert(Reserve reserve) {
       String sql = "insert into reserve values(?,?,?,?,?,?,?,?,?,?);";
       PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reserve.getNowDate());
            pstmt.setString(2, reserve.getId());
            pstmt.setString(3, reserve.getUsername());
            pstmt.setString(4, reserve.getTableNo());
            pstmt.setString(5, reserve.getPayTime());
            pstmt.setString(6, reserve.getStartTime());
            pstmt.setString(7, reserve.getGetTime());
            pstmt.setString(8, reserve.getReserveMenu());
            pstmt.setString(9, reserve.getPrice());
            pstmt.setString(10, reserve.getImage());

            int result = pstmt.executeUpdate();
            if(result == 1) {
               System.out.println("예약 성공!");
               System.out.println(reserve);
            }           
        } catch (SQLException e) {            
           System.out.println("예약 실패!");
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

    //시작 시간, 사용시간 
    public String reserveVerify(String nowDate, String tableNo) {
        String sql = "select startTime, getTime from reserve where nowDate=? and tableNo=?;";
        PreparedStatement pstmt = null;
        String str = "";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nowDate);
            pstmt.setString(2, tableNo);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
               str += rs.getString("startTime")+"~"+rs.getString("getTime")+"/";
            }
        } catch (SQLException e) {
           return "";
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
      return str;
    }

    public ObservableList<Reserve> selectAll() {
        reserveList = FXCollections.observableArrayList();
       String sql = "select * from reserve;";
       PreparedStatement pstmt = null;
       try {
         pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()) {
            Reserve reserve = new Reserve(rs.getString("nowDate"), rs.getString("id"), rs.getString("username"), 
                  rs.getString("tableNo"), rs.getString("payTime"), rs.getString("startTime"), rs.getString("getTime"),
                  rs.getString("reserveMenu"), rs.getString("price"), rs.getString("image"));
             reserveList.add(reserve);
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
       return reserveList;
    }
    
    public ObservableList<Reserve> selectOne(String id) {
       reserveList = FXCollections.observableArrayList();
       String sql = "select * from reserve where id = ?;";
       PreparedStatement pstmt = null;
       try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()) {
            Reserve reserve = new Reserve(rs.getString("nowDate"), rs.getString("id"), rs.getString("username"), 
            rs.getString("tableNo"), rs.getString("payTime"), rs.getString("startTime"), rs.getString("getTime"), 
            rs.getString("reserveMenu"), rs.getString("price"), rs.getString("image"));
             reserveList.add(reserve);
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
       return reserveList;
    }
    
    public ObservableList<Reserve> selectOneDate(String date) {
       reserveList = FXCollections.observableArrayList();
       String sql = "select * from reserve where nowDate = ?;";
       PreparedStatement pstmt = null;
       try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, date);
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()) {
            Reserve reserve = new Reserve(rs.getString("nowDate"), rs.getString("id"), rs.getString("username"),
                  rs.getString("tableNo"), rs.getString("payTime"), rs.getString("startTime"), rs.getString("getTime"), 
                  rs.getString("reserveMenu"), rs.getString("price"), rs.getString("image"));
             reserveList.add(reserve);
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
       return reserveList;
    }
    
    public String profit(String nowDate) {   //지정한 날짜의 총 합계 계산 
        String sql = "select sum(price) from reserve where nowDate=?;";
        PreparedStatement pstmt = null;
        String str = "";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nowDate);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
               str = rs.getString("sum(price)");
               if(str==null) {
                  return "0";
               }
            }
        } catch (SQLException e) {
           return "";
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
      return str;
    }
    public String profitMonth(String nowDate) {
        String sql = "select sum(price) from reserve where nowDate like?;";
        PreparedStatement pstmt = null;
        String str = "";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nowDate +"%"); //where nowDate like '2020%';
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
               str = rs.getString("sum(price)");
               if(str==null) {
                  return "0";
               }
            }
        } catch (SQLException e) {
           return "";
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
      return str;
    }
    
    public String profitYear(String nowDate) {
        String sql = "select sum(price) from reserve where nowDate like?;";
        PreparedStatement pstmt = null;
        String str = "";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nowDate.substring(0, 4)+"%"); //where nowDate like '2020%';
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
               str = rs.getString("sum(price)");
               if(str==null) {
                  return "0";
               }
            }
        } catch (SQLException e) {
           return "";
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
      return str;
    }
    
    
}