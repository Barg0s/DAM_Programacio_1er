package com.exercici0306;

public class VaixellPassatgers extends Vaixell implements Reglamentari {
    private int numPassatgers;
    private int maxPassatgers;

    public VaixellPassatgers(String nom, double capacitat, int maxPassatgers) {
        super(nom, capacitat);
        this.maxPassatgers = maxPassatgers;
        this.numPassatgers = 0;
    }

    public int getNumPassatgers() {
        return this.numPassatgers;
    }

    public void setNumPassatgers(int value) {
        this.numPassatgers = value;
    }

    public int getMaxPassatgers() {
        return this.maxPassatgers;
    }

    public void setMaxPassatgers(int value) {
        this.maxPassatgers = value;

    }

    public void afegirPassatger() {
        if (numPassatgers >= maxPassatgers) {
            throw new IllegalStateException("No es poden afegir més passatgers");
        }
        numPassatgers++;
    }
    

    @Override
    public boolean compleixNormativa() {
        return numPassatgers <= maxPassatgers;
    }
    

    @Override
    public String toString() {
        return "VaixellPassatgers[nom=" + getNom() + ", capacitat=" + getCapacitat() +", numPassatgers=" + numPassatgers + ", maxPassatgers=" + maxPassatgers + "]";
    } 
}
