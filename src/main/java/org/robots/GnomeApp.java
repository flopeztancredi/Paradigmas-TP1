package org.robots;

import javafx.application.Application;
import javafx.stage.Stage;
import org.robots.controlador.BotonesControlador;
import org.robots.controlador.InputControlador;
import org.robots.controlador.MenuControlador;
import org.robots.modelo.Juego;
import org.robots.vista.*;

import java.io.IOException;
import java.util.Objects;


/**
 * JavaFX App
 */
public class GnomeApp extends Application {
    private MenuUI menuVista;
    private Stage stage;

    private final String NOMBRE_JUEGO = "Gnome Messi";

    /**
     * Comienza la ejecución de la aplicación
     * @param stage Stage
     * @throws IOException No se pudo cargar el menú
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.getIcons().add(Objects.requireNonNull(Imagenes.getSprite(Imagenes.ICONO_JUEGO)).getImage());
        stage.setTitle(NOMBRE_JUEGO);
        inicializarMenu();
    }

    /**
     * Inicializa el menú del juego y su controlador y lo muestra
     * @throws IOException No se pudo cargar el menú
     */
    private void inicializarMenu() throws IOException {
        this.menuVista = new MenuUI(stage);
        var menuControlador = new MenuControlador(menuVista, this);
        menuControlador.iniciar();
        menuVista.mostrar();
    }

    /**
     * Inicializa el juego y sus controladores
     * @throws IOException No se pudo cargar el juego
     */
    public void inicializarJuego() throws IOException {
        var juegoModelo = new Juego(menuVista.getFilas(), menuVista.getColumnas());
        var juegoVista = new GameUI(stage);
        var grid = new GridUI(juegoModelo);
        
        var botonesControlador = new BotonesControlador(juegoModelo, juegoVista, menuVista, this);
        botonesControlador.iniciar();
        juegoVista.inicializarJuego(juegoModelo, grid);
        var gridControlador = new InputControlador(grid, juegoVista, juegoModelo);
        gridControlador.iniciar();
    }

    public static void main(String[] args) {
        launch();
    }
}