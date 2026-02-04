/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author karlg
 */
public class Interaccion {
    private Proteina origen;
    private Proteina destino;
    private double peso;

    public Interaccion(Proteina origen, Proteina destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public double getPeso() { return peso; }
    public Proteina getOrigen() { return origen; }
    public Proteina getDestino() { return destino; }
}

