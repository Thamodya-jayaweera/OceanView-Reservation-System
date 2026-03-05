package controllers;

import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

import Service.ReservationService;
import dao.ReservationDAO;
import jakarta.json.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import model.Rooms.RoomType;

@WebServlet("/reservations/*")
public class ReservationController extends HttpServlet {

    ReservationService reservationService = new ReservationService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getInputStream());
        JsonObject json = reader.readObject();
        reader.close();

        LocalDateTime checkIn = null;
        LocalDateTime checkOut = null;
        String name = json.getString("name");
        String address = json.getString("address");
        String contactNo = json.getString("contactNo");
        JsonArray jsonArray = json.getJsonArray("roomList");

        List<Integer> rooms = new ArrayList<>();

        for(JsonValue room : jsonArray){
            rooms.add(Integer.parseInt(room.toString()));
        }
        try {
            checkIn = LocalDateTime.parse(json.getString("checkIn"));
            checkOut = LocalDateTime.parse(json.getString("checkOut"));

        }catch (Exception e){
            e.printStackTrace();
        }

        if (contactNo == null || contactNo.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("CONTACT_NO_MISSING");
            return;
        }

        if (name == null || name.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("NAME_MISSING");
            return;
        }

        if (address == null || address.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("ADDRESS_MISSING");
            return;
        }

        if (checkIn == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("CHECKIN_DATE_MISSING");
            return;
        }

        if (checkOut == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("CHECKOUT_DATE_MISSING");
            return;
        }

        if (rooms.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("ROOMS_MISSING");
            return;
        }

        Long reservationNo = reservationService.saveReservation(checkIn,checkOut,name,address,contactNo,rooms);

        if (reservationNo!=null){
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("RESERVATION_SUCCESS:"+reservationNo.toString());
        }
        else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("RESERVATION_FAILED");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String res_id = req.getParameter("res_id") ;

        if (res_id == null || res_id.isBlank()){
            //id no present --- all reservations
            ReservationDAO reservationDAO = new ReservationDAO();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(reservationDAO.getAllReservationDetails().toString());
        }else {
            //id present --- reservation details
            ReservationDAO reservationDAO = new ReservationDAO();
            JsonObject reservationDetails = reservationDAO.getReservationDetails(Long.parseLong(res_id));
            if (reservationDetails == null){
                // no reservation found
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonObject errorJson = Json.createObjectBuilder()
                        .add("error", "NO_RESERVATIONS_FOUND")
                        .build();
                resp.getWriter().write(errorJson.toString());
            }else {
                JsonArray roomsDetails = reservationDAO.getRoomDetailsByReservation(Long.parseLong(res_id));
                if (roomsDetails==null){
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    JsonObject errorJson = Json.createObjectBuilder()
                            .add("error", "NO_ROOMS_FOUND")
                            .build();
                    resp.getWriter().write(errorJson.toString());
                }
                else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    JsonObject responce = Json.createObjectBuilder()
                            .add("reservationDetails",reservationDetails)
                            .add("roomsList",roomsDetails).build();
                    resp.getWriter().write(responce.toString());
                }
            }


        }
    }


}