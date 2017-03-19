package fssInfoGain;

import java.io.File;

import arff2bow.Data;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class MainInfoGain {

	public static void main(String[] args) throws Exception {
		// Si hay mas de 3 parámetro mostramos error y si no hay ninguno también
		if (args.length > 3) { //si hay más de 3 parámetro
			System.out.println("Hay demasiados parámetros, solo es necesario 2");
		} else if (args.length == 0) { //si no hay parámetros      
			System.out.println("Escriba como argumentos la dirección del fichero que queremos usar y la dirección donde ubicar el nuevo fichero resultante");
		} else {
			Data dataClass = new Data();
			// Cargamos los tres ficheros para aplicarles el filtro
			Instances data = dataClass.loadFile(args[0]);
			Instances datadev = dataClass.loadFile(args[1]);
			Instances test = dataClass.loadFile(args[2]);
			
			int clase = data.attribute("clase").index();
			
			data.setClassIndex(clase);
			datadev.setClassIndex(clase);
			
			Instances dataTrainFiltered = null;
			Instances dataDevFiltered = null;
			Instances testFiltered = null;
			
			// Aplicamos el filtro
			AttributeSelection aSelection = AttributeSelectionFilter.getAttributeSelectionFilter().getAttributeSelectionFilter(data);
			
			dataTrainFiltered = Filter.useFilter(data,aSelection);
			dataDevFiltered = Filter.useFilter(datadev,aSelection);
			testFiltered = Filter.useFilter(test,aSelection);
			
			String[] varSubstrings = args[0].split("/");
			String varSubstring="";
			for(int e=0;e<varSubstrings.length-1;e++ ){
				varSubstring =varSubstring+"/"+varSubstrings[e];
			}
			
			// Creamos un fichero ARFF con las nuevas instancias en representación BOW
			ArffSaver s= new ArffSaver();
			s.setInstances(dataTrainFiltered);
			s.setFile(new File(varSubstring + "/trainBOW_FSS_InfoGain.arff"));
			s.writeBatch();
			
			s.setInstances(dataDevFiltered);
			s.setFile(new File(varSubstring + "/devBOW_FSS_InfoGain.arff"));
			s.writeBatch();
			
			s.setInstances(testFiltered);
			s.setFile(new File(varSubstring + "/test_blindBOW_FSS_InfoGain.arff"));
			s.writeBatch();
		}
	}

}
