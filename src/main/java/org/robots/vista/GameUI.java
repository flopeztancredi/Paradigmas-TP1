package org.robots.vista;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.robots.modelo.Juego;

import java.io.IOException;

public class GameUI extends UI {
    private final Juego juego;
    private final Stage stage;

    @FXML
    private GridPane gridTablero;


    public GameUI(Stage stage, Juego juego) {
        this.juego = juego;
        this.stage = stage;
    }

    public void iniciarJuego() throws IOException {
        Parent p = loadFXML("juego", this);
        iniciarTablero(juego.getFilas(), juego.getColumnas());

        Scene scene = new Scene(p, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void iniciarTablero(int filas, int columnas) {
        gridTablero.setGridLinesVisible(false);
        for (int i = 0; i < columnas; i++) {
            ColumnConstraints columna = new ColumnConstraints();
            columna.prefWidthProperty().bind(gridTablero.heightProperty().divide(filas));
            gridTablero.getColumnConstraints().add(columna);
        }

        for (int i = 0; i < filas; i++) {
            RowConstraints fila = new RowConstraints();
            fila.prefHeightProperty().bind(gridTablero.widthProperty().divide(columnas));
            gridTablero.getRowConstraints().add(fila);
        }

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
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
}
