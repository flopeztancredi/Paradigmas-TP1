package org.robots.modelo;

import org.robots.modelo.herramientas.Direccion;
import org.robots.modelo.herramientas.Vector2;
import org.robots.modelo.personajes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Tablero {
    private final HashMap<Vector2, Celda> celdas;
    private final int filas;
    private final int columnas;
    private Jugador player;
    private ArrayList<Robot> robots;


    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        inicializarJugador();

        celdas = new HashMap<>();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas.put(new Vector2(i, j), new Celda());
            }
        }
    }

    public int getPuntuacionJugador() {
        return this.player.getPuntuación();
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public boolean esPosValida(Vector2 posicion) {
        return celdas.get(posicion).estaVacia();
    }

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
        this.celdas.get(pos).asignarObjeto(jugador);
    }


    public void inicializarRobots(int r1, int r2) {
        for (int i = 0; i < r1; i++) {
            var pos = generarPosAleatoria();
            var robot1 = new R1(pos, this);
            robots.add(robot1);
            celdas.get(pos).asignarObjeto(robot1);
        }

        for (int i = 0; i < r2; i++) {
            var pos = generarPosAleatoria();
            var robot2 = new R2(pos, this);
            robots.add(robot2);
            celdas.get(pos).asignarObjeto(robot2);
        }
    }


    public void reiniciarTablero() {
        for (HashMap.Entry<Vector2, Celda> entrada : celdas.entrySet()) {
            var celda = entrada.getValue();
            celda.sacarObjeto();
            if (celda.estaIncendiada()) {
                celda.extinguirFuego();
            }
        }
        this.robots.clear();
    }


    public void inicializarNivel(int robots1, int robots2) {
        reiniciarTablero();
        inicializarJugador();
        inicializarRobots(robots1, robots2);
    }


    public boolean mover(Direccion direccion) {
        var posicion = direccion.dirigirMovimiento(player.getPosicion(), this.filas, this.columnas);
        moverRobots(posicion);
        return moverJugador(posicion);
        // se tiene que mover al jugador desp de a los robots porque es el que se fija si se chocó con un robot
    }


    public boolean moverJugador(Vector2 posicion) {
        player.Moverse(posicion);
        reemplazarCelda(player.getPosicion(), posicion);
        return !hayColision(player);
    }


    public void moverRobots(Vector2 posicion) {
        for (Robot robot : robots) {
            celdas.get(robot.getPosicion()).sacarObjeto();
            boolean movimientoValido = robot.Moverse(posicion);
            if (movimientoValido) {
                reemplazarCelda(robot.getPosicion(), posicion);
            } else {
                hayColision(robot);
            }
        }
    }


    public boolean hayColision(Elemento elemento) {
        var celda = celdas.get(elemento.getPosicion());
        if (celda.estaVacia()) {
            player.sumarPuntos(10);
            return false;
        }

        if (elemento instanceof Jugador) {
            // perdiste: te agarró un robot o pisaste fuego
        } else { // soy un robot
            player.sumarPuntos(sacarRobots(celda.sacarObjeto()));
            player.sumarPuntos(sacarRobots(elemento));
            // incendiar celda
            celda.incendiar();
            // semanticamente habria que separar los casos donde el robot se chocó con otro robot y donde
            // se chocó con un incendio, pero a nivel código no hay diferencia, solamente estamos llamando
            // innecesariamente a incendiar.
        }
        return true;
    }


    public boolean hayGanador() {
        return this.robots.isEmpty();
    }


    // El problema con el reemplazarCelda es que no hacemos todos los movimientos a la vez, entonces quizás dependiendo
    // el orden se chocan robots que no necesariamente deberían chocarse si se mueven a la vez.

    // ahora reemplazarCelda solo te reemplaza la celda, no se encarga de nada más. Las colisiones se verifican antes.
    private void reemplazarCelda(Vector2 posAntigua, Vector2 posNueva) {
        Elemento elem = celdas.get(posAntigua).sacarObjeto();
        celdas.get(posNueva).asignarObjeto(elem);
    }
}
