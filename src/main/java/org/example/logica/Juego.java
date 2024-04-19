package org.example.logica;

public class Juego {
    private final Tablero tablero;
    private int nivel;
    private int puntuacion;


    public Juego(int filas, int columnas) {
        this.tablero = new Tablero(filas, columnas);
        this.nivel = 1;
        this.puntuacion = 0;
    }

    public void InicializarNivel() {
        var r1 = tablero.getFilas() * tablero.getColumnas() / 15 + this.nivel;
        var r2 = tablero.getFilas() * tablero.getColumnas() / 60 + this.nivel;
        tablero.inicializarNivel(r1, r2);
    }

    // Para moverse seguro?? como hariamos? Habría que pasar una posición acá directamente, no una dirección
    // eso lo podríamos hacer en la parte de vista, o sea la App (que DirigirMovimiento() se haga ahí),
    // pero creo que queda feo
    // Tenemos que hacer que Mover() reciba siempre una posición, si no va a ser complicado
    public int Mover(Direccion direccion) {
        this.puntuacion += tablero.getPuntuacionJugador();
        if (!tablero.mover(direccion)) {
            return -1;
        } else if (tablero.hayGanador()) {
            this.puntuacion += 100;
            this.nivel++;
            return 1;
        }
        return 0;
    }



}
