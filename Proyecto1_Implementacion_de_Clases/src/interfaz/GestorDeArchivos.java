/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author karlg
 */
public class GestorDeArchivos {

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

