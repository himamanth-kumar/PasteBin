package com.himamanth.pastebin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.himamanth.pastebin.model.Category;
import com.himamanth.pastebin.model.Paste;
import com.himamanth.pastebin.model.User;
import com.himamanth.pastebin.util.DBConnection;

public class PasteDAO 
{

    /**
     * Creates a new paste.
     */
    public boolean createPaste(Paste paste) throws Exception 
    {

        String sql = """
                INSERT INTO pastes
                (public_id, title, content, visibility, user_id, category_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
        	ps.setString(1, paste.getPublicId());
            ps.setString(2, paste.getTitle());
            ps.setString(3, paste.getContent());
            ps.setString(4, paste.getVisibility());
            ps.setInt(5, paste.getUser().getUserId());
            ps.setInt(6, paste.getCategory().getCategoryId());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } 
        catch (SQLException e) 
        {

            e.printStackTrace();
            return false;
        }
    }
    public Paste getPasteByPublicId(String publicId) throws Exception
    {
    	String sql = """
                SELECT *
                FROM pastes
                WHERE public_id = ?
                """;
    	try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, publicId);

            try (ResultSet rs = ps.executeQuery()) 
            {

                if (rs.next()) 
                {

                    Paste paste = new Paste();
                    paste.setPublicId(rs.getString("public_id"));
                    paste.setPasteId(rs.getInt("paste_id"));
                    paste.setTitle(rs.getString("title"));
                    paste.setContent(rs.getString("content"));
                    paste.setVisibility(rs.getString("visibility"));
                    paste.setCreatedAt(rs.getTimestamp("created_at"));
                    paste.setUpdatedAt(rs.getTimestamp("updated_at"));

                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    paste.setUser(user);

                    Category category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    paste.setCategory(category);

                    return paste;
                }

            }

        } 
        catch (SQLException e) 
        {

            e.printStackTrace();
        }

        return null;
    }


    /**
     * Returns one paste using paste id.
     */
    public Paste getPasteById(int pasteId) throws Exception 
    {

        String sql = """
                SELECT *
                FROM pastes
                WHERE paste_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, pasteId);

            try (ResultSet rs = ps.executeQuery()) 
            {

                if (rs.next()) {

                    Paste paste = new Paste();
                    paste.setPublicId(rs.getString("public_id"));
                    paste.setPasteId(rs.getInt("paste_id"));
                    paste.setTitle(rs.getString("title"));
                    paste.setContent(rs.getString("content"));
                    paste.setVisibility(rs.getString("visibility"));
                    paste.setCreatedAt(rs.getTimestamp("created_at"));
                    paste.setUpdatedAt(rs.getTimestamp("updated_at"));

                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    paste.setUser(user);

                    Category category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    paste.setCategory(category);

                    return paste;
                }

            }

        } 
        catch (SQLException e) 
        {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns all pastes.
     */
    public List<Paste> getAllPastes() throws Exception 
    {

        List<Paste> pastes = new ArrayList<>();

        String sql = """
                SELECT *
                FROM pastes
                ORDER BY created_at DESC
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Paste paste = new Paste();

                paste.setPublicId(rs.getString("public_id"));
                paste.setPasteId(rs.getInt("paste_id"));
                paste.setTitle(rs.getString("title"));
                paste.setContent(rs.getString("content"));
                paste.setVisibility(rs.getString("visibility"));
                paste.setCreatedAt(rs.getTimestamp("created_at"));
                paste.setUpdatedAt(rs.getTimestamp("updated_at"));

                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                paste.setUser(user);

                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                paste.setCategory(category);

                pastes.add(paste);
            }

        } 
        catch (SQLException e) 
        {

            e.printStackTrace();
        }

        return pastes;
    }

    /**
     * Updates an existing paste.
     */
    public boolean updatePaste(Paste paste) throws Exception 
    {

        String sql = """
                UPDATE pastes
					SET title = ?,
					 content = ?,
					  visibility = ?,
					  category_id = ?,
					  updated_at = CURRENT_TIMESTAMP
					WHERE paste_id = ?
					AND user_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

        	ps.setString(1, paste.getTitle());
        	ps.setString(2, paste.getContent());
        	ps.setString(3, paste.getVisibility());
        	ps.setInt(4, paste.getCategory().getCategoryId());
        	ps.setInt(5, paste.getPasteId());
        	ps.setInt(6, paste.getUser().getUserId());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } 
        catch (SQLException e) 
        {

            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a paste.
     */
    public boolean deletePaste(int pasteId, int userId) throws Exception 
    {

        String sql = """
                DELETE FROM pastes
                WHERE paste_id = ?
                AND user_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, pasteId);
            ps.setInt(2, userId);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } 
        catch (SQLException e) 
        {

            e.printStackTrace();
            return false;
        }
    }
    public List<Paste> getUserPastes(int userId) throws Exception {

        List<Paste> pastes = new ArrayList<>();

        String sql = """
                SELECT *
                FROM pastes
                WHERE user_id = ?
                ORDER BY created_at DESC
                """;

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql) ) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) 
            {

                while (rs.next()) 
                {

                    Paste paste = new Paste();
                    
                    paste.setPublicId(rs.getString("public_id"));
                    paste.setPasteId(rs.getInt("paste_id"));
                    paste.setTitle(rs.getString("title"));
                    paste.setContent(rs.getString("content"));
                    paste.setVisibility(rs.getString("visibility"));
                    paste.setCreatedAt(rs.getTimestamp("created_at"));
                    paste.setUpdatedAt(rs.getTimestamp("updated_at"));

                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    paste.setUser(user);

                    Category category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    paste.setCategory(category);

                    pastes.add(paste);
                }

            }

        } 
        catch (SQLException e) 
        {

            e.printStackTrace();

        }

        return pastes;
    }
    
}