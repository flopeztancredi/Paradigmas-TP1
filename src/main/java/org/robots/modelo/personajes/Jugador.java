package org.robots.modelo.personajes;

import org.robots.modelo.Movimiento;
import org.robots.modelo.herramientas.Vector2;

public class Jugador extends Elemento implements Movimiento {
    private int puntuacionPorMovimiento;

    public Jugador(Vector2 pos) {
        super(pos, "Jugador");
        puntuacionPorMovimiento = 0;
    }

    public boolean moverse(Vector2 posicion) {
        this.posicion = posicion;
        return true;
    }

    public void sumarPuntos(int puntos) {
        this.puntuacionPorMovimiento += puntos;
    }

    public int getPuntuacion() {
        var pts = this.puntuacionPorMovimiento;
        this.puntuacionPorMovimiento = 0;
        return pts;
    }
}
