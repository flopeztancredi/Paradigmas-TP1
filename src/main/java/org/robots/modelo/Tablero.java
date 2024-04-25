package org.robots.modelo;

import org.robots.modelo.herramientas.Vector2;
import org.robots.modelo.personajes.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    public boolean esPosIncendiada(Vector2 posicion) {return !celdas[posicion.getX()][posicion.getY()].estaIncendiada();}

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
        var pos = new Vector2(this.columnas/2, this.filas/2);
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

    private Celda conseguirCelda(Vector2 posBuscada) {
        for (HashMap.Entry<Vector2, Celda> entrada : celdas.entrySet()) {
            var posicion = entrada.getKey();
            if (posicion.getX() == posBuscada.getX() && posicion.getY() == posBuscada.getY()) {
                return entrada.getValue();
            }
        }
        return null;
    }


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

    public boolean quedarse() {
        return mover(this.player.getPosicion());
    }

    public boolean moverHacia(Vector2 posClickeada) {
        posClickeada.setX(Integer.compare(posClickeada.getX(), this.player.getX()));
        posClickeada.setY(Integer.compare(posClickeada.getY(), this.player.getY()));
        posClickeada.sumar(this.player.getPosicion());
        return mover(posClickeada);
    }

    public boolean mover(Vector2 pos) {
        boolean jugadorMuerto = moverJugador(pos);
        if (jugadorMuerto) {
            // return false;
        }
        return moverRobots(player.getPosicion());
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
            conseguirCelda(posAntigua).sacarObjeto();
            if (!(robot.moverse(posJugador))) {
                sacarRobots(robot);
            }
        }
        manejarColisiones();
    }

    private void manejarColision(Robot robot, Celda celda) {
        var elemento = celda.sacarObjeto();
        if (elemento instanceof Jugador) {
            celda.asignarObjeto(robot);
            return;
        }

        player.sumarPuntos(sacarRobots(robot));
        if (elemento instanceof Robot) {
            var fuego = new Fuego(robot.getPosicion());
            celda.incendiar(fuego);
            fuegos.add(fuego);
            player.sumarPuntos(sacarRobots(elemento));
        }
    }

    public boolean hayGanador() {
        return this.robots.isEmpty();
    }


    // El problema con el reemplazarCelda es que no hacemos todos los movimientos a la vez, entonces quizás dependiendo
    // el orden se chocan robots que no necesariamente deberían chocarse si se mueven a la vez.

    // ahora reemplazarCelda solo te reemplaza la celda, no se encarga de nada más. Las colisiones se verifican antes.
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
