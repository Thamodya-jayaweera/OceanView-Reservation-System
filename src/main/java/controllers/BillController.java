package controllers;


import Service.BillService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/bill")
public class BillController extends HttpServlet {
    BillService billService = new BillService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomType = req.getParameter("roomType");
        String str_numberOfNights = req.getParameter("numberOfNights");

        if (roomType == null || roomType.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("ROOM_TYPE_MISSING");
            return;
        }
        if (str_numberOfNights == null || str_numberOfNights.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("NUMBER_OF_NIGHTS_MISSING");
            return;
        }

             int numberOfNights = 0;
        try {
            numberOfNights = Integer.parseInt(str_numberOfNights);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("INVALID_NUMBER_OF_NIGHTS");
            return;
        }


        double total = billService.calculateBill(numberOfNights,roomType);
        if(total!=0){
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Total:"+total);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("INVALID_ROOM_TYPE");
            return;
        }



    }
}
