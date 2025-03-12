package com.exercici0305;

public class Jugador extends Participant implements Esportista, Competidor {
    private String equip;
    public Jugador(String nom, int edat, String equip) {
        super(nom, edat);
        this.equip = equip;
    }

    public String getEquip() {
        return this.equip;
    }

    public void setEquip(String value) {
        this.equip = value;
    }



    public void entrenar() {
        System.out.println("Entrenant com a jugador");

    }

    public void competir() {
        System.out.println("Competint com a jugador");

    }

    @Override
    public String toString() {
        return "Jugador[nom=" + nom + ", edat=" + edat + ", equip=" + equip + "]";
    }
}
