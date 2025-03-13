package com.exercicis;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONArray;

public class Exercici0203 {

    public static Scanner scanner;
    public static Locale defaultLocale;
    
    // Fes anar el 'main' de l'exercici amb:
    // ./run.sh com.exercicis.Exercici0203
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        defaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);

        String url0 = "http://example.com";
        validarURL(url0); 

        String url1 = "https://google";
        validarURL(url1); 
        
        try {
            ArrayList<HashMap<String, Object>> monuments = loadMonuments(".\\data\\monuments.json");
            ArrayList<HashMap<String, Object>> monumentsOrdenats = ordenaMonuments(monuments, "nom");
            ArrayList<HashMap<String, Object>> monumentsFiltrats = filtraMonuments(monuments, "categoria", "Monumental");
            
            System.out.println(getMonumentValue(monuments.get(2), "nom"));
            //System.out.println(getMonumentValue(monuments.get(2), "pais"));
            //System.out.println(getMonumentValue(monuments.get(2), "categoria"));
            //System.out.println(getMonumentValue(monuments.get(2), "any"));
            //System.out.println(getMonumentValue(monuments.get(2), "longitud"));

            //System.out.println(getCoordsString((monuments.get(2))));

            taulaMonuments(monumentsOrdenats);
            taulaMonuments(monumentsFiltrats);
            generaBaralla();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            guardaBaralla(".\\data\\baralla.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Locale.setDefault(defaultLocale);
        scanner.close();
    }

    /** 
     * Valida una URL a partir d'una cadena de text. 
     * 
     * Cal que:
     * 
     * - No pot ser null ni una cadena de text buida
     * - No pot contenir espais
     * - Ha de començar amb 'http://' o 'https://'
     * - El domini ha de contenir almenys un punt (excepte localhost)
     * - El domini no pot començar ni acabar amb un punt
     * 
     * URLs vàlides: ["http://example.com", "https://www.google.com", "https://sub.domini.cat/pagina", "http://localhost:8080", "http://www.ieti.cat:8080/horaris"]
     * URLs no vàlides: ["", "ftp://example.com", "http:/example", "http:/example.com", "https:// google.com", "https://.example.com", "https://example.", "example.com"]
     * 
     * @param url URL a validar
     * @return 'true' si la URL és vàlida, 'false' si no ho és
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testValidarURL
     */
    public static boolean validarURL(String url) {
        //si la url esta vacia empieza/acaba con punto y si tiene espacio-> si se cumple es falso
        if (url == null || url.length() == 0 || url.startsWith(".") || url.endsWith(".") || url.contains(" ")) {
            return false;
        }
        //las validas
        String[] valides = {"http://", "https://", "localhost"};
        //recorres la lista
        for (String valida : valides) {
            if (url.startsWith(valida)) { //si empieza con valida
                int pos = valida.length();
                if (url.charAt(pos) == '.') { //comprueba si el caracter despues de lo valido es un punto o no
                    return false; 
                }
                return true;
            }
        }
    
        return false; 
    }
    

    /**
     * Llegeix l'arxiu de 'filePath', retorna un ArrayList amb les dades 
     * dels monuments que són patrimoni de la humanitat.
     * 
     * Ha de generar 'HashMap' amb les dades de cada monument
     * L'atribut que no coincideix, ha d'estar en un 'HashMap' propi anomenat 'altres'
     * que té dues claus:
     * - 'clau': nom de l'atribut
     * - 'valor': valor de l'atribut
     * 
     * @param filePath Ruta de l'arxiu JSON
     * @return ArrayList amb les dades dels monuments
     * 
     * @throws IOException Si hi ha algun problema amb l'escriptura de l'arxiu forçant un 'try/catch'
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testLoadMonuments
     */
    public static ArrayList<HashMap<String, Object>> loadMonuments(String filePath) throws IOException {
        ArrayList<HashMap<String, Object>> rst = new ArrayList<>(); // Crear una lista vacía para almacenar los monumentos
    
        // Leer el contenido del archivo JSON especificado
        String content = new String(Files.readAllBytes(Paths.get(filePath))); 
        // Convertir el contenido JSON a un objeto JSONObject y obtener el array "monuments"
        JSONArray monumentsArray = new JSONObject(content).getJSONArray("monuments"); 
    
        // Recorrer cada elemento en el array de monumentos
        for (int i = 0; i < monumentsArray.length(); i++) {
            // Obtener cada monumento (un JSONObject) del array
            JSONObject monument = monumentsArray.getJSONObject(i);
            HashMap<String, Object> monumentHM = new HashMap<>(); // Crear un nuevo HashMap para almacenar los datos del monumento
    
            // Recorrer las claves del monumento (como "nom", "pais", "categoria", etc.)
            for (String key : monument.keySet()) {
                // Si la clave es "nom", "pais" o "categoria", se agrega directamente al HashMap
                if (key.equals("nom") || key.equals("pais") || key.equals("categoria")) {
                    monumentHM.put(key, monument.get(key)); // Agregar la clave y su valor al HashMap
                } 
                // Si la clave es "detalls", se procesa de forma especial ya que contiene un objeto con más detalles
                else if (key.equals("detalls")) {
                    JSONObject detalls = monument.getJSONObject(key); // Obtener el objeto "detalls" del monumento
                    HashMap<String, Object> detallsMap = new HashMap<>(); // Crear un HashMap para los detalles del monumento
                    HashMap<String, Object> altres = new HashMap<>(); // Crear un HashMap para los "otros" detalles que no son específicos
    
                    // Recorrer las claves dentro del objeto "detalls"
                    for (String detallsKey : detalls.keySet()) {
                        // Si la clave es "coordenades", procesar las coordenadas
                        if (detallsKey.equals("coordenades")) {
                            HashMap<String, Object> coordsMap = new HashMap<>();
                            JSONObject coordenadesJSON = detalls.getJSONObject("coordenades"); // Obtener el objeto "coordenades"
                            Double lat = coordenadesJSON.getDouble("latitud"); // Obtener la latitud
                            Double lon = coordenadesJSON.getDouble("longitud"); // Obtener la longitud
                            coordsMap.put("latitud", lat); // Guardar la latitud en el HashMap
                            coordsMap.put("longitud", lon); // Guardar la longitud en el HashMap
                            detallsMap.put("coordenades", coordsMap); // Añadir las coordenadas al HashMap de detalles
                        } 
                        // Si la clave es "any_declaracio", almacenar el año de declaración
                        else if (detallsKey.equals("any_declaracio")) {
                            int any = detalls.getInt("any_declaracio"); // Obtener el año de declaración
                            detallsMap.put("any_declaracio", any); // Guardar el año en el HashMap de detalles
                        } 
                        // Para cualquier otra clave, guardarla como un "otro" detalle
                        else {
                            HashMap<String, Object> altre = new HashMap<>();
                            altre.put("clau", detallsKey); // Guardar la clave
                            altre.put("valor", detalls.get(detallsKey)); // Guardar el valor correspondiente
                            altres.put(detallsKey, altre); // Añadir el otro detalle al HashMap "altres"
                        }
                    }
    
                    // Añadir el HashMap de "altres" (otros detalles) a los detalles
                    detallsMap.put("altres", altres);
                    // Añadir el HashMap de detalles al HashMap del monumento
                    monumentHM.put(key, detallsMap);
                }
            }
    
            // Añadir el HashMap del monumento a la lista de resultados
            rst.add(monumentHM);
        }
    
        // Devolver la lista de monumentos
        return rst;
    }
    
    /**
     * Obté el valor d'un monument segons el camp especificat.
     * Es pot utilitzar tant per a ordenació com per a filtratge.
     * 
     * - Si el camp és "nom", retorna el valor directament de la clau "nom".
     * - Si el camp és "pais", retorna el valor directament de la clau "pais".
     * - Si el camp és "categoria", retorna el valor directament de la clau "categoria".
     * - Si el camp és "any", accedeix al valor de "any_declaracio" dins de "detalls".
     * - Si el camp és "latitud" o "longitud", accedeix al valor corresponent dins de "coordenades",
     *   que es troba dins de "detalls".
     * - Si el valor no existeix o la jerarquia de dades no està present, retorna null.
     * 
     * @param monument HashMap amb les dades del monument.
     * @param key Clau pel qual es vol obtenir el valor (pot ser "nom", "any", "latitud" o "longitud").
     * @return Un 'Object' amb el valor corresponent si existeix, en cas contrari retorna null.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testGetMonumentValue
     */
    static Object getMonumentValue(HashMap<String, Object> monument, String key) {
        // El switch se utiliza para verificar qué clave del monumento estamos buscando
        switch (key) {
            // Si la clave es "nom", "pais" o "categoria", retornamos el valor directamente del HashMap
            case "nom":
            case "pais":
            case "categoria":
                return monument.get(key);
    
            // Si la clave es "any", buscamos el año de declaración dentro de los detalles
            case "any":
                // Obtener el HashMap de "detalls" dentro del monumento
                HashMap<String, Object> detalls = (HashMap<String, Object>) monument.get("detalls"); 
                // Si "detalls" no es null, obtenemos el valor de "any_declaracio"
                if (detalls != null) {
                    return detalls.get("any_declaracio");
                } else {
                    return null; // Si "detalls" es null, retornamos null
                }
    
            // Si la clave es "latitud" o "longitud", buscamos las coordenadas dentro de los detalles
            case "latitud":
            case "longitud":
                // Obtener el HashMap de "detalls" dentro del monumento
                HashMap<String, Object> detalls2 = (HashMap<String, Object>) monument.get("detalls"); 
                // Obtener el HashMap de "coordenades" dentro de "detalls"
                HashMap<String, Object> coordenadas = (HashMap<String, Object>) detalls2.get("coordenades"); 
                // Si "coordenades" no es null, retornamos la latitud o longitud según la clave
                if (coordenadas != null) {
                    return coordenadas.get(key);
                } else {
                    return null; // Si "coordenades" es null, retornamos null
                }
        }
        return null; // Si la clave no coincide con ningún caso, retornamos null
    }
    
    
    
    /**
     * Comprova si un valor es troba dins d'una llista de valors vàlids.
     * 
     * @param value Valor a comprovar.
     * @param validValues Llista de valors permesos.
     * @return True si el valor és dins de la llista, false en cas contrari.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testIsValidValue
     */
    public static boolean isValid(String value, String[] validValues) {
        // Si el array de valores válidos está vacío, devuelve false
        if (validValues.length == 0) return false;
    
        // Recorrer los valores válidos
        for (String valor : validValues) {
            // Si el valor proporcionado como parámetro (value) coincide con uno de los valores válidos, devuelve true
            if (valor.equals(value)) {
                return true;
            }
        }
    
        // Si no se encontró coincidencia, devuelve false
        return false;
    }
    

    /**
     * Ordena un ArrayList de monuments per un camp concret.
     * Els camps vàlids són: 'nom', 'any', 'latitud', 'longitud'
     *      * 
     * @param monuments llista dels monuments
     * @param sortKey camp per ordenar
     * @return ArrayList amb les dades dels monuments
     * 
     * @throws IllegalArgumentException si el paràmetre de columna és invàlid forçant un 'try/catch'
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testOrdenaMonuments
     */
    public static ArrayList<HashMap<String, Object>> ordenaMonuments(ArrayList<HashMap<String, Object>> monuments, String sortKey) throws IllegalArgumentException {
        // Crear una nueva lista a partir de la lista de monumentos para no modificar la original
        ArrayList<HashMap<String, Object>> rst = new ArrayList<>(monuments);
    
        // Verificar si la clave de ordenación proporcionada es válida
        if (!isValid(sortKey, new String[]{"nom", "any", "latitud", "longitud"})) {
            // Si la clave no es válida, lanzar una excepción con un mensaje de error
            throw new IllegalArgumentException("Columna invalida");
        }
    
        // Ordenar la lista de monumentos en función del valor asociado a la clave de ordenación
        Collections.sort(rst, (m1, m2) -> {
            // Obtener los valores asociados a la clave de ordenación para cada monumento
            Object value1 = getMonumentValue(m1, sortKey);
            Object value2 = getMonumentValue(m2, sortKey);
    
            // Comparar los valores dependiendo de la clave de ordenación
            if (sortKey.equals("nom")) {
                // Si la clave es "nom", comparar como cadenas de texto
                return ((String) value1).compareTo((String) value2);
            } else if (sortKey.equals("any")) {
                // Si la clave es "any", comparar como enteros (años)
                return ((Integer) value1).compareTo((Integer) value2);
            } else {
                // Si la clave es "latitud" o "longitud", comparar como números decimales (coordenadas)
                return ((Double) value1).compareTo((Double) value2);
            }
        });
    
        // Devolver la lista ordenada
        return rst;
    }
    

    /**
     * Filtra un ArrayList de monuments per un camp i un valor
     * Els camps vàlids són: 'nom', 'pais', 'categoria'
     *      * 
     * @param monuments llista dels monuments
     * @param filterKey camp per filtrar
     * @param filterValue valor a filtrar
     * @return ArrayList amb les dades dels monuments
     * 
     * @throws IllegalArgumentException si el paràmetre de columna és invàlid (no força un 'try/catch')
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testFiltraMonuments
     */
    public static ArrayList<HashMap<String, Object>> filtraMonuments(ArrayList<HashMap<String, Object>> monuments, String filterKey, String filterValue) throws IllegalArgumentException {
        // Crear una nueva lista vacía para almacenar los monumentos que coincidan con el filtro
        ArrayList<HashMap<String, Object>> rst = new ArrayList<>();
    
        // Verificar si la clave de filtrado proporcionada es válida
        if (!isValid(filterKey, new String[]{"nom", "pais", "categoria"})) {
            // Si la clave no es válida, lanzar una excepción con un mensaje de error
            throw new IllegalArgumentException("Columna invalida");
        }
    
        // Recorrer cada monumento de la lista
        for (HashMap<String, Object> monument : monuments) {
            // Comprobar si el valor del monumento para la clave de filtrado coincide con el valor proporcionado
            if (getMonumentValue(monument, filterKey).toString().equals(filterValue)) {
                // Si coincide, añadir el monumento a la lista de resultados
                rst.add(monument);
            }
        }
    
        // Devolver la lista de monumentos que cumplen con el filtro
        return rst;
    }
    
    /**
     * Genera una cadena de text vàlida per formar el marc d'una taula:
     * 
     * Exemple: {2, 5, 3} i { '┌', '┬', '┐' } genera "┌──┬─────┬───┐"
     * Exemple: {4, 3, 6} i { '├', '┼', '┤' } genera "├────┼───┼──────┤"
     * Exemple: {2, 4} i { '└', '┴', '┘' } genera "└──┴────┘"
     * 
     * @param columnWidths array amb els caràcters que ocupa cada columna
     * @param separators array amb els separadors inicial,central i final
     * @return String amb la cadena de text
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testGeneraMarcTaula
     */
    public static String generaMarcTaula(int[] columnWidths, char[] separators) {
        // Usamos StringBuilder para construir la cadena de caracteres de forma eficiente
        StringBuilder rst = new StringBuilder();
    
        // Añadir el primer separador al inicio de la tabla (por ejemplo, un borde superior)
        rst.append(separators[0]);
    
        // Recorremos cada columna de la tabla según sus anchos (columnWidths)
        for (int i = 0; i < columnWidths.length; i++) {
            // Añadimos el número adecuado de caracteres "─" para crear el borde de la columna
            rst.append(("─").repeat(columnWidths[i]));
    
            // Si no es la última columna, añadir el separador entre columnas (por ejemplo, "|")
            if (i < columnWidths.length - 1) {
                rst.append(separators[1]);
            }
        }
    
        // Añadir el último separador para completar la fila (por ejemplo, un borde de la tabla)
        rst.append(separators[2]);
    
        // Devolver la cadena construida
        return rst.toString();
    }
    
    /**
     * Formata una fila de la taula amb els valors de cada columna, ajustant l'amplada segons 
     * els valors especificats i afegint marges i separadors.
     *
     * Cada cel·la s'alinea a l'esquerra i es complementa amb espais en blanc si cal 
     * per ajustar-se a l'amplada de la columna.
     *
     * Exemples:
     * formatRow(new String[]{"Nom", "País", "Any"}, new int[]{10, 6, 4});
     * Retorna: "│Nom       │País  │Any │"
     *
     * formatRow(new String[]{"Machu Picchu", "Perú", "1983"}, new int[]{10, 6, 4});
     * Retorna: "│Machu Picc│Perú  │1983│"
     *
     * @param values Array amb els valors de cada columna.
     * @param columnWidths Array amb l'amplada de cada columna.
     * @return Una cadena de text formatejada representant una fila de la taula.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testFormatRow
     */
    public static String formatRow(String[] values, int[] columnWidths) {
        // Inicializamos la cadena vacía que contendrá la fila formateada
        String rst = "";
    
        // Recorremos todos los valores que se van a incluir en la fila
        for (int i = 0; i < values.length; i = i + 1) {
            // Añadir el separador de columna ("│") al inicio de cada celda
            rst += "│";
    
            // Obtener el valor de la celda correspondiente
            String value = values[i];
    
            // Si el valor es más largo que el ancho de la columna, lo recortamos
            if (value.length() > columnWidths[i]) {
                value = value.substring(0, columnWidths[i]);
            }
    
            // Añadir el valor a la cadena de la fila
            rst += value;
    
            // Calcular el número de espacios que se deben añadir para alinear la columna
            int spaceCount = columnWidths[i] - value.length();
    
            // Si hay espacios que agregar, añadirlos al final del valor para completar la columna
            if (spaceCount > 0) {
                rst += " ".repeat(spaceCount);
            }
        }
    
        // Añadir el separador de la última columna al final de la fila
        rst += "│";
    
        // Devolver la fila formateada
        return rst;
    }
    
    

    /**
     * Obté una representació en format text de les coordenades d'un monument.
     *
     * Fa servir la funció 'getMonumentValue'
     * 
     * Crida a getCoordsString(monument) → Retorna "40.4,116.5"
     *
     * @param monument HashMap que representa un monument.
     * @return Una cadena de text amb les coordenades en format "latitud,longitud",
     *         o una cadena buida si no es troben les dades.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testGetCoordsString
     */
    public static String getCoordsString(HashMap<String, Object> monument) {
        //pillas la latitud y la longitud de monument
        Double latitud = (Double) getMonumentValue(monument, "latitud");
        Double longitud = (Double) getMonumentValue(monument, "longitud");

        if (latitud == null || longitud == null){ //si estan vacias devuelven nada
            return " ";
        }
        
        return String.format("%.1f,%.1f", latitud, longitud); //si no esta vacio las devuelve con formato
    }

    /**
     * Mostra una taula amb la informació d'una llista de monuments.
     * 
     * El format de la taula ha de fer servir els caràcters: "┌", "┬", "┐", "├", "┼", "┤", "└", "┴" i "┘".
     * 
     * Ex.:
     * ┌──────────────┬─────┬────┬────────────┐
     * │Nom           │Pais │Any │Coords      │
     * ├──────────────┼─────┼────┼────────────┤
     * │Gran Muralla X│Xina │1987│40.4,116.6  │
     * │Machu Picchu  │Perú │1983│-13.2,-72.5 │
     * │Catedral de No│Franç│1991│48.9,2.3    │
     * │Parc Nacional │Tanzà│1981│-2.3,34.8   │
     * └──────────────┴─────┴────┴────────────┘
     * 
     * @param monuments llista dels monuments     
     * @param columnaOrdenacio La columna per la qual es vol ordenar ("nom", "radi", "massa", "distància").
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testTaulaMonuments
     */
    public static void taulaMonuments(ArrayList<HashMap<String, Object>> monuments) {
        StringBuilder rst = new StringBuilder();
        
        int [] columnWidths = {15, 10, 5, 10}; //guiones que tiene la tabla por seccion └──────────────┴─────┴────┴────────────┘

        char[] separators = {'┌', '┬', '┐'};
        rst.append(generaMarcTaula(columnWidths, separators)).append("\n"); //llamas a la funcion con los numeros y los separadores
        
        String[] headers = {"Nom", "Pais", "Any", "Coords"};
        rst.append(formatRow(headers, columnWidths)).append("\n");//llamas a la funcion con los numeros y los titulos
        
        char[] separators1 = {'├', '┼', '┤'};
        rst.append(generaMarcTaula(columnWidths, separators1)).append("\n");//llamas a la funcion con los numeros y los separadores

        for (int i = 0; i < monuments.size(); i++) {
            HashMap<String, Object> monument = monuments.get(i); //rellenas con datos la tabla(obtener de los monumentos)
            String nom = (String) getMonumentValue(monument, "nom");
            String pais = (String) getMonumentValue(monument, "pais");
            String any = String.valueOf(getMonumentValue(monument, "any"));
            String coords = getCoordsString(monument);

            String[] rowValues = {nom, pais, any, coords};
            rst.append(formatRow(rowValues, columnWidths)).append("\n");
        }

        separators[0] = '└';
        separators[1] = '┴';
        separators[2] = '┘';
        rst.append(generaMarcTaula(columnWidths, separators)).append("\n");//llamas a la funcion con los numeros y los separadores para cerrar

        System.out.println(rst); //imprimes la lista
    }

    /**
     * Genera una baralla de cartes espanyola i la retorna en un ArrayList ordenat aleatòriament.
     * 
     * La baralla consta de 40 cartes, amb quatre pals: "oros", "copes", "espases" i "bastos".
     * Cada pal té cartes numerades de l'1 al 12.
     * 
     * Cada carta es representa com un HashMap amb dues claus:
     * - "pal" (String): indica el pal de la carta (ex: "oros").
     * - "numero" (Integer): el número de la carta (ex: 7).
     * 
     * Exemple de sortida d'una carta:
     * { "pal": "copes", "número": 10 }
     * 
     * @return Un ArrayList que conté les 40 cartes de la baralla en ordre aleatori.
     * 
     * @test ./runTest.sh com.exercicis.TestExercici0203#testGeneraBaralla
     */
    public static ArrayList<HashMap<String, Object>> generaBaralla() {
        ArrayList<HashMap<String, Object>> baralla = new ArrayList<>();
        String[] palos = {"oros", "copes", "espases", "bastos"}; //lista con los palos de la baraja
        
        for (String palo : palos) {
            for (int i = 1; i <= 12; i++) { //haces un for del 1 al 12(los numeros de la baraja)
                HashMap<String, Object> carta = new HashMap<>(); //haces un hashmap por cada carta
                carta.put("pal", palo); //pones el palo(oro,copa...)
                carta.put("numero", i); //pones el numero(1-12)
                baralla.add(carta); //añades la carta al hashmap
            }
        }
        Collections.shuffle(baralla); //meneas la baraja
        
        return baralla; //devuelves la baraja
    }
    


    /**
     * Guarda la informació d'una baralla espanyola a 'filePath'
     * 
     * @param filePath
     * @throws IOException si hi ha algun error amb l'escriptura de l'arxiu forçant un 'try/catch'
     */
    public static void guardaBaralla(String filePath) throws IOException {
            ArrayList<HashMap<String, Object>> baralla = generaBaralla(); //generas la baraja en un arrayList
            JSONArray jsonArray = new JSONArray(); //haces un array de los json vacio

            for (HashMap<String, Object> carta : baralla) {
                JSONObject cartaJSON = new JSONObject(carta); //haces un objeto JSON para cada carta que tengas en el arraylist
                jsonArray.put(cartaJSON); //pones cada carta en el Array JSON
                
            }

            try (FileWriter file = new FileWriter(filePath)) { //esto es para escribir en el archivo que quieras
                file.write(jsonArray.toString(1)); // esto escribe el arrayJSON en el archivo (le puedes poner identacion pa que se vea bien)
            } catch (IOException e) {
                throw new IOException("Error guardant la baralla al fitxer: " + filePath, e); //gestionar error si no deja guardar
            }

    }
}