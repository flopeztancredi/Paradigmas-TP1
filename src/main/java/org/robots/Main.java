package org.robots;

import javafx.application.Application;
import javafx.stage.Stage;
import org.robots.vista.GameUI;
import org.robots.vista.MenuUI;

import java.io.IOException;


/**
 * JavaFX App
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var menu = new MenuUI(stage);
        menu.mostrar();
    }

    public static void main(String[] args) {
        launch();
    }
}