package model.Rooms;

public class Reservation_room {
    private Integer id;
    private String reservationNumber;
    private int roomNumber;
    private String status;

    public Reservation_room(Integer id, String reservationNumber, int roomNumber, String status) {
        this.id = id;
        this.reservationNumber = reservationNumber;
        this.roomNumber = roomNumber;
        this.status = status;
    }

    public Reservation_room(String reservationNumber, int roomNumber, String status) {
        this.reservationNumber = reservationNumber;
        this.roomNumber = roomNumber;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}



