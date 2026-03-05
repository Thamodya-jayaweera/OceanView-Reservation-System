package Service;


import model.Rooms.IRoom;
import utils.RoomFactory;

public class BillService {
    public double calculateBill(int numberOfNights , String roomType){
        IRoom selectedRoom = RoomFactory.createRoom(roomType);
        if(selectedRoom!=null) {
            double totalBill = selectedRoom.calculatePrice(numberOfNights);
            return totalBill;
        }
        else return 0;
    }
}
