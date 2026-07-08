package com.himamanth.pastebin.servlet;

import java.io.IOException;

import com.himamanth.pastebin.dao.UserDAO;
import com.himamanth.pastebin.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet 
{

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
        	if (!isValidPassword(password)) 
            {

                request.setAttribute(
                        "error",
                        "Password must be at least 12 characters and contain uppercase, lowercase and a digit.");

                request.getRequestDispatcher("register.jsp").forward(request, response);

                return;
            }


            // Check username
            if (userDAO.usernameExists(username)) {

                request.setAttribute("error", "Username already exists.");
                request.getRequestDispatcher("register.jsp")
                       .forward(request, response);
                return;
            }

            // Check email
            if (userDAO.emailExists(email)) {

                request.setAttribute("error", "Email already registered.");
                request.getRequestDispatcher("register.jsp")
                       .forward(request, response);
                return;
            }

            // Register user
            User user = new User(username, email, password);

            boolean registered = userDAO.registerUser(user);

            if (registered) {

                // PRG Pattern
            	response.sendRedirect(request.getContextPath() + "/login.jsp");

            } else {

                request.setAttribute("error", "Registration failed.");
                request.getRequestDispatcher("/register.jsp")
                       .forward(request, response);
            }

        } catch (Exception e) {

            
            e.printStackTrace();

            
            request.setAttribute(
                    "error",
                    "Something went wrong. Please try again later."
            );

            request.getRequestDispatcher("register.jsp")
                   .forward(request, response);
        }
    }
    private boolean isValidPassword(String password) {

        if (password == null || password.length() < 12) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char ch : password.toCharArray()) {

            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isLowerCase(ch)) {
                hasLower = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            }
        }

        return hasUpper && hasLower && hasDigit;
    }
}