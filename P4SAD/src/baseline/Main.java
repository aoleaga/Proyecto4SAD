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
		Instances filteredEvaluationData[] = applyFilters(evaluateTrainData, evaluateDevData, testData);
		
		NaiveBayesClassifier nb=new NaiveBayesClassifier();
		
		int minorityClass = FileManager.getFileManager().getMinorityClass(trainData);
		
		System.out.println();
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println("================TRAINING SECTION=================");
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println();

		bestNaiveOptions=nb.trainClassifier(trainData, devData, minorityClass);
		
		System.out.println();
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println("================EVALUATE SECTION=================");
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println();
		
		nb.evaluateModel(filteredEvaluationData[0], filteredEvaluationData[1], minorityClass, bestNaiveOptions);
		
		System.out.println();
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println("================TEST SECTION=====================");
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println();
		Instances evaluationData= filteredEvaluationData[2];
		Instances joinedTrainDevTest = manager.joinTrainDev(joinedTrainDev, evaluationData);
		nb.predictClass(joinedTrainDev, filteredEvaluationData[2], bestNaiveOptions);
		//nb.predictClass(joinedTrainDev, joinedTrainDevTest, bestNaiveOptions);
}
	
private static Instances[] applyFilters(Instances pTrain,Instances pDev,Instances pTest){
			
			Instances[] results = new Instances[3];
			
			//Preprocess
			RandomizeInstances randomInstances = new RandomizeInstances();
			SelectAttributes selectAttributes = new SelectAttributes();
			// RemoveUselessAttributes removeUseless = new RemoveUselessAttributes(); -- Not Used
			NominalBinary nominalToBinary = new NominalBinary();
			DiscretizeAttributes discretize = new DiscretizeAttributes();
			NormalizeAttributes normalize = new NormalizeAttributes();
			
			Instances filteredTrainData=null,filteredDevData=null,filteredTestData=null;
			
			//---Batch Filtering
			
			System.out.println("");
			System.out.println("Applying selected filters");
			System.out.println("-------------------------");
			
			filteredTrainData = pTrain;
			filteredDevData = pDev;
			filteredTestData = pTest;
			
			//Attribute Selection
			
			System.out.println("Attribute Selection Filter");
			selectAttributes.setInputFormat(filteredTrainData);
			filteredTrainData = selectAttributes.filter(filteredTrainData);
			filteredDevData = selectAttributes.filter(filteredDevData);
			filteredTestData = selectAttributes.filter(filteredTestData);
			System.out.println("Done");
			
			//Remove Useless - Not used
			/*
				System.out.println("Remove Useless Filter");
				removeUseless.setInputFormat(filteredTrainData);
				filteredTrainData = removeUseless.filter(filteredTrainData);
				filteredDevData = removeUseless.filter(filteredDevData);
				System.out.println("Done");
			*/
			
			//Discretize

				System.out.println("Discretize Filter");
				discretize.setInputFormat(filteredTrainData);
				filteredTrainData = discretize.filter(filteredTrainData);
				filteredDevData = discretize.filter(filteredDevData);
				filteredTestData = discretize.filter(filteredTestData);
				System.out.println("Done");
			
			//Randomize
				System.out.println("Randomize Filter");
				filteredTrainData = randomInstances.filter(filteredTrainData);
				System.out.println("Done");
			
			//Normalize
				System.out.println("Normalize Filter");
				normalize.setInputFormat(filteredTrainData);
				filteredTrainData = normalize.filter(filteredTrainData);
				filteredDevData = normalize.filter(filteredDevData);
				filteredTestData = normalize.filter(filteredTestData);
				System.out.println("Done");

			
			//-- End Batch Filtering
			System.out.println("");
			System.out.println("Filters Done");
			System.out.println("");
			
			results[0] = filteredTrainData;
			results[1] = filteredDevData;
			results[2] = filteredTestData;
			
			return results;
}
	
}
	
	
	

