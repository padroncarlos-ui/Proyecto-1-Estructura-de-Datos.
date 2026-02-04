/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author karlg
 */
public class Grafo {
    private List<Proteina> nodos = new ArrayList<>();
    private Map<Proteina, List<Interaccion>> adyacencia = new HashMap<>();

    public void añadirProteina(Proteina p) {
        if (!nodos.contains(p)) {
            nodos.add(p);
            adyacencia.put(p, new ArrayList<>());
        }
    }

    public void agregarInteraccion(Proteina p1, Proteina p2, double w) {
        Interaccion inter = new Interaccion(p1, p2, w);
        adyacencia.get(p1).add(inter);
        // Si es no dirigido, añadir también de p2 a p1
    }

    public List<Interaccion> obtenerAdyacentes(Proteina p) {
        return adyacencia.getOrDefault(p, new ArrayList<>());
    }

    public int obtenerGrado(Proteina p) {
        return obtenerAdyacentes(p).size();
    }

    public List<Proteina> getNodos() { return nodos; }
}

