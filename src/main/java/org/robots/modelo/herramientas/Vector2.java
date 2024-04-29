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

    /**
     * Suma un vector a este vector
     * @param vector Vector2
     */
    public void sumar(Vector2 vector) {
        this.x += vector.getX();
        this.y += vector.getY();
    }

    /**
     * Suma dos vectores y devuelve un nuevo vector con el resultado
     * @param vector1 Vector2
     * @param vector2 Vector2
     * @return Vector2
     */
    public static Vector2 sumar(Vector2 vector1, Vector2 vector2) {
        return new Vector2(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }

    /* Getters */

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /* Setters */

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
