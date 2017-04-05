package fssInfoGain;

import java.io.File;

import data.Data;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class MainInfoGain {

	public static void main(String[] args) throws Exception {
		// Si hay mas de 1 parámetro mostramos error y si no hay ninguno también
		if (args.length > 1) { //si hay más de 1 parámetro
			System.out.println("Hay demasiados parámetros, solo es necesario 1");
		} else if (args.length == 0) { //si no hay parámetros      
			System.out.println("Escriba como argumentos la dirección del fichero que queremos aplicar el filtro Info Gain");
		} else {
			Data dataClass = new Data();
			// Cargamos el fichero para aplicarle el filtro
			Instances data = dataClass.loadFile(args[0]);
			
			int clase = data.attribute("clase").index();
			
			data.setClassIndex(clase);
			
			Instances dataFiltered = null;
			
			// Aplicamos el filtro
			dataFiltered = AttributeSelectionFilter.getAttributeSelectionFilter().useAttributeSelectionFilter(data);

			String varSubstring= args[0];
			varSubstring = varSubstring.substring(0, varSubstring.length() - 5);

			// Creamos un fichero ARFF con las nuevas instancias en representación BOW
			ArffSaver s= new ArffSaver();
			s.setInstances(dataFiltered);
			s.setFile(new File(varSubstring + "_FSS_InfoGain.arff"));
			s.writeBatch();
		}
	}

}
