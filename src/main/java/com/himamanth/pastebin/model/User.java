package com.himamanth.pastebin.model;

import java.sql.Timestamp;

/**
 * User model represents one row of the users table.
 * It stores all information related to a user in one object.
 * This makes it easy to pass user data between different classes.
 */
public class User
{
	private int userId;
	private String username;
	private String email;
	private String passwordHash;
	private Timestamp createdAt;
	public User()
	{
		
	}
	public User(String username, String email, String passwordHash)
	{
		this.username = username;
		this.email= email;
		this.passwordHash = passwordHash;
	}
	public void setEmail(String email)
	{
		if(email != null && email.contains("@"))
		{
			this.email = email;
		}
	}
	public String getEmail()
	{
		return email;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	public int getUserId()
	{
		return userId;
	}
	public void setUsername(String username)
	{
		if(username != null && 
							username.length() >= 4 &&
							username.length() <= 20)
		{
			this.username = username;
		}
	}
	public String getUsername()
	{
		return username;
	}
	public String getPasswordHash() 
	{
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) 
	{
		
		if(passwordHash != null && passwordHash.contains("'a'-z"))
		{
			this.passwordHash=passwordHash;
		}
	}
	public Timestamp getCreatedAt() 
	{
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) 
	{
		this.createdAt = createdAt;
	}
	
}