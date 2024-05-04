package org.robots.modelo.personajes;

import org.robots.modelo.herramientas.Vector2;

public abstract class Elemento {
    protected Vector2 posicion;

    public Elemento(Vector2 posicion) {
        this.posicion = posicion;
    }

    /* Validaciones */

    public boolean esJugador() {
        return this instanceof Jugador;
    }

    public boolean esRobot1() {
        return this instanceof R1;
    }

    public boolean esRobot2() { return this instanceof R2; }

    public boolean esFuego() { return this instanceof Fuego; }

    /* Getters */

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
