package com.himamanth.pastebin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}