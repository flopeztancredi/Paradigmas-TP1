package org.robots.controlador;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.robots.modelo.Estado;
import org.robots.modelo.Juego;
import org.robots.modelo.herramientas.Vector2;
import org.robots.vista.GameUI;
import org.robots.vista.GridUI;

public class InputControlador {
    private final GridUI gridUI;
    private final GameUI gameUI;
    private final Juego juego;
    private final Scene scene;


    public InputControlador(GridUI gridUI, GameUI gameUI, Juego juego, Scene scene) {
        this.gridUI = gridUI;
        this.gameUI = gameUI;
        this.juego = juego;
        this.scene = scene;
    }

    public void iniciar() {
        gridUI.setGridMouseHandler(e -> {
            Node c = getCelda(e);
            if (c == null) return;
            var estadoJuego = juego.mover(GridPane.getRowIndex(c), GridPane.getColumnIndex(c));
            gameUI.actualizarEstado(estadoJuego);
        });

        scene.setOnKeyPressed(e -> {
            var estadoJuego = Estado.JUGANDO;
            var cambio = false;
            switch (e.getCode()) {
                case W:
                    estadoJuego = juego.moverDireccion(juego.ARRIBA);
                    cambio = true;
                    break;
                case A:
                    estadoJuego = juego.moverDireccion(juego.IZQUIERDA);
                    cambio = true;
                    break;
                case S:
                    estadoJuego = juego.moverDireccion(juego.ABAJO);
                    cambio = true;
                    break;
                case D:
                    estadoJuego = juego.moverDireccion(juego.DERECHA);
                    cambio = true;
                    break;
                case CONTROL:
                    cambio = true;
                    estadoJuego = juego.quedarse();
                    break;
                case Q:
                    estadoJuego = juego.moverDireccion(Vector2.sumar(juego.ARRIBA, juego.IZQUIERDA));
                    cambio = true;
                    break;
                case E:
                    estadoJuego = juego.moverDireccion(Vector2.sumar(juego.ARRIBA, juego.DERECHA));
                    cambio = true;
                    break;
                case Z:
                    estadoJuego = juego.moverDireccion(Vector2.sumar(juego.ABAJO, juego.IZQUIERDA));
                    cambio = true;
                    break;
                case C:
                    estadoJuego = juego.moverDireccion(Vector2.sumar(juego.ABAJO, juego.DERECHA));
                    cambio = true;
                    break;
                case T:
                    estadoJuego = juego.mover();
                    cambio = true;
                    break;
                case M:
                    cambio = true;
                default:
                    break;
            }
            if (cambio) {
                gameUI.actualizarEstado(estadoJuego);
            }
        });
    }

    private Node getCelda(MouseEvent e) {
        var source = (Node) e.getTarget();
        if (source instanceof GridPane) return null;
        while (!(source instanceof Pane)) source = source.getParent();
        return source;
    }
}