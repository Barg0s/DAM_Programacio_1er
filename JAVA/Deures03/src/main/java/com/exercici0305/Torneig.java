package com.exercici0305;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Torneig {
    private ArrayList<Participant> participants;


    public Torneig() {
        this.participants = new ArrayList<>();

    }

    public void afegirParticipant(Participant p) {
        participants.add(p);
    }

    public void competir() {
        for (Participant p : participants){
            if (p instanceof Competidor){
                ((Competidor) p).competir();
            }
        }

    }

    public void entrenar() {
        for (Participant p : participants){
            if (p instanceof Esportista){
                ((Esportista) p).entrenar();
            }
        }

    }

    

    public ArrayList<Participant> getParticipants() {
        return this.participants;

    }

    public void printParticipants() {
        for (Participant p : participants){
            System.out.println(p);
        }

    }
}
