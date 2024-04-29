package org.robots.modelo.personajes;

import org.robots.modelo.Movimiento;
import org.robots.modelo.Tablero;
import org.robots.modelo.herramientas.Vector2;

public class Jugador extends Elemento implements Movimiento {
    private int puntuacion;

    public Jugador(Vector2 pos) {
        super(pos);
        puntuacion = 0;
    }

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
