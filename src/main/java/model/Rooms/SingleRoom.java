package model.Rooms;

public class SingleRoom implements  IRoom{


    @Override
    public double getRoomPrice() {
        return 10000;
    }

    @Override
    public double calculatePrice(int days) {
        return days*getRoomPrice();
    }

    @Override
    public String getType() {
        return RoomType.SINGLE.toString();
    }
}
