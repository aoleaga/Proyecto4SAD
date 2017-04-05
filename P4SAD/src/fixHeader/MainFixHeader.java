package fixHeader;

import java.io.File;

import data.Data;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class MainFixHeader {

	public static void main(String[] args) throws Exception {
		// Si hay mas de 2 parámetro mostramos error y si no hay ninguno también
		if (args.length > 2) { //si hay más de 2 parámetros
			System.out.println("Hay demasiados parámetros, solo son necesarios 2");
		} else if (args.length == 0) { //si no hay parámetros      
			System.out.println("Escriba como argumentos la dirección del fichero que tiene el diccionario a usar y el fichero que queremos que tenga el mismo diccionario");
		} else {
			Data dataClass = new Data();
			// Cargamos los tres ficheros para aplicarles el filtro
			Instances data = dataClass.loadFile(args[0]);
			Instances dataToFix = dataClass.loadFile(args[1]);
				
			int clase = data.attribute("clase").index();
			
			data.setClassIndex(clase);
			dataToFix.setClassIndex(clase);
			
			Instances dataFixed = data;
					
			// Eliminamos todos las instancias menos el numero de instancias que necesitemos
			for (int num = dataFixed.numInstances() - 1; num >= dataToFix.numInstances(); num--){
				dataFixed.remove(num);
			}
			
			// Ponemos todos los valores de las instancias a 0
			for (int numIns = 0; numIns < dataToFix.numInstances(); numIns++){
				dataFixed.instance(numIns).setValue(0, dataToFix.instance(numIns).value(0));
				for (int atrib = 1; atrib < dataFixed.numAttributes(); atrib++ ){
					dataFixed.instance(numIns).setValue(atrib, 0.0);
				}
			}
			
			for (int i = 0; i < dataToFix.numInstances(); i++){
				for (int e = 0; e < dataToFix.numAttributes(); e++){
					if (dataToFix.instance(i).value(e)!=0.0){
						// SEARH = 1 --> TEST , SEARCH = 0 --> TRAIN, DEV
						for (int search = 1;search < data.numAttributes(); search++){
							if (data.attribute(search).name().equals(dataToFix.instance(i).attribute(e).name())){
								dataFixed.instance(i).setValue(search, dataToFix.instance(i).value(e));
							}
						}
					}
				}
			}
			
			
			String varSubstring= args[1];
			varSubstring = varSubstring.substring(0, varSubstring.length() - 5);

			// Creamos un fichero ARFF con las nuevas instancias en representación BOW
			ArffSaver s= new ArffSaver();
			s.setInstances(dataFixed);
			s.setFile(new File(varSubstring + "Fixed.arff"));
			s.writeBatch();
		}
	}
}
