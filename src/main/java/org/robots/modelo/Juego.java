package org.robots.modelo;

import org.robots.modelo.herramientas.Vector2;
import org.robots.modelo.personajes.Elemento;

import java.util.ArrayList;
import java.util.Random;

public class Juego {
    private final Tablero tablero;
    private int nivel;
    private int puntuacion;
    private int cantTpSafe;
    private boolean esTp;



    public Juego(int filas, int columnas) {
        this.tablero = new Tablero(filas, columnas);
        this.nivel = 0;
        this.cantTpSafe = 0;
        this.puntuacion = 0;
        this.esTp = false;
    }

    public void siguienteNivel() {
        nivel++; cantTpSafe++;
        int r1 = Math.max(tablero.getFilas() * tablero.getColumnas() / 150 + this.nivel-1, 2);
        int r2 = tablero.getFilas() * tablero.getColumnas() / 600 + this.nivel-1;
        tablero.siguienteNivel(r1, r2);
    }

    public Estado mover(int fila, int columna) {
        boolean sigueJugando;
        if (this.esTp) {
            sigueJugando = tablero.mover(new Vector2(fila, columna));
            this.esTp = false;
        } else {
            sigueJugando = tablero.moverHacia(new Vector2(fila, columna));
        }
        this.puntuacion += tablero.getPuntuacionJugador();
        return definirEstado(sigueJugando);
    }

    public Estado mover() {
        boolean sigueJugando = tablero.mover(posAleatoria(this.getFilas(), this.getColumnas()));
        this.esTp = false;
        this.puntuacion += tablero.getPuntuacionJugador();
        return definirEstado(sigueJugando);
    }

    public Vector2 posAleatoria(int rangoX, int rangoY) {
        var rand = new Random();
        return new Vector2(rand.nextInt(rangoX), rand.nextInt(rangoY));
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

    /* Activar TP */

    public void activarTpSafe() {
        this.esTp = this.cantTpSafe-- >= 0;
    }

    public void activarTp() {
        this.esTp = true;
    }

    /* Getters */

    public int getFilas() {
        return tablero.getFilas();
    }

    public int getColumnas() {
        return tablero.getColumnas();
    }

    public int getNivel() {
        return nivel;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public int getCantTpSafe() {
        return this.cantTpSafe;
    }

    public ArrayList<Elemento> getElementos() {
        return tablero.getElementos();
    }

}
