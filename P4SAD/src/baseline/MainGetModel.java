package baseline;

import weka.core.Instances;

public class MainGetModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean[] bestOptions= new boolean[2];
		boolean[] bestNaiveOptions; 
		bestOptions[0]=true;
		bestOptions[1]=true;
		
		FileManager manager = FileManager.getFileManager();
		
		Instances trainData=manager.loadData(args[0]);
		Instances devData=manager.loadData(args[1]);
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
	}

}
