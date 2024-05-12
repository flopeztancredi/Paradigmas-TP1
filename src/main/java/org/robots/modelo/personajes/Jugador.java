package org.robots.modelo.personajes;

import org.robots.modelo.Movil;
import org.robots.modelo.Tablero;
import org.robots.modelo.herramientas.Vector2;

public class Jugador extends Elemento implements Movil {
    private int puntuacion;

    public Jugador(Vector2 pos) {
        super(pos, "messi");
        puntuacion = 0;
    }

    /**
     * moverse asigna la posición pasada por parámetro al jugador
     *
     * @param posicion Vector2
     * @param tablero  Tablero
     */
    public void moverse(Vector2 posicion, Tablero tablero) { this.posicion = posicion; }

    public void sumarPuntos(int puntos) {
        this.puntuacion += puntos;
    }

    /* Getters */

    public int getPuntuacion() {
        return this.puntuacion;
    }
}
