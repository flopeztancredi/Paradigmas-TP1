package org.robots.vista;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import org.robots.modelo.Juego;

public class GridUI {
    private final Juego juego;
    private Scene scene;
    private GridPane gridTablero;

    public GridUI(Juego juego) {
        this.juego = juego;
    }

    /**
     * Inicializa el tablero con las filas y columnas del juego
     * @param gridTablero GridPane
     * @param scene Scene
     */
    public void inicializarTablero(GridPane gridTablero, Scene scene) {
        this.gridTablero = gridTablero;
        this.scene = scene;
        gridTablero.setGridLinesVisible(false);
        for (int i = 0; i < juego.getColumnas(); i++) {
            ColumnConstraints columna = new ColumnConstraints();
            columna.prefWidthProperty().bind(gridTablero.heightProperty().divide(juego.getFilas()));
            gridTablero.getColumnConstraints().add(columna);
        }

        for (int i = 0; i < juego.getFilas(); i++) {
            RowConstraints fila = new RowConstraints();
            fila.prefHeightProperty().bind(gridTablero.widthProperty().divide(juego.getColumnas()));
            gridTablero.getRowConstraints().add(fila);
        }

        for (int i = 0; i < juego.getFilas(); i++) {
            for (int j = 0; j < juego.getColumnas(); j++) {
                Pane p = new Pane();
                if ((i + j) % 2 == 0) {
                    p.setStyle(Colores.FONDO_CLARO);
                } else {
                    p.setStyle(Colores.FONDO_OSCURO);
                }
                gridTablero.add(p, j, i);
            }
        }
    }

    /**
     * Dibuja el tablero con los elementos del juego
     */
    public void dibujarTablero() {
        vaciarTablero();
        for (var elemento : juego.getElementos()) {
            Pane p = (Pane) gridTablero.getChildren().get(elemento.getY() + elemento.getX() * juego.getColumnas());
            ImageView sprite = Imagenes.getRandomSprite(elemento.getNombre());
            assert sprite != null;
            sprite.fitHeightProperty().bind(p.heightProperty());
            sprite.fitWidthProperty().bind(p.widthProperty());
            sprite.setPreserveRatio(true);
            p.getChildren().add(sprite);
        }
    }

    /**
     * Vacía todas las celdas del tablero
     */
    private void vaciarTablero() {
        for (int i = 0; i < juego.getFilas(); i++) {
            for (int j = 0; j < juego.getColumnas(); j++) {
                Pane p = (Pane) gridTablero.getChildren().get(j + i * juego.getColumnas());
                p.getChildren().clear();
            }
        }
    }

    /* Setters */

    public void setGridMouseHandler(EventHandler<MouseEvent> handler) {
        gridTablero.setOnMouseClicked(handler);
    }

    public void setGridKeyHandler(EventHandler<KeyEvent> handler) {
        scene.setOnKeyPressed(handler);
    }
}
