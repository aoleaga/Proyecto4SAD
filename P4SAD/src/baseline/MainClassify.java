package baseline;

import weka.core.Instances;

public class MainClassify {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FileManager manager = FileManager.getFileManager();
		NaiveBayesClassifier nb = new NaiveBayesClassifier();
		
		Instances trainData=manager.loadData(args[0]);
		Instances devData=manager.loadData(args[1]);
		Instances testData=manager.loadData(args[2]);
		
		String pathModel = args[3];
		
		boolean[] pBestOptions= new boolean[2];
		
		if(args[4].equals("true")){
			pBestOptions[0] = true;
		}
		else if(args[4].equals("false")){
			pBestOptions[0] = false;
		}
		
		if(args[5].equals("true")){
			pBestOptions[1] = true;
		}
		else if(args[5].equals("false")){
			pBestOptions[1] = false;
		}
		
		System.out.println("Use Kernel Esitmator: "+pBestOptions[0]);
		System.out.println("Use Supervised Discretization: "+pBestOptions[1]);
		
		Instances joinedTrainDev = manager.joinTrainDev(trainData, devData);
		
		System.out.println();
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println("================TEST SECTION=====================");
		System.out.println("=================================================");
		System.out.println("=================================================");
		System.out.println();
		
		nb.predictClass(joinedTrainDev, testData, pathModel, pBestOptions);

	}

}
