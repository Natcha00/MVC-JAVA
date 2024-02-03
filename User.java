import java.util.ArrayList;

class User {
    private String name;
    private ArrayList<Room> reserveRoom;

    public User(String name) {
        this.name = name;
        this.reserveRoom = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Room> getReserveRoom() {
        return this.reserveRoom;
    }

    public void resetReserveRoom() {
        this.reserveRoom = new ArrayList<>();
    }

    public void reserveRoom(Room room) {
        reserveRoom.add(room);
        room.setterUserReserve(this);
    }
}