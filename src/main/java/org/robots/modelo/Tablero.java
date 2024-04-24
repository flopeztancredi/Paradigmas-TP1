package org.robots.modelo;

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
    private final ArrayList<Robot> robots;
    private final ArrayList<Fuego> fuegos;


    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = inicializarCeldas(filas, columnas);
        this.robots = new ArrayList<>();
        this.fuegos = new ArrayList<>();
    }

    private HashMap<Vector2, Celda> inicializarCeldas(int filas, int columnas) {
        HashMap<Vector2, Celda> celdas = new HashMap<>();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas.put(new Vector2(i, j), new Celda());
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

    public boolean esPosValida(Vector2 posicion) {
        for (var position : celdas.keySet()) {
            if (position.getX() == posicion.getX() && position.getY() == posicion.getY()) {
                return celdas.get(position).estaVacia();
            }
        }
        return false;
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
        conseguirCelda(pos).asignarObjeto(jugador);
        // celdas.get(pos).asignarObjeto(jugador);
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
        var pos = new Vector2(this.player.getX()+posClickeada.getX(), this.player.getY()+posClickeada.getY());
        return mover(pos);
    }

    public boolean mover(Vector2 pos) {
        boolean jugadorMuerto = moverJugador(pos);
        if (jugadorMuerto) {
            // return false;
        }
        return moverRobots(player.getPosicion());
    }


    public boolean moverJugador(Vector2 direccion) {
        Vector2 posAntigua = new Vector2(player.getPosicion());
        player.Moverse(direccion);
        reemplazarCelda(posAntigua, player.getPosicion());
        return hayColision(player);
    }


    public boolean moverRobots(Vector2 posJugador) {
        for (Robot robot : robots) {
            Vector2 posAntigua = new Vector2(robot.getPosicion());
            if (robot.Moverse(posJugador)) {
                reemplazarCelda(posAntigua, robot.getPosicion());
            } else {
                return !hayColision(robot);
            }
        }
        return true;
    }


    public boolean hayColision(Elemento elemento) {
        var celda = conseguirCelda(elemento.getPosicion());
        if (celda.estaVacia() || elemento instanceof Jugador) {
            // player.sumarPuntos(10);
            return false;
        }

        var robot1 = celda.sacarObjeto(); // este puede ser un jugador! guarda con eso
        player.sumarPuntos(sacarRobots(robot1));
        player.sumarPuntos(sacarRobots(elemento));
        var fuego = new Fuego(elemento.getPosicion());
        celda.incendiar(fuego);
        fuegos.add(fuego);
        return true;
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
