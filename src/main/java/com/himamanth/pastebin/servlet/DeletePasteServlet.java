package com.himamanth.pastebin.servlet;

import java.io.IOException;

import com.himamanth.pastebin.dao.PasteDAO;
import com.himamanth.pastebin.model.Paste;
import com.himamanth.pastebin.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/deletePaste")
public class DeletePasteServlet extends HttpServlet {

    private PasteDAO pasteDAO = new PasteDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            						throws ServletException, IOException 
    {

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

            int pasteId =Integer.parseInt(request.getParameter("id"));

            Paste paste = pasteDAO.getPasteById(pasteId);

            if (paste == null) {

                response.sendError(HttpServletResponse.SC_NOT_FOUND);

                return;
            }

            if (paste.getUser().getUserId() != user.getUserId()) 
            {

                response.sendError(HttpServletResponse.SC_FORBIDDEN);

                return;
            }

            boolean deleted =pasteDAO.deletePaste(pasteId);

            if (deleted)
            {

                response.sendRedirect("dashboard");

            } else 
            {

                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            }

        } 
        catch (Exception e) 
        {

            throw new ServletException(e);

        }

    }

}