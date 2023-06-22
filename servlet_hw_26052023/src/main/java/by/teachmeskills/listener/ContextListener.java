package by.teachmeskills.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String login = servletContext.getInitParameter("dbuser");
        String password = servletContext.getInitParameter("dbpass");
        String url = servletContext.getInitParameter("dburl");
        DBConnectionManager dbConnectionManager = new DBConnectionManager(url, login, password);
        servletContext.setAttribute("DBManager", dbConnectionManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        DBConnectionManager dbConnectionManager = (DBConnectionManager) servletContext.getAttribute("DBManager");
        dbConnectionManager.closeConnection();
    }
}
