package org.robots.modelo;

import org.robots.modelo.herramientas.Vector2;

public interface Movimiento {
    boolean moverse(Vector2 posicion, Tablero tablero);
}
