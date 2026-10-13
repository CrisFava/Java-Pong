package br.com.cristianfava.pongserver.game;

public class Player extends GameObject {
    private final String name;
    private int points;

    public Player(String name, int points, Vector2<Integer> pos) {
        super(pos, new Vector2<>(5, 10));

        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
