package org.example.logica;

public abstract class Robot extends Elemento implements Movimiento{
    public Robot(Vector2 posicion) {
        super(posicion);
    }

    public void Moverse(Vector2 posición);
}
