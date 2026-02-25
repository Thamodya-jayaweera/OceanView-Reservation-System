package model.Rooms;

public class SuiteRoom implements IRoom{


    @Override
    public double getRoomPrice() {
        return 250000;
    }

    @Override
    public double calculatePrice(int days) {
        return days*getRoomPrice();
    }

    @Override
    public String getType() {
        return RoomType.SUITE.toString();
    }
}
