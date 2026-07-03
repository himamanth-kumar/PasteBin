package com.himamanth.pastebin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.himamanth.pastebin.model.User;
import com.himamanth.pastebin.util.DBConnection;

public class UserDAO 
{

    public boolean registerUser(User user) throws Exception 
    {

        String sql = """
                INSERT INTO users(username, email, password_hash)
                VALUES (?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {

            // Hash the password for storing in DataBase
            String hashedPassword = BCrypt.hashpw(
                     user.getPasswordHash(),
                    BCrypt.gensalt()
            );

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, hashedPassword);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean usernameExists(String username) throws Exception 
    {

        String sql = """
                SELECT 1
                FROM users
                WHERE username = ?
                """;

        try 
        (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
         ){

            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery())
            {

            	return rs.next();
            }
            /*
              	If you don't close ResultSet:

				Database cursor remains open
				Memory is used
				Too many open cursors can slow the application
             
             */
            
        } 
        catch (SQLException e) 
        {

            e.printStackTrace();
            return false;

        }
    }
    public boolean emailExists(String email) throws Exception 
    {

        String sql = """
                SELECT 1
                FROM users
                WHERE email = ?
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        	) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) 
            {

                return rs.next();

            }

        } 
        catch (SQLException e) 
        {

            e.printStackTrace();
            return false;

        }
    }
    public User login(String email, String password) throws Exception {

        String sql = """
                SELECT *
                FROM users
                WHERE email = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String storedHash = rs.getString("password_hash");

                    if (BCrypt.checkpw(password, storedHash)) 
                    {

                        User user = new User();

                        user.setUserId(rs.getInt("user_id"));
                        user.setUsername(rs.getString("username"));
                        user.setEmail(rs.getString("email"));
                        user.setPasswordHash(storedHash);
                        user.setCreatedAt(rs.getTimestamp("created_at"));

                        return user;
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}