/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *
 * @author Antonio
 */
public class GestorGrafos {
    // Clase interna para manejar las conexiones
    private class Arista {
        int destino, peso;
        Arista siguiente;
        Arista(int d, int p, Arista s) {
            this.destino = d;
            this.peso = p;
            this.siguiente = s;
        }
    }

    private final Arista[] adj;
    private final int numNodos;

    public GestorGrafos(int nodos) {
        this.numNodos = nodos;
        this.adj = new Arista[nodos];
    }

    public void conectar(int origen, int destino, int peso) {
        adj[origen] = new Arista(destino, peso, adj[origen]);
    }

    // BFS: Devuelve un arreglo con el orden de visita
    public int[] obtenerRutaBFS(int inicio) {
        boolean[] visitado = new boolean[numNodos];
        int[] resultado = new int[numNodos];
        int[] cola = new int[numNodos];
        int frente = 0, fin = 0, idx = 0;

        visitado[inicio] = true;
        cola[fin++] = inicio;

        while (frente < fin) {
            int u = cola[frente++];
            resultado[idx++] = u;

            for (Arista a = adj[u]; a != null; a = a.siguiente) {
                if (!visitado[a.destino]) {
                    visitado[a.destino] = true;
                    cola[fin++] = a.destino;
                }
            }
        }
        return resultado;
    }

    // DIJKSTRA: Devuelve un arreglo con las distancias mínimas
    public int[] obtenerDistanciasDijkstra(int inicio) {
        int[] distancias = new int[numNodos];
        boolean[] visitados = new boolean[numNodos];
        
        for (int i = 0; i < numNodos; i++) {
            distancias[i] = 2147483647; // Equivalente a Integer.MAX_VALUE
        }
        distancias[inicio] = 0;

        for (int i = 0; i < numNodos; i++) {
            int u = -1;
            for (int j = 0; j < numNodos; j++) {
                if (!visitados[j] && (u == -1 || distancias[j] < distancias[u])) {
                    u = j;
                }
            }

            if (u == -1 || distancias[u] == 2147483647) break;
            visitados[u] = true;

            for (Arista a = adj[u]; a != null; a = a.siguiente) {
                if (distancias[u] + a.peso < distancias[a.destino]) {
                    distancias[a.destino] = distancias[u] + a.peso;
                }
            }
        }
        return distancias;
    }
}
