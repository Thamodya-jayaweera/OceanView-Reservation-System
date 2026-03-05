package dao;

import model.Rooms.Reservation_room;
import utils.Dbconnection;

import java.sql.PreparedStatement;

public class ReservationRoomDAO {
    public ReservationRoomDAO(Long reservationId, Long roomId, String booked) {

    }

    public boolean save(Reservation_room reservations2Rooms) {
        String sql = "INSERT INTO reservation_room (reservationNumber, roomNumber, status) VALUES ( ?, ?, ?);";
        try (PreparedStatement ps = Dbconnection.getConnection().prepareStatement(sql)) {
            ps.setLong(1, reservations2Rooms.getReservationNumber());
            ps.setInt(2, reservations2Rooms.getRoomNumber());
            ps.setString(3, reservations2Rooms.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public ReservationRoomDAO() {
    }
}
