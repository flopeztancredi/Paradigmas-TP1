package org.robots.vista;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.robots.modelo.Estado;
import org.robots.modelo.Juego;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class GameUI extends UI {
    private final Juego juego;
    private final Stage stage;
    private boolean tpSafe = false;

    private final int AUMENTAR = 1;
    private final int DISMINUIR = -1;

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

    @FXML
    private Label nivel;

    @FXML
    private Label points;


    public GameUI(Stage stage, Juego juego) {
        this.juego = juego;
        this.stage = stage;
    }

    public void iniciarJuego() throws IOException {
        Parent p = loadFXML("juego", this);
        iniciarTablero(juego.getFilas(), juego.getColumnas());
        juego.siguienteNivel();
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
            Estado estadoJuego;
            if (this.tpSafe) {
                estadoJuego = juego.mover(x, y);
                cambiarTpSafe(DISMINUIR);
                safe.setSelected(false);
                this.tpSafe = false;
            } else {
                estadoJuego = juego.moverHacia(x, y);
            }
            try {
                corroborarEstado(estadoJuego);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dibujarTablero();
        });

        random.setOnAction(e -> {
            var rand = new Random();
            var estadoJuego = juego.mover(rand.nextInt(juego.getFilas()), rand.nextInt(juego.getColumnas()));
            try {
                corroborarEstado(estadoJuego);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dibujarTablero();
        });

        safe.setOnAction(actionEvent -> {
            if (juego.activarTpSafe()) {
                this.tpSafe = true;
            }
        });

        wait.setOnAction(e -> {
            var estadoJuego = juego.quedarse();
            try {
                corroborarEstado(estadoJuego);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dibujarTablero();
        });

        Scene scene = new Scene(p, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void actualizarValores() {
        nivel.setText(String.valueOf(juego.getNivel()));
        points.setText(String.valueOf(juego.getPuntuacion()));
    }

    private void corroborarEstado(Estado estadoJuego) throws IOException {
        if (estadoJuego == Estado.JUGANDO) return;

        String estadoNivel = " ";
        var reiniciar = new ButtonType("Volver al menu");
        var salir = new ButtonType("Salir");
        ArrayList<ButtonType> b = new ArrayList<>();
        b.add(reiniciar); b.add(salir);
        if (estadoJuego == Estado.PERDIDO) {
            estadoNivel = "Perdiste!";
        } else if (estadoJuego == Estado.GANADO) {
            estadoNivel = "Ganaste el nivel!";
            var siguiente = new ButtonType("Avanzar al siguiente nivel");
            b.add(siguiente);
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, estadoNivel);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(b);
        Optional<ButtonType> respuesta = alert.showAndWait();
        if (respuesta.get().equals(salir)) {
            stage.close();
        } else if (respuesta.get().equals(reiniciar)) {
            var menu = new MenuUI(stage);
            menu.mostrar();
        } else {
            juego.siguienteNivel();
            cambiarTpSafe(AUMENTAR);
        }
    }

    private void cambiarTpSafe(int n) {
        int cantidad = Integer.parseInt(cantTpSafe.getText()) + n;
        cantTpSafe.setText(Integer.toString(cantidad));

        if (cantidad == 0) {
            safe.setDisable(true);
        } else {
            safe.setDisable(false);
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
        actualizarValores();

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
}
