package com.himamanth.pastebin.model;

import java.sql.Timestamp;

/**
 * Paste model represents one row of the pastes table.
 * It stores all information related to a paste.
 */
public class Paste {

    private int pasteId;
    private String title;
    private String content;
    private String visibility;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private User user;
    private Category category;

    
    public Paste() {

    }

    
    public Paste(String title, String content, String visibility,
                 User user, Category category) {

        this.title = title;
        this.content = content;
        this.visibility = visibility;
        this.user = user;
        this.category = category;
    }

    public int getPasteId() {
        return pasteId;
    }

    public void setPasteId(int pasteId) {
        this.pasteId = pasteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        if (title != null && !title.isBlank()) {
            this.title = title;
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {

        if (content != null && !content.isBlank()) {
            this.content = content;
        }
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {

        if ("PUBLIC".equalsIgnoreCase(visibility)
                || "PRIVATE".equalsIgnoreCase(visibility)) {

            this.visibility = visibility.toUpperCase();
        }
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}