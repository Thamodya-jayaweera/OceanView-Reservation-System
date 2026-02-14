
package Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Dbconnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestServices {

    public void TestGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {

        Connection connection = Dbconnection.getConnection();

        if (connection == null) {
            response.getWriter().write("DB Connection Failed");
            return;
        }

        String sql = "SELECT * FROM testdata";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            String data = resultSet.getString("col1");
            response.getWriter().write("Data from DB: " + data + "\n");
        }

        resultSet.close();
        ps.close();
        connection.close();
    }
}
