package org.robots.modelo;

import org.robots.modelo.herramientas.Vector2;
import org.robots.modelo.personajes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Tablero {
    private final Celda[][] celdas;
    private final int filas;
    private final int columnas;
    private Jugador player;
    private final ArrayList<Robot> robots;
    private final ArrayList<Fuego> fuegos;


    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = inicializarCeldas(filas, columnas);
        this.robots = new ArrayList<>();
        this.fuegos = new ArrayList<>();
    }

    private Celda[][] inicializarCeldas(int filas, int columnas) {
        Celda[][] celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
            }
        }
        return celdas;
    }

    public int getPuntuacionJugador() {
        return this.player.getPuntuacion();
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public boolean esPosValida(Vector2 posicion) {return celdas[posicion.getX()][posicion.getY()].estaVacia();}

    public boolean esPosIncendiada(Vector2 posicion) {return celdas[posicion.getX()][posicion.getY()].estaIncendiada();}

    public int sacarRobots(Elemento robot) {
        this.robots.remove(robot);
        if (robot instanceof R1) {
            return 25;
        } else {
            return 50;
        }
    }

    public Vector2 generarPosAleatoria() {
        var rand = new Random();
        Vector2 posicion = new Vector2(rand.nextInt(filas), rand.nextInt(columnas));
        while (!esPosValida(posicion)) {
            posicion = new Vector2(rand.nextInt(filas), rand.nextInt(columnas));
        }
        return posicion;
    }

    public void inicializarJugador() {
        var pos = new Vector2(this.filas/2, this.columnas/2);
        var jugador = new Jugador(pos);
        this.player = jugador;
        conseguirCelda(pos).asignarObjeto(jugador);
    }


    public void inicializarRobots(int r1, int r2) {
        for (int i = 0; i < r1; i++) {
            var pos = generarPosAleatoria();
            var robot1 = new R1(pos, this);
            robots.add(robot1);
            conseguirCelda(pos).asignarObjeto(robot1);
            // celdas.get(pos).asignarObjeto(robot1);
        }

        for (int i = 0; i < r2; i++) {
            var pos = generarPosAleatoria();
            var robot2 = new R2(pos, this);
            robots.add(robot2);
            conseguirCelda(pos).asignarObjeto(robot2);
            // celdas.get(pos).asignarObjeto(robot2); esto no funciona porque pos es una nueva instancia de Vector2
        }
    }

    private Celda conseguirCelda(Vector2 posBuscada) { return celdas[posBuscada.getX()][posBuscada.getY()]; }


    public void reiniciarTablero() {
        for (var filaCeldas : celdas) {
            for (var celda : filaCeldas) {
                celda.sacarObjeto();
            }
        }
        this.robots.clear();
        this.fuegos.clear();
    }

    public void inicializarNivel(int robots1, int robots2) {
        // reiniciarTablero();
        inicializarJugador();
        inicializarRobots(robots1, robots2);
    }

    public boolean quedarse() { return mover(this.player.getPosicion()); }

    public boolean moverHacia(Vector2 posClickeada) {
        posClickeada.setX(Integer.compare(posClickeada.getX(), this.player.getX()));
        posClickeada.setY(Integer.compare(posClickeada.getY(), this.player.getY()));
        posClickeada.sumar(this.player.getPosicion());
        return mover(posClickeada);
    }

    public boolean mover(Vector2 pos) {
        moverRobots(pos);
        return moverJugador(pos);
    }

    private boolean moverJugador(Vector2 pos) {
        if (!(Objects.requireNonNull(conseguirCelda(pos)).estaVacia())) {
            return false;
        }
        Vector2 posAntigua = new Vector2(player.getPosicion());
        player.moverse(pos);
        reemplazarCelda(posAntigua, player.getPosicion());
        return true;
    }


    private void moverRobots(Vector2 posJugador) {
        for (var robot : robots) {
            var posAntigua = new Vector2(robot.getPosicion());
            Objects.requireNonNull(conseguirCelda(posAntigua)).sacarObjeto();
            if (!(robot.moverse(posJugador))) {
                sacarRobots(robot);
            }
        }
        manejarColisiones();
    }

    private void manejarColisiones() {
        for (var robot: robots) { // se rompe cuando se chocan, creo que es algo del for
            var celdaParaMoverse = conseguirCelda(robot.getPosicion());
            if (celdaParaMoverse.estaVacia()) {
                celdaParaMoverse.asignarObjeto(robot);
            } else {
                manejarColision(robot, celdaParaMoverse);
            }
        }
    }

    private void manejarColision(Robot robot, Celda celdaParaMoverse) {
        var elemento = celdaParaMoverse.sacarObjeto();
        if (elemento instanceof Jugador) {
            celdaParaMoverse.asignarObjeto(robot);
            return;
        } // si no, me choco contra un robot

        var fuego = new Fuego(robot.getPosicion());
        celdaParaMoverse.incendiar(fuego);
        fuegos.add(fuego);
        player.sumarPuntos(sacarRobots(robot));
        player.sumarPuntos(sacarRobots(elemento));
    }

    public boolean hayGanador() { return this.robots.isEmpty(); }

    private void reemplazarCelda(Vector2 posAntigua, Vector2 posNueva) {
        Elemento elem = conseguirCelda(posAntigua).sacarObjeto();
        conseguirCelda(posNueva).asignarObjeto(elem);
    }

    public ArrayList<Elemento> getElementos() {
        ArrayList<Elemento> elementos = new ArrayList<>(this.robots);
        elementos.add(this.player);
        elementos.addAll(this.fuegos);
        return elementos;
    }
}
