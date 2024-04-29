package org.robots.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.robots.GnomeApp;

import java.io.IOException;

public class UI {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    /**
     * Carga un archivo FXML y lo asocia a un controlador
     * @param fxml String
     * @param controlador Object
     * @return Parent
     * @throws IOException No se pudo cargar el archivo FXML
     */
    public Parent loadFXML(String fxml, Object controlador) throws IOException {
        FXMLLoader loader = new FXMLLoader(GnomeApp.class.getResource(fxml + ".fxml"));
        loader.setController(controlador);
        return loader.load();
    }
}
