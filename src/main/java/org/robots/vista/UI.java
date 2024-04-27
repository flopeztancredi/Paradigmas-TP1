package org.robots.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.robots.Main;

import java.io.IOException;

public class UI {
    private final int HEIGHT = 480;
    private final int WIDTH = 640;

    public Parent loadFXML(String fxml, Object controlador) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
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
