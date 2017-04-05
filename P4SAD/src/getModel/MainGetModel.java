package getModel;

import data.Data;
import weka.core.Instances;

public class MainGetModel {

	public static void main(String[] args) throws Exception {
		// Si hay mas de 2 parámetro mostramos error y si no hay ninguno también
		if (args.length > 2) { //si hay más de 2 parámetros
		System.out.println("Hay demasiados parámetros");
		} else if (args.length == 0) { //si no hay parámetros      
			System.out.println("Escriba la dirección del archivo a analizar");
		} else {

		Data dataClass = new Data();
		Instances train = dataClass.loadFile(args[0]);
		Instances dev = dataClass.loadFile(args[1]);
		
		// Establecemos los parametros optimos obtenidos en el barrido
		LogisticClassifier.getLogisticClassifier().setMaxIts(3);
		LogisticClassifier.getLogisticClassifier().setRidge(0.00);
		
		// Unimos los dos Datasets
		Instances traindev = LogisticClassifier.getLogisticClassifier().mergeDataSets(train, dev);
		
		int clase = traindev.attribute("clase").index();
		traindev.setClassIndex(clase);
		
		LogisticClassifier.getLogisticClassifier().trainingSet(traindev);
		LogisticClassifier.getLogisticClassifier().evaluateHoldOut(traindev);
		LogisticClassifier.getLogisticClassifier().evaluateCrossValidation(traindev);
		
		String[] varSubstrings = args[0].split("/");
		String varSubstring="";
		for(int e=0;e<varSubstrings.length-1;e++ ){
			varSubstring =varSubstring+"/"+varSubstrings[e];
		}

		// Guardamos el modelo resultante en un fichero binario
		LogisticClassifier.getLogisticClassifier().saveModel(varSubstring + "/Logistic.model");
		}
	}
}
