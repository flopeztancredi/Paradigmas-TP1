package org.robots.vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class Imagenes {

    public static final String[] JUGADOR_DEFAULT = {
            "assets/messi1.png",
            "assets/messi2.png",
            "assets/messi3.png"
    };

    public static final String[] JUGADOR_GANADOR = {
            "assets/messi_win.png"
    };

    public static final String[] JUGADOR_PERDEDOR = {
            "assets/messi_lose.png"
    };

    public static final String[] ROBOT1_SPRITES = {
            "assets/vandijk1.png",
            "assets/vandijk2.png",
            "assets/vandijk3.png",
            "assets/vandijk4.png",
    };

    public static final String[] ROBOT2_SPRITES = {
            "assets/mbappe1.png",
            "assets/mbappe2.png",
            "assets/mbappe3.png",
            "assets/mbappe4.png",
    };

    public static final String[] FUEGO_SPRITES = {
            "assets/fuego.png",
    };

    public static final String[] ICONOS = {
            "assets/copa.png",
    };

    /**
     * Devuelve una imagen aleatoria del array de rutas indicado por parámetro
     * @param ruta String[]
     * @return ImageView
     */
    public static ImageView getRandomSprite(String[] ruta) {
        Random random = new Random();
        int index = random.nextInt(ruta.length);
        try {
            return loadImage(ruta[index]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Devuelve una imagen según la ruta indicada por parámetro
     * @param ruta String
     * @return ImageView
     */
    private static ImageView loadImage(String ruta) throws FileNotFoundException {
        FileInputStream archivoImg = new FileInputStream(ruta);
        return new ImageView(new Image(archivoImg));
    }
}
