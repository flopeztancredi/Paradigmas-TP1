package org.robots.modelo;

import org.robots.modelo.herramientas.Vector2;
import org.robots.modelo.personajes.Elemento;

import java.util.ArrayList;

public class Juego {
    private final Tablero tablero;
    private int nivel;
    private int puntuacion;
    private int tpSafe;



    public Juego(int filas, int columnas) {
        this.tablero = new Tablero(filas, columnas);
        this.nivel = 0;
        this.tpSafe = 0;
        this.puntuacion = 0;
    }

    public void siguienteNivel() {
        nivel++; tpSafe++;
        int r1 = Math.max(tablero.getFilas() * tablero.getColumnas() / 150 + this.nivel-1, 2);
        int r2 = tablero.getFilas() * tablero.getColumnas() / 600 + this.nivel-1;
        tablero.siguienteNivel(r1, r2);
    }

    public Estado mover(int fila, int columna) {
        boolean sigueJugando = tablero.mover(new Vector2(fila, columna));
        this.puntuacion += tablero.getPuntuacionJugador();
        return definirEstado(sigueJugando);
    }

    public Estado moverHacia(int fila, int columna) {
        Vector2 posicion = new Vector2(fila, columna);
        boolean sigueJugando = tablero.moverHacia(posicion);
        this.puntuacion += tablero.getPuntuacionJugador();
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

        return tablero.esGanador() ? Estado.GANADO : Estado.JUGANDO;
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

    public int getNivel() {return nivel;}

    public int getPuntuacion() {return puntuacion;}

}
