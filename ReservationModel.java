import java.util.ArrayList;

class ReservationModel {
    private ArrayList<Game> games;
    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    
    public ReservationModel() {
        // Initialize 10 games with available=3
        games = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            int available = 3;
            if(i==10){
                //สมมติมีห้องอื่น เอาเกมไปหมดแล้ว
                available = 0;
            }
            Game game = new Game("Game " + i, available);
            games.add(game);
        }

        // Initialize 5 rooms
        rooms = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Room room = new Room(String.valueOf(i));
            rooms.add(room);
        }

        users = new ArrayList<>();

        // ห้องที่ 3 ถูกจอง
        Room r = rooms.get(2);
        r.setterUserReserve(new User("x"));
    }

    /* ดึงข้อมูล game */
    public ArrayList<Game> getGames() {
        return games;
    }

    /* ดึงข้อมูล room */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /* ดึงข้อมูล user */
    public ArrayList<User> getUsers() {
        return users;
    }

    /* ค้นหาห้อง */
    public Room findRoom(String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    /* ค้นหา user */
    public User findUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /* user ใหม่ที่ยังไม่เคยลงชื่อ */
    public User addNewUser(User user) {
        users.add(user);
        return user;
    }

    /* user เลือกเกมเข้ามา(เลือกหลายเกม) และ ทำการจองห้อง */
    public void userReserveRoom(User user, Room room, ArrayList<Game> games) {
        user.reserveRoom(room);
        for (Game game : games) {
            room.addGame(game);
            game.DecreaseGame();
        }
    }

    /* user ทำการยกเลิกห้องทั้งหมด */
    public void userCancelAllReserve(User user) {
        for (Room room : user.getReserveRoom()) {
            room.setterUserReserve(null);
        }
        user.resetReserveRoom();
    }
}