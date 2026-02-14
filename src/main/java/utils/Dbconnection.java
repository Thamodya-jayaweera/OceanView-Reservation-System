package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/oceanhoteldb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Root@1234";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("MySQL connected");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
