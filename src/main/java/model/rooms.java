package model;

public class rooms {

    private String roomType;
    private String roomNumber;

    // Default Constructor
    public rooms() {
    }

    // Parameterized Constructor
    public rooms(String roomType, String roomNumber) {
        this.roomType = roomType;
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
