package baseline;

import java.util.ArrayList;

import baseline.filters.DiscretizeAttributes;
import baseline.filters.NominalBinary;
import baseline.filters.NormalizeAttributes;
import baseline.filters.RandomizeInstances;
import baseline.filters.SelectAttributes;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.neighboursearch.BallTree;
import weka.core.neighboursearch.CoverTree;
import weka.core.neighboursearch.KDTree;
import weka.core.neighboursearch.LinearNNSearch;

public class Main {

	public static void main(String[] args) throws Exception{
		

		
		boolean[] bestOptions= new boolean[2];
		boolean[] bestNaiveOptions; 
		bestOptions[0]=true;
		bestOptions[1]=true;
		
		FileManager manager = FileManager.getFileManager();
		
		Instances trainData=manager.loadData(args[0]);
		Instances devData=manager.loadData(args[1]);
		Instances testData=manager.loadData(args[2]);
		Instances joinedTrainDev = manager.joinTrainDev(trainData, devData);
		
		
		Instances[] splitData = manager.splitTrainDev(joinedTrainDev, 0.7);
		Instances evaluateTrainData = splitData[0];
		Instances evaluateDevData = splitData[1];
		//Instances filteredEvaluationData[] = applyFilters(evaluateTrainData, evaluateDevData, testData);
		
		NaiveBayesClassifier nb=new NaiveBayesClassifier();
		
		int minorityClass = FileManager.getFileManager().getMinorityClass(trainData);
		
		System.out.println();
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println("================TRAINING SECTION=================");
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println();

		bestNaiveOptions=nb.trainClassifier(evaluateTrainData, evaluateDevData, minorityClass);
		
		System.out.println();
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println("================EVALUATE SECTION=================");
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println();
		
		nb.evaluateModel(trainData, devData, minorityClass, bestNaiveOptions);
		//nb.evaluateModel(evaluateTrainData, evaluateDevData, minorityClass, bestNaiveOptions);
		//nb.evaluateModel(joinedTrainDev, testData, minorityClass, bestNaiveOptions);
		//nb.evaluateModel(filteredEvaluationData[0], filteredEvaluationData[1], minorityClass, bestNaiveOptions);
		
		System.out.println();
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println("================TEST SECTION=====================");
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println();
		//Instances evaluationData= filteredEvaluationData[2];
		//nb.predictClass(joinedTrainDev, testData, bestNaiveOptions);
		//nb.predictClass(joinedTrainDev, evaluationData, bestNaiveOptions);
		
	}
	
}
	
	
	
