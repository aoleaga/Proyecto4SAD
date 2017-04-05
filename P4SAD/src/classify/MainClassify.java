package classify;

import java.io.FileWriter;

import data.Data;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class MainClassify {
		
	public static void main(String[] args) throws Exception {
		// Si hay mas de 2 parámetro mostramos error y si no hay ninguno también
		if (args.length > 2) { //si hay más de 2 parámetros
		System.out.println("Hay demasiados parámetros");
		} else if (args.length == 0) { //si no hay parámetros      
			System.out.println("Escriba la dirección del archivo test y del modelo");
		} else {
			
			// load unlabeled data
			Data dataClass = new Data();
			Instances unlabeled = dataClass.loadFile(args[0]);
			
			 // set class attribute
			int clase = unlabeled.attribute("clase").index();
			unlabeled.setClassIndex(clase);
			
			// create copy
			Instances labeled = new Instances(unlabeled);
			
			String[] varSubstrings = args[0].split("/");
			String varSubstring="";
			for(int e=0;e<varSubstrings.length-1;e++ ){
				varSubstring =varSubstring+"/"+varSubstrings[e];
				
			}
			
			FileWriter bw = new FileWriter(varSubstring + "/test.predictions.txt");
			bw.write("Logistic Classifier: \n");
			
			Logistic estimador = (Logistic) SerializationHelper.read(args[1]);
			
			// label instances
			for (int i = 0; i < unlabeled.numInstances(); i++) {
				double clsLabel = estimador.classifyInstance(unlabeled.instance(i));
				labeled.instance(i).setClassValue(clsLabel);
				bw.write((i + 1) + " -> " + unlabeled.classAttribute().value((int) clsLabel) + "\n");
			}
			bw.close();
		}
	}
	
}
