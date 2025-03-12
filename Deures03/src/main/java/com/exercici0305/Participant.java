package com.exercici0305;

public class Participant {
    protected String nom;
    protected int edat;


    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEdat() {
        return this.edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }


    public Participant(String nom, int edat) {
        this.nom = nom;
        this.edat = edat;
    }
    @Override
    public String toString() {
        return "Participant[nom=" + nom + ", edat=" + edat + "]";
    }    
}
