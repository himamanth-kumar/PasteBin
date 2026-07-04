package com.himamanth.pastebin.servlet;

import java.io.IOException;

import com.himamanth.pastebin.dao.PasteDAO;
import com.himamanth.pastebin.model.Paste;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/paste")
public class ViewPasteServlet extends HttpServlet 
{

    private PasteDAO pasteDAO = new PasteDAO();

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            			throws ServletException, IOException 
    {

        try {

            int pasteId =Integer.parseInt(request.getParameter("id"));

            Paste paste =pasteDAO.getPasteById(pasteId);

            if (paste == null) 
            {

                response.sendError(HttpServletResponse.SC_NOT_FOUND);

                return;
            }

            request.setAttribute(
                    "paste",
                    paste
            );

            request.getRequestDispatcher
            (
                    "viewPaste.jsp"
            ).forward(request, response);

        } 
        catch (Exception e) {

            throw new ServletException(e);

        }

    }

}