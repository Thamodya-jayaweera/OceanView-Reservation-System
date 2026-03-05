package controllers;

import Service.ReservationService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Rooms.IRoom;
import model.Rooms.RoomType;
import utils.RoomFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@WebServlet("/rooms/")
public class RoomsController extends HttpServlet {
    ReservationService reservationService = new ReservationService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String operation = req.getParameter("operation");

        if (operation == null || operation.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            JsonObject errorJson = Json.createObjectBuilder()
                    .add("error", "OPERATION_MISSING")
                    .build();

            resp.getWriter().write(errorJson.toString());
            return;
        }

        if (operation.equals("getAvailableRooms")) {

            String roomTypeParam = req.getParameter("roomType");
            String checkInParam = req.getParameter("checkIn");

            if (roomTypeParam == null || roomTypeParam.isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                JsonObject errorJson = Json.createObjectBuilder()
                        .add("error", "ROOM_TYPE_MISSING")
                        .build();

                resp.getWriter().write(errorJson.toString());
                return;
            }

            if (checkInParam == null || checkInParam.isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                JsonObject errorJson = Json.createObjectBuilder()
                        .add("error", "CHECK_IN_MISSING")
                        .build();

                resp.getWriter().write(errorJson.toString());
                return;
            }

//            System.out.println(LocalDateTime.parse(checkInParam));
            List<Long> availableRoomNumbers = reservationService.getAvailableRooms(roomTypeParam, LocalDateTime.parse(checkInParam));

            // Build JSON array
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (Long room : availableRoomNumbers) {
                arrayBuilder.add(room);
            }

            JsonObject responseJson = Json.createObjectBuilder()
                    .add("availableRooms", arrayBuilder)
                    .build();

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(responseJson.toString());

        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            JsonObject errorJson = Json.createObjectBuilder()
                    .add("error", "INVALID_OPERATION")
                    .build();

            resp.getWriter().write(errorJson.toString());
        }
    }

}
