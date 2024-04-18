package org.example.logica;

import java.util.ArrayList;
import java.util.Arrays;
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
                celdas.put(new Vector2(i, j), new Celda());
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
        var posNueva = dirección.DirigirMovimiento(posVieja, this.filas, this.columnas);
        player.Moverse(posNueva);
        if (!celdas.get(posNueva).AsignarObjeto(player)) {
            // perdiste: te prendiste fuego.
            // devolvemos un booleano acá o lo manejamos en colisiones?
            // para mi lo mejor es manejarlo en colisiones, pero poniendolo aca te ahorras los movimientos
            // de los robots
        }

        for (Robot robot : robots) {
            if (robot instanceof R2) {
                robot.Moverse(posNueva);
            }
        }

        // la superposicion de dos robots nunca va a pasar durante el movimiento -- siempre hay que
        // chequearlo al final. Lo que si hay que chequear durante son los incendios. Tiene sentido que
        // una celda entonces avise que hay que sacar un elemento cuando se asigna y esta esta incendiada.
        // volver a poner para R1 y R2 los movimientos particulares?... si le encontramos la vuelta a
        // saber que R2 piso una celda en el medio... quizas devolver como valor la que pisa, pero
        // que Moverse() tenga que devolver algo para R2 y para R1 no, raro


        for (Robot robot : robots) {
            posVieja = robot.getPosicion();
            robot.Moverse(posNueva);
            celdas.get(posVieja).SacarObjetos();
            if (!celdas.get(robot.getPosicion()).AsignarObjeto(robot)) {
                robots.remove(robot);
            }
        }

    }

    public boolean Colisiones() {
        var celdaJugador = celdas.get(player.getPosicion());
        if (celdaJugador.EstaIncendiada() || celdaJugador.HayRobot()){
            return true;
        }

        for (Robot r : this.robots) {
            var celdaRobot = celdas.get(r.getPosicion());
            if (celdaRobot.EstaIncendiada() || celdaRobot.getObjeto().size() > 1) {
                this.robots.removeAll(celdaRobot.SacarObjetos());
                celdaRobot.Incendiar();
            }
        }

        return false;
    }

    public boolean HayPerdedor() {
        return Colisiones();
    }

    public boolean HayGanador() {
        return this.robots.isEmpty();
    }



    // El problema con el reemplazarCelda es que no hacemos todos los movimientos a la vez, entonces quizás dependiendo
    // el orden se chocan robots que no necesariamente deberían chocarse si se mueven a la vez.
    /*private void reemplazarCelda(Vector2 posAntigua, Vector2 posNueva, Elemento objeto) {
        celdas.get(posAntigua).SacarObjeto();
        var nuevaCelda = celdas.get(posNueva);
        if (!nuevaCelda.AsignarObjeto(objeto)) {
            if (objeto instanceof Jugador || nuevaCelda.getObjeto() instanceof Jugador) {
                // perdiste: te agarró un robot
            } else {

                nuevaCelda.Incendiar(posNueva);
            }
        }
    }*/
}
