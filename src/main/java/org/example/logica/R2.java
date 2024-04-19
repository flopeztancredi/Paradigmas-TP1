package org.example.logica;

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