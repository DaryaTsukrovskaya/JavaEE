package by.teachmeskills.shop;


import by.teachmeskills.shop.listener.DBConnectionManager;
import by.teachmeskills.shop.model.Category;
import by.teachmeskills.shop.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;


@WebServlet("/login")
public class AppServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (((User) req.getSession().getAttribute("user")).getName().equals("isEmpty")) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
            dispatcher.forward(req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        if (name != null && password != null) {
            ServletContext servletContext = getServletContext();
            DBConnectionManager dbConnectionManager = (DBConnectionManager) servletContext.getAttribute("DBManager");
            Connection connection = dbConnectionManager.getConnection();
            User user = CRUDUtils.getUser(name, connection);
            if (user != null && user.getPassword().equals(password)) {
                req.getSession().setAttribute("user", user);
            } else if (user == null) {
                user = new User(name, password);
                CRUDUtils.createUser(user, connection);
                req.getSession().setAttribute("user", user);
            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
                requestDispatcher.forward(req, resp);
            }
            req.setAttribute("categories", CRUDUtils.getCategories(connection));
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("home.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
