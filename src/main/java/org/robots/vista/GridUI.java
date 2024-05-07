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
import org.robots.modelo.Estado;
import org.robots.modelo.Juego;
import org.robots.modelo.personajes.Elemento;

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
     * @param estadoJuego Estado
     */
    public void dibujarTablero(Estado estadoJuego) {
        vaciarTablero();
        for (var elemento : juego.getElementos()) {
            Pane p = (Pane) gridTablero.getChildren().get(elemento.getY() + elemento.getX() * juego.getColumnas());
            ImageView sprite = conseguirSprite(elemento, estadoJuego);
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

    /**
     * Consigue la sprite del elemento indicado por parámetro según el estadoJuego correspondiente
     * @param elemento Elemento
     * @param estadoJuego Estado
     * @return ImageView
     */
    private ImageView conseguirSprite(Elemento elemento, Estado estadoJuego) {
        if (elemento.esJugador() && estadoJuego == Estado.JUGANDO) {
            return Imagenes.getRandomSprite(Imagenes.JUGADOR_DEFAULT);
        } else if (elemento.esJugador() && estadoJuego == Estado.GANADO) {
            return Imagenes.getRandomSprite(Imagenes.JUGADOR_GANADOR);
        } else if (elemento.esJugador() && estadoJuego == Estado.PERDIDO) {
            return Imagenes.getRandomSprite(Imagenes.JUGADOR_PERDEDOR);
        } else if (elemento.esRobot1()) {
            return Imagenes.getRandomSprite(Imagenes.ROBOT1_SPRITES);
        } else if (elemento.esRobot2()) {
            return Imagenes.getRandomSprite(Imagenes.ROBOT2_SPRITES);
        } else { // elemento.esFuego()
            return Imagenes.getRandomSprite(Imagenes.FUEGO_SPRITES);
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
