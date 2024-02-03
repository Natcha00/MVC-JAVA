class Game {
    private String name;
    private int available;

    public Game(String name, int available) {
        this.name = name;
        this.available = available;
    }

    public void DecreaseGame() {
        this.available = this.available - 1;
    }

    public String getName() {
        return this.name;
    }

    public int getAvailable() {
        return this.available;
    }

    public boolean isAvailable() {
        return this.available > 0;
    }
}