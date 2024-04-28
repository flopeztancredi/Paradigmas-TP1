package org.robots;

import javafx.application.Application;
import javafx.stage.Stage;
import org.robots.controlador.BotonesControlador;
import org.robots.controlador.MenuControlador;
import org.robots.modelo.Juego;
import org.robots.vista.GameUI;
import org.robots.vista.Imagenes;
import org.robots.vista.MenuUI;
import org.robots.vista.PopUp;

import java.io.FileInputStream;
import java.io.IOException;


/**
 * JavaFX App
 */
public class GnomeApp extends Application {
    private MenuUI menu;
    private GameUI game;
    private Juego modelo;
    private Stage stage;

    private final String APP_NAME = "Gnome Messi";

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.getIcons().add(Imagenes.getRandomSprite(Imagenes.ICONOS).getImage());
        stage.setTitle(APP_NAME);
        inicializarMenu();
    }

    private void inicializarMenu() throws IOException {
        this.menu = new MenuUI(stage);
        var menuControlador = new MenuControlador(menu, this);
        menuControlador.iniciar();
        menu.mostrar();
    }

    public void inicializarJuego() throws IOException {
        this.modelo = new Juego(menu.getFilas(), menu.getColumnas());
        this.game = new GameUI(stage);
        var botonesControlador = new BotonesControlador(modelo, game, menu, this);
        botonesControlador.iniciar();
        game.inicializarJuego(modelo);
    }

    public static void main(String[] args) {
        launch();
    }
}