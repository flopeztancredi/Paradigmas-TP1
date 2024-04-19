package org.example.logica;

public class Jugador extends Elemento implements Movimiento {
    public Jugador(Vector2 pos) {
        super(pos);
    }

    public boolean Moverse(Vector2 posicion) {
        this.posicion = posicion;
        return true;
    }

}
