package org.Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySource("classpath:db.properties")
@Component
public class H2Test {
	
	
	@Autowired
	private Environment env;
	
	   public void callMeH2() { 
		   
	      Connection conn = null; 
	      Statement stmt = null; 
	      String sql = null;
	      try { 
	             
	         System.out.println("Connecting to database...");  
	         conn = DriverManager.getConnection(env.getProperty("db.url"),env.getProperty("db.username"),env.getProperty("db.password")); 
	         System.out.println("Connected database successfully..."); 
	         stmt = conn.createStatement();	    
	         
	 
	         
	         System.out.println("Creating table in given database..."); 
	         sql =  "CREATE TABLE IF NOT EXISTS   REGISTRATION " + 
	            "(id INTEGER not NULL, " + 
	            " first VARCHAR(255), " +  
	            " last VARCHAR(255), " +  
	            " age INTEGER, " +  
	            " PRIMARY KEY ( id ))";  
	         stmt.executeUpdate(sql);
	         System.out.println("Created table in given database..."); 
	     

	         sql = "INSERT INTO Registration " + "VALUES (100, 'Zara', 'Ali', 18)"; 	         
	         stmt.executeUpdate(sql); 
	         sql = "INSERT INTO Registration " + "VALUES (101, 'Mahnaz', 'Fatma', 25)";  	         
	         stmt.executeUpdate(sql); 
	         sql = "INSERT INTO Registration " + "VALUES (102, 'Zaid', 'Khan', 30)"; 	         
	         stmt.executeUpdate(sql); 
	         sql = "INSERT INTO Registration " + "VALUES(103, 'Sumit', 'Mittal', 28)"; 	         
	         stmt.executeUpdate(sql); 
	         System.out.println("Inserted records into the table..."); 
	         
	         
	         sql = "SELECT id, first, last, age FROM Registration"; 
	         ResultSet rs = stmt.executeQuery(sql); 
	         
	         while(rs.next()) { 
	            // Retrieve by column name 
	            int id  = rs.getInt("id"); 
	            int age = rs.getInt("age"); 
	            String first = rs.getString("first"); 
	            String last = rs.getString("last");  
	            
	            // Display values 
	            System.out.print("ID: " + id); 
	            System.out.print(", Age: " + age); 
	            System.out.print(", First: " + first); 
	            System.out.println(", Last: " + last); 
	         } 
	         
	         
	         
//	         String dropEverythingSql = "DROP ALL OBJECTS";	        
//		     stmt.executeUpdate(dropEverythingSql);
//	         
//	    *****THIS IS NOT NEEDED BECAUSE:
//	         hbm2ddl closes the connection after creating the table, so h2 discards it.
//
//	         If you have your connection-url configured like this
//
//	         jdbc:h2:mem:test
//	         the content of the database is lost at the moment the last connection is closed.
//
//	         If you want to keep your content you have to configure the url like this
//
//	         jdbc:h2:mem:test;DB_CLOSE_DELAY=-1
//	         If doing so, h2 will keep its content as long as the vm lives.

	         	       
	         rs.close(); 
	         stmt.close(); 
	         conn.close(); 
	      } catch(SQLException se) { 
	         //Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         //Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } finally { 
	         //finally block used to close resources 
	         try{ 
	            if(stmt!=null) stmt.close(); 
	         } catch(SQLException se2) { 
	         } // nothing we can do 
	         try { 
	            if(conn!=null) conn.close(); 
	         } catch(SQLException se){ 
	            se.printStackTrace(); 
	         } //end finally try 
	      } //end try 
	      System.out.println("Goodbye!");
	   } 

}
