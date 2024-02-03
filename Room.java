import java.util.ArrayList;

class Room {
    private String name;
    private ArrayList<Game> games;
    private User userReserve;

    public Room(String name) {
        this.name = name;
        this.games = new ArrayList<>();
        this.userReserve = null; 
    }

    public String getName() {
        return this.name;
    }

    public boolean canReservedRoom() {
        return userReserve == null;
    }

    public void setterUserReserve(User userReserve) {
        this.userReserve = userReserve;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public ArrayList<Game> getGame() {
        return this.games;
    }
}