package org.robots.modelo.herramientas;

public enum Direccion {
    ARRIBA(new Vector2(-1, 0)),
    ABAJO(new Vector2(1, 0)),
    IZQUIERDA(new Vector2(0, -1)),
    DERECHA(new Vector2(0, 1));

    private final Vector2 dir;

    Direccion(Vector2 dir) {
        this.dir = dir;
    }

    public Vector2 getDir() {
        return dir;
    }
}
