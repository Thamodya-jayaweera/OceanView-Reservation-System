package dao;

import model.Rooms.Reservation_room;
import utils.Dbconnection;

import java.sql.PreparedStatement;

public class ReservationRoomDAO {

    public boolean save(Reservation_room reservation_room) {
        String sql = "INSERT INTO reservation_room (reservation_number, room_number, status) VALUES ( ?, ?, ?);";
        try (PreparedStatement ps = Dbconnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, reservation_room.getReservationNumber());
            ps.setInt(2, reservation_room.getRoomNumber());
            ps.setString(3, reservation_room.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public ReservationRoomDAO() {
    }
}
