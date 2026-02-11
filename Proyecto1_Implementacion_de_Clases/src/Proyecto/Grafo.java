/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *
 * @author karlg
 */
public class Grafo {
    private Proteina[] proteinas;
    private Interaccion[][] adyacencia; 
    private int[] numInteracciones;
    private int cantidadProteinas;
    private int capacidad;

    public Grafo() {
        this.capacidad = 20; //Capacidad Inicial
        this.proteinas = new Proteina[capacidad];
        this.adyacencia = new Interaccion[capacidad][capacidad];
        this.numInteracciones = new int[capacidad];
        this.cantidadProteinas = 0;
    }

    /**
     * Modifica el grafo. 
     * <p>
     * Añade una nueva proteína que no haya sido añadida antes
     * </p>
     * <p>
     * Verifica si la proteína ya está insertada para evitar duplicarla
     * </p>
     */
    public void addProteina(Proteina p) {
        if (buscarIndice(p) != -1) return;
        if (cantidadProteinas == capacidad) redimensionar();
        proteinas[cantidadProteinas] = p;
        numInteracciones[cantidadProteinas] = 0;
        cantidadProteinas++;
    }

    /**
     * Modifica el grafo. 
     * <p>
     * Agrega interacción no dirigida
     * </p>
     * Define si A interactúa con B, B interactúa con A.
     */
    public void agregarInteraccion(String nombreA, String nombreB, double peso) {
        int idxA = buscarPorNombre(nombreA);
        int idxB = buscarPorNombre(nombreB);

        if (idxA != -1 && idxB != -1) {
            // Interacción A -> B
            Interaccion inter1 = new Interaccion(proteinas[idxA], proteinas[idxB], peso);
            insertarInteraccion(idxA, inter1);
            
            // Interacción B -> A 
            Interaccion inter2 = new Interaccion(proteinas[idxB], proteinas[idxA], peso);
            insertarInteraccion(idxB, inter2);
        }
    }

    /**
     * Modifica el grafo 
     * <p>
     * Busca y elimina una proteína por su nombre.
     * </p>
     * Borra todas sus interacciones asociadas.
     */
    public void eliminarProteina(String nombre) {
        int index = buscarPorNombre(nombre);
        if (index == -1) return;

        // Elimina todas las interacciones que apuntan a esta proteína en otros nodos
        for (int i = 0; i < cantidadProteinas; i++) {
            if (i == index) continue;
            eliminarReferencia(i, nombre);
        }

        // Desplaza el arreglo para borrar el nodo
        for (int i = index; i < cantidadProteinas - 1; i++) {
            proteinas[i] = proteinas[i + 1];
            adyacencia[i] = adyacencia[i + 1];
            numInteracciones[i] = numInteracciones[i + 1];
        }
        cantidadProteinas--;
    }

    /**
     * Identificación de "Hubs"
     * <p>
     * Busca la proteína con el mayor grado de conexiones
     * </p>
     */
    public Proteina obtenerHubPrincipal() {
        if (cantidadProteinas == 0) return null;
        int maxGrado = -1;
        int indiceHub = 0;

        for (int i = 0; i < cantidadProteinas; i++) {
            if (numInteracciones[i] > maxGrado) {
                maxGrado = numInteracciones[i];
                indiceHub = i;
            }
        }
        return proteinas[indiceHub];
    }

    /**
     * Metódo auxiliar de gestión 
     * Añade nuevas conexiones entre proteínas
     */
    private void insertarInteraccion(int idx, Interaccion inter) {
        if (numInteracciones[idx] >= adyacencia[idx].length) {
            expandirFila(idx);
        }
        adyacencia[idx][numInteracciones[idx]] = inter;
        numInteracciones[idx]++;
    }
    
    /**
     * Metódo auxiliar de gestión 
     * Elimina conexiones ya establecidas entre proteínas
     * dentro de la lista de adyacencias
     * y reduce el tamaño de la lista
     */
    private void eliminarReferencia(int idxNodo, String nombreDestino) {
        int k = 0;
        while (k < numInteracciones[idxNodo]) {
            if (adyacencia[idxNodo][k].getDestino().getNombre().equals(nombreDestino)) {
                // Desplaza las interacciones para eliminar la referencia
                for (int j = k; j < numInteracciones[idxNodo] - 1; j++) {
                    adyacencia[idxNodo][j] = adyacencia[idxNodo][j + 1];
                }            
                numInteracciones[idxNodo]--;
            } else {
                k++;
            }
        }
    }

    /**
     * Busca una proteína ya insertada a partir de un nombre
     * Realiza una búsqueda lineal sobre el arreglo de proteínas comparando
     * el nombre proporcionado con el identificador de cada objeto.
     */
    private int buscarPorNombre(String nombre) {
        for (int i = 0; i < cantidadProteinas; i++) {
            if (proteinas[i].getNombre().equals(nombre)) return i;
        }
        return -1;
    }

    /**
    * Busca el índice de una proteína por su nombre
    * realiza la búsqueda comparandola con el nombre dado
    */
    private int buscarIndice(Proteina p) {
        return buscarPorNombre(p.getNombre());
    }

     /**
     * Duplica la capacidad de la estructura del almacenamiento 
     * Se llama cuando el arreglo de las proteínas alcanza su limite 
     */
    private void redimensionar() {
        capacidad *= 2;
        Proteina[] nP = new Proteina[capacidad];
        Interaccion[][] nA = new Interaccion[capacidad][];
        int[] nC = new int[capacidad];
        System.arraycopy(proteinas, 0, nP, 0, cantidadProteinas);
        System.arraycopy(adyacencia, 0, nA, 0, cantidadProteinas);
        System.arraycopy(numInteracciones, 0, nC, 0, cantidadProteinas);
        proteinas = nP;
        adyacencia = nA;
        numInteracciones = nC;
    }

    /**
     * Aumenta la capacidad de interacciones para una proteína específica
     * Si la fila está vacia la capacidad iniciará en 1
     */
    private void expandirFila(int idx) {
        int nuevaCap = (adyacencia[idx].length == 0) ? 1 : adyacencia[idx].length * 2;
        Interaccion[] nuevaFila = new Interaccion[nuevaCap];
        System.arraycopy(adyacencia[idx], 0, nuevaFila, 0, numInteracciones[idx]);
        adyacencia[idx] = nuevaFila;
    }
    
    // Getters necesarios para BFS/DFS y Dijkstra
    public int getCantidadProteinas() { return cantidadProteinas; }
    public Proteina[] getProteinas() { return proteinas; }
    public Interaccion[] getAdyacentes(int idx) { return adyacencia[idx]; }
    public int getNumAdyacentes(int idx) { return numInteracciones[idx]; }

    Iterable<Proteina> getNodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    int obtenerGrado(Proteina p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

