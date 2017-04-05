package fssTFIDF;

import java.io.File;


import data.Data;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class MainTFIDF {

	public static void main(String[] args) throws Exception {
		// Si hay mas de 1 parámetro mostramos error y si no hay ninguno también
		if (args.length > 1) { //si hay más de 1 parámetro
			System.out.println("Hay demasiados parámetros, solo es necesario 1");
		} else if (args.length == 0) { //si no hay parámetros      
			System.out.println("Escriba como argumentos la dirección del fichero que queremos aplicar el filtro BOWTFIDF");
		} else {
			Data dataClass = new Data();
			// Cargamos el fichero para aplicarle el filtro
			Instances data = dataClass.loadFile(args[0]);
			// En caso de que no se trate de un archivo test
			if (!args[0].contains("test")) {
				// Seleccionamos la columna de los datos a estimar (la que se llame clase en este caso)
				int clase = data.attribute("clase").index();
				data.setClassIndex(clase);
			}
			
			Instances dataFiltered = null;
			
			// Aplicamos el filtro StringToWordVector
			dataFiltered = Tfidf.getTfidf().useTFIDFFilter(data);
			
			String varSubstring= args[0];
			varSubstring = varSubstring.substring(0, varSubstring.length() - 5);

			// Creamos un fichero ARFF con las nuevas instancias en representación BOW_TFIDF
			ArffSaver s= new ArffSaver();
			s.setInstances(dataFiltered);
			s.setFile(new File(varSubstring + "BOW_Tfidf.arff"));
			s.writeBatch();
		}
	}

}

