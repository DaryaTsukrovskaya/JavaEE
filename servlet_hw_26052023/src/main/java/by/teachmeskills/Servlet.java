package by.teachmeskills;

import by.teachmeskills.listener.DBConnectionManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        try {
            ServletContext servletContext = getServletContext();
            DBConnectionManager dbConnectionManager = (DBConnectionManager) servletContext.getAttribute("DBManager");
            Connection connection = dbConnectionManager.getConnection();
            User user = null;
            PreparedStatement getUserStatement = connection.prepareStatement("SELECT * FROM users WHERE name=?");
            getUserStatement.setString(1, name);
            ResultSet set = getUserStatement.executeQuery();
            while (set.next()) {
                user = new User(set.getString(1), set.getString(2));
            }
            if (user != null && user.getPassword().equals(password)) {
                req.getSession().setAttribute("user", user);
            } else if (user == null) {
                PreparedStatement createUserStatement = connection.prepareStatement("INSERT INTO users(name,password) VALUES(?,?)");
                createUserStatement.setString(1, name);
                createUserStatement.setString(2, password);
                createUserStatement.execute();
                user = new User(name, password);
                req.getSession().setAttribute("user", user);
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("home.jsp");
            requestDispatcher.forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
