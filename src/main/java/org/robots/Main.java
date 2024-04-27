package org.robots;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.robots.modelo.Juego;
import org.robots.vista.GameUI;
import org.robots.vista.MenuUI;

import java.io.IOException;


/**
 * JavaFX App
 */
public class Main extends Application {
    private Scene gameScene;
    private Scene menuScene;

    private MenuUI menu;
    private GameUI game;


    @Override
    public void start(Stage stage) throws IOException {
        mostrarMenu(stage);
    }

    public void mostrarMenu(Stage stage) throws IOException {
        this.menu = new MenuUI(stage);
        menu.agregarHandler(e -> {
            try {
                mostrarJuego(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        menu.mostrar();
    }

    public void mostrarJuego(Stage stage) throws IOException {
        this.game = new GameUI(stage, new Juego(menu.getFilas(), menu.getColumnas()));
        game.iniciarJuego();
    }

    public static void main(String[] args) {
        launch();
    }
}