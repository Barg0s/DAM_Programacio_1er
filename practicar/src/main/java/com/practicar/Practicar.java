package com.practicar;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Practicar {

    
    public static boolean isTestEnvironment = false;
    static ArrayList<HashMap<String, Object>> datos = new ArrayList<>();

    public static void loadMortgages(String fileName) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        datos = getArrayList(content);
    }
    public static ArrayList<HashMap<String, Object>> getArrayList(String jsonstr) {
        JSONArray arr = new JSONArray(jsonstr);
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            list.add(new HashMap<>(arr.getJSONObject(i).toMap()));
        }
        return list;
    }
    
    public static void main(String[] args) {
       mostrarDatos();
    }
   
    /**
     * Esborra la pantalla de la consola.
     *
     * Aquesta funció neteja la pantalla depenent del sistema operatiu. 
     * Per a Windows, executa el comandament `cls` mitjançant `cmd`. 
     * Per a altres sistemes operatius, utilitza seqüències d'escape ANSI.
     * Si es produeix un error durant l'execució, aquest s'imprimeix a la consola.
     */
    public static void clearScreen() {
        if (isTestEnvironment) {
            return;
        }
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void mostrarDatos() {
        try {
            // Leer el contenido del archivo JSON (especificado en la ruta) y convertirlo en un String
            String content = new String(Files.readAllBytes(Paths.get("./JAVA/PrimerExam/data/random.json")));
            
            // Convertir el contenido leído a un JSONObject
            JSONObject jsonObject = new JSONObject(content);
            
            // Obtener el array "pedidos" del JSON
            JSONArray array = new JSONObject(content).getJSONArray("pedidos");
    
            // Acceder al objeto "usuario" dentro del JSON
            JSONObject usuario = jsonObject.getJSONObject("usuario");
    
            // Mostrar los datos del "usuario" en consola
            System.out.println("Nombre: " + usuario.getString("nombre"));
            System.out.println("Edad: " + usuario.getInt("edad"));
            System.out.println("Correo: " + usuario.getString("correo"));
            System.out.println("Teléfono: " + usuario.getString("telefono"));
            System.out.println("Dirección: ");
            
            // Acceder a los datos de "direccion" dentro del objeto "usuario"
            JSONObject direccion = usuario.getJSONObject("direccion");
            System.out.println("  Calle: " + direccion.getString("calle"));
            System.out.println("  Ciudad: " + direccion.getString("ciudad"));
            System.out.println("  Código Postal: " + direccion.getString("codigo_postal"));
            System.out.println("  País: " + direccion.getString("pais"));
    
            // Iterar sobre el array "pedidos"
            for (int i = 0; i < array.length(); i++) {
                // Obtener cada objeto "pedido" del array
                JSONObject pedido = array.getJSONObject(i);
                // Crear un HashMap para almacenar los datos del pedido
                HashMap<String, Object> pedidoHM = new HashMap<>();
                
                // Iterar sobre las claves del objeto "pedido"
                for (String key : pedido.keySet()) {
                    // Si la clave es "id", "fecha" o "total", agregar el valor al HashMap
                    if (key.equals("id") || key.equals("fecha") || key.equals("total")) {
                        pedidoHM.put(key, pedido.get(key));
                    } else {
                        // Si la clave no es "id", "fecha" o "total", es para productos
                        HashMap<String, Object> productosHM = new HashMap<>();
                        
                        // Obtener el array "productos" dentro de cada "pedido"
                        JSONArray productArray = pedido.getJSONArray("productos");
                        
                        // Iterar sobre los productos del pedido
                        for (int j = 0; j < productArray.length(); j++) {
                            // Obtener el objeto "producto" dentro del array "productos"
                            JSONObject producto = productArray.getJSONObject(j);
                            
                            // Iterar sobre las claves del objeto "producto" y agregarlas al HashMap
                            for (String key2 : producto.keySet()) {
                                productosHM.put(key2, producto.get(key2));
                            }
                            
                            // Al final, agregar el HashMap de productos al HashMap de "pedido"
                            pedidoHM.put("Productos", productosHM);
                        }
                    }
                }
                // Mostrar los datos del pedido en consola
                System.out.println(pedidoHM);
            }
        } catch (IOException e) {
            // Capturar y mostrar cualquier error relacionado con la lectura del archivo JSON
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
        } catch (org.json.JSONException e) {
            // Capturar y mostrar cualquier error relacionado con el procesamiento del JSON
            System.err.println("Error al procesar el JSON: " + e.getMessage());
        }
    }
}    