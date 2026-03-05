package model.Rooms;

public class TripleRoom implements IRoom{


    @Override
    public String getRoomNumber() {
        return "";
    }

    @Override
    public void setRoomNumber(String roomNumber) {

    }

    @Override
    public double getRoomPrice() {
        return 25000;
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
        return RoomType.TRIPLE.toString();
    }
}
