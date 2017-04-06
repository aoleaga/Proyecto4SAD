package barrido;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;

public class Classifier {
	
	public Classifier(){
	}
	
	public void classifyAndGetBestFMeasure(Instances train, Instances dev) throws Exception{
		double fMeasure=0;
		double ridge = 0;
		double ridgeOpt = 0;
		int maxitsOpt = 0;
		int claseMin = 0;
		
		claseMin = this.obtenerClaseMinoritaria(train);
		
		for(int i = 0; i < 100; i++){
			System.out.println("------------------------- Ridge = " + ridge + " ----------------------");
			Logistic estimador = new Logistic();
			for(int its = -1; its < 10; its++){
				System.out.println("............... MaxIts = " + its + " ................");
					estimador.setMaxIts(its);
					estimador.setRidge(ridge);
				
					estimador.buildClassifier(train);
		        
					// evaluate classifier and print some statistics
					Evaluation evaluator = new Evaluation(train);
					evaluator.evaluateModel(estimador, dev);
			
					// hay que usar el fmeasure de la clase minoritaria
					System.out.println(evaluator.fMeasure(claseMin));
					
					System.out.println(evaluator.toSummaryString());
					
					System.out.println(evaluator.toClassDetailsString());

					if(evaluator.fMeasure(0) > fMeasure){
						fMeasure = evaluator.fMeasure(claseMin);
						ridgeOpt = estimador.getRidge();
						maxitsOpt = its;
					}
			}
			ridge = ridge + 0.006;
		}
		
		System.out.println("El mejor resultado se alcanza con:\n      ridge: " + ridgeOpt + " \n      maxits: " + maxitsOpt + " \nOpteniendo un F-Measure en la clase minoritaria de: " + fMeasure);
	}

	
	public int obtenerClaseMinoritaria(Instances data){
		int clase = 0;
		int claseMin = 0;
		for (int i = 0; i < data.numInstances(); i++){
			if (data.instance(i).classValue()==0.0){
				clase++;
			}
		}
		if ((data.numInstances() - clase)>clase){
			claseMin = 1;
		}
		return claseMin;
	}
}
