package model;

import model.Rooms.IRoom;

import java.time.LocalDate;
import java.util.List;

public class Reservation {

    private String reservationNumber;
    private int guestId;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private List<IRoom> rooms;

    // ===============================
    // GETTERS & SETTERS
    // ===============================

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
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

    public List<IRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<IRoom> rooms) {
        this.rooms = rooms;
    }
}