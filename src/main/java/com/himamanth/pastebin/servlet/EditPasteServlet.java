package com.himamanth.pastebin.servlet;

import java.io.IOException;

import com.himamanth.pastebin.dao.PasteDAO;
import com.himamanth.pastebin.model.Category;
import com.himamanth.pastebin.model.Paste;
import com.himamanth.pastebin.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/editPaste")
public class EditPasteServlet extends HttpServlet {

    private PasteDAO pasteDAO = new PasteDAO();

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            					throws ServletException, IOException {

        try {

            int pasteId = Integer.parseInt(request.getParameter("id"));

            Paste paste = pasteDAO.getPasteById(pasteId);

            if (paste == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            HttpSession session =request.getSession(false);

            User loggedUser =
                    (User) session.getAttribute("user");

            if (loggedUser == null || paste.getUser().getUserId()!= loggedUser.getUserId()) 
            {

                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            request.setAttribute("paste", paste);

            request.getRequestDispatcher("editPaste.jsp").forward(request, response);

        } 
        catch (Exception e) 
        {
            throw new ServletException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
    						throws ServletException, IOException {

        try {

            int pasteId =Integer.parseInt(request.getParameter("pasteId"));

            String title =request.getParameter("title");

            String content =request.getParameter("content");

            String visibility = request.getParameter("visibility");

            int categoryId =Integer.parseInt(request.getParameter("categoryId"));

            HttpSession session =request.getSession(false);

            User user =(User) session.getAttribute("user");

            Category category = new Category();
            category.setCategoryId(categoryId);

            Paste paste = new Paste();

            paste.setPasteId(pasteId);
            paste.setTitle(title);
            paste.setContent(content);
            paste.setVisibility(visibility);
            paste.setCategory(category);
            paste.setUser(user);

            boolean updated = pasteDAO.updatePaste(paste);

            if (updated) 
            {

                response.sendRedirect("paste?id=" + pasteId);

            } else {

                request.setAttribute( "error", "Unable to update paste.");

                request.setAttribute
                (
                        "paste",
                        paste);

                request.getRequestDispatcher("editPaste.jsp")
                        .forward(request, response);

            }

        } 
        catch (Exception e) 
        {

            throw new ServletException(e);

        }

    }

}