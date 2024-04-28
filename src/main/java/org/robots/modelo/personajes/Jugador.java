package org.robots.modelo.personajes;

import org.robots.modelo.Movimiento;
import org.robots.modelo.herramientas.Vector2;

public class Jugador extends Elemento implements Movimiento {
    private int puntuacion;

    public Jugador(Vector2 pos) {
        super(pos, "Jugador");
        puntuacion = 0;
    }

    public boolean moverse(Vector2 posicion) {
        this.posicion = posicion;
        return true;
    }

    public void sumarPuntos(int puntos) { this.puntuacion += puntos; }

    public int getPuntuacion() {
        var pts = this.puntuacion;
        this.puntuacion = 0;
        return pts;
    }
}
