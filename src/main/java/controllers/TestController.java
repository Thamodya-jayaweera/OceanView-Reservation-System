package controllers;

import Service.TestServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/test")
public class TestController extends HttpServlet {

    TestServices testServices = new TestServices();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            testServices.TestGet(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Internal Server Error");
        }
    }
}
