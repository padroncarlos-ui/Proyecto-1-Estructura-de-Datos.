/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author karlg
 */
public class Proteina {
    private String nombre;
    private boolean esHub;

    public Proteina(String nombre) {
        this.nombre = nombre;
        this.esHub = false;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public boolean isEsHub() { return esHub; }
    public void setEsHub(boolean esHub) { this.esHub = esHub; }

    @Override
    public String toString() { return nombre; }
  }


