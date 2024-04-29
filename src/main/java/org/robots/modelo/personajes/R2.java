package org.robots.modelo.personajes;

import org.robots.modelo.Tablero;
import org.robots.modelo.herramientas.Vector2;

public class R2 extends Robot {


    public R2(Vector2 posicion) {
        super(posicion, 50);
    }

    @Override
    public boolean moverse(Vector2 posJugador, Tablero tablero) {
        if (!super.moverse(posJugador, tablero)) {
            return false;
        }
        return super.moverse(posJugador, tablero);
    }

}