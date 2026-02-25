package dao;

import model.Reservation;
import model.Rooms.IRoom;
import utils.Dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ReservationDAO {

    // 🔥 SAVE (Multiple Rooms Support)
    public boolean save(Reservation reservation) {

        Connection con = null;

        try {
            con = Dbconnection.getConnection();
            con.setAutoCommit(false); // transaction start

            // 1️⃣ Insert into reservations table
            String sql1 = "INSERT INTO reservations " +
                    "(reservation_number, guest_name, address, contact_number, check_in, check_out) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps1 = con.prepareStatement(sql1);

            ps1.setString(1, reservation.getReservationNumber());
            ps1.setString(2, reservation.getGuestName());
            ps1.setString(3, reservation.getAddress());
            ps1.setString(4, reservation.getContactNumber());
            ps1.setDate(5, java.sql.Date.valueOf(reservation.getCheckIn()));
            ps1.setDate(6, java.sql.Date.valueOf(reservation.getCheckOut()));

            ps1.executeUpdate();


            // 2️⃣ Insert into reservation_room table
            String sql2 = "INSERT INTO reservation_room " +
                    "(reservation_number, room_number, status) VALUES (?, ?, ?)";

            for (IRoom room : reservation.getRooms()) {

                PreparedStatement ps2 = con.prepareStatement(sql2);

                ps2.setString(1, reservation.getReservationNumber());

                // ⚠ Here you should pass real room_number
                ps2.setInt(2, 0); // temporary (update later with real room_number)

                ps2.setString(3, "BOOKED");

                ps2.executeUpdate();
            }

            con.commit(); // commit transaction
            return true;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ignored) {}
            e.printStackTrace();
        }

        return false;
    }

    // 🔥 SEARCH (Without room loading for now)
    public Reservation findByReservationNumber(String number) {

        String sql = "SELECT * FROM reservations WHERE reservation_number=?";

        try (Connection con = Dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Reservation(
                        rs.getString("reservation_number"),
                        rs.getString("guest_name"),
                        rs.getString("address"),
                        rs.getString("contact_number"),
                        rs.getDate("check_in").toLocalDate(),
                        rs.getDate("check_out").toLocalDate(),
                        new ArrayList<>() // rooms load karanna next step eke
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}