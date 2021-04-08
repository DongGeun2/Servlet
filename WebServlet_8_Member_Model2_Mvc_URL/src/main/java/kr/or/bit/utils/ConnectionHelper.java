package kr.or.bit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// POOL 
public class ConnectionHelper {
	
	
	public static Connection getConnection(String dbname) {

	      if (dbname.toLowerCase().equals("oracle")) {
	         try {
	               Context initContext = new InitialContext(); //현재 프로젝트에서 특정이름을 가진 객체를 검색
	               DataSource source = (DataSource) initContext.lookup("java:comp/env/jdbc/oracle"); // java:comp/env 는 고정된값 /jdbc/oracle 이라는 객체를 찾아줌
	               Connection conn = source.getConnection(); // 찾은 디비를 conn에 연결 
	               return conn;
	         } catch (Exception ex) {
	            System.out.println("connection" + ex.getMessage());
	            return null;
	         }
	      } else if (dbname.toLowerCase().equals("mysql")) {
	    	 try {
	            // 1.
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demoweb", "devadmin",
	                  "mysql");

	            // 2.
	            /*
	             * Context initContext = new InitialContext(); Context context =
	             * (Context)initContext.lookup("java:/comp/env"); DataSource source =
	             * (DataSource)context.lookup("mysql/demoweb"); Connection conn =
	             * source.getConnection();
	             */
	            return conn;
	} catch (Exception ex) {
	            return null;
	         }
	      } else {
	         return null;
	      }

	   }

	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			}catch (Exception e) {
				
			}
		}
	}
	public static void close(Statement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			}catch (Exception e) {
				
			}
		}
	}
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch (Exception e) {
				
			}
		}
	}
}
