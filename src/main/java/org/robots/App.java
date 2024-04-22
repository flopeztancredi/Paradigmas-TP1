package org.robots;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.robots.controlador.RootControlador;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent p = loadFXML("juego", stage);

        Scene scene = new Scene(p, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private Parent loadFXML(String fxml, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        loader.setController(new RootControlador(stage));
        return loader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}