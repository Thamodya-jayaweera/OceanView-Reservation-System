package model.Rooms;

public class TripleRoom implements  IRoom{


    @Override
    public double getRoomPrice() {
        return 20000;
    }

    @Override
    public double calculatePrice(int days) {
        return days*getRoomPrice();
    }

    @Override
    public String getType() {
        return RoomType.TRIPLE.toString();
    }
}
