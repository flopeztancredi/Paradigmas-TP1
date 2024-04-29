package org.robots.vista;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Objects;

public class PopUp extends Alert {
    public static final ButtonType SALIR = new ButtonType("Salir");
    public static final ButtonType VOLVER = new ButtonType("Volver al menú");
    public static final ButtonType REINTENTAR = new ButtonType("Reintentar");
    public static final ButtonType AVANZAR = new ButtonType("Avanzar nivel");

    private final String TITULO = "Gnome Messi";
    private final String GANADOR = "Ganaste el nivel!";
    private final String PERDEDOR = "Perdiste el nivel!";
    private final String CONTENIDO = "Puntuación: ";

    public PopUp(boolean esGanador, String puntuacion) {
        super(AlertType.CONFIRMATION);
        super.getButtonTypes().clear();
        super.getButtonTypes().addAll(SALIR, VOLVER);
        super.setTitle(TITULO);
        super.setContentText(CONTENIDO + puntuacion);
        ((Stage) this.getDialogPane().getScene().getWindow()).getIcons().add(Objects.requireNonNull(Imagenes.getRandomSprite(Imagenes.ICONOS)).getImage());

        if (esGanador) {
            super.setHeaderText(GANADOR);
            super.getButtonTypes().add(AVANZAR);
            super.setGraphic(Imagenes.getRandomSprite(Imagenes.JUGADOR_GANADOR));
        } else {
            super.setHeaderText(PERDEDOR);
            super.getButtonTypes().add(REINTENTAR);
            super.setGraphic(Imagenes.getRandomSprite(Imagenes.JUGADOR_PERDEDOR));
        }
    }
}
