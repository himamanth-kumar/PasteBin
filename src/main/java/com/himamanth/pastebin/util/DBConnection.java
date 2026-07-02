package com.himamanth.pastebin.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class DBConnection
{
	public static Connection getConnection() throws SQLException, Exception
	{
		Properties properties = new Properties();
		InputStream input = DBConnection.class
							.getClassLoader()
							.getResourceAsStream("db.properties");
		properties.load(input);
		String url = properties.getProperty("db.url");
		String username = properties.getProperty("db.username");
		String password = properties.getProperty("db.password");
		
		return DriverManager.getConnection(url, username, password);
	}
	public static void main(String[] args) throws Exception {
	    try 
	    {
	        Connection con = getConnection();

	        if (con != null) 
	        {
	            System.out.println("Database Connected Successfully!");
	        }

	    } 
	    catch (SQLException e) 
	    {
	        e.printStackTrace();
	    }
	}
}