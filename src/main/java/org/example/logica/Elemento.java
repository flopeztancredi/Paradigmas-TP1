package org.example.logica;

public class Elemento {
    protected Vector2 posicion;

    public Elemento(Vector2 posicion) {
        this.posicion = posicion;
    }

    public Vector2 getPosicion() {
        return posicion;
    }
}
