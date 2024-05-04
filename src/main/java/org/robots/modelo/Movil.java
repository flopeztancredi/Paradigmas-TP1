package org.robots.modelo;

import org.robots.modelo.herramientas.Vector2;

public interface Movil {
    /**
     * moverse es un método a implementar, en donde se recibe la posicion a la que se va a mover el jugador
     * y el tablero. Devuelve True si el movimiento fue válido, False en caso contrario
     * @param posJugador Vector2
     * @param tablero Tablero
     * @return boolean
     */
    boolean moverse(Vector2 posJugador, Tablero tablero);
}
