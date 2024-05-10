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

    /* Validaciones */

    public boolean esPosValida(Vector2 posicion) { return celdas[posicion.getX()][posicion.getY()].estaVacia(); }

    public boolean esPosObstaculizada(Vector2 posicion) {
        var elemento =  conseguirCelda(posicion).getElemento();
        if (elemento == null) return false;
        return elemento.esObstaculo();
    }

    public boolean esGanador() { return this.robots.isEmpty(); }

    /* Inicializaciones */

    /**
     * inicializarCeldas crea una matriz de celdas vacías de tamaño filas x columnas
     * @param filas int
     * @param columnas int
     * @return Celda[][]
     */
    private Celda[][] inicializarCeldas(int filas, int columnas) {
        Celda[][] celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
            }
        }
        return celdas;
    }

    /**
     * inicializarJugador crea un jugador en la posición central del tablero y lo asigna a la celda correspondiente
     */
    private void inicializarJugador() {
        Vector2 pos = new Vector2(this.filas/2, this.columnas/2);
        if (player == null) player = new Jugador(pos);
        player.moverse(pos, this);
        conseguirCelda(pos).asignarObjeto(player);
    }

    /**
     * inicializarRobots crea r1 robots de tipo R1 y r2 robots de tipo R2 en posiciones aleatorias del tablero
     * @param r1 int
     * @param r2 int
     */
    private void inicializarRobots(int r1, int r2) {
        for (int i = 0; i < r1; i++) {
            Vector2 pos = generarPosAleatoria();
            R1 robot1 = new R1(pos);
            robots.add(robot1);
            conseguirCelda(pos).asignarObjeto(robot1);
        }

        for (int i = 0; i < r2; i++) {
            Vector2 pos = generarPosAleatoria();
            R2 robot2 = new R2(pos);
            robots.add(robot2);
            conseguirCelda(pos).asignarObjeto(robot2);
        }
    }

    /**
     * reiniciarTablero elimina todos los elementos del tablero y los reinicia
     */
    private void reiniciarTablero() {
        for (Fuego f : fuegos) {
            conseguirCelda(f.getPosicion()).sacarObjeto();
        }
        this.fuegos.clear();
        if (this.player != null) {
            conseguirCelda(this.player.getPosicion()).sacarObjeto();
        }
    }

    /**
     * siguienteNivel prepara al tablero para jugar el próximo nivel. Recibe la cantidad de R1 y R2
     * que aparecerán en este.
     * @param robots1 int
     * @param robots2 int
     */
    public void siguienteNivel(int robots1, int robots2) {
        reiniciarTablero();
        inicializarJugador();
        inicializarRobots(robots1, robots2);
    }

    /* Movimientos */

    /**
     * mover mueve al jugador y a los robots a la posición pos.
     * Devuelve True si la posición del jugador no coincide con la de otro elemento.
     * @param pos Vector2
     * @return boolean
     */
    public boolean mover(Vector2 pos) {
        conseguirCelda(player.getPosicion()).sacarObjeto();
        moverRobots(pos);
        return moverJugador(pos);
    }

    /**
     * moverJugador coloca al jugador en la celda en la que debe estar según su posición. Devuelve False si
     * esta celda ya está ocupada, True en caso contrario (y suma puntos).
     * @param pos Vector2
     * @return boolean
     */
    private boolean moverJugador(Vector2 pos) {
        if (!conseguirCelda(pos).asignarObjeto(player)) {
            return false;
        }
        player.moverse(pos, this);
        player.sumarPuntos(PUNTUACION_MOVIMIENTO);
        return true;
    }

    /**
     * moverRobots saca a los robots de su antigua posición, calcula para todos la nueva posición en la que
     * deberían estar en base a la del jugador, y luego asigna los movimientos correspondientes.
     * @param posJugador Vector2
     */
    private void moverRobots(Vector2 posJugador) {
        for (var robot : robots) {
            var posAntigua = new Vector2(robot.getPosicion());
            conseguirCelda(posAntigua).sacarObjeto();
            robot.moverse(posJugador, this);
        }
        asignarMovimientos();
    }

    /* Colisiones */

    /**
     * asignarMovimiento mueve cada robot a la celda correspondiente, manejando posibles colisiones
     */
    private void asignarMovimientos() {
        ArrayList<Robot> robots = new ArrayList<>(this.robots);
        for (var robot: robots) {
            var celdaParaMoverse = conseguirCelda(robot.getPosicion());
            if (!celdaParaMoverse.asignarObjeto(robot)) {
                manejarColision(robot, celdaParaMoverse);
            }
        }
    }

    /**
     * manejarColision elimina al Robot y suma los puntos correspondientes al Jugador, si el Robot colisiona con un Fuego
     * o elimina tanto al Robot como al otro Robot con el que colisiona y añade un Fuego en su lugar, sumando los puntos
     * correspondientes al Jugador.
     * @param robot Robot
     * @param celdaParaMoverse Celda
     */
    private void manejarColision(Robot robot, Celda celdaParaMoverse) {
        var elemento = celdaParaMoverse.sacarObjeto();
        if (elemento.esObstaculo()) {
            celdaParaMoverse.asignarObjeto(elemento);
        } else {
            player.sumarPuntos(sacarRobots((Robot) elemento));
            var fuego = new Fuego(robot.getPosicion());
            celdaParaMoverse.asignarObjeto(fuego);
            fuegos.add(fuego);
        }
        player.sumarPuntos(sacarRobots(robot));
    }

    /* Operaciones auxiliares */

    /**
     * sacarRobots elimina al Robot de la lista de robots y devuelve su puntuación
     * @param robot Robot
     * @return int
     */
    private int sacarRobots(Robot robot) {
        this.robots.remove(robot);
        return robot.getPuntuacion();
    }

    /**
     * generarPosAleatoria genera una posición aleatoria válida en el tablero
     * @return Vector2
     */
    private Vector2 generarPosAleatoria() {
        var rand = new Random();
        Vector2 posicion = new Vector2(rand.nextInt(filas), rand.nextInt(columnas));
        while (!esPosValida(posicion)) {
            posicion = new Vector2(rand.nextInt(filas), rand.nextInt(columnas));
        }
        return posicion;
    }

    /**
     * conseguirCelda devuelve la celda en la posición posBuscada
     * @param posBuscada Vector2
     * @return Celda
     */
    private Celda conseguirCelda(Vector2 posBuscada) {
        if (posBuscada.getX() < 0 || posBuscada.getX() >= this.filas || posBuscada.getY() < 0 || posBuscada.getY() >= this.columnas) {
            throw new IllegalArgumentException("Posicion invalida");
        }
        return this.celdas[posBuscada.getX()][posBuscada.getY()];
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
}