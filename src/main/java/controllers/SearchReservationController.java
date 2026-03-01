package controllers;

import Service.ReservationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Reservation;
import model.Rooms.IRoom;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/searchReservation")
public class SearchReservationController extends HttpServlet {

    private ReservationService reservationService = new ReservationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String reservationNumber = request.getParameter("reservationNumber");

        try {

            Reservation reservation =
                    reservationService.getReservation(reservationNumber);

            if (reservation == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"message\":\"Reservation Not Found\"}");
                return;
            }

            out.print("{");
            out.print("\"reservationNumber\":\"" + reservation.getReservationNumber() + "\",");
            out.print("\"guestName\":\"" + reservation.getGuestName() + "\",");
            out.print("\"address\":\"" + reservation.getAddress() + "\",");
            out.print("\"contactNumber\":\"" + reservation.getContactNumber() + "\",");
            out.print("\"checkIn\":\"" + reservation.getCheckIn() + "\",");
            out.print("\"checkOut\":\"" + reservation.getCheckOut() + "\",");

            // ✅ Total Price
            out.print("\"totalPrice\":\"" + reservation.getTotalPrice() + "\",");

            // ✅ Multiple Rooms
            out.print("\"rooms\":[");
            List<IRoom> rooms = reservation.getRooms();

            for (int i = 0; i < rooms.size(); i++) {
                out.print("\"" + rooms.get(i).getType() + "\"");

                if (i < rooms.size() - 1) {
                    out.print(",");
                }
            }
            out.print("]");

            out.print("}");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}