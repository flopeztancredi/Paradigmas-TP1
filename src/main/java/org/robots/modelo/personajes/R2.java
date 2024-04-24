package org.robots.modelo.personajes;

import org.robots.modelo.Tablero;
import org.robots.modelo.herramientas.Vector2;

public class R2 extends Robot {

    public R2(Vector2 posicion, Tablero tablero) {
        super(posicion, tablero, "R2");
    }

    @Override
    public boolean Moverse(Vector2 posJugador) {
        if (!super.Moverse(posJugador)) {
            return false;
        }
        return super.Moverse(posJugador);
    }

}