package org.example.logica;

public class R2 extends Robot {
    public R2(Vector2 posicion) {
        super(posicion);
    }

    public void Moverse(Vector2 posicion) {

    }

    private Vector2 calcularPosicion(Vector2 posDeseada) {
        Vector2 direccion = posDeseada.restar(this.posicion);
        if (direccion.getX() > 0) {
            direccion.setX(1);
        } else if (direccion.getX() < 0) {
            direccion.setX(-1);
        }

        if (direccion.getY() > 0) {
            direccion.setY(1);
        } else if (direccion.getY() < 0) {
            direccion.setY(-1);
        }

        return direccion;
    }
}