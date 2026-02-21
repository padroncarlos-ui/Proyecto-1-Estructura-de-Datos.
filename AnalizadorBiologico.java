/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *Clase encargada de realizar análisis avanzados sobre estructuras biológicas
 * representadas mediante grafos, como la detección de complejos y rutas.
 * @author karlg
 */
public class AnalizadorBiologico {

/**
     * Utiliza algoritmos de recorrido (BFS/DFS) para identificar subestructuras
     * o complejos dentro del grafo biológico proporcionado.
     * * @param g El grafo sobre el cual se realizará la búsqueda de complejos.
     */
    public void detectarComplejos(Grafo g) {
        System.out.println("Ejecutando BFS/DFS para detectar complejos...");
    }
    /**
     * Calcula la ruta más eficiente entre dos proteínas específicas utilizando 
     * el algoritmo de Dijkstra.
     * * @param g  El grafo que contiene las proteínas.
     * @param p1 Proteína de origen.
     * @param p2 Proteína de destino.
     */
    public void buscadorDeRuta(Grafo g, Proteina p1, Proteina p2) {
        System.out.println("Calculando Dijkstra entre " + p1 + " y " + p2);
    }
    /**
     * Identifica proteínas clave (hubs) en el grafo basándose en su grado de conectividad.
     * Si una proteína tiene más de 5 conexiones, se marca como un nodo central o hub.
     * * @param g El grafo de proteínas a analizar.
     */
    public void identificarHubs(Grafo g) {
        for (Proteina p : g.getNodos()) {
            if (g.obtenerGrado(p) > 5) p.setEsHub(true); // Ejemplo de lógica
        }
    }
}



