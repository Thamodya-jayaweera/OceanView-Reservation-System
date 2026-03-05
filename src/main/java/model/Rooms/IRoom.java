package model.Rooms;

public interface IRoom {
    String getRoomNumber();
    void setRoomNumber(String roomNumber);

    double getRoomPrice();
    double calculatePrice(int days);

    RoomType getRoomType();  // enum return

    String getType();
}
