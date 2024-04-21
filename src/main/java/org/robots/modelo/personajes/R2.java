package org.robots.modelo.personajes;

import org.robots.modelo.Tablero;
import org.robots.modelo.herramientas.Vector2;

public class R2 extends Robot {

    public R2(Vector2 posicion, Tablero tablero) {
        super(posicion, tablero);
    }

    @Override
    public boolean Moverse(Vector2 posicion) {
        if (!super.Moverse(posicion)) {
            return false;
        }
        return super.Moverse(posicion);
    }

}