package com.exercicis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Exercici0201 {

    public static Scanner scanner;
    public static Locale defaultLocale;

    public static void main(String[] args) {
        
        scanner = new Scanner(System.in);
        defaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
        
        int[] arrEnters = generaArrayEnters(10);
        mostraArrayEstadistiques(arrEnters);

        ArrayList<Integer> lstEnters = generaLlistaEnters(10);
        mostraLlistaEstadistiques(lstEnters);

        filtraArrayParaulesAmbA();
        filtraLlistaParaulesAmbA();

        double[] arrDecimals = generaArrayDecimals(15);
        filtraArrayDecimalsSuperiors50(arrDecimals);

        ArrayList<Double> lstDecimals = generaLlistaDecimals(15);
        filtraLlistaDecimalsSuperiors50(lstDecimals);

        HashMap<String, Integer> persones = new HashMap<>();
        persones.put("Anna", 25);
        persones.put("Joan", 30);
        persones.put("Marc", 20);
        mostrarLlistaOrdenadesPerEdat(persones);

        mostrarFrecuenciaParaules();
        invertirMapaClauValor();
        fusionarMapesSumantValors();

        HashMap<String, Double> notes = new HashMap<>();
        notes.put("Anna", 7.5);
        notes.put("Joan", 6.8);
        notes.put("Marta", 8.2);
        notes.put("Pere", 4.1);
        notes.put("Enric", 2.0);
        notes.put("Amparo", 6.9);
        notes.put("Olga", 9.0);
        notes.put("Manel", 2.2);

        calcularEstadistiquesNotesEstudiants(notes);

        Locale.setDefault(defaultLocale);
        scanner.close();
    }

    /**
     * Genera un array d'enters aleatoris.
     *
     * @param mida la mida de l'array a generar
     * @return un array d'enters amb valors entre 0 i 99
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testGeneraArrayEnters
     */
    public static int[] generaArrayEnters(int mida) {

        int[] rst = new int[mida];
        Random rand = new Random();

        for (int i = 0; i < mida;i++){
            int randomnum = rand.nextInt(99);
            rst[i] = randomnum;
                
            
        }
        return rst;
    }

    /**
     * Calcula i mostra estadístiques d'un array d'enters.
     * 
     * Imprimeix per pantalla l'array, el valor màxim, el mínim i la mitjana.
     * Format d'output:
     * "Array: [valors]"
     * "Màxim: X  Mínim: Y  Mitjana: Z"
     *
     * @param array l'array d'enters sobre el qual calcular les estadístiques
     * @test ./runTest.sh com.exercicis.TestExercici0201#testMostraArrayEstadistiques
     */
    public static void mostraArrayEstadistiques(int[] array) {

        int max = array[0]; 
        int min = array[0];
        int suma = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min){
                min = array[i];
            }
            suma += array[i];

        }
        double mitjana = suma / (double) array.length;
        System.out.print("Array: [");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("Màxim: " + max + "  Mínim: " + min + "  Mitjana: " + mitjana);
    }

    
    

    /**
     * Genera una llista d'enters aleatoris.
     *
     * @param mida la mida de la llista a generar
     * @return una ArrayList d'enters amb valors entre 0 i 99
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testGeneraLlistaEnters
     */
    public static ArrayList<Integer> generaLlistaEnters(int mida) {
        ArrayList<Integer> rst = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < mida;i++){
            int randomnum = rand.nextInt(99);
            rst.add(randomnum);
                
            
        }
       
        return rst;
    }

    /**
     * Calcula i mostra estadístiques d'una llista d'enters.
     * 
     * Imprimeix per pantalla la llista, el valor màxim, el mínim i la mitjana.
     * Format d'output:
     * "Llista: [valors]"
     * "Màxim: X  Mínim: Y  Mitjana: Z"
     * 
     *
     * @param llista la llista d'enters sobre la qual calcular les estadístiques
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testMostraLlistaEstadistiques
     */
    public static void mostraLlistaEstadistiques(ArrayList<Integer> llista) {
        System.out.print("Llista: [");
        for (int i = 0; i < llista.size(); i++) {
            System.out.print(llista.get(i));
            if (i < llista.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]"); // Cerrar el corchete y hacer un salto de línea
    
        // Calcular el número máximo del array
        int max = llista.get(0);
        int min = llista.get(0);
        int suma = llista.get(0);
        for (int i = 1; i < llista.size(); i++) {
            if (llista.get(i) > max) {
                max = llista.get(i);
            }
            if (llista.get(i) < min){
                min = llista.get(i);
            }
            suma += llista.get(i);

        }
        double mitjana = suma / (double) llista.size();
        System.out.println("Màxim: " + max + " Mínim: " + min + " Mitjana: " + mitjana);

    }

    /**
     * Demana a l'usuari que escrigui 5 paraules separades per ',' o ', ' 
     * i mostra aquelles que comencen amb 'a'.
     * 
     * Guarda la llista en un "String[] paraules"
     * 
     * Es mostra per pantalla:
     * "Escriu 5 paraules separades per ',' o ', ':" per sol·licitar les entrades,
     * i després "Paraules que comencen amb 'a':" seguit de les paraules filtrades.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testFiltraArrayParaulesAmbA
     */
    public static void filtraArrayParaulesAmbA() {
        System.out.println("Escriu 5 paraules separades per ',' o ', ':");
        String input = scanner.nextLine();
        String[] paraules = input.replace(", ", ",").split(",");
        int count = 0;
        for (String p : paraules) {
            if (p.toLowerCase().startsWith("a"))
                count++;
        }
        String[] filtrades = new String[count];
        int index = 0;
        for (String p : paraules) {
            if (p.toLowerCase().startsWith("a")) {
                filtrades[index++] = p;
            }
        }
        String rst = String.join(", ", filtrades);
        System.out.println("Paraules que comencen amb 'a': " + rst);
    }
       
       
    /**
     * Demana a l'usuari que escrigui 5 paraules separades per ',' o ', ' 
     * i mostra aquelles que comencen amb 'a' en una sola línia separades per ", ".
     * 
     * Guarda la llista en un "ArrayList<String> paraules".
     * 
     * Es mostra per pantalla:
     * "Escriu 5 paraules separades per ',' o ', ':" per sol·licitar les entrades,
     * i després "Paraules que comencen amb 'a':" seguit de les paraules filtrades.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testFiltraLlistaParaulesAmbA
     */
    public static void filtraLlistaParaulesAmbA() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escriu 5 paraules separades per ',' o ', ':");
        String input = scanner.nextLine();
        String[] paraulesArray = input.replace(", ", ",").split(",");
        ArrayList<String> paraules = new ArrayList<>(Arrays.asList(paraulesArray));
        ArrayList<String> filtrades = new ArrayList<>();
        for (String p : paraules) {
            if (p.toLowerCase().startsWith("a"))
                filtrades.add(p);
        }
        String rst = String.join(", ", filtrades);
        System.out.println("Paraules que comencen amb 'a': " + rst);
    }

    /**
     * Genera un array de decimals aleatoris.
     *
     * @param mida la mida de l'array a generar
     * @return un array de decimals amb valors entre 0.0 i 100.0
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testGeneraArrayDecimals
     */
    public static double[] generaArrayDecimals(int mida) {
        double[] array = new double[mida];
        Random rand = new Random();

        for (int i = 0; i < mida;i++){
            Double randomnum = rand.nextDouble(99);
            array[i] = randomnum;
                
            
        }
        return array;
    
    }

    /**
     * Genera una llista de decimals aleatoris.
     *
     * @param mida la mida de la llista a generar
     * @return una ArrayList de decimals amb valors entre 0.0 i 100.0
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testFiltraArrayDecimalsSuperiors50
     */
    public static ArrayList<Double> generaLlistaDecimals(int mida) {
        ArrayList<Double> rst = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < mida;i++){
            Double randomnum = rand.nextDouble(99);
            rst.add(randomnum);
                
            
        }
       
        return rst;
    }


    /**
     * Filtra i mostra els decimals superiors a 50 d'un array.
     * 
     * Imprimeix per pantalla l'array original de decimals i, a continuació,
     * la llista dels decimals que són majors que 50.
     * Format d'output:
     * "Array original: [valors]"
     * "Valors majors que 50: [valors filtrats]"
     * 
     *
     * @param decimals l'array de decimals a filtrar
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testGeneraLlistaDecimals
     */
    public static void filtraArrayDecimalsSuperiors50(double[] decimals) {
        System.out.print("Array original: [");
        for (int i = 0; i < decimals.length; i++) {
            System.out.printf("%.2f", decimals[i]);
            if (i < decimals.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    
        int count = 0;
        for (double d : decimals) {
            if (d > 50)
                count++;
        }
        double[] filtrats = new double[count];
        int index = 0;
        for (double d : decimals) {
            if (d > 50) {
                filtrats[index++] = d;
            }
        }
        System.out.print("Valors majors que 50: [");
        for (int i = 0; i < filtrats.length; i++) {
            System.out.printf("%.2f", filtrats[i]);
            if (i < filtrats.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }   
    /**
     * Filtra i mostra els decimals superiors a 50 d'una llista.
     * 
     * Imprimeix per pantalla la llista original de decimals i, a continuació,
     * la llista dels decimals que són majors que 50.
     * Format d'output:
     * "Llista original: [valors]"
     * "Valors majors que 50: [valors filtrats]"
     *
     * @param decimals la llista de decimals a filtrar
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testFiltraLlistaDecimalsSuperiors50
     */
    public static void filtraLlistaDecimalsSuperiors50(ArrayList<Double> decimals) {
        System.out.print("Llista original: [");
        for (int i = 0; i < decimals.size(); i++) {
            System.out.printf("%.2f", decimals.get(i));
            if (i < decimals.size() - 1)
                System.out.print(", ");
        }
        System.out.println("]");


        System.out.print("Valors majors que 50: [");
        for (int i = 0; i < decimals.size(); i++) {
            if (decimals.get(i) > 50.0){
            System.out.printf("%.2f", decimals.get(i));
            if (i < decimals.size() - 1)
                System.out.print(", ");
        }}
        System.out.println("]");
    }   
    
    
    
    /**
     * Mostra per pantalla les persones ordenades per edat.
     * 
     * Recorre un HashMap de persones (nom i edat) i imprimeix cada persona en format "Nom (Edat)"
     * ordenat per edat de manera ascendent.
     *
     * @param persones un HashMap on la clau és el nom de la persona i el valor és la seva edat
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testMostrarLlistaOrdenadesPerEdat
     */
    public static void mostrarLlistaOrdenadesPerEdat(HashMap<String, Integer> persones) {
        ArrayList<String> keys = new ArrayList<>(persones.keySet());
        keys.sort((k1, k2) -> persones.get(k1).compareTo(persones.get(k2)));
        for (String key : keys) {
            System.out.println(key + " (" + persones.get(key) + ")");
        }
    }

    /**
     * Demana a l'usuari que introdueixi una frase i mostra la freqüència de cada paraula.
     * 
     * L'usuari escriu una frase per la consola i el mètode separa les paraules usant els espais.
     * A continuació, es calcula la freqüència de cada paraula i es mostra per pantalla en format de HashMap.
     * 
     * 
     * Es mostra per pantalla:
     * "Introdueix una frase:" i després "Freqüència de paraules: {paraula=frequencia, ...}".
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testMostrarFrecuenciaParaules
     */
    public static void mostrarFrecuenciaParaules() {  
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introdueix una frase:");

        String aa = scanner.nextLine();
        String[] frase = aa.split(" ");
        HashMap<String, Integer> paraula = new HashMap<>();
        
        for (int i = 0; i < frase.length; i++) {
            if (paraula.containsKey(frase[i])) {
                paraula.put(frase[i], paraula.get(frase[i]) + 1);
            } else {
                paraula.put(frase[i], 1);
            }
        }

        // Imprimir la frecuencia de las palabras
        System.out.println("Freqüència de paraules: " + paraula);
    }
    /**
     * Inverteix un HashMap intercanviant claus i valors.
     * 
     * Es crea un HashMap amb elements (A=1, B=2, C=3) i es construeix un nou HashMap on cada valor 
     * es converteix en clau i cada clau es converteix en valor.
     * 
     * 
     * Es mostra per pantalla:
     * "Mapa original: {A=1, B=2, C=3}" i "Mapa invertit: {1=A, 2=B, 3=C}".
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testInvertirMapaClauValor
     */
    public static void invertirMapaClauValor() {
        HashMap<String, Integer> mapa = new HashMap<>();
        mapa.put("A", 1);
        mapa.put("B", 2);
        mapa.put("C", 3);
        HashMap<Integer, String> invertit = new HashMap<>();
        for (Map.Entry<String, Integer> e : mapa.entrySet()) {
            invertit.put(e.getValue(), e.getKey());
        }
        System.out.println("Mapa original: " + mapa);
        System.out.println("Mapa invertit: " + invertit);
    }

    /**
     * Fusiona dos HashMap sumant els valors de les claus comuns.
     * 
     * Es defineixen dos mapes:
     * <ul>
     *   <li>Mapa 1: {X=10, Y=20}</li>
     *   <li>Mapa 2: {Y=5, Z=15}</li>
     * </ul>
     * El mètode crea un nou HashMap on, per cada clau existent en ambdós mapes, es suma el valor.
     * 
     * 
     * Es mostra per pantalla:
     * "Mapa fusionat: {X=10, Y=25, Z=15}".
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testFusionarMapesSumantValors
     */
    public static void fusionarMapesSumantValors() {
        HashMap<String, Integer> mapa1 = new HashMap<>();
        mapa1.put("X", 10);
        mapa1.put("Y", 20);

        HashMap<String, Integer> mapa2 = new HashMap<>();
        mapa2.put("Y", 5);
        mapa2.put("Z", 15);

        HashMap<String, Integer> mapaFusionat = new HashMap<>();

        for (String key : mapa1.keySet()) {
            mapaFusionat.put(key, mapa1.get(key));
        }

        for (String key : mapa2.keySet()) {
            if (mapaFusionat.containsKey(key)) {
                mapaFusionat.put(key, mapaFusionat.get(key) + mapa2.get(key));
            } else {
                mapaFusionat.put(key, mapa2.get(key));
            }
        }

        System.out.println("Mapa fusionat: " + mapaFusionat);
    }

    /**
     * Calcula i mostra les estadístiques (mitjana, màxim i mínim) de les notes dels estudiants.
     * 
     * Es defineix un HashMap on la clau és el nom de l'estudiant i el valor la seva nota.
     * El mètode calcula la mitjana, la nota màxima i la nota mínima i les mostra per pantalla.
     * 
     * Es mostra per pantalla amb 2 decimals:
     * "Mitjana: [valor], Màxim: [valor], Mínim: [valor]".
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0201#testCalcularEstadistiquesNotesEstudiants
     */
    public static void calcularEstadistiquesNotesEstudiants(HashMap<String, Double> estudiants) {
        double mitjana = 0.0;
        int cnt = 0;
        Double min = estudiants.get("Anna");
        Double max = estudiants.get("Anna");
        for (String key : estudiants.keySet()){
                mitjana += estudiants.get(key);
                if (estudiants.get(key) > max) {
                    max = estudiants.get(key);
                }
                if (estudiants.get(key) < min) {
                    min = estudiants.get(key);
                }
                cnt++;
        }

        double total = mitjana / cnt;
        System.out.println("Mitjana: " + String.format("%.2f", total) + ", Màxim: " + max + ", Mínim: " + min);

    }

    
}
