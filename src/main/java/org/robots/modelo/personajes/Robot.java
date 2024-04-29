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

    /**
     * moverse calcula la posición a la que se debe ir el robot según la posicion del Jugador. Devuelve
     * True si el movimiento es válido, False en caso contrario
     * @param posJugador Vector2
     * @param tablero Tablero
     * @return boolean
     */
    public boolean moverse(Vector2 posJugador, Tablero tablero) {
        var movimiento = calcularPosicion(posJugador);
        this.posicion.sumar(movimiento);
        return !tablero.esPosIncendiada(this.posicion);
    }

    /**
     * calcularPosición devuelve el movimiento que debe hacer el robot para acercarse a la posición
     * del Jugador. Devuelve un Vector2 en el que sus componentes pueden ser 1, 0 o -1
     * @param posDeseada Vector2
     * @return Vector2
     */
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
