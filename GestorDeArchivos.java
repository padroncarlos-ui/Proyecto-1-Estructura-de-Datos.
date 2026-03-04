/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_EDD1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Clase responsable de manejar tanto la carga de los archivos .csv y
 * guardado, permitiendo su almacenamiento en el sistema de archivos.
 * 
 * @author karlg
 */
public class GestorDeArchivos {
    private Interfaz interfaz;
    public GestorDeArchivos(Interfaz interfaz) {
        this.interfaz = interfaz;
    }
    
    /**
     * Serializa el objeto Grafo y lo guarda en un archivo binario en la ruta especificada.
     * Utiliza un bloque try-with-resources para asegurar el cierre automático de los flujos.
     * * @param path Ruta absoluta o relativa donde se creará el archivo.
     */
    public void guardarArchivo(String path, Grafo grafoLogico){

        try (PrintWriter escritor = new PrintWriter(new java.io.FileWriter(path))) {
            //Obtenemos todas las proteínas
            Proteina[] lista = grafoLogico.getProteinas(); 
            int cant = grafoLogico.getCantidadProteinas();
            
            //Recorrre cada proteína y sus interacciones
            for (int i = 0; i < cant; i++) {
                Interaccion[] inters = grafoLogico.getAdyacentes(i); //Lista de conexiones de la proteína actual.
                int numInters = grafoLogico.getNumAdyacentes(i); //Obtiene las conexiones de la proteína.
                
                if (inters != null) { // Verifica que la lista de interacciones no sea nula.
                    for (int j = 0; j < numInters; j++) { //Recorre cada una de las interacciones de esa proteína.
                        
                        Interaccion inter = inters[j]; //Extrae el objeto interacción actual.

                        if (inter != null && inter.getOrigen() != null && inter.getDestino() != null) { //Verifica que todas las interacciones y nodos existan correctamente.
                            //Evita que las interacciones se repitan pero a la inversa, ordenandolo de forma ascendente una vez
                            if (inter.getOrigen().getNombre().compareTo(inter.getDestino().getNombre()) < 0) { //Guarda las interacciones de forma ascendente para evitar duplicaciones.
                                // Escribe una línea en el archivo con el formato:
                                escritor.println(inter.getOrigen().getNombre() + "," +
                                inter.getDestino().getNombre() + "," +
                                (int)inter.getPeso());
                            }
                        } 
                    } 
                }
            }
            if (interfaz != null){
                interfaz.EscribirInfo("Archivo guardado con éxito.");
            }

        }catch(IOException e){
            if (interfaz != null){
                interfaz.EscribirInfo("Error al intentar guardar el archivo.");
            }
        }        
    }

    /**
     * Método encargado de la importación de los archivos .csv, trabaja en 
     * conjunto con la lógica del boton de cargar archivo para leer solamente
     * los archivos correspondientes con el formato para que el grafo funcione. 
     * * @param path Ruta del archivo desde donde se cargará el grafo.
     * @param ruta
     * @param grafoLogico
     * @return El objeto {@link Grafo} recuperado o una nueva instancia en caso de error.
     * @throws java.lang.Exception
     */
    public boolean cargarArchivo(String ruta, Grafo grafoLogico) throws Exception{
        File archivo = new File(ruta); // Crea un objeto de tipo File que apunta a la ruta del disco donde pueda estar el archivo.
        
        //Abre el archivo y lo lee línea por línea.
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) { 
            String linea; //Variable temporal para almacenar el texto de cada línea.
            
            //Se ejecuta mientras existan líneas de texto dentro del archivo.
            while ((linea = br.readLine()) != null) {
                
                String[] partes = linea.split(","); //Divide la línea en partes cada vez que encuentra una coma.
                if (partes.length == 3) { //Verifica que la línea tenga 3 datos, las proteínas A y B y el peso.
                    String p1 = partes[0].trim(); //Obtiene el nombre de la primera proteína y quita espacios extra.
                    String p2 = partes[1].trim(); //Obtiene el nombre de la segunda proteína y quita espacios extra.
                    double peso = Double.parseDouble(partes[2].trim()); //Convierte el texto del peso a un número decimal.

                    //Crea los objetos Proteina.
                    Proteina prot1 = new Proteina(p1);
                    Proteina prot2 = new Proteina(p2);

                    //Inserta los objetos proteína en la clase Grafo.
                    grafoLogico.addProteina(prot1);
                    grafoLogico.addProteina(prot2);
                    grafoLogico.agregarInteraccion(p1, p2, peso);  //Establece la conexión entre las proteínas y su peso.   
            }
        }
            return true;

    }catch (Exception e) {     
        if (interfaz != null) {
            interfaz.EscribirInfo("Error al leer el archivo");
        }
        return false;
    }    
  }
}
        
