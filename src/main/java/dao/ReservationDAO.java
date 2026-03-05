package dao;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import model.Rooms.IRoom;
import model.Rooms.Reservation_room;
import model.Rooms.RoomType;
import utils.Dbconnection;
import model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.sql.ResultSet;


public class ReservationDAO {

    public boolean saveReservation(Reservation reservation) {

        String sql = "INSERT INTO reservations " +
                "(reservation_number, guest_id, check_in, check_out ) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection con = Dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, reservation.getReservationNumber());
            ps.setLong(2, reservation.getGuestId());
            ps.setObject(3, reservation.getCheckIn());
            ps.setObject(4, reservation.getCheckOut());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
    public  List<Long> getAvailableRoomNumbers(List<String> types, LocalDateTime checkIn, LocalDateTime checkOut) {
        List<Long> availableRooms = new ArrayList<>();
        if (types == null || types.isEmpty()) {
            return availableRooms;
        }

        // build placeholders
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < types.size(); i++) {
            placeholders.append("?");
            if (i < types.size() - 1) {
                placeholders.append(",");
            }
        }

        String sql = "SELECT r.room_number FROM rooms r " +
                "WHERE r.room_type IN (" + placeholders.toString() + ") " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 " +
                "    FROM reservation_room rr " +
                "    JOIN reservations res ON rr.reservation_number = res.reservation_number " +
                "    WHERE rr.room_number = r.room_number " +
                "      AND rr.status = 'BOOKED' " +
                "      AND NOT (res.check_out <= ? OR res.check_in >= ?) " +
                ")";

        try (Connection con = Dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int index = 1;
            // set room types
            for (String type : types) {
                ps.setString(index++, type);
            }

            // set checkIn and checkOut
            ps.setTimestamp(index++, Timestamp.valueOf(checkIn));
            ps.setTimestamp(index, Timestamp.valueOf(checkOut));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                availableRooms.add(rs.getLong("room_number"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableRooms;
    }

    public JsonArray getAllReservationDetails(){
        String sql = "select * from reservations r join guests g on r.guest_id = g.guest_id;";

        try (PreparedStatement ps = Dbconnection.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            JsonArrayBuilder allReservations = Json.createArrayBuilder();
            while (rs.next()){
                JsonObject reservationDetails = Json.createObjectBuilder()
                        .add("reservationNumber", rs.getString("reservation_number"))
                        .add("checkIn", rs.getString("check_in"))
                        .add("checkOut", rs.getString("check_out"))
                        .add("name", rs.getString("name"))
                        .add("address", rs.getString("address"))
                        .add("contactNo", rs.getString("contact_number"))
                        .build();

                allReservations.add(reservationDetails);
            }
            JsonArray allReservationsList = allReservations.build();
            return allReservationsList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonObject getReservationDetails(Long resNo){
        String sql = "select * from reservations r join guests g on r.guest_id = g.guest_id where r.reservation_number = ?;";

        try (PreparedStatement ps = Dbconnection.getConnection().prepareStatement(sql)) {

            ps.setLong(1, resNo);


            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                JsonObject reservationDetails = Json.createObjectBuilder()
                        .add("reservationNumber", rs.getString("reservation_number"))
                        .add("checkIn", rs.getString("check_in"))
                        .add("checkOut", rs.getString("check_out"))
                        .add("name", rs.getString("name"))
                        .add("address", rs.getString("address"))
                        .add("contactNo", rs.getString("contact_number"))
                        .build();

                return reservationDetails;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonArray getRoomDetailsByReservation(Long resNo){
        String sql = "select * from reservation_room join rooms r on r.room_number = reservation_room.room_number where reservation_number= ?;";

        try (PreparedStatement ps = Dbconnection.getConnection().prepareStatement(sql)) {

            ps.setLong(1, resNo);


            ResultSet rs = ps.executeQuery();

            JsonArrayBuilder roomsBuilder = Json.createArrayBuilder();

            while (rs.next()) {
                JsonObject roomdetails = Json.createObjectBuilder()
                        .add("roomNumber", rs.getInt("room_number"))
                        .add("roomType", rs.getString("room_type"))
                        .build();

                roomsBuilder.add(roomdetails);
            }

            JsonArray rooms = roomsBuilder.build();

            return rooms;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    }






