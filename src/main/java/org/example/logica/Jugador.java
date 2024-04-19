package org.example.logica;

public class Jugador extends Elemento implements Movimiento {
    private int puntuaciónPorMovimiento;

    public Jugador(Vector2 pos) {
        super(pos);
        puntuaciónPorMovimiento = 0;
    }

    public boolean Moverse(Vector2 posicion) {
        this.posicion = posicion;
        puntuaciónPorMovimiento = 0;
        return true;
    }

    public void sumarPuntos(int puntos) {
        this.puntuaciónPorMovimiento += puntos;
    }

    public int getPuntuación() {
        return puntuaciónPorMovimiento;
    }
}
