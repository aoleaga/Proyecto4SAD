package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

public class Data {
	
	public Data(){
	}

	public Instances loadFile(String path){
		// Abrimos el fichero
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar path del fichero de datos:" + path);
		}
		// Cargamos las instancias
		Instances data = null;
		try {
			data = new Instances(reader);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: " + path);
		}
		// Cerramos el fichero
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al cerrar el fichero");
		}
			
		return data;
				
	}
}
	