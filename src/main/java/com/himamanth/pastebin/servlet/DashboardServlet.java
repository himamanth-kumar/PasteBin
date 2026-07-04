package com.himamanth.pastebin.servlet;

import java.io.IOException;
import java.util.List;

import com.himamanth.pastebin.dao.PasteDAO;
import com.himamanth.pastebin.model.Paste;
import com.himamanth.pastebin.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private PasteDAO pasteDAO = new PasteDAO();

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            						throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) 
        {
        	response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) 
        {
        	response.sendRedirect("login.jsp");
            return;
        }

        try 
        {

            List<Paste> pastes =pasteDAO.getUserPastes(user.getUserId());

            request.setAttribute("pastes", pastes);

            request.getRequestDispatcher("dashboard.jsp").forward(request, response);

        } 
        catch (Exception e) 
        {

            throw new ServletException(e);

        }

    }

}