package org.example.logica;

public class Celda {
    private Elemento elemento;
    private boolean incendiada = false;

    public Celda() {
        this.elemento = null;
    }

    public boolean asignarObjeto(Elemento objeto) {
        if (!estaVacia()) {
            return false;
        }
        this.elemento = objeto;
        return true;
    }

    public Elemento sacarObjeto() {
        Elemento objeto = this.elemento;
        this.elemento = null;
        return objeto;
    }

    public boolean estaVacia() {
        return this.elemento == null;
    }

    public Elemento getElemento() {
        return this.elemento;
    }

    public void incendiar() {
        this.incendiada = true;
    }

    public void extinguirFuego() {
        this.incendiada = false;
    }

    public boolean estaIncendiada() {
        return this.incendiada;
    }
}
