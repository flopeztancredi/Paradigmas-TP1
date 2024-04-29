package org.robots.modelo;

import org.robots.modelo.herramientas.Vector2;
import org.robots.modelo.personajes.Elemento;

import java.util.ArrayList;
import java.util.Random;

public class Juego {
    private final Tablero tablero;
    private int nivel = 0;
    private int cantTpSafe = 0;
    private boolean tpActivado = false;

    public final Vector2 ARRIBA = new Vector2(-1, 0);
    public final Vector2 ABAJO = new Vector2(1, 0);
    public final Vector2 IZQUIERDA = new Vector2(0, -1);
    public final Vector2 DERECHA = new Vector2(0, 1);

    public Juego(int filas, int columnas) {
        this.tablero = new Tablero(filas, columnas);
    }

    /**
     * siguienteNivel aumenta el nivel del juego y la cantidad de teleports seguros
     * y actualiza el tablero con el número de robots correspondientes al nivel
     */
    public void siguienteNivel() {
        nivel++; cantTpSafe++;
        int r1 = Math.max(tablero.getFilas() * tablero.getColumnas() / 150 + this.nivel-1, 2);
        int r2 = tablero.getFilas() * tablero.getColumnas() / 600 + this.nivel-1;
        tablero.siguienteNivel(r1, r2);
    }

    public Estado mover(int fila, int columna) {
        var posicion = new Vector2(fila, columna);
        if (this.tpActivado) {
            this.cantTpSafe--;
            this.tpActivado = false;
        } else {
            posicion.setX(Integer.compare(fila, tablero.getPosicionJugador().getX()));
            posicion.setY(Integer.compare(columna, tablero.getPosicionJugador().getY()));
            posicion.sumar(tablero.getPosicionJugador());
        }
        boolean sigueVivo = tablero.mover(posicion);
        return definirEstado(sigueVivo);
    }

    /**
     * mover mueve al Jugador a una posición aleatoria
     * @return Estado
     */
    public Estado mover() {
        boolean sigueVivo = tablero.mover(posAleatoria(this.getFilas(), this.getColumnas()));
        return definirEstado(sigueVivo);
    }

    /**
     * quedarse mueve al Jugador a la misma posición en la que se encuentra
     * @return Estado
     */
    public Estado quedarse() {
        boolean sigueVivo = tablero.mover(tablero.getPosicionJugador());
        return definirEstado(sigueVivo);
    }

    /**
     * moverDireccion mueve al Jugador en la direccion recibida por parámetro
     * @param direccion Vector2
     * @return Estado
     */
    public Estado moverDireccion(Vector2 direccion) {
        var posJugador = tablero.getPosicionJugador();
        var posicion = Vector2.sumar(direccion, posJugador);
        if (posicion.getX() >= this.getFilas() || posicion.getX() < 0) posicion.setX(posJugador.getX());
        if (posicion.getY() >= this.getColumnas() || posicion.getY() < 0) posicion.setY(posJugador.getY());
        return mover(posicion.getX(), posicion.getY());
    }

    /**
     * posAleatoria devuelve una posición aleatoria dentro de un rango
     * @param rangoX int
     * @param rangoY int
     * @return Vector2
     */
    private Vector2 posAleatoria(int rangoX, int rangoY) {
        var rand = new Random();
        return new Vector2(rand.nextInt(rangoX), rand.nextInt(rangoY));
    }

    /**
     * definirEstado devuelve PERDIDO si sigueJugando es False. Si sigueJugando, devuelve GANADO si
     * esGanador es True, devuelve JUGANDO si todavía no hay ganador.
     * @param sigueJugando boolean
     * @return Estado
     */
    private Estado definirEstado(boolean sigueJugando) {
        if (!sigueJugando) {
            return Estado.PERDIDO;
        }

        return tablero.esGanador() ? Estado.GANADO : Estado.JUGANDO;
    }

    /**
     * toggleTpSafe cambia el estado del teletransporte seguro
     */
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
        return tablero.getPuntuacionJugador();
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
