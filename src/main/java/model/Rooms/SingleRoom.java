package model.Rooms;

public class SingleRoom implements IRoom{


    @Override
    public String getRoomNumber() {
        return "";
    }

    @Override
    public void setRoomNumber(String roomNumber) {

    }

    @Override
    public double getRoomPrice() {
        return 15000;
    }

    @Override
    public double calculatePrice(int days) {
        return days*getRoomPrice();
    }

    @Override
    public RoomType getRoomType() {
        return null;
    }

    @Override
    public String getType() {
        return RoomType.SINGLE.toString();
    }
}
