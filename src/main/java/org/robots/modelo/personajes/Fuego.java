package org.robots.modelo.personajes;

import org.robots.modelo.herramientas.Vector2;

public class Fuego extends Elemento {
    public Fuego(Vector2 posicion) {
        super(posicion, "Fuego");
    }

    @Override
    public boolean mataRobot() {
        return true;
    }
}
