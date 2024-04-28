package org.robots.vista;

import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

public class Imagenes {

    public static final String[] JUGADOR_DEFAULT = {
            "img/messi1.png",
//            "src/main/resources/img/messi2.png",
//            "src/main/resources/img/messi3.png",
    };

    public static final String[] JUGADOR_GANADOR = {
            "img/messi4.png"
    };

    public static final String[] JUGADOR_PERDEDOR = {
            "img/jugador/messi5.png"
    };

    public static final String[] ROBOT1_SPRITES = {
            "img/mbappe1.png",
//            "src/main/resources/img/vandijk2.png",
//            "src/main/resources/img/vandijk3.png",
//            "src/main/resources/img/vandijk4.png",
    };

    public static final String[] ROBOT2_SPRITES = {
            "img/mbappe1.png",
//            "src/main/resources/img/mbappe2.png",
//            "src/main/resources/img/mbappe3.png",
//            "src/main/resources/img/mbappe4.png",
    };

    public static final String[] FUEGO_SPRITES = {
            "img/jugador/fuego.png",
    };

    public static Image getRandomSprite(String[] ruta) {
        Random random = new Random();
        int index = random.nextInt(ruta.length);
        return loadImage(ruta[index]);
    }

    private static Image loadImage(String ruta) {
        return new Image(Objects.requireNonNull(Image.class.getClassLoader().getResourceAsStream(JUGADOR_DEFAULT[0])));
    }
}
