package getModel;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

public class LogisticClassifier {
	private static LogisticClassifier miLogisticClassifier = new LogisticClassifier();
	final Logistic estimador;
	final int folds = 10;
	final Random random = new Random(1);
	
	private LogisticClassifier (){	
		estimador = new Logistic();
	}
	
	public static LogisticClassifier getLogisticClassifier(){
		return miLogisticClassifier;
	}
	
	// Unimos dos Datasets
	public Instances mergeDataSets(Instances data1, Instances data2){
		for (int i = data2.numInstances() - 1; i >= 0; i--){
			data1.add(data2.instance(i));
		}
		return data1;
	}
	
	// Evaluacion Cross Validation
	public void evaluateCrossValidation(Instances traindev){
		try {
			Evaluation evaluator = new Evaluation(traindev);
			evaluator.crossValidateModel(estimador, traindev, folds, random);
			System.out.println("********* 10-FOLD CROSS VALIDATION *********");
			System.out.println(evaluator.toClassDetailsString());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	// Evaluacion no honesta
	public void trainingSet(Instances traindev){
		try {
			// Construimos el clasificador con el train
			estimador.buildClassifier(traindev);
 
			// evaluate classifier and print some statistics
			Evaluation evaluator = new Evaluation(traindev);
			evaluator.evaluateModel(estimador, traindev);
			System.out.println("********* EVALUACIÓN NO HONESTA *********");
			System.out.println(evaluator.toClassDetailsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Evaluacion Hold Out
	public void evaluateHoldOut(Instances data){
		final double percentage = 70;
		// Barajamos las instancias de forma aleatoria
		data.randomize(new Random(1));
		//  filter that removes a given percentage of a dataset
		RemovePercentage rmvp = new RemovePercentage();
		
		try {
			// Set whether selected values should be removed or kept. If true the selected values are kept and unselected values are deleted.
			rmvp.setInvertSelection(true);
			rmvp.setPercentage(percentage);
			rmvp.setInputFormat(data);
			Instances trainDataSet = Filter.useFilter(data, rmvp);
		
			rmvp = new RemovePercentage();
			rmvp.setPercentage(percentage);
			rmvp.setInputFormat(data);
			Instances testDataSet = Filter.useFilter(data, rmvp);
        
			// Construimos el clasificador con el train
			estimador.buildClassifier(trainDataSet);
        
			// evaluate classifier and print some statistics
			Evaluation evaluator = new Evaluation(trainDataSet);
			evaluator.evaluateModel(estimador, testDataSet);
			System.out.println("********* HOLD OUT (70% = TRAIN, 30% = TEST) *********");
			System.out.println(evaluator.toClassDetailsString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setRidge(Double ridge){
		estimador.setRidge(ridge);
	}
	
	public void setMaxIts(int its){
		estimador.setMaxIts(its);;
	}
	
	// Guardar el modelo en el path indicado
	public void saveModel(String path){
		try {
			SerializationHelper.write(path, estimador );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}