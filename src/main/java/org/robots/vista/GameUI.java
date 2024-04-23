package org.robots.vista;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
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
        juego.inicializarNivel();
        dibujarTablero();

        gridTablero.setOnMouseClicked(e -> {
            // esto calcula la celda seleccionada, pero no funciona al 100%.
            // tenemos que ver como hacer para que le pegue siempre, o arreglarnoslas de otra forma (meterle handlers a TODOS los panes?)
            int x = (int) (e.getX() / (gridTablero.getWidth() / juego.getColumnas()));
            int y = (int) (e.getY() / (gridTablero.getHeight() / juego.getFilas()));
            juego.mover(x, y);
            dibujarTablero();
        });

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

    private void dibujarTablero() {
        // esto está horrible. basicamente borra tdo y vuelve a dibujar
        for (int i = 0; i < juego.getFilas(); i++) {
            for (int j = 0; j < juego.getColumnas(); j++) {
                Pane p = (Pane) gridTablero.getChildren().get(j + i * juego.getColumnas());
                p.getChildren().clear();
            }
        }

        for (var elemento : juego.getElementos()) {
            Pane p = (Pane) gridTablero.getChildren().get(elemento.getX() + elemento.getY() * juego.getColumnas());
            Label l = new Label(elemento.getNombre());
            l.setAlignment(javafx.geometry.Pos.CENTER);
            l.prefWidthProperty().bind(p.widthProperty());
            l.prefHeightProperty().bind(p.heightProperty());
            p.getChildren().add(l);
        }
    }
}
