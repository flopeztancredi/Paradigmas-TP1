package org.robots.modelo.personajes;

import org.robots.modelo.herramientas.Vector2;

public class Elemento {
    protected Vector2 posicion;

    public Elemento(Vector2 posicion) {
        this.posicion = posicion;
    }

    public Vector2 getPosicion() {
        return posicion;
    }
}
