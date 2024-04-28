package org.robots.vista;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import org.robots.modelo.Juego;

public class GridUI {
    private final Juego juego;
    private final GridPane gridTablero;

    public GridUI(Juego juego, GridPane gridTablero) {
        this.juego = juego;
        this.gridTablero = gridTablero;
        iniciarTablero();
    }

    public void iniciarTablero() {
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
                    p.setStyle("-fx-background-color: #C7DCFA");
                } else {
                    p.setStyle("-fx-background-color: #13305C");
                }
                gridTablero.add(p, j, i);
            }
        }
    }

    public void dibujarTablero() {
        for (int i = 0; i < juego.getFilas(); i++) {
            for (int j = 0; j < juego.getColumnas(); j++) {
                Pane p = (Pane) gridTablero.getChildren().get(j + i * juego.getColumnas());
                p.getChildren().clear();
            }
        }

        for (var elemento : juego.getElementos()) {
            Pane p = (Pane) gridTablero.getChildren().get(elemento.getY() + elemento.getX() * juego.getColumnas());
            Label l = new Label(elemento.getNombre());
            l.setAlignment(Pos.CENTER);
            l.prefWidthProperty().bind(p.widthProperty());
            l.prefHeightProperty().bind(p.heightProperty());
            if (l.getText().equals("Jugador")) {
                l.setStyle("-fx-background-color: #F0F0F0");
            } else if (l.getText().equals("Fuego")) {
                l.setStyle("-fx-background-color: #777777");
            } else {
                l.setStyle("-fx-background-color: #FF8000");
            }
            p.getChildren().add(l);
        }
    }

    public void setGridMouseHandler(EventHandler<MouseEvent> handler) {
        gridTablero.setOnMouseClicked(handler);
    }
}
