package com.himamanth.pastebin.model;

import java.sql.Timestamp;

/**
 * Category model represents one row of the categories table.
 * It stores information related to a paste category.
 */
public class Category {

    private int categoryId;
    private String name;
    private Timestamp createdAt;

    
    public Category() {

    }

    
    public Category(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (name != null && !name.isBlank()) {
            this.name = name;
        }
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}