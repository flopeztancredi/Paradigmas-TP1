package org.example.logica;

public class Dirección {
    private final int movimiento;

    public Dirección(int movimiento) {
        this.movimiento = movimiento;
    }

    public Vector2 DirigirMovimiento(Vector2 pos) {
        var x = 0;
        var y = 0;

        switch (movimiento) {
            case 1:
                x = 1;
                break;
            case 2:
                x = 1;
                y = 1;
                break;
            case 3:
                y = 1;
                break;
            case 4:
                x = -1;
                y = 1;
                break;
            case 5:
                x = -1;
                break;
            case 6:
                x = -1;
                y = -1;
                break;
            case 7:
                y = -1;
                break;
            case 8:
                x = 1;
                y = -1;
                break;
            default:
                break;
        }

        var mov = new Vector2(x, y);
        return pos.sumar(mov);
    };

    public int getMovimiento() {
        return movimiento;
    }
}
