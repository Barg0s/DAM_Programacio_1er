package com.exercici0306;

public class Carrega {
    protected String descripcio;
    protected double pes;


    public Carrega(String descripcio, double pes) {
            this.descripcio = descripcio;
            this.pes = pes;
    }

    public String getDescripcio() {
        return this.descripcio;
    }

    public void setDescripcio(String value) {
        this.descripcio = value;
    }

    public double getPes() {
        return this.pes;
    }

    public void setPes(double value) {
        this.pes = value;
    }

    @Override
    public String toString() {
        return "Carrega[descripcio=" + descripcio + ", pes=" + pes +"]";
    }
}
