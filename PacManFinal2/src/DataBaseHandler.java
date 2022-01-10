import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DataBaseHandler {
	private static String DB_URL = "jdbc:mysql://localhost:3306/pacman";
	private static String USER = "root";
	private static String PASS = "";

	   public static void initialize() {
		   createDatabaseIfNotExists();
		   addTableIfNotExists();
	   }
	   public static void createDatabaseIfNotExists() {
	      // Open a connection
	      try{		    
	    	 Class.forName("com.mysql.cj.jdbc.Driver");
    		 Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		 Statement stmt = conn.createStatement();
	         String sql = "CREATE DATABASE IF NOT EXISTS PACMAN";
	         stmt.executeUpdate(sql);
	         conn.close();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   }
	   
	   public static void addTableIfNotExists() {
		      try{		      
		    	  Class.forName("com.mysql.cj.jdbc.Driver"); 
		    	  Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    		  Statement stmt = conn.createStatement();
		          String sql = "CREATE TABLE IF NOT EXISTS SCORE " +
		                   "("+
		                   " name VARCHAR(255), " + 
		                   " score INTEGER" + 
		                   ")"; 

		         stmt.executeUpdate(sql);
		         conn.close();
		       } catch (SQLException e) {
		          e.printStackTrace();
		       } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	   }
	   
	   public static void insertDatabase(String Name, int score) {
		   try {
			 Class.forName("com.mysql.cj.jdbc.Driver"); 
		   	 Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			 Statement stmt = conn.createStatement();	      
			 String sql = "INSERT INTO SCORE VALUES (" + Name + "," + score + ")";
	         stmt.executeUpdate(sql);	  
	         conn.close();
	      } catch (SQLException | ClassNotFoundException e) {
	         e.printStackTrace();
	      } 
	   }
	   
	   public static List<Highscore.score> getData() {
		   String QUERY = "SELECT name, score FROM SCORE";
		   List<Highscore.score> lscore = new LinkedList<>();
		      try {
		    	  Class.forName("com.mysql.cj.jdbc.Driver"); 
		    	  Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		    	  Statement stmt = conn.createStatement();
		    	  ResultSet rs = stmt.executeQuery(QUERY);
		    	  conn.close();    		      
    	      while(rs.next()){
    	            //Display values           
    	    	  	String name = rs.getString("name");
    	            int score = rs.getInt("score");
    	            Highscore.score sc = new Highscore.score();
    	            sc.score = score;
    	            sc.name = name;
    	            lscore.add(sc);
    	         }
    	      } catch (SQLException | ClassNotFoundException e) {
    	         e.printStackTrace();
    	      } 
		      return lscore;
	   }
	   
}

//I could not get this to work, I don't know what I was forgetting but after spending countless hours on this project I am burnt out. 

