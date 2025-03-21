package org.robots.modelo.personajes;

import org.robots.modelo.Tablero;
import org.robots.modelo.herramientas.Vector2;

public class R2 extends Robot {
    private final int MULTIPLICADOR = 2;

    public R2(Vector2 posicion) {
        super(posicion, "mbappe");
    }

    /**
     * moverse para R2 realiza dos movimientos por separado.
     *
     * @param posJugador Vector2
     * @param tablero    Tablero
     */
    @Override
    public void moverse(Vector2 posJugador, Tablero tablero) {
        super.moverse(posJugador, tablero);
        if (tablero.esPosObstaculizada(this.posicion)) {
            return;
        }
        super.moverse(posJugador, tablero);
    }

    @Override
    public int getPuntuacion() {
        return super.getPuntuacion() * MULTIPLICADOR;
    }

}