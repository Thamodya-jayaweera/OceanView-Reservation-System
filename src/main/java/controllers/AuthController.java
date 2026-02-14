package controllers;

import Service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/*")
public class AuthController extends HttpServlet {

    private AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if ("/register".equals(path)) {

            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String fullname = req.getParameter("fullname");

            boolean success = authService.register(username, password, fullname);

            if (success) {
                out.print("{\"message\":\"User registered successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"message\":\"User already exists\"}");
            }

        } else if ("/login".equals(path)) {

            String username = req.getParameter("username");
            String password = req.getParameter("password");

            String token = authService.login(username, password);

            if (token != null) {
                out.print("{\"token\":\"" + token + "\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"message\":\"Invalid credentials\"}");
            }
        }
    }
}
