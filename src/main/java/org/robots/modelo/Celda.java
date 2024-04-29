package org.robots.modelo;

import org.robots.modelo.personajes.Elemento;

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

    /**
     * sacarObjeto elimina al objeto de la celda y lo devuelve
     * @return Elemento
     */
    public Elemento sacarObjeto() {
        Elemento objeto = this.elemento;
        this.elemento = null;
        return objeto;
    }

    /* Validaciones */

    public boolean estaVacia() {
        return this.elemento == null;
    }

    public boolean estaIncendiada() {
        return (elemento != null && elemento.esFuego());
    }
}
