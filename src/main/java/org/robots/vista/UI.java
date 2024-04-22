package org.robots.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.robots.App;
import org.robots.modelo.Juego;

import java.io.IOException;

public class UI {
    protected final int HEIGHT = 480;
    protected final int WIDTH = 640;

    public Parent loadFXML(String fxml, Object controlador) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        loader.setController(controlador);
        return loader.load();
    }
}
