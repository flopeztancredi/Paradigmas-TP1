package org.robots.modelo;

import org.robots.modelo.personajes.Elemento;
import org.robots.modelo.personajes.Fuego;

public class Celda {
    private Elemento elemento;

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

    public void incendiar(Fuego fuego) {
        this.elemento = fuego;
    }

    public boolean estaIncendiada() {
        return (this.elemento instanceof Fuego);
    }
}
