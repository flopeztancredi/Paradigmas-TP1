package org.robots.vista;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.robots.controlador.InputControlador;
import org.robots.modelo.Estado;
import org.robots.modelo.Juego;

import java.io.IOException;

public class GameUI extends UI {
    private final Stage stage;
    private final Parent parent;
    private Runnable volverAlMenu;
    private Runnable reiniciarJuego;
    private Juego juego;
    private GridUI grid;

    @FXML
    private GridPane gridTablero;

    @FXML
    private Button btnRandomTp;

    @FXML
    private ToggleButton btnSafeTp;

    @FXML
    private Button btnWait;

    @FXML
    private Label cantTpSafe;

    @FXML
    private Label nivel;

    @FXML
    private Label points;

    public GameUI(Stage stage) throws IOException {
        this.stage = stage;
        this.parent = loadFXML("juego", this);
    }

    public void inicializarJuego(Juego juego) {
        Scene scene = new Scene(this.parent, super.getWIDTH(), super.getHEIGHT());
        this.juego = juego;
        this.grid = new GridUI(juego, gridTablero);
        var gridControlador = new InputControlador(grid, this, juego, scene);
        gridControlador.iniciar();
        juego.siguienteNivel();
        grid.dibujarTablero();

        stage.setScene(scene);
        stage.show();
    }

    public void actualizarEstado(Estado estadoJuego) {
        grid.dibujarTablero();
        actualizarValores();
        try {
            corroborarEstado(estadoJuego);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void actualizarValores() {
        nivel.setText(String.valueOf(juego.getNivel()));
        points.setText(String.valueOf(juego.getPuntuacion()));
        cantTpSafe.setText(Integer.toString(juego.getCantTpSafe()));
    }

    private void corroborarEstado(Estado estadoJuego) throws IOException {
        if (estadoJuego == Estado.JUGANDO) return;

        PopUp popUp = new PopUp(estadoJuego == Estado.GANADO, points.getText());
        ButtonType buttonType = popUp.showAndWait().get();
        if (buttonType.equals(PopUp.SALIR)) {
            stage.close();
        } else if (buttonType.equals(PopUp.VOLVER)) {
            volverAlMenu.run();
        } else if (buttonType.equals(PopUp.REINTENTAR)) {
            reiniciarJuego.run();
        } else if (buttonType.equals(PopUp.AVANZAR)) {
            juego.siguienteNivel();
            grid.dibujarTablero();
            actualizarValores();
        }
    }

    public void actualizarTpSafe() {
        int cantidad = juego.getCantTpSafe();
        cantTpSafe.setText(Integer.toString(cantidad));
        btnSafeTp.setDisable(cantidad == 0);
    }

    public void setVolverAlMenuHandler(Runnable runnable) { this.volverAlMenu = runnable; }

    public void setWaitBtnHandler(EventHandler<ActionEvent> handler) { btnWait.setOnAction(handler); }

    public void setRandomBtnHandler(EventHandler<ActionEvent> handler) { btnRandomTp.setOnAction(handler); }

    public void setSafeBtnHandler(EventHandler<ActionEvent> handler) { btnSafeTp.setOnAction(handler); }

    public void setReiniciarHandler(Runnable runnable) { this.reiniciarJuego = runnable; }
}