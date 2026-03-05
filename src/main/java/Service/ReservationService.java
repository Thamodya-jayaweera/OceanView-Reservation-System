package Service;

import dao.GuestDAO;
import dao.ReservationDAO;
import dao.ReservationRoomDAO;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import model.Guest;
import model.Reservation;
import model.Rooms.IRoom;
import model.Rooms.Reservation_room;
import utils.BookingNoGenerator;
import utils.RoomFactory;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationService {

    public Long saveReservation(LocalDateTime checkIn, LocalDateTime checkOut, String name, String address, String contactNo, List<Integer> roomList){
        //guest data save
        Guest guest = new Guest(name, address, contactNo);
        GuestDAO guestDAO = new GuestDAO();
        Integer saved_guestId = guestDAO.saveGuest(guest);

      BookingNoGenerator bookingNoGenarator = new  BookingNoGenerator();
        Long reservationId = bookingNoGenarator.genarate();

        Reservation reservation = new Reservation(reservationId.toString(), saved_guestId, checkIn, checkOut);

        ReservationDAO reservationDAO = new ReservationDAO();
        boolean isReservationSaved = reservationDAO.saveReservation(reservation);

        if (!isReservationSaved){
            return null;
        }

        for (Integer roomId : roomList) {

            Reservation_room reservationRoom = new Reservation_room(reservationId.toString(), roomId, "BOOKED");

            ReservationRoomDAO reservationRoomDAO = new ReservationRoomDAO();
            reservationRoomDAO.save(reservationRoom);

        }

        return reservationId;

    }

    JsonObject getReservationDetails(Long reservationNumber){
        return null;
    }

    JsonArray getAllReservations(){
        return null;
    }

    public List<Long> getAvailableRooms(String roomType, LocalDateTime checkIn){

        String standardRoomType = "";

        IRoom room = RoomFactory.createRoom(roomType);
        if(room==null){
            return null;
        }
        standardRoomType = room.getType();

        ReservationDAO reservationDAO = new ReservationDAO();
        List<Long> availableRooms =reservationDAO.getAvailableRoomNumbers(List.of(standardRoomType), checkIn , checkIn.plusDays(1));

      if (availableRooms.isEmpty()){
    return null;
}
        return availableRooms;
    }


}
