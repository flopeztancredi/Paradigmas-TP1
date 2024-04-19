package org.example.logica;

import java.util.Random;

// 0 = der
// 1 = upder
// 2 = up
// 3 = upizq
// 4 = izq
// 5 = dwnizq
// 6 = dwn
// 7 = dwnder
// 8 = ran

public class Direccion {
    private int movimiento;

    public Direccion(int movimiento) {
        this.movimiento = movimiento;
    }

    public Vector2 dirigirMovimiento(Vector2 pos, int filas, int columnas) {
        var x = 0;
        var y = 0;

        if (this.movimiento == 8) {
            var rand = new Random();
            var nuevaPos = new Vector2(rand.nextInt(filas), rand.nextInt(columnas));
            return nuevaPos;
        }

        if (corroborarMovimiento(0, 1, 7, columnas-1, pos.getX())) {
            x = 1;
        }
        if (corroborarMovimiento(3, 4, 5, 0, pos.getX())) {
            x = -1;
        }
        if (corroborarMovimiento(1, 2, 3, 0, pos.getY())) {
            y = 1;
        }
        if (corroborarMovimiento(5, 6, 7, filas-1, pos.getY())) {
            y = -1;
        }

        var mov = new Vector2(x, y);
        return pos.sumar(mov);
    };

    public boolean corroborarMovimiento(int opcion1, int opcion2, int opcion3, int limite, int pos) {
        return (this.movimiento == opcion1 || this.movimiento == opcion2 || this.movimiento == opcion3) && limite != pos;
    }

    public void setMovimiento(int dirección) {
        this.movimiento = dirección;
    }

    public int getMovimiento() {
        return movimiento;
    }

}
