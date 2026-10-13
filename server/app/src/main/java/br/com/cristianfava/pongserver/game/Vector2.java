package br.com.cristianfava.pongserver.game;

import java.util.Objects;

public class Vector2<X extends Number> implements Comparable<Vector2<X>>, Cloneable {
    public X x, y;

    public static Vector2<Integer> zero = new Vector2<Integer>(0, 0);

    public Vector2(X x, X y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }

        this.x = x;
        this.y = y;
    }

    private Vector2(Vector2<X> v) throws NullPointerException {
        if (v == null) {
            throw new NullPointerException("O vetor não pode ser nulo.");
        }

        this.x = v.x;
        this.y = v.y;
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(this.x.doubleValue(), 2) + Math.pow(this.y.doubleValue(), 2));
    }

    public Vector2<Double> normalize() {
        var magnitude = this.getMagnitude();
        return new Vector2<>(
                this.x.doubleValue() / magnitude,
                this.y.doubleValue() / magnitude
        );
    }

    @Override
    public int compareTo(Vector2 other) {
        var m1  = this.getMagnitude();
        var m2 = other.getMagnitude();

        return Double.compare(m1, m2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o instanceof Vector2<?> other) {
            return this.x == other.x && this.y == other.y;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public Vector2<X> clone() throws NullPointerException {
        return new Vector2<X>(this);
    }
}
