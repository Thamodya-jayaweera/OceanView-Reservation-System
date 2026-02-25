package controllers;

import Service.ReservationService;
import jakarta.json.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Reservation;
import model.Rooms.*;
import utils.RoomFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/reservation/*")
public class ReservationController extends HttpServlet {

    private ReservationService reservationService = new ReservationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if ("/add".equals(path)) {

            try (JsonReader reader = Json.createReader(req.getInputStream())) {

                JsonObject json = reader.readObject();

                String reservationNumber = json.getString("reservationNumber", null);
                String guestName = json.getString("guestName", null);
                String address = json.getString("address", null);
                String contactNumber = json.getString("contactNumber", null);
                String checkInStr = json.getString("checkIn", null);
                String checkOutStr = json.getString("checkOut", null);

                // 🔥 JSON array ekak widiyata roomTypes gannawa
                JsonArray roomArray = json.getJsonArray("roomTypes");

                if (reservationNumber == null || guestName == null ||
                        checkInStr == null || checkOutStr == null ||
                        roomArray == null || roomArray.isEmpty()) {

                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"message\":\"Required fields missing\"}");
                    return;
                }

                // 🔥 Multiple rooms create karanawa using Factory
                List<IRoom> rooms = new ArrayList<>();

                for (JsonValue value : roomArray) {
                    String type = value.toString().replace("\"", "");
                    RoomType roomType = RoomType.valueOf(type);
                    IRoom room = RoomFactory.createRoom(roomType);
                    rooms.add(room);
                }

                Reservation reservation = new Reservation(
                        reservationNumber,
                        guestName,
                        address,
                        contactNumber,
                        LocalDate.parse(checkInStr),
                        LocalDate.parse(checkOutStr),
                        rooms   // 🔥 list eka pass karanawa
                );

                boolean success = reservationService.addReservation(reservation);

                if (success) {
                    out.print("{\"message\":\"Reservation Added Successfully\"}");
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"message\":\"Failed to Add Reservation\"}");
                }

            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
}