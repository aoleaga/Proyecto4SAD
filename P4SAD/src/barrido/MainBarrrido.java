package barrido;

import data.Data;
import weka.core.Instances;

public class MainBarrrido {

	public static void main(String[] args) throws Exception {
		// Si hay mas de 2 parámetro mostramos error y si no hay ninguno también
		if (args.length > 2) { //si hay más de 2 parámetros
		System.out.println("Hay demasiados parámetros");
		} else if (args.length == 0) { //si no hay parámetros      
			System.out.println("Escriba la dirección del archivo a analizar");
		} else {
		// TODO Auto-generated method stub
		Data dataClass = new Data();
		Instances train = dataClass.loadFile(args[0]);
		Instances dev = dataClass.loadFile(args[1]);
		
		int clase = train.attribute("clase").index();
		train.setClassIndex(clase);
		dev.setClassIndex(clase);

		Classifier classifier = new Classifier();
		classifier.classifyAndGetBestFMeasure(train, dev);	
	
		}
	}
}
