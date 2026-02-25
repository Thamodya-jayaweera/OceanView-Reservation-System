package model;

import model.Rooms.IRoom;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Reservation {

    private String reservationNumber;
    private String guestName;
    private String address;
    private String contactNumber;

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<IRoom> rooms;   // 🔥 multiple rooms

    public Reservation(String reservationNumber, String guestName,
                       String address, String contactNumber,
                       LocalDate checkIn, LocalDate checkOut,
                       List<IRoom> rooms) {

        this.reservationNumber = reservationNumber;
        this.guestName = guestName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.rooms = rooms;
    }

    public long getDays() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public double getTotalPrice() {
        double total = 0;
        for (IRoom room : rooms) {
            total += room.calculatePrice((int) getDays());
        }
        return total;
    }

    public List<IRoom> getRooms() {
        return rooms;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public void setRooms(List<IRoom> rooms) {
        this.rooms = rooms;
    }

}