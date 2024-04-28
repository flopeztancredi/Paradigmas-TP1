package org.robots.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.robots.GnomeApp;

import java.io.IOException;

public class UI {
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;

    public Parent loadFXML(String fxml, Object controlador) throws IOException {
        FXMLLoader loader = new FXMLLoader(GnomeApp.class.getResource(fxml + ".fxml"));
        loader.setController(controlador);
        return loader.load();
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }
}
