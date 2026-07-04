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

@WebServlet("/createPaste")
public class CreatePasteServlet extends HttpServlet {

    private PasteDAO pasteDAO = new PasteDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Read form values
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String visibility = request.getParameter("visibility");

        int categoryId =Integer.parseInt(request.getParameter("categoryId"));

        // Get logged-in user from session
        HttpSession session = request.getSession(false);

        User user =(User) session.getAttribute("user");

        // Create Category object
        Category category = new Category();
        category.setCategoryId(categoryId);

        // Create Paste object
        Paste paste = new Paste();

        paste.setTitle(title);
        paste.setContent(content);
        paste.setVisibility(visibility);

        paste.setUser(user);
        paste.setCategory(category);

        try {

            boolean created =pasteDAO.createPaste(paste);

            if (created) 
            {

                response.sendRedirect("home.jsp");

            } 
            else {

                request.setAttribute(
                        "error",
                        "Unable to create paste."
                );

                request.getRequestDispatcher(
                        "createPaste.jsp"
                ).forward(request, response);

            }

        } catch (Exception e) {

            throw new ServletException(e);

        }

    }

}