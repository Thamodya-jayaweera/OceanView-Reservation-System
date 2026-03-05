package dao;

import model.Rooms.IRoom;
import utils.Dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RoomDAO {
    public boolean saveRoom(IRoom room) {

        String sql = "INSERT INTO rooms (room_number, room_type) VALUES (?, ?)";

        try (Connection con = Dbconnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room.getRoomType().name());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
