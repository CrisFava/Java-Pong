package br.com.cristianfava.pongserver.game;

public class GameObject {
    private Vector2<Integer> position, scale;
    private Vector2<Double> velocity = new Vector2<>(0d, 0d);

    public GameObject() {
        this.position = Vector2.zero;
        this.scale = Vector2.zero;
    }

    public GameObject(Vector2<Integer> position, Vector2<Integer> scale) throws NullPointerException {
        if (position == null || scale == null) {
            throw new NullPointerException("Os vetores não podem ser nulos!");
        }

        this.position = position.clone();
        this.scale = scale.clone();
    }

    public Vector2<Integer> getPosition() {
        return position.clone();
    }

    public void setPosition(Vector2<Integer> position) {
        this.position = position.clone();
    }

    public Vector2<Integer> getScale() {
        return scale.clone();
    }

    public void setScale(Vector2<Integer> scale) {
        this.scale = scale.clone();
    }

    public Vector2<Double> getVelocity() {
        return velocity.clone();
    }

    public void setVelocity(Vector2<Double> velocity) {
        this.velocity = velocity.clone();
    }
}
