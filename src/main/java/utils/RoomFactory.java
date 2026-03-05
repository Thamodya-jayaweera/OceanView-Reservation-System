package utils;

import model.Rooms.*;

public class RoomFactory {
        public static IRoom createRoom(String roomType){
            if (roomType == null) {
                throw new IllegalArgumentException("Room type cannot be null");
            }

            roomType = roomType.toUpperCase();

            if (roomType.equals(RoomType.SINGLE.toString())){
                return new SingleRoom();
            }else if (roomType.equals(RoomType.DOUBLE.toString())){
                return new DoubleRoom();
            }else if (roomType.equals(RoomType.TRIPLE.toString())){
                return new TripleRoom();
            }else if (roomType.equals(RoomType.SUITE.toString())){
                return new SuiteRoom();
            }
            return null;
        }




    }
