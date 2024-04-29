package org.robots.modelo.personajes;

import org.robots.modelo.Movimiento;
import org.robots.modelo.Tablero;
import org.robots.modelo.herramientas.Vector2;

public class Robot extends Elemento implements Movimiento {
    private final int puntuacion;

    public Robot(Vector2 posicion, int puntuacion) {
        super(posicion);
        this.puntuacion = puntuacion;
    }

    public boolean moverse(Vector2 posJugador, Tablero tablero) {
        var movimiento = calcularPosicion(posJugador);
        this.posicion.sumar(movimiento);
        return !tablero.esPosIncendiada(this.posicion);
    }

    private Vector2 calcularPosicion(Vector2 posDeseada) {
        Vector2 movimiento = new Vector2(posDeseada.getX(), posDeseada.getY());
        movimiento.setX(Integer.compare(posDeseada.getX(), this.posicion.getX()));
        movimiento.setY(Integer.compare(posDeseada.getY(), this.posicion.getY()));
        return movimiento;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

}
