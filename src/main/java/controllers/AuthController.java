package controllers;
import Service.AuthService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
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

            JsonReader reader = Json.createReader(req.getInputStream());
            JsonObject json = reader.readObject();
            reader.close();

            String username = json.getString("username");
            String password = json.getString("password");
            String fullname = json.getString("fullname");

            boolean success = authService.register(username, password, fullname);

            if (success) {
                out.print("{\"message\":\"User registered successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"message\":\"User already exists\"}");
            }

        } else if ("/login".equals(path)) {

            JsonReader reader = Json.createReader(req.getInputStream());
            JsonObject json = reader.readObject();
            reader.close();

            String username = json.getString("username");
            String password = json.getString("password");


            if (username == null || password == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"message\":\"Username and password required\"}");
                return;
            }

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
