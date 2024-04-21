package org.robots.vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var vBox = new VBox();

        var labelTitulo = new Label("Robots");
        var labelPuntaje = new Label("Nivel: 0 \t Puntaje: 0");
        var titulo = new VBox(labelTitulo, labelPuntaje);


        var label = new Label("Boca");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}