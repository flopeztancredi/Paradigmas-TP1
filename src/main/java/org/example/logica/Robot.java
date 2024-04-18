package org.example.logica;

public abstract class Robot extends Elemento implements Movimiento {
    public Robot(Vector2 posicion) {
        super(posicion);
    }

    public void Moverse(Vector2 posicion) {
        var movimiento = calcularPosicion(posicion);
        this.posicion.sumar(movimiento);
    }

    protected Vector2 calcularPosicion(Vector2 posDeseada) {
        Vector2 vectorDireccion = posDeseada.restar(this.posicion);
        if (vectorDireccion.getX() > 0) {
            vectorDireccion.setX(1);
        } else if (vectorDireccion.getX() < 0) {
            vectorDireccion.setX(-1);
        }

        if (vectorDireccion.getY() > 0) {
            vectorDireccion.setY(1);
        } else if (vectorDireccion.getY() < 0) {
            vectorDireccion.setY(-1);
        }

        return vectorDireccion;
    };

}
