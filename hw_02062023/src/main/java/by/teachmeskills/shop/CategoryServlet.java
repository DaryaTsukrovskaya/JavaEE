package by.teachmeskills.shop;


import by.teachmeskills.shop.listener.DBConnectionManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("category.jsp");
        req.setAttribute("categoryName", req.getParameter("name"));
        ServletContext servletContext = getServletContext();
        DBConnectionManager dbConnectionManager = (DBConnectionManager) servletContext.getAttribute("DBManager");
        Connection connection = dbConnectionManager.getConnection();
        req.setAttribute("categoryProducts", CRUDUtils.getCategoryProducts(req.getParameter("name"),connection));
        requestDispatcher.forward(req, resp);
    }
}
