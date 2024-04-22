package org.robots;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.robots.modelo.Juego;
import org.robots.vista.MenuUI;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var menu = new MenuUI(stage);
        menu.mostrar();
    }

    public static void main(String[] args) {
        launch();
    }
}