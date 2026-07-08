package com.himamanth.pastebin.servlet;

import java.io.IOException;

import com.himamanth.pastebin.dao.PasteDAO;
import com.himamanth.pastebin.model.Paste;
import com.himamanth.pastebin.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/p/*")
public class ViewPasteServlet extends HttpServlet 
{

    private PasteDAO pasteDAO = new PasteDAO();

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            			throws ServletException, IOException 
    {

        try {

        	String publicId = request.getPathInfo();

        	if (publicId == null || publicId.length() <= 1) 
        	{
        		response.sendRedirect("dashboard");
        	    return;
        	}

        	publicId = publicId.substring(1);
        	

        	Paste paste = pasteDAO.getPasteByPublicId(publicId);
        	

            if (paste == null) 
            {

                response.sendError(HttpServletResponse.SC_NOT_FOUND);

                return;
            }
            HttpSession session = request.getSession(false);

            User loggedUser = null;

            if (session != null) 
            {
                loggedUser = (User) session.getAttribute("user");
            }

            if ("PRIVATE".equalsIgnoreCase(paste.getVisibility())) {

                if (loggedUser == null ||loggedUser.getUserId() != paste.getUser().getUserId()) {

                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }

            request.setAttribute("paste", paste);

            request.getRequestDispatcher("/viewPaste.jsp").forward(request, response);

        } 
        catch (Exception e) {

            throw new ServletException(e);

        }

    }

}