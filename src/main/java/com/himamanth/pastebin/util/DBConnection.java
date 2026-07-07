package com.himamanth.pastebin.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection 
{

	public static Connection getConnection() throws Exception {

	    Class.forName("org.postgresql.Driver");

	    Properties properties = new Properties();

	    InputStream input = DBConnection.class
	            .getClassLoader()
	            .getResourceAsStream("db.properties");

	    if (input == null) {
	        throw new RuntimeException("db.properties not found");
	    }

	    properties.load(input);

	    String url = properties.getProperty("db.url");
	    String username = properties.getProperty("db.username");
	    String password = properties.getProperty("db.password");

	    return DriverManager.getConnection(url, username, password);
	}
}