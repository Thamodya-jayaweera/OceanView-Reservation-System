package dao;
import utils.Dbconnection;
import model.Guest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GuestDAO {
    public Integer saveGuest(Guest guest) {
        String sql = "INSERT INTO guests (name, address, contact_number) VALUES (?, ?, ?);";

        try (PreparedStatement ps = Dbconnection.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, guest.getName());
            ps.setString(2, guest.getAddress());
            ps.setString(3, guest.getContactNumber());
            int affectedRows =  ps.executeUpdate();
            if(affectedRows > 0){
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // return generated guest id
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
