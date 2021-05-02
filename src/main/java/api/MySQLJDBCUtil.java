package api;

import java.io.IOException;
import java.sql.*;
import app.Config;

public class MySQLJDBCUtil {
	public static Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
        Connection conn = null;

        try {
        	
        	Config config = new Config();
        
            // assign db parameters
            String url = config.db_connection;
            String user = config.db_username;
            String password = config.db_password;
            
            // create a connection to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } finally {
        	try{
                if(conn != null) {
                	System.out.println(String.format("Connected to database %s "
                            + "successfully.", conn.getCatalog()));
//                	conn.close();
                }
                  
	     	} catch (SQLException ex){
	                System.out.println(ex.getMessage());
	     	}
        }
        return conn;
    }
}
