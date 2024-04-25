package org.robots.modelo.personajes;

import org.robots.modelo.Movimiento;
import org.robots.modelo.Tablero;
import org.robots.modelo.herramientas.Vector2;

public abstract class Robot extends Elemento implements Movimiento {
    private final Tablero tablero;

    public Robot(Vector2 posicion, Tablero tablero, String nombre) {
        super(posicion, nombre);
        this.tablero = tablero;
    }

    public boolean moverse(Vector2 posJugador) {
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

}
