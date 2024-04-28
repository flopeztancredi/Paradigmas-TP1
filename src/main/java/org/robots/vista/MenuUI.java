package org.robots.vista;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuUI extends UI {
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
        setScene();
    }

    public void setScene() {
        this.scene = new Scene(parent, super.getWIDTH(), super.getHEIGHT());
    }

    public void mostrar() {
        stage.setScene(scene);
        stage.show();
    }

    public void setBtnHandler(EventHandler<ActionEvent> handler) {
        btnJugar.setOnAction(handler);
    }

    public int getFilas() {
        return Integer.parseInt(txtFilas.getText());
    }

    public int getColumnas() {
        return Integer.parseInt(txtColumnas.getText());
    }

}
