package org.example.logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Tablero {
    private HashMap<Vector2, Celda> celdas;
    private final int filas;
    private final int columnas;
    private Jugador player;
    private ArrayList<Robot> robots;


    public Tablero(int filas, int columnas, Jugador player) {
        this.filas = filas;
        this.columnas = columnas;
        inicializarJugador();

        celdas = new HashMap<Vector2, Celda>();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas.put(new Vector2(i, j), new Celda(null));
            }
        }
    }

    public boolean esPosicionValida(Vector2 posNueva) {
        return celdas.get(posNueva).EstaVacia();
    }


    public Vector2 posicionAleatoria() {
        var rand = new Random();
        Vector2 posicion = new Vector2(rand.nextInt(filas), rand.nextInt(columnas));
        while (!esPosicionValida(posicion)) {
            posicion = new Vector2(rand.nextInt(filas), rand.nextInt(columnas));
        }
        return posicion;
    }


    public void inicializarJugador() {
        var pos = posicionAleatoria();
        var jugador = new Jugador(pos);
        celdas.replace(pos, new Celda(jugador));
        this.player = jugador;
    }


    public void inicializarRobots(int r1, int r2) {
        for (int i = 0; i < r1; i++) {
            var pos = posicionAleatoria();
            var robot1 = new R1(pos);
            celdas.get(pos).AsignarObjeto(robot1);
        }

        for (int i = 0; i < r2; i++) {
            var pos = posicionAleatoria();
            var robot2 = new R2(pos);
            celdas.get(pos).AsignarObjeto(robot2);
        }
    }


    public void nuevoNivel(int robots1, int robots2) {
        inicializarJugador();
        inicializarRobots(robots1, robots2);
    }


    public void mover(Dirección dirección) {

        var posVieja = player.getPosicion();
        var posNueva = dirección.DirigirMovimiento(posVieja);
        player.Moverse(posNueva);
        reemplazarCelda(posVieja, player.getPosicion(), player);

        for (Robot robot : robots) {
            posVieja = robot.getPosicion();
            robot.Moverse(posNueva);
            reemplazarCelda(posVieja, robot.getPosicion(), robot);
        }

    }

    private void reemplazarCelda(Vector2 posAntigua, Vector2 posNueva, Elemento objeto) {
        celdas.get(posAntigua).SacarObjeto();
        var nuevaCelda = celdas.get(posNueva);
        if (!nuevaCelda.AsignarObjeto(objeto)) {
            if (objeto instanceof Jugador || nuevaCelda.getObjeto() instanceof Jugador) {
                // perdiste
            } else {
                if (objeto instanceof Robot) {
                    robots.remove(objeto);
                }
                if (nuevaCelda.getObjeto() instanceof Robot) {
                    robots.remove(nuevaCelda.getObjeto());
                }
                nuevaCelda.Incendiar(posNueva);
            }
        }
    }
}
