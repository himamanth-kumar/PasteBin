//PRG Pattern
package com.himamanth.pastebin.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.himamanth.pastebin.dao.UserDAO;
import com.himamanth.pastebin.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Check username
        try {
			if(userDAO.usernameExists(username)) 
			{

			    request.setAttribute(
			            "error",
			            "Username already exists."
			    );

			    request.getRequestDispatcher("register.jsp")
			            .forward(request, response);

			    return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Check email
        try {
			if(userDAO.emailExists(email)) 
			{

			    request.setAttribute(
			            "error",
			            "Email already registered."
			    );

			    request.getRequestDispatcher("register.jsp")
			            .forward(request, response);

			    return;
			}
		} 
        catch (ServletException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        catch (IOException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        catch (Exception e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        User user = new User(
                username,
                email,
                password
        );

        boolean registered = false;
		try 
		{
			registered = userDAO.registerUser(user);
			} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if(registered)
        {

            response.sendRedirect("login.jsp");

        } 
        else 
	        {
	
	            request.setAttribute(
	                    "error",
	                    "Registration failed."
	            );
	
	            request.getRequestDispatcher("register.jsp")
	                    .forward(request, response);
	
	        }

    }

}