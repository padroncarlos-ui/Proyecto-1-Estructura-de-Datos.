/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *Representa una unidad proteica dentro de una red de interacciones biológicas.
 * En la estructura de datos del proyecto, esta clase actúa como un nodo del grafo.
 * Incluye metadatos como el nombre de la proteína y su clasificación como 'hub' 
 * (nodo de alta conectividad).
 * @author karlg
 */
public class Proteina {
    private String nombre;
    private boolean esHub;
    /**
     * Constructor para inicializar una proteína con su identificador o nombre.
     * Por defecto, la proteína no se considera un 'hub' hasta que el análisis lo determine.
     * * @param nombre El nombre o ID único de la proteína.
     */
    public Proteina(String nombre) {
        this.nombre = nombre;
        this.esHub = false;
    }
    /**
     * Obtiene el nombre identificativo de la proteína.
     * @return Una cadena de texto con el nombre de la proteína.
     */
    public String getNombre() { return nombre; }
    /**
     * Permite modificar o asignar un nuevo nombre a la proteína.
     * @param nombre El nuevo nombre de la proteína.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /**
     * Indica si la proteína ha sido identificada como un 'hub' en la red.
     * Un hub suele representar una proteína con un número elevado de interacciones.
     * * @return {@code true} si es un hub, {@code false} en caso contrario.
     */
    public boolean isEsHub() { return esHub; }
    /**
     * Define si la proteína debe ser clasificada como un nodo central (hub).
     * @param esHub Valor booleano para establecer el estado de hub.
     */
    public void setEsHub(boolean esHub) { this.esHub = esHub; }
    /**
     * Define si la proteína debe ser clasificada como un nodo central (hub).
     * @param esHub Valor booleano para establecer el estado de hub.
     */
    @Override
    public String toString() { return nombre; }
  }


