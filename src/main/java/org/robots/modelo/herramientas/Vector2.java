package org.robots.modelo.herramientas;

public class Vector2 {
    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void sumar(Vector2 vector) {
        this.x += vector.getX();
        this.y += vector.getY();
    }

    public void restar(Vector2 vector) {
        this.x -= vector.getX();
        this.y -= vector.getY();
    }
}
