package com.himamanth.pastebin.servlet;

import java.io.IOException;

import com.himamanth.pastebin.dao.UserDAO;
import com.himamanth.pastebin.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException 
    {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = null;
		try 
		{
			user = userDAO.login(email, password);
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}

        if(user != null) {

            HttpSession session = request.getSession();

            session.setAttribute("user", user);

            response.sendRedirect(request.getContextPath() + "/dashboard");

        } 
        else {

            request.setAttribute(
                    "error",
                    "Invalid email or password."
            );

            response.sendRedirect(request.getContextPath() + "/login.jsp");

        }

    }

}