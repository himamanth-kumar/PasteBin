package com.himamanth.pastebin.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection 
{

    public static Connection getConnection() throws Exception {

        Class.forName("org.postgresql.Driver");

        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || username == null || password == null) {

            Properties properties = new Properties();

            InputStream input = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("db.properties not found");
            }

            properties.load(input);

            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        }

        return DriverManager.getConnection(url, username, password);
    }
}