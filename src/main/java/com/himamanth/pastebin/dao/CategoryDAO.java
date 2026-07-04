package com.himamanth.pastebin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.himamanth.pastebin.model.Category;
import com.himamanth.pastebin.util.DBConnection;

public class CategoryDAO {

    /**
     * Returns all categories.
     */
    public List<Category> getAllCategories() throws Exception 
    {

        List<Category> categories = new ArrayList<>();

        String sql = """
                SELECT *
                FROM categories
                ORDER BY name
                """;

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) 
        {

            while (rs.next()) 
            {

                Category category = new Category();

                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setCreatedAt(rs.getTimestamp("created_at"));

                categories.add(category);
            }

        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return categories;
    }

    /**
     * Returns one category using category id.
     */
    public Category getCategoryById(int categoryId) throws Exception {

        String sql = """
                SELECT *
                FROM categories
                WHERE category_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) 
        {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) 
            {

                if (rs.next()) 
                {

                    Category category = new Category();

                    category.setCategoryId(rs.getInt("category_id"));
                    category.setName(rs.getString("name"));
                    category.setCreatedAt(rs.getTimestamp("created_at"));

                    return category;
                }

            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return null;
    }

}