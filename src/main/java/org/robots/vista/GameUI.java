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
    private final Parent parent;

    private final int AUMENTAR = 1;
    private final int DISMINUIR = -1;

    private final GridUI grid;

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

    public GameUI(Stage stage, Juego juego) throws IOException {
        this.juego = juego;
        this.stage = stage;
        this.parent = loadFXML("juego", this);
        this.grid  = new GridUI(juego, gridTablero);
    }

    public void iniciarJuego() {
        grid.iniciarTablero();
        juego.siguienteNivel();
        grid.dibujarTablero();

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
            var estadoJuego = juego.mover(x, y);
            grid.dibujarTablero();

            try {
                corroborarEstado(estadoJuego);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            actualizarValores();
            safe.setSelected(false);
        });

        random.setOnAction(e -> {
            var estadoJuego = juego.mover();
            try {
                corroborarEstado(estadoJuego);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            actualizarValores();
            grid.dibujarTablero();
        });

        safe.setOnAction(actionEvent -> {
            juego.activarTpSafe();
            actualizarTpSafe();
        });

        wait.setOnAction(e -> {
            var estadoJuego = juego.quedarse();
            try {
                corroborarEstado(estadoJuego);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            actualizarValores();
            grid.dibujarTablero();
        });

        Scene scene = new Scene(this.parent, super.getWIDTH(), super.getHEIGHT());
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
        var volver = new ButtonType("Volver al menu");
        var salir = new ButtonType("Salir");
        var reiniciar = new ButtonType("Volver a intentar");
        ArrayList<ButtonType> b = new ArrayList<>();
        b.add(volver); b.add(reiniciar); b.add(salir);
        if (estadoJuego == Estado.PERDIDO) {
            estadoNivel = "Perdiste!";
        } else if (estadoJuego == Estado.GANADO) {
            estadoNivel = "Ganaste el nivel!";
            var siguiente = new ButtonType("Avanzar al siguiente nivel");
            b.add(siguiente);
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Gnome Messi");
        alert.setHeaderText(estadoNivel);
        alert.setContentText("Puntuacion: " + points.getText());
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(b);
        Optional<ButtonType> respuesta = alert.showAndWait();
        if (respuesta.get().equals(salir)) {
            stage.close();
        } else if (respuesta.get().equals(volver)) {
            var menu = new MenuUI(stage);
            menu.mostrar();
        } else if (respuesta.get().equals(reiniciar)) {
            var game = new GameUI(stage, new Juego(juego.getFilas(), juego.getColumnas()));
            game.iniciarJuego();
        } else {
            juego.siguienteNivel();
            grid.dibujarTablero();
            actualizarTpSafe();
        }
    }

    private void actualizarTpSafe() {
        int cantidad = juego.getCantTpSafe();
        cantTpSafe.setText(Integer.toString(cantidad));
        safe.setDisable(cantidad == 0);
    }
}
