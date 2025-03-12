package com.exercici0303;

public class Sistema {
    public void configurarSistema() {
        ConfiguracioGlobal configuracio = ConfiguracioGlobal.getInstance();
        System.out.print("Configurant sistema amb idioma " + configuracio.getIdioma() + " i zona horària " + configuracio.getZonaHoraria());

        // Fràncès: Configuration du système en langue ABC et fuseau horaire de DEF
        // Dothraki: Thorat system ma chekof ABC ma asavvaja DEF.
    }
}
