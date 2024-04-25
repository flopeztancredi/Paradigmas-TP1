package org.robots.vista;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.robots.modelo.Juego;

import java.io.IOException;
import java.util.Random;

public class GameUI extends UI {
    private final Juego juego;
    private final Stage stage;
    private boolean tpSafe = false;

    @FXML
    private GridPane gridTablero;

    @FXML
    private Button random;

    @FXML
    private ToggleButton safe;

    @FXML
    private Button wait;

    @FXML
    private Label cantTpSafe;


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
            Node celda;
            var source = (Node) e.getTarget();
            if (source instanceof GridPane) {
                return;
            }
            while (!(source instanceof Pane)) {
                source = source.getParent();
            }
            celda = source;
            var x = GridPane.getRowIndex(celda);
            var y = GridPane.getColumnIndex(celda);
            if (this.tpSafe) {
                juego.mover(x, y);
                this.tpSafe = false;
                disminuirTpSafe();
            } else {
                juego.moverHacia(x, y);
            }
            dibujarTablero();
        });

        random.setOnAction(e -> {
            var rand = new Random();
            juego.mover(rand.nextInt(juego.getColumnas()), rand.nextInt(juego.getColumnas()));
            dibujarTablero();
        });

        safe.setOnAction(actionEvent -> {
            if (juego.activarTpSafe()) {
                this.tpSafe = true;
            }
        });

        wait.setOnAction(e -> {
            juego.quedarse();
            dibujarTablero();
        });

        Scene scene = new Scene(p, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void disminuirTpSafe() {
        int cantidad = Integer.parseInt(cantTpSafe.getText()) - 1;
        cantTpSafe.setText(Integer.toString(cantidad));

        if (cantidad == 0) {
            safe.setDisable(true);
        }
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
            System.out.println(elemento.getX() + " " + elemento.getY() + " " + elemento.getNombre());
            Pane p = (Pane) gridTablero.getChildren().get(elemento.getY() + elemento.getX() * juego.getColumnas());
            Label l = new Label(elemento.getNombre());
            l.setAlignment(javafx.geometry.Pos.CENTER);
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
}
