/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_EDD1;

/**
 * Clase donde se encuentran los algoritmos de búsqueda como el BFS y 
 * Dijkstra, los cuales son llamados por otros métodos para poder encontrar
 * componentes conexos o rutas.
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

    
    /**
     * Método encargado de identificar componentes conexos en el grafo y poder 
     * resaltarlos para visualizarlos mejor, etiqueta cada proteína con un número de grupo, 
     * de modo que todas las proteínas que están conectadas entre sí.
     * @return 
     */
    public int[] obtenerBFS() { //BFS: Devuelve un arreglo con el orden de visita.
        int[] componentes = new int[numNodos]; 
        boolean[] visitado = new boolean[numNodos];
        int idComponente = 1; //Contador que asigna un número único a cada grupo aislado.
        
        for (int i = 0; i < numNodos; i++) { //Recorre cada uno de los nodos del grafo para asegurarse de no olvidar ningún grupo aislado.
            if (!visitado[i]) { //Si la proteína actual no ha sido visitada aún, se le asigna un nuevo grupo.
                
                // Iniciar BFS para encontrar todos los nodos de este grupo
                int[] cola = new int[numNodos];
                int frente = 0, fin = 0;
                
                visitado[i] = true; //Marca la proteína inicial del grupo como visitada.
                cola[fin++] = i; //Inserta la proteína inicial en la cola.
                componentes[i] = idComponente; //Le asigna el número de grupo actual a esta proteína.
                
                while (frente < fin) { //recorre la colamientras no esté vacía.
                    int u = cola[frente++]; //Extrae la proteína que está al frente de la cola para revisar sus conexiones.
                    
                    for (Arista a = adj[u]; a != null; a = a.siguiente) { //Recorre todas las conexiones de la proteína.
                        if (!visitado[a.destino]) { //Si la conexión no ha sido visitada, la marcará como visitada para que no se vuelva a agregar.
                            visitado[a.destino] = true;
                            componentes[a.destino] = idComponente; //Se asigna el mismo iD que sus interacciones.
                            cola[fin++] = a.destino; //Se agrega a la cola para revisar sus interacciones más tarde.
                        }
                    }
                }
                idComponente++; // Siguiente grupo aislado
            }
        }
        return componentes; //Devuelve cada grupo aislado.
    }

    /**
     * Método que calcula la ruta metabólica de menor peso y mas eficiente 
     * entre una proteína A y una proteína B siempre y cuando 
     * tengan una ruta que las conecte 
     * @param inicio
     * @param fin
     * @return 
     */
    public int[] obtenerDistanciasDijkstra(int inicio, int fin) { // DIJKSTRA: Devuelve un arreglo con las distancias mínimas.
        int[] distancias = new int[numNodos]; //Arreglo para guardar el costo total acumulado desde el inicio hasta cada nodo.
        int[] padres = new int[numNodos]; //Guarda la ubicación de cada nodo.
        boolean[] visitados = new boolean[numNodos]; //Marca los nodos cuyo camino más corto ya ha sido confirmado.
        
        for (int i = 0; i < numNodos; i++) { //Recorre todos los nodos para darles valores iniciales.
            distancias[i] = 2147483647; //Establece una distancia inicial practicamente infinita.
            padres[i] = -1; //Inidica que ningún nodo tiene un antecesor por el momento.
        }
        distancias[inicio] = 0; //La distancia desde el inicio hacia sí mismo es 0.

        for (int i = 0; i < numNodos; i++) { //Procesa cada nodo del grafo.
            int u = -1; //Encuentra el nodo con la distancia mínima actual.
            for (int j = 0; j < numNodos; j++) { //Busca entre todos los nodos el más cercano.
                if (!visitados[j] && (u == -1 || distancias[j] < distancias[u])) { //Si el nodo j es el más cercano entonces:
                    u = j; //Se selecciona como el nodo actual.
                }
            }

            if (u == -1 || distancias[u] == 2147483647){ //Si no se pueden alcanzar más nodos entonces detiene el algoritmo.
                break;
            }
            visitados[u] = true;
            if (u == fin){ //Si llega al destino, se detiene
                break;
            }
            
            for (Arista a = adj[u]; a != null; a = a.siguiente) { //Recorre todas las conexiones del nodo actual usando la lista de adyacencia.
                if (distancias[u] + a.peso < distancias[a.destino]) { //Si la distancia acumulada del nodo actual + el peso es menor que la distancia, entonces:
                    distancias[a.destino] = distancias[u] + a.peso; //Actualiza con el nuevo camino más corto encontrado.
                    padres[a.destino] = u; //Guarda que para llegar al destino hay que pasar por u.
                }
            }
        }
        return padres; //El arreglo contiene la información para volver al inicio.
    }
}
