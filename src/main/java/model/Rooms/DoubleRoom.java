package model.Rooms;

public class DoubleRoom implements IRoom{


    @Override
    public double getRoomPrice() {
        return 15000;
    }

    @Override
    public double calculatePrice(int days) {
        return days*getRoomPrice();
    }

    @Override
    public String getType() {
        return RoomType.DOUBLE.toString();
    }
}
