package by.teachmeskills;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/math")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        int num1 = 8;
        int num2 = 4;
        String sumResult = num1 + " + " + num2 + " = " + Calculator.sum(num1, num2);
        String subResult = num1 + " - " + num2 + " = " + Calculator.sub(num1, num2);
        String multResult = num1 + " * " + num2 + " = " + Calculator.mult(num1, num2);
        String divisResult = num1 + " / " + num2 + " = " + Calculator.div(num1, num2);
        req.setAttribute("sum", sumResult);
        req.setAttribute("sub", subResult);
        req.setAttribute("mult", multResult);
        req.setAttribute("divis", divisResult);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(req, resp);
    }
}