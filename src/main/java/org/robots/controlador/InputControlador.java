package org.robots.controlador;

import javafx.scene.Node;
import javafx.scene.Scene;
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
            assert c != null;
            var estadoJuego = juego.mover(GridPane.getRowIndex(c), GridPane.getColumnIndex(c));
            gameUI.actualizarEstado(estadoJuego);
        });

        scene.setOnKeyPressed(e -> {
            var estadoJuego = Estado.JUGANDO;
            switch (e.getCode()) {
                case W:
                case KP_UP:
                    estadoJuego = juego.moverDireccion(juego.ARRIBA);
                    break;
                case A:
                case KP_LEFT:
                    estadoJuego = juego.moverDireccion(juego.IZQUIERDA);
                    break;
                case S:
                case KP_DOWN:
                    estadoJuego = juego.moverDireccion(juego.ABAJO);
                    break;
                case D:
                case KP_RIGHT:
                    estadoJuego = juego.moverDireccion(juego.DERECHA);
                    break;
                case CONTROL:
                    estadoJuego = juego.quedarse();
                    break;
                case Q:
                    estadoJuego = juego.moverDireccion(Vector2.sumar(juego.ARRIBA, juego.IZQUIERDA));
                    break;
                case E:
                    estadoJuego = juego.moverDireccion(Vector2.sumar(juego.ARRIBA, juego.DERECHA));
                    break;
                case Z:
                    estadoJuego = juego.moverDireccion(Vector2.sumar(juego.ABAJO, juego.IZQUIERDA));
                    break;
                case C:
                    estadoJuego = juego.moverDireccion(Vector2.sumar(juego.ABAJO, juego.DERECHA));
                    break;
                case T:
                    estadoJuego = juego.mover();
                    break;
                default:
                    break;
            }
            gameUI.actualizarEstado(estadoJuego);
        });
    }

    private Node getCelda(MouseEvent e) {
        var source = (Node) e.getTarget();
        if (source instanceof GridPane) return null;
        while (!(source instanceof Pane)) source = source.getParent();
        return source;
    }
}