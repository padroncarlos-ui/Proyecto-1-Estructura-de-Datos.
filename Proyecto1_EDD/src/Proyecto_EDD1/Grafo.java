/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_EDD1;


/**
 * Clase que contiene el motor lógico de las operaciones que tienen que ver con 
 * las modificaciones e interacciones del grafo.
 * 
 * @author karlg
 */
public class Grafo {
    private Interfaz Interfaz; //Referencia a la ventana principal para mostrar mensajes.
    private Proteina[] proteinas; //Arreglo que almacena los objetos Proteina (nodos).
    private Interaccion[][] adyacencia; //Matriz donde cada fila contiene las conexiones de una proteína.
    private int[] numInteracciones; //Contador de cuantas interacciones tiene cada proteína.
    private int cantidadProteinas; //Contador actual de nodos insertados.
    private int capacidad; //Tamaño máximo actual de los arreglos antes de redimensionar.
    
    private org.graphstream.graph.Graph grafoVisual; //Referencia al grafo de GraphStream.

    public Grafo(org.graphstream.graph.Graph graph, Interfaz Interfaz) { //Constructor de las estructuras.
        this.grafoVisual = graph; //Asocia el grafo visual.
        this.capacidad = 20; //Capacidad Inicial
        this.proteinas = new Proteina[capacidad]; //Arreglo de proteínas.
        this.adyacencia = new Interaccion[capacidad][capacidad]; //Matriz de adyacencia inicial.
        this.numInteracciones = new int[capacidad]; //Inicia el contador de grados en 0.
        this.cantidadProteinas = 0; //Inicia en 0.
        this.Interfaz = Interfaz; //Asocia la ventana principal.
    }

    /**
     * Ayuda a modificar el grafo. 
     * <p>
     * Añade una nueva proteína que no haya sido añadida antes
     * </p>
     * <p>
     * Verifica si la proteína ya está insertada para evitar duplicarla
     * </p>
     */
    public void addProteina(Proteina p) {
        if (buscarIndice(p) != -1){ //Si el nombre de la proteína existe no hace nada.
            return;
        }
        if (cantidadProteinas == capacidad){ //Si no hay espacio, llama al método redimensionar() para duplicar el tamaño de los arreglos.
            redimensionar();
        }
        proteinas[cantidadProteinas] = p; //Guarda la nueva proteína en la última posición libre.
        numInteracciones[cantidadProteinas] = 0; //Inicia su numero de interacciones en 0.
        cantidadProteinas++; //Incrementa el contador total.
        
        if (grafoVisual.getNode(p.getNombre()) == null) { //Si el nodo no existe visualmente, lo añade y le coloca su nombre.
            org.graphstream.graph.Node n = grafoVisual.addNode(p.getNombre()); 
            n.setAttribute("ui.label", p.getNombre());
        }
        
    }
    
    /**
     * Método que verifica si ya existen conexiones entre proteínas.
     * @param idxOrigen
     * @param nombreDestino
     * @return 
     */
    private boolean Interacciones(int idxOrigen, String nombreDestino) {
        for (int i = 0; i < numInteracciones[idxOrigen]; i++) { //recorre todas las conexiones de la proteína.
            if (adyacencia[idxOrigen][i].getDestino().getNombre().equals(nombreDestino)) { //Obtiene el nombre de la proteína destino de esa interacción y lo compara con el nombre buscado.
                return true; //Si son iguales, entonces la interacción ya existe.
            }
        }
        return false; //La interacción aún no existe.
    }
    
    /**
     * Ayuda a modifica el grafo. 
     * <p>
     * Agrega una interacción no dirigida.
     * </p>
     * Define si A interactúa con B, B interactúa con A.
     */
    public boolean agregarInteraccion(String nombreA, String nombreB, double peso) {
        int idxA = buscarPorNombre(nombreA); //Busca la posición de la proteína A.
        int idxB = buscarPorNombre(nombreB);//Busca la posición de la proteína b.

        if (idxA != -1 && idxB != -1) { //Verifica la existencia de ambos nodos.      
            if (Interacciones(idxA, nombreB)) { //Caso en el que la interacción ya exista
                if (this.Interfaz != null) {
                    this.Interfaz.EscribirInfo("La interacción entre " + nombreA + " y " + nombreB + " ya existe.");
                }
                return false; //Evita crear conexiones duplicadas.
            }

            insertarInteraccion(idxA, new Interaccion(proteinas[idxA], proteinas[idxB], peso)); //Agrega B a la lista de A.
            insertarInteraccion(idxB, new Interaccion(proteinas[idxB], proteinas[idxA], peso)); //Agrega A a la lista de B para que sea bidireccional.
 
            String idArista = nombreA + "_" + nombreB; //Define el ID para la línea visual.
            String idInverso = nombreB + "_" + nombreA; //Define el ID inverso para validación.
            
            //Crear visualmente la interacción.
            if (grafoVisual.getEdge(idArista) == null && grafoVisual.getEdge(idInverso) == null) {
                try{
                    org.graphstream.graph.Edge e = grafoVisual.addEdge(idArista, nombreA, nombreB); //Crea la interacción.
                    e.setAttribute("ui.label", (int)peso); //Muestra el peso.
                    e.setAttribute("weight", peso); //Guarda el peso como atributo.
                }catch (Exception ex) {}
            }
            return true;
        }
        return false;
    }

    /**
     * Ayuda a modificar el grafo.
     * <p>
     * Busca y elimina una proteína por su nombre.
     * </p>
     * Borra todas sus interacciones asociadas.
     */
    public void eliminarProteina(String nombre) {
        int index = buscarPorNombre(nombre); //Busca la posición del nodo a borrar.
        if (index == -1) return;
        
        if (grafoVisual.getNode(nombre) != null) {
            grafoVisual.removeNode(nombre); //Elimina el nodo visualmente.
        }
 
        for (int i = 0; i < cantidadProteinas; i++) { // Elimina todas las interacciones que apuntan a esta proteína en otros nodos
            if (i == index) continue;
            eliminarReferencia(i, nombre);
        }

        // Desplaza el arreglo para borrar el nodo.
        for (int i = index; i < cantidadProteinas - 1; i++) {
            proteinas[i] = proteinas[i + 1];
            adyacencia[i] = adyacencia[i + 1];
            numInteracciones[i] = numInteracciones[i + 1];
        }
        cantidadProteinas--; //Reduce el contador total.
        //Limpia la posición en la que estuvo el nodo a eliminar.
        proteinas[cantidadProteinas] = null;
        adyacencia[cantidadProteinas] = null;
        numInteracciones[cantidadProteinas] = 0;      
    }

    /**
     * Identificación de "Hubs"
     * <p>
     * Busca la proteína con el mayor grado de conexiones y la resalta en el grafo
     * utilizando la hoja de estilos.
     * </p>
     */
    public void obtenerHubPrincipal() {       
        try {         
            if (cantidadProteinas == 0) return;
            //Encuentra el grado máximo en caso de una eliminación.
            int maxGrado = 0;
            for (int i = 0; i < cantidadProteinas; i++) { //Busca el número máximo de conexiones que tiene una proteína.
                if (numInteracciones[i] > maxGrado) {
                    maxGrado = numInteracciones[i];
                    }
                }
            
            if (maxGrado == 0) { //Caso en el que no haya ninguna conexión en el grafo.
                this.Interfaz.EscribirInfo("No hay interacciones en el grafo.");
                return;
            }

            // Acumula nombres de Hubs en caso de que hayan varios hubs con las mismas conexiones
            StringBuilder sb = new StringBuilder("Identificación de Hubs completada.\n");
            sb.append("Hubs detectados con ").append(maxGrado).append(" conexiones:\n");
            for (int i = 0; i < cantidadProteinas; i++) {
                String nombreProt = proteinas[i].getNombre();
                org.graphstream.graph.Node n = grafoVisual.getNode(nombreProt);

                if (n != null) {
                    if (numInteracciones[i] == maxGrado) { //Si la proteína cuenta con la mayor cantidad de interacciones, lo resalta.
                        n.setAttribute("ui.class", "hub"); //Resalta en rojo.
                        sb.append("- ").append(nombreProt).append("\n"); //Añade al reporte.
                    } else {
                        n.removeAttribute("ui.class"); // Quita el resaltado a los demás.
                    }
                }
            }

            //Imprime en ConsolaTxt todos los Hubs en caso de que sean varios.
            if (this.Interfaz != null) {
                this.Interfaz.EscribirInfo(sb.toString());
            }
            
        } catch (Exception e) {
            Interfaz.EscribirInfo("Error al identificar Hubs");
        }      
    }
    
    /**
     * Metódo auxiliar de gestión.
     * Añade nuevas conexiones entre proteínas.
     */
    private void insertarInteraccion(int idx, Interaccion inter) {
        if (numInteracciones[idx] >= adyacencia[idx].length) { //Revisa que el tamaño de las conexiones no sea mayor al tamaño máximo del arreglo.
            expandirFila(idx); //Si el arreglo está lleno, llama al método para duplicar su tamaño.
        }
        adyacencia[idx][numInteracciones[idx]] = inter; //Guarda la información del peso y del destino.
        numInteracciones[idx]++; //Aumenta la cantidad de interacciones de la proteína.
    }
    
    /**
     * Metódo auxiliar de gestión.
     * Elimina conexiones ya establecidas entre proteínas
     * dentro de la lista de adyacencias
     * y reduce el tamaño de la lista.
     */
    private void eliminarReferencia(int idxNodo, String nombreDestino) {
        int k = 0; //Inicia un contador para recorrer la lista.
        while (k < numInteracciones[idxNodo]) { //revisa cada una de las interacciones de la proteína.
            if (adyacencia[idxNodo][k].getDestino().getNombre().equals(nombreDestino)) {
                // Desplaza las interacciones para eliminar la referencia
                for (int j = k; j < numInteracciones[idxNodo] - 1; j++) {
                    adyacencia[idxNodo][j] = adyacencia[idxNodo][j + 1];
                }            
                numInteracciones[idxNodo]--; //Reduce el contador de interacciones totales.
            } else {
                k++; //Pasa a la siguiente posición en caso de que la interacción no sea la que se busca.
            }
        }
    }

    /**
     * Busca una proteína ya insertada a partir de un nombre
     * Realiza una búsqueda lineal sobre el arreglo de proteínas comparando
     * el nombre proporcionado con el identificador de cada objeto.
     */
    public int buscarPorNombre(String nombre) {
        for (int i = 0; i < cantidadProteinas; i++) {
            if (proteinas[i].getNombre().equals(nombre)) return i;
        }
        return -1;
    }

    /**
    * Busca el índice de una proteína por su nombre.
    * Realiza la búsqueda comparandola con el nombre dado.
    */
    private int buscarIndice(Proteina p) {
        return buscarPorNombre(p.getNombre());
    }

     /**
     * Duplica la capacidad de la estructura del almacenamiento. 
     * Se llama cuando el arreglo de las proteínas alcanza su limite. 
     */
    private void redimensionar() {
        capacidad *= 2; //Duplica la capacidad actual.
        Proteina[] nP = new Proteina[capacidad]; //Crea un nuevo arreglo con la nueva capacidad.
        Interaccion[][] nA = new Interaccion[capacidad][]; //Crea una nueva matriz de adyacencia con más filas.
        int[] nC = new int[capacidad]; //Crea un nuevo arreglo de contadores de interacciones con la nueva capacidad.
        
        //Copia datos viejos a arreglos nuevos.
        System.arraycopy(proteinas, 0, nP, 0, cantidadProteinas);
        System.arraycopy(adyacencia, 0, nA, 0, cantidadProteinas);
        System.arraycopy(numInteracciones, 0, nC, 0, cantidadProteinas);
        
        for (int i = cantidadProteinas; i < capacidad; i++) {
            nA[i] = new Interaccion[10]; // Capacidad inicial para nuevas proteínas
        }
        proteinas = nP;
        adyacencia = nA;
        numInteracciones = nC;
    }

    /**
     * Aumenta la capacidad de interacciones para una proteína específica.
     * Si la fila está vacia la capacidad iniciará en 1
     */
    private void expandirFila(int idx) {
        int nuevaCap = (adyacencia[idx].length == 0) ? 1 : adyacencia[idx].length * 2; //Calcula el nuevo tamaño.
        Interaccion[] nuevaFila = new Interaccion[nuevaCap]; //Crea un nuevo arreglo con la capacidad ampliada.
        System.arraycopy(adyacencia[idx], 0, nuevaFila, 0, numInteracciones[idx]); //Copia los datos viejos.
        adyacencia[idx] = nuevaFila; ////Reemplaza el arreglo pequeño por el nuevo arreglo más grande
    }
    
    // Getters necesarios para BFS y Dijkstra
    public int getCantidadProteinas() { return cantidadProteinas; } //Devuelve el número actual de proteínas registradas en el grafo.
    public Proteina[] getProteinas() { return proteinas; } //Devuelve el arreglo completo de objetos Proteina.
    public Interaccion[] getAdyacentes(int idx) { return adyacencia[idx]; } //Devuelve el arreglo de conexiones de la proteína situada en el índice idx.
    public int getNumAdyacentes(int idx) { return numInteracciones[idx]; } //Devuelve cuántas conexiones tiene la proteína en el índice idx.


    public Proteina[] getNodos() { //Crea y devuelve una lista limpia de las proteínas existentes.
        Proteina[] existentes = new Proteina[cantidadProteinas]; //Crea un nuevo arreglo del tamaño de las proteínas reales.
        System.arraycopy(proteinas, 0, existentes, 0, cantidadProteinas); //Copia solo las proteínas válidas
        return existentes; //Devuelve el nuevo arreglo
    }

    public int obtenerGrado(Proteina p) { //Calcula cuántas conexiones tiene una proteína específica.
        int idx = buscarIndice(p); //Llama al método que busca en qué posición del arreglo una proteína específica.
        if (idx != -1) {
            return numInteracciones[idx]; //Devuelve su número de interacciones.
        }
        return 0; //Si la proteína no existe, su grado será 0.
    }
}

