package org.robots.modelo.personajes;

import org.robots.modelo.Movil;
import org.robots.modelo.Tablero;
import org.robots.modelo.herramientas.Vector2;

public class Jugador extends Elemento implements Movil {
    private int puntuacion;

    public Jugador(Vector2 pos) {
        super(pos);
        puntuacion = 0;
    }

    /**
     * moverse asigna la posición pasada por parámetro al jugador, devuelve True
     * @param posicion Vector2
     * @param tablero Tablero
     * @return boolean
     */
    public boolean moverse(Vector2 posicion, Tablero tablero) {
        this.posicion = posicion;
        return true;
    }

    public void sumarPuntos(int puntos) {
        this.puntuacion += puntos;
    }

    /* Getters */

    public int getPuntuacion() {
        return this.puntuacion;
    }
}
