package org.robots.modelo.personajes;

import org.robots.modelo.herramientas.Vector2;

public abstract class Elemento {
    private final String nombre;
    protected Vector2 posicion;

    public Elemento(Vector2 posicion, String nombre) {
        this.posicion = posicion;
        this.nombre = nombre;
    }

    /* Validaciones */

    public boolean esObstaculo() {
        return false;
    }

    /* Getters */

    public String getNombre() {
        return this.nombre;
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public int getX() {
        return posicion.getX();
    }

    public int getY() {
        return posicion.getY();
    }
}
