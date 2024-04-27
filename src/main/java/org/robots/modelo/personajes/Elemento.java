package org.robots.modelo.personajes;

import org.robots.modelo.herramientas.Vector2;

public class Elemento {
    protected Vector2 posicion;
    private final String nombre;

    public Elemento(Vector2 posicion, String nombre) {
        this.posicion = posicion;
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public boolean esJugador() {
        return this instanceof Jugador;
    }

    public boolean esRobot() {
        return this instanceof Robot;
    }

    public boolean esFuego() {
        return this instanceof Fuego;
    }
}
