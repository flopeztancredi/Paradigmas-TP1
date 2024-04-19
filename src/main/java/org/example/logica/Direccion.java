package org.example.logica;

// 0 = der
// 1 = upder
// 2 = up
// 3 = upizq
// 4 = izq
// 5 = dwnizq
// 6 = dwn
// 7 = dwnder
// 8 = ran
// 9 = safe

public class Direccion {
    private int movimiento;

    public Direccion(int movimiento) {
        this.movimiento = movimiento;
    }

    public Vector2 dirigirMovimiento(Vector2 pos, int filas, int columnas) {
        var x = 0;
        var y = 0;

        if (this.movimiento == 8) {
            // return vector random con filas, columnas
        }
        if (this.movimiento == 9) {
            // este es mas complicado, podemos manejarlo desde el tablero también, pero tiene mas sentido hacerlo
            // desde acá. quizas podemos randomizar hasta que haya una distancia hacia todos los robots mayor a...
            // alguna cuenta con filas y columnas?
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
