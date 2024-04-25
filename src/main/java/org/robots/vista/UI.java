package org.robots.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.robots.Main;

import java.io.IOException;

public class UI {
    protected final int HEIGHT = 480;
    protected final int WIDTH = 640;

    public Parent loadFXML(String fxml, Object controlador) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        loader.setController(controlador);
        return loader.load();
    }
}
