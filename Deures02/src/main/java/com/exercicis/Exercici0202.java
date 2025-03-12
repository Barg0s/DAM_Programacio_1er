package com.exercicis;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONArray;

public class Exercici0202 {

    public static Scanner scanner;
    public static Locale defaultLocale;
    
    // ./run.sh com.exercicis.Exercici0202
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        defaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);

        //showJSONAstronautes("./data/astronautes.json");
        JSONAstronautesToArrayList("./data/astronautes.json");

        showEsportistesOrdenatsPerMedalla("./data/esportistes.json", "or");
        showEsportistesOrdenatsPerMedalla("./data/esportistes.json", "plata");

        mostrarPlanetesOrdenats("./data/planetes.json", "nom");
        mostrarPlanetesOrdenats("./data/planetes.json", "radi");
        mostrarPlanetesOrdenats("./data/planetes.json", "massa");
        mostrarPlanetesOrdenats("./data/planetes.json", "distancia");


        ArrayList<HashMap<String, Object>> dades = new ArrayList<>();

        ArrayList<String> caracteristiquesPacific = new ArrayList<>();
        caracteristiquesPacific.add("És l'oceà més gran del món");
        caracteristiquesPacific.add("Conté la fossa de les Marianes, la més profunda del món");
        caracteristiquesPacific.add("Conté una illa de plàstics contaminants.");

        ArrayList<String> caracteristiquesAtlantic = new ArrayList<>();
        caracteristiquesAtlantic.add("Separa Amèrica d'Europa i Àfrica");
        caracteristiquesAtlantic.add("Conté el famós Triangle de les Bermudes");

        ArrayList<String> caracteristiquesMediterrani = new ArrayList<>();
        caracteristiquesMediterrani.add("És un mar gairebé tancat");
        caracteristiquesMediterrani.add("Connecta amb l'oceà Atlàntic a través de l'estret de Gibraltar");

        dades.add(crearMassaAigua("Oceà Pacífic", "oceà", 168723000, 10924, caracteristiquesPacific));
        dades.add(crearMassaAigua("Oceà Atlàntic", "oceà", 85133000, 8486, caracteristiquesAtlantic));
        dades.add(crearMassaAigua("Oceà Índic", "oceà", 70560000, 7450, new ArrayList<>()));
        dades.add(crearMassaAigua("Oceà Àrtic", "oceà", 15558000, 5450, new ArrayList<>()));
        dades.add(crearMassaAigua("Mar Mediterrani", "mar", 2500000, 5121, caracteristiquesMediterrani));
        dades.add(crearMassaAigua("Mar Carib", "mar", 2754000, 7686, new ArrayList<>()));
        dades.add(crearMassaAigua("Mar de la Xina Meridional", "mar", 3500000, 5560, new ArrayList<>()));

        try {
            generarJSON(dades, "./data/aigua.json");
        } catch (IOException e) {
            e.printStackTrace();
        }


        Locale.setDefault(defaultLocale);
        scanner.close();
    }

    /**
     * Llegeix l'arxiu de 'filePath' i mostra per consola les dades dels astronautes.
     * 
     * El format és:
     * > Astronauta 0:
     *   Nom: Yuri Gagarin
     *   Naixement: 1934
     * > Astronauta 1:
     *   Nom: Neil Armstrong
     *   Naixement: 1930
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0202#testShowJSONAstronautes
     */
    public static void showJSONAstronautes(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("astronautes");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject astronauta = jsonArray.getJSONObject(i);
                String nom = astronauta.getString("nom");
                int anyNaixement = astronauta.getInt("any_naixement");

                System.out.println("> Astronauta " + (i)  + ":"); 
                System.out.println("  Nom: " + nom);
                System.out.println("  Naixement: " + anyNaixement);
            }
        } catch (Exception e) {
            System.err.println("Error JSON");
        }
    }
    /**
     * Llegeix l'arxiu de 'filePath', retorna un ArrayList amb les dades dels astronautes
     * Les dades han d'estar en un HashMap amb les claus "nom" i "any_naixement"
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0202#testJSONAstronautesToArrayList
     */
    public static ArrayList<HashMap<String, Object>> JSONAstronautesToArrayList(String filePath) {
        ArrayList<HashMap<String, Object>> rst = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("astronautes");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject astronauta = jsonArray.getJSONObject(i);
                HashMap<String,Object> dades = new HashMap<>();
                String nom = astronauta.getString("nom");
                int anyNaixement = astronauta.getInt("any_naixement");
                dades.put("nom", nom);
                dades.put("any_naixement", anyNaixement);
                rst.add(dades);
            }
        } catch (Exception e) {
            System.err.println("Error JSON");
            return new ArrayList<>();
        }
        return rst;
    }

    /**
     * Llegeix l'arxiu de 'filePath', retorna un ArrayList amb les dades dels esportistes
     * Les dades han d'estar en un HashMap amb: nom, any_naixement, pais i medalles
     * Les medalles de la clau 'medalles' han d'estar en un HashMap amb les claus "or", "plata" i "bronze"
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0202#testJSONEsportistesToArrayList
     */
    public static ArrayList<HashMap<String, Object>> JSONEsportistesToArrayList(String filePath) {
        ArrayList<HashMap<String, Object>> rst = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HashMap<String, Object> map = new HashMap<>();
                map.put("nom", jsonObject.getString("nom"));
                map.put("any_naixement", jsonObject.getInt("any_naixement"));
                map.put("pais", jsonObject.getString("pais"));

                JSONObject medalles = jsonObject.getJSONObject("medalles_olimpiques");
                HashMap<String, Object> mapMedalles = new HashMap<>();
                mapMedalles.put("or", medalles.getInt("or"));
                mapMedalles.put("plata", medalles.getInt("plata"));
                mapMedalles.put("bronze", medalles.getInt("bronze"));

                map.put("medalles", mapMedalles);
                rst.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    /**
     * Llegeix l'arxiu JSON i retorna una llista d'esportistes ordenada per una medalla específica (or, plata o bronze).
     *
     * Si el tipus no és "or", "plata" o "bronze" llança una excepció IllegalArgumentException. Amb el text:
     * "Tipus de medalla invàlid: {tipusMedalla}. Tipus vàlids: 'or', 'plata' o 'bronze'."
     * 
     * @param filePath Ruta de l'arxiu JSON amb les dades dels esportistes.
     * @param tipusMedalla Tipus de medalla per ordenar: "or", "plata" o "bronze".
     * @return Llista ordenada d'esportistes segons el nombre de medalles especificat.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0202#testOrdenarEsportistesPerMedalla
     */
    public static ArrayList<HashMap<String, Object>> ordenarEsportistesPerMedalla(String filePath, String tipusMedalla) {
        ArrayList<HashMap<String, Object>> esportistes = JSONEsportistesToArrayList(filePath);

        if (!tipusMedalla.equals("or") && !tipusMedalla.equals("plata") && !tipusMedalla.equals("bronze")) {
            throw new IllegalArgumentException("Tipus de medalla invàlid: " + tipusMedalla + ". Tipus vàlids: 'or', 'plata' o 'bronze'.");
        }

        esportistes.sort((esportista0, esportista1) -> {
            HashMap<?, ?> medalles0 = (HashMap<?, ?>) esportista0.get("medalles");
            HashMap<?, ?> medalles1 = (HashMap<?, ?>) esportista1.get("medalles");

            Integer a = (Integer) medalles0.get(tipusMedalla);
            Integer b = (Integer) medalles1.get(tipusMedalla);

            return b.compareTo(a);
        });

        return esportistes;
    }

    /**
     * Mostra una taula amb els esportistes ordenats per una medalla específica (or, plata o bronze).
     *
     * Les paraules "or", "plata" i "bronze" han de ser mostrades amb la 
     * primera lletra en majúscules i la resta en minúscules.
     * 
     * El format de la taula ha de fer servir els caràcters: "┌", "┬", "┐", "├", "┼", "┤", "└", "┴" i "┘".
     * 
     * Un exemple simplificat de la taula seria:
     * ┌──────────────────────┬─────────────────┬────────────┬────────┐
     * │ Nom                  │ País            │ Naixement  │ Plata  │
     * ├──────────────────────┼─────────────────┼────────────┼────────┤
     * │ Larisa Latynina      │ Unió Soviètica  │ 1934       │ 5      │
     * │ Michael Phelps       │ Estats Units    │ 1985       │ 3      │
     * └──────────────────────┴─────────────────┴────────────┴────────┘
     * 
     * @param filePath Ruta de l'arxiu JSON amb les dades dels esportistes.
     * @param tipusMedalla Tipus de medalla per ordenar: "or", "plata" o "bronze".
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0202#testShowEsportistesOrdenatsPerOr
     * @test ./runTest.sh com.exercicis.TestExercici0202#testShowEsportistesOrdenatsPerPlata
     * @test ./runTest.sh com.exercicis.TestExercici0202#testShowEsportistesOrdenatsPerBronze
     */
    public static void showEsportistesOrdenatsPerMedalla(String filePath, String tipusMedalla) {
        ArrayList<HashMap<String, Object>> participants = ordenarEsportistesPerMedalla(filePath, tipusMedalla);
        
        
        String top =    "┌──────────────────────┬─────────────────┬────────────┬────────┐";
        String middle = "├──────────────────────┼─────────────────┼────────────┼────────┤";
        String bottom = "└──────────────────────┴─────────────────┴────────────┴────────┘";
    
        System.out.println(top);
        System.out.printf("│ %-20s │ %-15s │ %-10s │ %-6s │%n", "Nom", "País", "Naixement", tipusMedalla);
        System.out.println(middle);
    
        for (HashMap<String, Object> participant : participants) {
            String nom = (String) participant.get("nom");
            String any_naixement = String.valueOf(participant.get("any_naixement")); 
            String pais = (String) participant.get("pais");
    
            HashMap<String, Integer> medalles = (HashMap<String, Integer>) participant.get("medalles");
    
            int numMedalles = medalles.get(tipusMedalla); 
    
            System.out.printf("│ %-20s │ %-15s │ %-10s │ %-6d │%n", nom, pais, any_naixement, numMedalles);
        }
    
        System.out.println(bottom);
    }
    
    
    
    /**
     * Llegeix l'arxiu JSON i converteix la informació dels planetes en una llista d'objectes HashMap.
     * 
     * Cada planeta es representa com un HashMap amb les claus:
     * - "nom" -> String amb el nom del planeta.
     * - "dades_fisiques" -> HashMap amb:
     *     - "radi_km" -> Double amb el radi en quilòmetres.
     *     - "massa_kg" -> Double amb la massa en kilograms.
     * - "orbita" -> HashMap amb:
     *     - "distancia_mitjana_km" -> Double amb la distància mitjana al Sol en quilòmetres.
     *     - "periode_orbital_dies" -> Double amb el període orbital en dies.
     * 
     * @param filePath Ruta de l'arxiu JSON amb les dades dels planetes.
     * @return Una ArrayList de HashMap amb la informació dels planetes.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0202#testJSONPlanetesToArrayList
     */
    public static ArrayList<HashMap<String, Object>> JSONPlanetesToArrayList(String filePath) {
        ArrayList<HashMap<String, Object>> planetesList = new ArrayList<>();
    
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("planetes");
    
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject planetaJSON = jsonArray.getJSONObject(i);
    
                HashMap<String, Object> planeta = new HashMap<>();
                planeta.put("nom", planetaJSON.getString("nom"));
    
                JSONObject dadesJSON = planetaJSON.getJSONObject("dades_fisiques");
                HashMap<String, Double> dadesFisiques = new HashMap<>();
                dadesFisiques.put("radi_km", dadesJSON.getDouble("radi_km"));
                dadesFisiques.put("massa_kg", dadesJSON.getDouble("massa_kg"));
    
                planeta.put("dades_fisiques", dadesFisiques);
    
                JSONObject orbitaJSON = planetaJSON.getJSONObject("orbita");
                HashMap<String, Double> orbita = new HashMap<>();
                orbita.put("distancia_mitjana_km", orbitaJSON.getDouble("distancia_mitjana_km"));
                orbita.put("periode_orbital_dies", orbitaJSON.getDouble("periode_orbital_dies"));
    
                planeta.put("orbita", orbita);
    
                planetesList.add(planeta);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return planetesList;
    }
    
    
    /**
     * Mostra una taula amb els planetes ordenats segons una columna especificada.
     * 
     * Els valors vàlids per a la columna d'ordenació són:
     * - "nom" -> Ordena alfabèticament pel nom del planeta.
     * - "radi" -> Ordena numèricament pel radi del planeta.
     * - "massa" -> Ordena numèricament per la massa del planeta.
     * - "distància" -> Ordena numèricament per la distància mitjana al Sol.
     * 
     * El format de la taula ha de fer servir els caràcters: "┌", "┬", "┐", "├", "┼", "┤", "└", "┴" i "┘".
     * 
     * Ex.:
     * ┌──────────────┬────────────┬──────────────┬────────────────┐
     * │ Nom          │ Radi (km)  │ Massa (kg)   │ Distància (km) │
     * ├──────────────┼────────────┼──────────────┼────────────────┤
     * │ Mercuri      │ 2439.7     │ 3.3011e23    │ 57910000       │
     * │ Venus        │ 6051.8     │ 4.8675e24    │ 108200000      │
     * └──────────────┴────────────┴──────────────┴────────────────┘
     * 
     * @param filePath Ruta de l'arxiu JSON amb les dades dels planetes.
     * @param columnaOrdenacio La columna per la qual es vol ordenar ("nom", "radi", "massa", "distància").
     * 
     * @throws IllegalArgumentException si el paràmetre de columna és invàlid.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0202#testMostrarPlanetesOrdenatsNom
     * @test ./runTest.sh com.exercicis.TestExercici0202#testMostrarPlanetesOrdenatsRadi
     * @test ./runTest.sh com.exercicis.TestExercici0202#testMostrarPlanetesOrdenatsMassa
     * @test ./runTest.sh com.exercicis.TestExercici0202#testMostrarPlanetesOrdenatsDistancia
     */
    

     public static void mostrarPlanetesOrdenats(String filePath, String tipus) throws IllegalArgumentException {
        ArrayList<HashMap<String, Object>> planetes = JSONPlanetesToArrayList(filePath);

        if (!tipus.equals("nom") && !tipus.equals("radi") && !tipus.equals("massa") && !tipus.equals("distancia")) {
            throw new IllegalArgumentException("Tipus de paràmetre invàlid: " + tipus);
        }

        planetes.sort((planeta0, planeta1) -> {
            if (tipus.equals("nom")) {
                return ((String) planeta0.get("nom")).compareTo((String) planeta1.get("nom"));
            } else {
                HashMap<String, Object> dades0 = (HashMap<String, Object>) planeta0.get("dades_fisiques");
                HashMap<String, Object> orbita0 = (HashMap<String, Object>) planeta0.get("orbita");

                HashMap<String, Object> dades1 = (HashMap<String, Object>) planeta1.get("dades_fisiques");
                HashMap<String, Object> orbita1 = (HashMap<String, Object>) planeta1.get("orbita");

                double valor0 = 0, valor1 = 0;

                switch (tipus) {
                    case "radi":
                        valor0 = (double) dades0.get("radi_km");
                        valor1 = (double) dades1.get("radi_km");
                        break;
                    case "massa":
                        valor0 = (double) dades0.get("massa_kg");
                        valor1 = (double) dades1.get("massa_kg");
                        break;
                    case "distancia":
                        valor0 = (double) orbita0.get("distancia_mitjana_km");
                        valor1 = (double) orbita1.get("distancia_mitjana_km");
                        break;
                }
                return Double.compare(valor0, valor1);
            }
        });


        System.out.println("┌──────────────┬────────────┬──────────────┬────────────────┐");
        System.out.println("│ Nom          │ Radi (km)  │ Massa (kg)   │ Distància (km) │");
        System.out.println("├──────────────┼────────────┼──────────────┼────────────────┤");

        for (HashMap<String, Object> planeta : planetes) {
            String nom = (String) planeta.get("nom");
            double radi = ((HashMap<String, Number>) planeta.get("dades_fisiques")).get("radi_km").doubleValue();
            double massa = ((HashMap<String, Number>) planeta.get("dades_fisiques")).get("massa_kg").doubleValue();
            double distancia = ((HashMap<String, Number>) planeta.get("orbita")).get("distancia_mitjana_km").doubleValue();

            System.out.printf("│ %-12s │ %-10.1f │ %-12.3e │ %-14.0f │%n", nom, radi, massa, distancia);
        }

        System.out.println("└──────────────┴────────────┴──────────────┴────────────────┘");
    }
    



    /**
     * Crea un HashMap que representa una massa d'aigua amb característiques addicionals.
     * 
     * @param nom Nom del mar o oceà.
     * @param tipus Tipus (mar o oceà).
     * @param superficie_km2 Superfície en km².
     * @param profunditat_max_m Profunditat màxima en metres.
     * @param caracteristiques Llista d'informació addicional sobre el mar o oceà.
     * @return Un HashMap amb les dades del mar o oceà.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0202#testCrearMassaAigua
     */
    public static HashMap<String, Object> crearMassaAigua(String nom, String tipus, double superficie_km2, double profunditat_max_m, ArrayList<String> caracteristiques) {
        HashMap<String, Object> massaAigua = new HashMap<>();
        massaAigua.put("nom", nom);
        massaAigua.put("tipus", tipus);
        massaAigua.put("superficie_km2", superficie_km2);
        massaAigua.put("profunditat_max_m", profunditat_max_m);
        massaAigua.put("caracteristiques", caracteristiques);
        return massaAigua;
    }

    /**
     * Genera un arxiu JSON amb la informació de mars i oceans. Identat amb "4 espais":
     * [
     *     {
     *         "nom": "Oceà Pacífic",
     *          "tipus": "oceà",
     *          "profunditat_max_m": 10924,
     *          "superficie_km2": 1.68723E8,
     *          "caracteristiques": [
     *              "És l'oceà més gran del món",
     *              "Conté la fossa de les Marianes, la més profunda del món",
     *              "Conté una illa de plàstics contaminants."
     *         ]
     *      },
     *      {
     *          "nom": "Oceà Atlàntic",
     *          "tipus": "oceà", ...
     * 
     * @param filePath Ruta de l'arxiu JSON a crear.
     * @throws IOException Si hi ha algun problema amb l'escriptura de l'arxiu forçant un try/catch
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0202#testValidarFormatJSON
     */
    public static void generarJSON(ArrayList<HashMap<String, Object>> dades, String filePath) throws IOException {

        JSONArray jsonArray = new JSONArray(dades);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Arxiu de mar i oceans creat correctament.");

    }
}