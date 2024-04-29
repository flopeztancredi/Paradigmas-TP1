package org.robots.vista;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
    private Button btnSafeTp;

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

    /**
     * Inicializa el juego con el tablero y el juego
     * @param juego Juego
     * @param grid GridUI
     */
    public void inicializarJuego(Juego juego, GridUI grid) {
        this.juego = juego;
        this.grid = grid;
        Scene scene = new Scene(this.parent, WIDTH, HEIGHT);
        grid.inicializarTablero(gridTablero, scene);

        juego.siguienteNivel();
        grid.dibujarTablero(Estado.JUGANDO);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Actualiza el tablero y los valores del juego
     * @param estadoJuego Estado
     */
    public void actualizarEstado(Estado estadoJuego) {
        grid.dibujarTablero(estadoJuego);
        actualizarValores();
        corroborarEstado(estadoJuego);
    }

    /**
     * Actualiza los valores del juego vistos en la interfaz
     */
    private void actualizarValores() {
        nivel.setText(String.valueOf(juego.getNivel()));
        points.setText(String.valueOf(juego.getPuntuacion()));
        cantTpSafe.setText(Integer.toString(juego.getCantTpSafe()));
        btnSafeTp.setDisable(juego.getCantTpSafe() == 0);
        btnSafeTp.setStyle((juego.isTpSafe() ? Colores.FONDO_ACTIVO : Colores.FONDO_INACTIVO) + "; " + Colores.BORDE_BLANCO);
    }

    /**
     * Corrobora el estado del juego y muestra el popUp correspondiente si estadoJuego es GANADO o PERDIDO
     * @param estadoJuego Estado
     */
    private void corroborarEstado(Estado estadoJuego) {
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
            grid.dibujarTablero(Estado.JUGANDO);
            actualizarValores();
        }
    }

    /* Setters */

    public void setVolverAlMenuHandler(Runnable runnable) { this.volverAlMenu = runnable; }

    public void setWaitBtnHandler(EventHandler<ActionEvent> handler) { btnWait.setOnAction(handler); }

    public void setRandomBtnHandler(EventHandler<ActionEvent> handler) { btnRandomTp.setOnAction(handler); }

    public void setSafeBtnHandler(EventHandler<ActionEvent> handler) { btnSafeTp.setOnAction(handler); }

    public void setReiniciarHandler(Runnable runnable) { this.reiniciarJuego = runnable; }
}