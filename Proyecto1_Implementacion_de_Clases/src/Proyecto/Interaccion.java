/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *Representa una conexión o interacción biológica entre dos proteínas dentro de un grafo.
 * Esta clase actúa como la arista de la estructura, vinculando un nodo de origen 
 * con uno de destino y asignándoles un peso específico (por ejemplo, fuerza de interacción)
 * @author karlg
 */
public class Interaccion {
    private Proteina origen;
    private Proteina destino;
    private double peso;
    /**
     * Constructor para crear una nueva interacción entre dos proteínas.
     * * @param origen  La proteína desde la cual parte la interacción.
     * @param destino La proteína hacia la cual se dirige la interacción.
     * @param peso    Valor numérico que representa la intensidad o probabilidad de la interacción.
     */
    public Interaccion(Proteina origen, Proteina destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }
    /**
     * Obtiene el valor del peso asociado a esta interacción.
     * @return El peso de la conexión como un valor {@code double}.
     */
    public double getPeso() { return peso; }
    /**
     * Obtiene la proteína que actúa como punto de partida de la interacción.
     * @return El objeto {@link Proteina} de origen.
     */
    public Proteina getOrigen() { return origen; }
    
    public Proteina getDestino() { return destino; }
}

