package org.robots.controlador;

import org.robots.GnomeApp;
import org.robots.vista.MenuUI;

import java.io.IOException;

public class MenuControlador {
    private final MenuUI menuUI;
    private final GnomeApp gnomeApp;

    public MenuControlador(MenuUI menuUI, GnomeApp gnomeApp) {
        this.menuUI = menuUI;
        this.gnomeApp = gnomeApp;
    }

    public void iniciar() {
        menuUI.setBtnHandler(e -> {
            try {
                gnomeApp.inicializarJuego();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
