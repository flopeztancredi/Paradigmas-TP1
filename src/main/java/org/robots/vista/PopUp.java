package org.robots.vista;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class PopUp extends Alert {
    public static final ButtonType SALIR = new ButtonType("Salir");
    public static final ButtonType VOLVER = new ButtonType("Volver al menú");
    public static final ButtonType REINTENTAR = new ButtonType("Reintentar nivel");
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

        if (esGanador) {
            super.setHeaderText(GANADOR);
            super.getButtonTypes().add(AVANZAR);
        } else {
            super.setHeaderText(PERDEDOR);
            super.getButtonTypes().add(REINTENTAR);
        }
    }
}
