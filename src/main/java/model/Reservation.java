package model;

import model.Rooms.IRoom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Reservation {
    private String reservationNumber; // VARCHAR(20) match
    private int guestId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    public Reservation(String reservationNumber, int guestId, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.reservationNumber = reservationNumber;
        this.guestId = guestId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

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

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }
}