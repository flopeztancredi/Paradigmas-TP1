package org.robots.controlador;

import org.robots.GnomeApp;
import org.robots.modelo.Estado;
import org.robots.modelo.Juego;
import org.robots.vista.GameUI;
import org.robots.vista.MenuUI;

import java.io.IOException;

public class BotonesControlador {
    private final Juego juego;
    private final GameUI gameUI;
    private final MenuUI menuUI;
    private final GnomeApp gnomeApp;


    public BotonesControlador(Juego juego, GameUI gameUI, MenuUI menuUI, GnomeApp gnomeApp) {
        this.juego = juego;
        this.gameUI = gameUI;
        this.menuUI = menuUI;
        this.gnomeApp = gnomeApp;
    }

    /**
     * iniciar le asigna la acción correspondiente a cada uno de los botones disponibles del juego
     */
    public void iniciar() {
        gameUI.setWaitBtnHandler(e -> {
            var estadoJuego = juego.quedarse();
            gameUI.actualizarEstado(estadoJuego);
        });

        gameUI.setSafeBtnHandler(e -> {
            juego.toggleTpSafe();
            gameUI.actualizarEstado(Estado.JUGANDO);
        });

        gameUI.setRandomBtnHandler(e -> {
            var estadoJuego = juego.mover();
            gameUI.actualizarEstado(estadoJuego);
        });

        gameUI.setVolverAlMenuHandler(menuUI::mostrar);

        gameUI.setReiniciarHandler(() -> {
            try {
                gnomeApp.inicializarJuego();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}