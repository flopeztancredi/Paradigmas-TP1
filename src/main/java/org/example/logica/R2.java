package org.example.logica;

public class R2 extends Robot {
    public R2(Vector2 posicion) {
        super(posicion);
    }

    public void Moverse(Vector2 posicion) {
        var movimiento1 = calcularPosicion(posicion);
        this.posicion.sumar(movimiento1);
        var movimiento2 = calcularPosicion(posicion);
        this.posicion.sumar(movimiento2);
    }


}