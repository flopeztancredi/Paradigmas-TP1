package org.robots.vista;

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

        btnJugar.setOnAction(e -> {
            try {
                iniciarJuego();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public void mostrar() {
        Scene scene = new Scene(parent, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void iniciarJuego() throws IOException {
        int filas = Integer.parseInt(txtFilas.getText());
        int columnas = Integer.parseInt(txtColumnas.getText());
        var gameUI = new GameUI(stage, new Juego(filas, columnas));
        gameUI.iniciarJuego();
    }
}
