package com.exercici0306;

public class VaixellMercaderies extends Vaixell implements Reglamentari {
        private String paisRegistre;

    public VaixellMercaderies(String nom, double capacitat, String paisRegistre) {
        super(nom, capacitat);
        this.paisRegistre = paisRegistre;
    }

    public String getPaisRegistre() {
        return this.paisRegistre;
    }

    public void setPaisRegistre(String value) {
        this.paisRegistre = value;

    }

    @Override
    public boolean compleixNormativa() {
        if (getPesTotal() < this.capacitat   ){
            return true;
        }else{
        return false;}
    }

    @Override
    public String toString() {
        return "VaixellMercaderies[nom=" + nom + ", capacitat=" + capacitat + ", paisRegistre=" + paisRegistre + "]";    }
}
