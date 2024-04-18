package org.example.logica;

import java.util.ArrayList;

public class Celda {
    private ArrayList<Elemento> objetos;
    private boolean incendiada = false;

    public Celda(Elemento e) {
        this.objetos = new ArrayList<>();
        this.objetos.add(e);
    }
    public Celda() {
        this.objetos = new ArrayList<>();
    }

    public boolean AsignarObjeto(Elemento objeto) {
        var estabaVacia = objetos.isEmpty();
        this.objetos.add(objeto);
        return estabaVacia;
    }

    public ArrayList<Elemento> SacarObjetos(){
        var objetosSacados = this.objetos; // copia o referencia?
        this.objetos.clear();
        return objetosSacados;
    };

    public boolean EstaVacia() {return objetos.isEmpty();}

    public boolean HayRobot() {
        for (Elemento e : this.objetos) {
            if (e instanceof Robot) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Elemento> getObjeto() { return objetos; }

    public void Incendiar() { this.incendiada = true; }

    public boolean EstaIncendiada() { return this.incendiada; }
}
