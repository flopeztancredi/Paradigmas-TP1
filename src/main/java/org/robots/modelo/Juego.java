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
        // int r1 = tablero.getFilas() * tablero.getColumnas() / 30 + this.nivel;
        // int r2 = tablero.getFilas() * tablero.getColumnas() / 120 + this.nivel;
        tablero.inicializarNivel(2, 0);
    }

    // Para moverse seguro?? como hariamos? Habría que pasar una posición acá directamente, no una dirección
    // eso lo podríamos hacer en la parte de vista, o sea la App (que DirigirMovimiento() se haga ahí),
    // pero creo que queda feo
    // Tenemos que hacer que Mover() reciba siempre una posición, si no va a ser complicado
    public int moverHacia(int fila, int columna) {
        this.puntuacion += tablero.getPuntuacionJugador();
        Vector2 posicion = new Vector2(fila, columna);
        tablero.moverHacia(posicion);
//        if (!tablero.mover(posicion)) {
//            return -1;
//        } else if (tablero.hayGanador()) {
//            this.puntuacion += 100;
//            this.nivel++;
//            return 1;
//        }
        return 0;
    }

    public boolean activarTpSafe() {
        if (this.tpSafe <= 0) {
            return false;
        }
        this.tpSafe--;
        return true;
    }

    public int mover(int fila, int columna) {
        tablero.mover(new Vector2(fila, columna));
        return 0;
    }

    public int quedarse() {
        tablero.quedarse();
        return 0;
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
