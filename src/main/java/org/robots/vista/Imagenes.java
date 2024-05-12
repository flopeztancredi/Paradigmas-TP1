package org.robots.vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class Imagenes {
    private static final int NUM_SPRITES = 4;
    private static final String ASSETS_PATH = "assets/";
    private static final String FILE_EXTENSION = ".png";

    public static final String JUGADOR_GANADOR = "messi_win";
    public static final String JUGADOR_PERDEDOR = "messi_lose";
    public static final String ICONO_JUEGO = "copa";

    /**
     * Devuelve una imagen aleatoria del tipo de elemento indicado
     * @param tipoElemento String
     * @return ImageView
     */
    public static ImageView getRandomSprite(String tipoElemento) {
        Random random = new Random();
        int index = random.nextInt(NUM_SPRITES);
        String ruta = ASSETS_PATH + tipoElemento + index + FILE_EXTENSION;
        try {
            return loadImage(ruta);
        } catch (FileNotFoundException e) {
            return getSprite(tipoElemento);
        }
    }

    /**
     * Devuelve la imagen del tipo de elemento indicado
     * @param tipoElemento String
     * @return ImageView
     */
    public static ImageView getSprite(String tipoElemento) {
        String ruta = ASSETS_PATH + tipoElemento + FILE_EXTENSION;
        try {
            return loadImage(ruta);
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
