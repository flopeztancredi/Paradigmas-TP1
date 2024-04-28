package org.robots.vista;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuUI extends UI {
    private final int INVALIDO = -1;
    private final Parent parent;
    private final Stage stage;
    private Scene scene;

    @FXML
    private Button btnJugar;

    @FXML
    private TextField txtFilas;

    @FXML
    private TextField txtColumnas;

    public MenuUI(Stage stage) throws IOException {
        this.stage = stage;
        this.parent = loadFXML("menu", this);
        this.scene = new Scene(parent, super.getWIDTH(), super.getHEIGHT());
    }

    public void mostrar() {
        stage.setScene(scene);
        stage.show();
    }

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

    public void setBtnHandler(EventHandler<ActionEvent> handler) {
        btnJugar.setOnAction(handler);
    }

    public void setSceneHandler(EventHandler<KeyEvent> handler) {
        scene.setOnKeyPressed(handler);
    }

}
