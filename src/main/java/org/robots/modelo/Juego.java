package org.robots.modelo;

import org.robots.modelo.herramientas.Vector2;
import org.robots.modelo.personajes.Elemento;

import java.util.ArrayList;

public class Juego {
    private final Tablero tablero;
    private int nivel;
    private int puntuacion;
    private int tpSafe = 1;


    public Juego(int filas, int columnas) {
        this.tablero = new Tablero(filas, columnas);
        this.nivel = 1;
        this.puntuacion = 0;
    }

    public void inicializarNivel() {
        // int r1 = tablero.getFilas() * tablero.getColumnas() / 30 + this.nivel-1;
        // int r2 = tablero.getFilas() * tablero.getColumnas() / 120 + this.nivel-1;
        tablero.inicializarNivel(3, 0);
    }

    public Estado mover(int fila, int columna) {
        boolean sigueJugando = tablero.mover(new Vector2(fila, columna));
        return definirEstado(sigueJugando);
    }

    public Estado moverHacia(int fila, int columna) {
        this.puntuacion += tablero.getPuntuacionJugador();
        Vector2 posicion = new Vector2(fila, columna);
        boolean sigueJugando = tablero.moverHacia(posicion);
        return definirEstado(sigueJugando);
    }

    public Estado quedarse() {
        boolean sigueJugando = tablero.quedarse();
        return definirEstado(sigueJugando);
    }

    private Estado definirEstado(boolean sigueJugando) {
        if (!sigueJugando) {
            return Estado.PERDIDO;
        }

        return tablero.esGanador() ? Estado.GANADO : Estado.JUGANDO; // tira perdiste una vez cuando esta cerca?
    }

    public boolean activarTpSafe() {
        if (this.tpSafe <= 0) {
            return false;
        }
        this.tpSafe--;
        return true;
    }

    public int getFilas() {
        return tablero.getFilas();
    }

    public int getColumnas() {
        return tablero.getColumnas();
    }

    public ArrayList<Elemento> getElementos() {
        return tablero.getElementos();
    }

    public int getTpSafe() {
        return this.tpSafe;
    }

}
