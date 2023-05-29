package by.teachmeskills.ps.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    private static String dbUrl = "jdbc:mysql://localhost:3306/PAYMENT_SYSTEM_DB";
    private static String dbUsername = "root";
    private static String dbPassword = "1111";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }
}
