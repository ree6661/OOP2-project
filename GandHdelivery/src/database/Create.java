package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Create { //maybe make all private?
	static final String database = "deliverydb";
	static final String url = "jdbc:mysql://localhost/" + database;
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
		   Connection conn = 
				   DriverManager.getConnection(url, admin, adminPass);
		   return conn;
	   } catch(SQLException e) {
		   System.out.println(e);
	   }
	   return null;
   }
}