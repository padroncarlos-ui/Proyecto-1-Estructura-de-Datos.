/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author karlg
 */
public class AnalizadorBiologico {
      public void detectarComplejos(Grafo g) {
        System.out.println("Ejecutando BFS/DFS para detectar complejos...");
    }

    public void buscadorDeRuta(Grafo g, Proteina p1, Proteina p2) {
        System.out.println("Calculando Dijkstra entre " + p1 + " y " + p2);
    }

    public void identificarHubs(Grafo g) {
        for (Proteina p : g.getNodos()) {
            if (g.obtenerGrado(p) > 5) p.setEsHub(true); // Ejemplo de lógica
        }
    }
}

class GestorDeArchivos {
    public void guardarArchivo(String path, Grafo g) {
        System.out.println("Grafo guardado en: " + path);
    }

    public Grafo cargarArchivo(String path) {
        System.out.println("Cargando grafo desde: " + path);
        return new Grafo();
    }
}

