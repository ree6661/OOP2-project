package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public final class Create { //maybe make all private?
	static final String database = "deliverydb";
	static final String url = "jdbc:mysql://localhost/" + database + "?useUnicode=true&characterEncoding=UTF-8";
	static final String admin = "root";
	static final String adminPass = "Mysql@localhost:3306";
	
	static Connection conn;
	static PreparedStatement create;
	static String sql;
	
   public static void main(String[] args) {
	   try {
		   conn = getConnection();
		   DB();
	   } catch(Exception e) {
		   e.printStackTrace();
	   }
	   
   }
   
   public static void DB() throws SQLException {

    	 sql = "create database if not exists " + database;
    	 create = conn.prepareStatement(sql);
    	 create.executeUpdate(sql);
         System.out.println("Database created successfully...");
   }
   
   public static Connection getConnection() throws SQLException {
	   try {
		   Properties info = new Properties();
		   info.put("user", admin);
		   info.put("password", adminPass);
		   info.put("userUnicode", "true");
		   Connection conn = 
				   DriverManager.getConnection(url, info);
		   return conn;
	   } catch(SQLException e) {
		   System.out.println(e);
	   }
	   return null;
   }
}