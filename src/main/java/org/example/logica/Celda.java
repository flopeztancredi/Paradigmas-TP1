package org.example.logica;

public class Celda {
    private Elemento objeto;

    public Celda(Elemento objeto) {
        this.objeto = objeto;
    }

    public boolean AsignarObjeto(Elemento objeto) {
        if (!EstaVacia()) {
            return false;
        }
        this.objeto = objeto;
        return true;
    }

    public boolean SacarObjeto(){
      if (EstaVacia()) {
          return false;
      }
      this.objeto = null;
      return true;
    };

    public boolean EstaVacia() {
        return objeto == null;
    }

    public Elemento getObjeto() {
        return objeto;
    }
}
