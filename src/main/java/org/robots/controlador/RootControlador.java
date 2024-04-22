package org.robots.controlador;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RootControlador implements Initializable {
    private final Stage stage;
    @FXML
    private AnchorPane pane;

    @FXML
    private GridPane tablero;

    public RootControlador(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tablero.setGridLinesVisible(true);

        var columnas = 7;
        var filas = 7;
        for (int j = 0; j < filas; j++) {
            RowConstraints fila = new RowConstraints();
            fila.setPercentHeight(100.0 / filas);
            tablero.getRowConstraints().add(fila);
        }

        for (int i = 0; i < columnas; i++) {
            ColumnConstraints columna = new ColumnConstraints();
            columna.percentWidthProperty().bind(tablero.heightProperty().divide(filas));
            tablero.getColumnConstraints().add(columna);
        }
    }
}