package org.robots.modelo;

import org.robots.modelo.herramientas.Vector2;
import org.robots.modelo.personajes.*;

import java.util.ArrayList;
import java.util.Random;

public class Tablero {
    private final Celda[][] celdas;
    private final int filas;
    private final int columnas;

    private Jugador player;
    private final ArrayList<Robot> robots;
    private final ArrayList<Fuego> fuegos;

    private static final int PUNTUACION_MOVIMIENTO = 10;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = inicializarCeldas(filas, columnas);
        this.robots = new ArrayList<>();
        this.fuegos = new ArrayList<>();
    }

    /* Getters */

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getPuntuacionJugador() {
        return this.player.getPuntuacion();
    }

    public Vector2 getPosicionJugador() {
        return this.player.getPosicion();
    }

    public ArrayList<Elemento> getElementos() {
        ArrayList<Elemento> elementos = new ArrayList<>(this.robots);
        elementos.add(this.player);
        elementos.addAll(this.fuegos);
        return elementos;
    }

    /* Validaciones */

    public boolean esPosValida(Vector2 posicion) { return celdas[posicion.getX()][posicion.getY()].estaVacia(); }

    public boolean esPosIncendiada(Vector2 posicion) { return celdas[posicion.getX()][posicion.getY()].estaIncendiada(); }

    public boolean esGanador() { return this.robots.isEmpty(); }

    /* Inicializaciones */

    private Celda[][] inicializarCeldas(int filas, int columnas) {
        Celda[][] celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
            }
        }
        return celdas;
    }

    private void inicializarJugador() {
        Vector2 pos = new Vector2(this.filas/2, this.columnas/2);
        this.player = new Jugador(pos);
        conseguirCelda(pos).asignarObjeto(player);
    }

    private void inicializarRobots(int r1, int r2) {
        for (int i = 0; i < r1; i++) {
            Vector2 pos = generarPosAleatoria();
            R1 robot1 = new R1(pos, this);
            robots.add(robot1);
            conseguirCelda(pos).asignarObjeto(robot1);
        }

        for (int i = 0; i < r2; i++) {
            var pos = generarPosAleatoria();
            var robot2 = new R2(pos, this);
            robots.add(robot2);
            conseguirCelda(pos).asignarObjeto(robot2);
        }
    }

    private void reiniciarTablero() {
        for (Fuego f : fuegos) {
            conseguirCelda(f.getPosicion()).sacarObjeto();
        }
        this.fuegos.clear();
        if (this.player != null) {
            conseguirCelda(this.player.getPosicion()).sacarObjeto();
        }
    }

    public void siguienteNivel(int robots1, int robots2) {
        reiniciarTablero();
        inicializarJugador();
        inicializarRobots(robots1, robots2);
    }

    /* Movimientos */

    public boolean quedarse() { return mover(this.player.getPosicion()); }

    public boolean mover(Vector2 pos) {
        moverRobots(pos);
        return moverJugador(pos);
    }

    private boolean moverJugador(Vector2 pos) {
        Vector2 posAntigua = new Vector2(player.getPosicion());
        if (conseguirCelda(posAntigua).getElemento().esJugador()) {
            conseguirCelda(posAntigua).sacarObjeto();
        }
        if (!conseguirCelda(pos).estaVacia()) {
            return false;
        }
        player.moverse(pos);
        player.sumarPuntos(PUNTUACION_MOVIMIENTO);
        conseguirCelda(pos).asignarObjeto(player);
        return true;
    }

    private void moverRobots(Vector2 posJugador) {
        for (var robot : robots) {
            var posAntigua = new Vector2(robot.getPosicion());
            conseguirCelda(posAntigua).sacarObjeto();
            robot.moverse(posJugador);
        }
        asignarMovimientos();
    }

    /* Colisiones */

    private void asignarMovimientos() {
        ArrayList<Robot> robots = new ArrayList<>(this.robots);
        for (var robot: robots) {
            var celdaParaMoverse = conseguirCelda(robot.getPosicion());
            if (celdaParaMoverse.estaVacia()) {
                celdaParaMoverse.asignarObjeto(robot);
            } else {
                manejarColision(robot, celdaParaMoverse);
            }
        }
    }

    // manejarColision con polimorfismo te extrañamos

    private void manejarColision(Robot robot, Celda celdaParaMoverse) {
        var elemento = celdaParaMoverse.sacarObjeto();
        if (elemento.esJugador()) {
            celdaParaMoverse.asignarObjeto(robot);
            return;
        }

        player.sumarPuntos(sacarRobots(robot));
        if (elemento.esFuego()) {
            celdaParaMoverse.asignarObjeto(elemento);
            return;
        }
        var fuego = new Fuego(robot.getPosicion());
        celdaParaMoverse.incendiar(fuego);
        fuegos.add(fuego);
        player.sumarPuntos(sacarRobots((Robot) elemento));
    }

    /* Operaciones auxiliares */

    private int sacarRobots(Robot robot) {
        this.robots.remove(robot);
        return robot.getPuntuacion();
    }

    public Vector2 generarPosAleatoria() {
        var rand = new Random();
        Vector2 posicion = new Vector2(rand.nextInt(filas), rand.nextInt(columnas));
        while (!esPosValida(posicion)) {
            posicion = new Vector2(rand.nextInt(filas), rand.nextInt(columnas));
        }
        return posicion;
    }

    private Celda conseguirCelda(Vector2 posBuscada) {
        if (posBuscada.getX() < 0 || posBuscada.getX() >= this.filas || posBuscada.getY() < 0 || posBuscada.getY() >= this.columnas) {
            throw new IllegalArgumentException("Posicion invalida");
        }
        return this.celdas[posBuscada.getX()][posBuscada.getY()];
    }
}