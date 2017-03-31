package baseline;

import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.Instances;

public class NaiveBayesClassifier {
	
	public NaiveBayesClassifier(){}

	/**
	 * Receives two set of instances for a Hold-out training with naive Bayes
	 * @param pTrainData
	 * @param pDevData
	 * @param pMinorityClass
	 * @return
	 */
	public boolean[] trainClassifier(Instances pTrainData,Instances pDevData,int pMinorityClass)
	{
		NaiveBayes clasificador = new NaiveBayes();
		boolean[] bestOptions = new boolean[2];
		double bestFmeasure = 0;
		
		bestOptions[0] = true;
		bestOptions[1] = true;
		
		Evaluation evaluator=null,bestEvaluator=null;
		
		try {
			
			
			clasificador.setUseKernelEstimator(true);
			
			System.out.println("Starting Naive Bayes Training");
			System.out.println();
			
			for(int i=0;i<2;i++)
			{
				System.out.print("*");
				clasificador.setUseSupervisedDiscretization(true);
				for(int j=0;j<2;j++)
				{
					clasificador.buildClassifier(pTrainData);
					evaluator = new Evaluation(pTrainData);
					evaluator.evaluateModel(clasificador, pDevData);
				
					if(bestFmeasure<evaluator.fMeasure(pMinorityClass))
					{
						bestFmeasure = evaluator.fMeasure(pMinorityClass);
						bestEvaluator = evaluator;
						boolean option1,option2;
						if(i==0) option1 = true; else option1 = false;
						if(j==0) option2 = true; else option2 = false;
						bestOptions[0] = option1;
						bestOptions[1] = option2;
					}
					
					clasificador.setUseSupervisedDiscretization(false);
				}
				
				clasificador.setUseKernelEstimator(false);
			}
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//Show & Save results
			System.out.println("Results=====");
			System.out.println("Best options:");
			System.out.println("Use Kernel Estimator: " + bestOptions[0]);
			System.out.println("Use Supervised Discretization: "+bestOptions[1]);
			System.out.println(bestEvaluator.toSummaryString());
			System.out.println(bestEvaluator.toClassDetailsString());
			System.out.println(bestEvaluator.toMatrixString());
			FileManager.getFileManager().SaveResults(bestEvaluator.toClassDetailsString(),bestEvaluator.toSummaryString(),bestEvaluator.toMatrixString(),bestEvaluator.fMeasure(pMinorityClass),"NaiveBayes_TrainResults","Use Kernel Estimator: " + bestOptions[0]+" "+"\nUse Supervised Discretization: "+bestOptions[1]);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bestOptions;
	}
	
	
	/**
	 * Evaluates the Naive Bayes model
	 * @param pTrain
	 * @param pTest
	 * @param pMinorityClass
	 * @param pBestOptions
	 */
	public void evaluateModel(Instances pTrain,Instances pTest,int pMinorityClass, boolean[] pBestOptions){
		System.out.println("Starting Naive Bayes Evaluation");
		System.out.println();
		NaiveBayes clasificador = new NaiveBayes();
		NaiveBayes clasificador1 = new NaiveBayes();
		NaiveBayes clasificador2 = new NaiveBayes();
		
		String pSR=null;
		String pDR=null;
		String pS10=null;
		String pMR = null;
		String pD10=null;
		String pM10=null;
		String pSH=null;
		String pDH = null;
		String pMH=null;
		double pF10=0.0;
		double pFH=0.0;
		double pFR=0.0;
		
		try {
			Evaluation evaluador = new Evaluation(pTrain);
			evaluador.crossValidateModel(clasificador, pTest, 10, new Random(42));
			System.out.println("Results Evaluate Model 10-fold crossvaliadtion =====");
			pS10=evaluador.toSummaryString();
			pD10=evaluador.toClassDetailsString();
			pM10=evaluador.toMatrixString();
			pF10=evaluador.fMeasure(pMinorityClass);
			System.out.println(evaluador.toSummaryString());
			System.out.println(evaluador.toClassDetailsString());
			System.out.println(evaluador.toMatrixString());
			//FileManager.getFileManager().SaveResults(evaluador.toClassDetailsString(),evaluador.toSummaryString(),evaluador.toMatrixString(),evaluador.fMeasure(pMinorityClass),"NaiveBayes_EvaluationResults","");
			
			clasificador.setUseKernelEstimator(pBestOptions[0]);
			clasificador.setUseSupervisedDiscretization(pBestOptions[1]);
			clasificador.buildClassifier(pTrain);
			try {
				Evaluation evaluador1 = new Evaluation(pTrain);
				evaluador1.evaluateModel(clasificador, pTrain);
				System.out.println("Results Evaluate Model resubstitution error =====");
				pSR=evaluador1.toSummaryString();
				pDR=evaluador1.toClassDetailsString();
				pMR=evaluador1.toMatrixString();
				pFR=evaluador1.fMeasure(pMinorityClass);
				System.out.println(evaluador1.toSummaryString());
				System.out.println(evaluador1.toMatrixString());
				System.out.println(evaluador1.toClassDetailsString());
				System.out.println(evaluador1.weightedRecall());
				System.out.println(evaluador1.weightedPrecision());
				System.out.println(evaluador1.weightedFMeasure());
				//FileManager.getFileManager().SaveResults(evaluador.toSummaryString(),evaluador.toSummaryString(),evaluador.toMatrixString(),evaluador.fMeasure(pMinorityClass),"NaiveBayes_EvaluationResults","");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Se produjo un error al evaluar el modelo");
		}
		
		try {
			//Show & Save results
			Evaluation evaluador2 = new Evaluation(pTrain);
			evaluador2.evaluateModel(clasificador, pTest);
			System.out.println("Results Evaluate Model Hold-Out =====");
			pSH = evaluador2.toSummaryString();
			pDH=evaluador2.toClassDetailsString();
			pMH=evaluador2.toMatrixString();
			pFH=evaluador2.fMeasure(pMinorityClass);
			System.out.println(evaluador2.toSummaryString());
			System.out.println(evaluador2.toClassDetailsString());
			System.out.println(evaluador2.toMatrixString());
			//FileManager.getFileManager().SaveResults2(pS10,pD10,pM10,pSR,pDR,pMR,pSH,pDH,pMH,evaluador.toClassDetailsString(),evaluador.toSummaryString(),evaluador.toMatrixString(),evaluador.fMeasure(pMinorityClass),"NaiveBayes_EvaluationResults","");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileManager.getFileManager().SaveResults2(pS10,pD10,pM10,pSR,pDR,pMR,pSH,pDH,pMH,pF10,pFR,pFH,"NaiveBayes_EvaluationResults","");
	}
	
	
	/**
	 * Predicts the class of the Test File with Naive Bayes
	 * @param pTrain
	 * @param pTest
	 * @param pBestOptions
	 */
	public void predictClass(Instances pTrain,Instances pTest,boolean[] pBestOptions){
		
		NaiveBayes clasificador = new NaiveBayes();
		Evaluation evaluador = null;
		System.out.println("Starting Naive Bayes Predictions");
		try {
			clasificador.setUseKernelEstimator(pBestOptions[0]);
			clasificador.setUseSupervisedDiscretization(pBestOptions[1]);
			//System.out.println(pTrain.numAttributes());
			//System.out.println(pTrain.numInstances());
			//System.out.println(pTest.numAttributes());
			//System.out.println(pTest.numInstances());
			//System.out.println(pTrain);
			//System.out.println(pTest);
			clasificador.buildClassifier(pTrain);
			
			evaluador = new Evaluation(pTrain);
			System.out.println(pTrain.numAttributes());
			System.out.println(pTrain.numInstances());
			System.out.println(pTest.numAttributes());
			System.out.println(pTest.numInstances());
			evaluador.evaluateModel(clasificador, pTest);
						
			double predictions[] = new double[pTest.numInstances()];
			for (int i = 0; i < pTest.numInstances(); i++) {
					predictions[i] = evaluador.evaluateModelOnceAndRecordPrediction(clasificador, pTest.instance(i));
			}
			FileManager.getFileManager().savePredictions(predictions, "NaiveBayes");
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Se produjo un error al predecir el modelo");
		}
	}
}
