/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *Clase responsable de la persistencia de datos del sistema. 
 * Proporciona mecanismos para serializar y deserializar objetos de tipo {@link Grafo},
 * permitiendo su almacenamiento en el sistema de archivos.
 * @author karlg
 */
public class GestorDeArchivos {
    /**
     * Serializa el objeto Grafo y lo guarda en un archivo binario en la ruta especificada.
     * Utiliza un bloque try-with-resources para asegurar el cierre automático de los flujos.
     * * @param path Ruta absoluta o relativa donde se creará el archivo.
     * @param g    Instancia de {@link Grafo} que se desea almacenar. Debe implementar Serializable.
     */
    public void guardarArchivo(String path, Grafo g) {
        try (FileOutputStream fileOut = new FileOutputStream(path);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            out.writeObject(g);
            System.out.println("Grafo guardado exitosamente en: " + path);
            
        } catch (IOException i) {
            System.err.println("Error al guardar el archivo: " + i.getMessage());
            i.printStackTrace();
        }
    }
    /**
     * Recupera un objeto Grafo desde un archivo binario mediante deserialización.
     * Si ocurre un error durante la lectura o la clase no es compatible, 
     * se devuelve una nueva instancia vacía de Grafo para evitar punteros nulos.
     * * @param path Ruta del archivo desde donde se cargará el grafo.
     * @return El objeto {@link Grafo} recuperado o una nueva instancia en caso de error.
     */

    public Grafo cargarArchivo(String path) {
        Grafo grafoCargado = null;
        try (FileInputStream fileIn = new FileInputStream(path);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            grafoCargado = (Grafo) in.readObject();
            System.out.println("Grafo cargado exitosamente desde: " + path);
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
            return new Grafo(); 
        }
        return grafoCargado;
    }
}

