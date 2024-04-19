package org.example.logica;

public abstract class Robot extends Elemento implements Movimiento {
    private final Tablero tablero;

    public Robot(Vector2 posicion, Tablero tablero) {
        super(posicion);
        this.tablero = tablero;
    }

    public boolean Moverse(Vector2 posicion) {
        var movimiento = calcularPosicion(posicion);
        this.posicion.sumar(movimiento);
        return tablero.esPosValida(posicion);
    }

    private Vector2 calcularPosicion(Vector2 posDeseada) {
        Vector2 vectorDireccion = posDeseada.restar(this.posicion);
        vectorDireccion.setX(Integer.compare(vectorDireccion.getX(), 0));
        vectorDireccion.setY(Integer.compare(vectorDireccion.getY(), 0));
        return vectorDireccion;
    }

}
