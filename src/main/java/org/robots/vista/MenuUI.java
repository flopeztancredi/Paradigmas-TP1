package org.robots.vista;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.robots.modelo.Juego;

import java.io.IOException;

public class MenuUI extends UI {
    private final Parent parent;
    private final Stage stage;

    @FXML
    private Button btnJugar;

    @FXML
    private TextField txtFilas;

    @FXML
    private TextField txtColumnas;

    public MenuUI(Stage stage) throws IOException {
        this.stage = stage;
        this.parent = loadFXML("menu", this);
    }

    public void mostrar() {
        Scene scene = new Scene(parent, super.getWIDTH(), super.getHEIGHT());
        stage.setScene(scene);
        stage.show();
    }

    public void agregarHandler(EventHandler<ActionEvent> handler) {
        btnJugar.setOnAction(handler);
    }

    public int getFilas() {
        return Integer.parseInt(txtFilas.getText());
    }

    public int getColumnas() {
        return Integer.parseInt(txtColumnas.getText());
    }

}
