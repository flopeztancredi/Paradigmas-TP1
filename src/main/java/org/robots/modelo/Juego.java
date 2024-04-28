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
    private boolean tpActivado;

    public final Vector2 ARRIBA = new Vector2(-1, 0);
    public final Vector2 ABAJO = new Vector2(1, 0);
    public final Vector2 IZQUIERDA = new Vector2(0, -1);
    public final Vector2 DERECHA = new Vector2(0, 1);

    public Juego(int filas, int columnas) {
        this.tablero = new Tablero(filas, columnas);
        this.nivel = 0;
        this.cantTpSafe = 0;
        this.puntuacion = 0;
        this.tpActivado = false;
    }

    public void siguienteNivel() {
        nivel++; cantTpSafe++;
        int r1 = Math.max(tablero.getFilas() * tablero.getColumnas() / 150 + this.nivel-1, 2);
        int r2 = tablero.getFilas() * tablero.getColumnas() / 600 + this.nivel-1;
        tablero.siguienteNivel(r1, r2);
    }

    public Estado mover(int fila, int columna) {
        boolean sigueVivo;
        Vector2 posicion = new Vector2(fila, columna);
        if (this.tpActivado) {
            sigueVivo = tablero.mover(posicion);
            this.cantTpSafe--;
            this.tpActivado = false;
        } else {
            posicion.setX(Integer.compare(fila, tablero.getPosicionJugador().getX()));
            posicion.setY(Integer.compare(columna, tablero.getPosicionJugador().getY()));
            posicion.sumar(tablero.getPosicionJugador());
            sigueVivo = tablero.mover(posicion);
        }
        this.puntuacion += tablero.getPuntuacionJugador();
        return definirEstado(sigueVivo);
    }

    public Estado mover() {
        boolean sigueJugando = tablero.mover(posAleatoria(this.getFilas(), this.getColumnas()));
        this.puntuacion += tablero.getPuntuacionJugador();
        return definirEstado(sigueJugando);
    }

    public Estado moverDireccion(Vector2 direccion) {
        Vector2 posicion = Vector2.sumar(direccion, tablero.getPosicionJugador());
        boolean sigueJugando;
        if (posicion.getX() >= this.getFilas() || posicion.getX() < 0 || posicion.getY() >= this.getColumnas() || posicion.getY() < 0) {
            sigueJugando = tablero.quedarse();
        } else {
            sigueJugando = tablero.mover(posicion);
        }
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

    public void toggleTpSafe() {
        this.tpActivado = !this.tpActivado;
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

    public boolean isTpSafe() {
        return this.tpActivado;
    }
}
