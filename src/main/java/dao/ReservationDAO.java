package dao;

import model.Reservation;
import utils.Dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReservationDAO {

    public boolean save(Reservation reservation) {

        String sql = "INSERT INTO reservations " +
                "(reservation_number, guest_name, address, contact_number, room_type, check_in, check_out) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reservation.getReservationNumber());
            ps.setString(2, reservation.getGuestName());
            ps.setString(3, reservation.getAddress());
            ps.setString(4, reservation.getContactNumber());
            ps.setString(5, reservation.getRoomType());
            ps.setDate(6, java.sql.Date.valueOf(reservation.getCheckIn()));
            ps.setDate(7, java.sql.Date.valueOf(reservation.getCheckOut()));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
