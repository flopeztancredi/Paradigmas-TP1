package org.example.logica;

public class R1 extends Robot{

    public R1(Vector2 posicion) {
        super(posicion);
    };

    public void Moverse(Vector2 posicion) {
        var movimiento = calcularPosicion(posicion);
        this.posicion.sumar(movimiento);
    }
}
