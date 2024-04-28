package org.robots.controlador;

import javafx.scene.input.KeyCode;
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
            if (menuUI.getFilas() < 0 || menuUI.getColumnas() < 0 ) {
                return;
            }
            try {
                gnomeApp.inicializarJuego();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        menuUI.setSceneHandler(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                menuUI.getBtnJugar().fire();
            }
        });
    }
}
