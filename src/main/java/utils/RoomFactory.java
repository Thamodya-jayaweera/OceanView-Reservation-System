package utils;

import model.Rooms.*;

public class RoomFactory {
    public static IRoom createRoom(RoomType type) {

        switch (type) {
            case SINGLE:
                return new SingleRoom();
            case DOUBLE:
                return new DoubleRoom();
            case TRIPLE:
                return new TripleRoom();
            case SUITE:
                return new SuiteRoom();
            default:
                throw new IllegalArgumentException("Invalid Room Type");
        }
    }



}
