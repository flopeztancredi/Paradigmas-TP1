package org.robots.vista;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuUI extends UI {
    private final int INVALIDO = -1;
    private final Stage stage;
    private final Scene scene;

    @FXML
    private Button btnJugar;

    @FXML
    private TextField txtFilas;

    @FXML
    private TextField txtColumnas;

    public MenuUI(Stage stage) throws IOException {
        this.stage = stage;
        var parent = loadFXML("menu", this);
        this.scene = new Scene(parent, WIDTH, HEIGHT);
    }

    /**
     * Muestra la escena
     */
    public void mostrar() {
        stage.setScene(scene);
        stage.show();
    }

    /* Getters */

    public int getFilas() {
        int valor;
        try {
            valor = Integer.parseInt(txtFilas.getText());
        } catch (NumberFormatException e) {
            valor = INVALIDO;
        }
        return valor;
    }

    public int getColumnas() {
        int valor;
        try {
            valor = Integer.parseInt(txtColumnas.getText());
        } catch (NumberFormatException e) {
            valor = INVALIDO;
        }
        return valor;
    }

    public Button getBtnJugar() {
        return this.btnJugar;
    }

    /* Setters */

    public void setBtnHandler(EventHandler<ActionEvent> handler) {
        btnJugar.setOnAction(handler);
    }

    public void setSceneHandler(EventHandler<KeyEvent> handler) {
        scene.setOnKeyPressed(handler);
    }

}
