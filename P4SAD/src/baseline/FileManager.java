package baseline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import weka.classifiers.bayes.NaiveBayes;
//import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class FileManager {
	
	private String savePath="";
	private String fileName = "SVM_Model.mdl";
	
	private static FileManager myFileManager = null;
	
	private FileManager(){}
	
	public static FileManager getFileManager(){
		if(myFileManager == null) myFileManager = new FileManager();
		return myFileManager;
	}

	
	/**
	 * Load the information of the file to a Instances variable
	 * @param pFilePath - Path to the data file
	 * @return Instances
	 */
	public Instances loadData(String pFilePath){
		System.out.println("Loading "+pFilePath);
		String filePath = pFilePath;
		FileReader fi = null;
		Instances loadedData = null;
	
	    // Open the file
		try {
			fi= new FileReader(pFilePath);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar path del fichero de datos:"+filePath);
		}
		
		// Load the instances
		try {
			loadedData = new Instances(fi);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: "+filePath);
		}
		
		// Close the file
		try {
			fi.close();
		} catch (IOException e) {
			System.out.println("ERROR: No se pudo cerrar el archivo correctamente.");
		}
		
		//Set class index
		if(loadedData.attribute("clase")!=null){
			loadedData.setClass(loadedData.attribute("clase"));
		}
		else{
			loadedData.setClassIndex(loadedData.numAttributes()-1);
		}
		
		return loadedData;
	}

	
	/**
	 * Changes the class index to the one given.
	 * @param pData - Instances
	 * @param pPosition - Position where the class attribute is
	 * @return The instances with the assigned attribute as class
	 */
	public Instances setClassIndex(Instances pData, int pPosition){
		if(pPosition > pData.numAttributes()-1 && pPosition<0)
		{
			System.out.println("The index value is not correct, the last attribute will be the class index.");
			pData.setClassIndex(pData.numAttributes()-1);
		}
		else
			pData.setClassIndex(pPosition);
		return pData;
	}
	
	
	/**
	 * Given the Instances and a percentage to split into, creates two sets of data
	 * @param pData - Instances
	 * @param pTrainPercentaje - Double
	 * 			Value between 0 and 1
	 * @return Instances[] . Position 0 - The first half of the split
	 *  data. Position 1 - The second half of the split data
	 */
	public Instances[] splitTrainDev(Instances pData,double  pTrainPercentaje){
		
		if(pTrainPercentaje<0 || pTrainPercentaje>1)
		{
			System.out.println("The pertentage given is not correct. The split will be of 70%");
			pTrainPercentaje=0.7;
		}
		
		Instances newData[] = new Instances[2];
		int trainSize = (int) Math.round(pData.numInstances() * pTrainPercentaje);
		int testSize = pData.numInstances() - trainSize;
		
		newData[0] = new Instances(pData, 0, trainSize);
		newData[1] = new Instances(pData, trainSize, testSize);
		
		return newData;
	}
	
	
	/**
	 * 
	 * @param pTrain
	 * @param pDev
	 * @return Joined Instances 
	 */
	public Instances joinTrainDev(Instances pTrain, Instances pDev){
		int i=0;
		int numAt1=pTrain.numInstances();
		int numAt2=pDev.numInstances();
		if(numAt1>numAt2)
		{
			while(i<pDev.numInstances())
			{
				pTrain.add(pDev.instance(i));
				i++;
			}
			return pTrain;
		}
		else
		{
			while(i<pTrain.numInstances())
			{
				pDev.add(pTrain.instance(i));
				i++;
			}
			return pDev;
		}
	}
	
	
	/**
	 * Saves the trained model
	 * @param pModel
	 */
	public void SaveModel(NaiveBayes pModel){
		
		try {
			SerializationHelper.write(savePath+fileName, pModel);
			
			System.out.println("Model correctly saved at:");
			System.out.println(savePath+fileName);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Saves to TXT the summary received from the evaluator
	 * @param pSummary
	 * @param pMatrix
	 * @param pResultsName
	 */
	public void SaveResults(String pSummary1,String pDetails1,String pMatrix1,double pFmeasure,String pResultsName,String pAdditional){
				
		String usuario=System.getProperty("user.name");
		File file =new File("C:/Users/"+usuario+"/Desktop/EvaluationBaseline.txt");
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Users/"+usuario+"/Desktop/EvaluationBaseline.txt"));
			PrintWriter wr = new PrintWriter(bw); 
			wr.append(pAdditional);
			wr.append(pSummary1);
			wr.append("Fmeasure = "+pFmeasure);
			wr.append(pDetails1);
			wr.append(pMatrix1);
			
			wr.close();
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//		Date date = new Date();
//		PrintWriter writer = null;
//		
//		try {
//			
//			writer = new PrintWriter("naiveBayes"+dateFormat.format(date)+"_"+pResultsName+".txt", "UTF-8");
//			writer.println(pAdditional);
//			writer.println(pSummary);
//			writer.println("Fmeasure; "+ pFmeasure);
//			writer.println(pMatrix);
//			writer.close();
//		
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}
	
	
	//TEMP
	
	public void savePredictions(double[] predictions,String classifier)
	{
		String usuario=System.getProperty("user.name");
		File file =new File("C:/Users/"+usuario+"/Desktop/TestPredictionsBaseline.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Users/"+usuario+"/Desktop/TestPredictionsBaseline.txt"));
			PrintWriter wr = new PrintWriter(bw); 
			for(int i=0;i<predictions.length;i++){
				wr.append(Double.toString(predictions[i]));
			}
			wr.close();
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//		Date date = new Date();
//		PrintWriter writer;
//
//			try {
//				writer= new PrintWriter(savePath+"TestBaselinePredicitions");
//				//writer = new PrintWriter(savePath+"_"+dateFormat.format(date)+"_"+classifier+"_predicitions.txt", "UTF-8");
//				//writer = new PrintWriter(savePath+"_"+classifier+"_predicitions.txt", "UTF-8");
//				for(int i=0;i<predictions.length;i++)
//					writer.println(predictions[i]);
//					
//				writer.close();
//			} catch (FileNotFoundException | UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//			System.out.println("Predictions saved at "+savePath+"_"+classifier+"_Predicitions.txt");
	}
	//END TEMP
		
	public void setFileName(String pFileName) {
		fileName = pFileName;
	}

	public void setSavePath(String pSavePath) {
		savePath = pSavePath;
		
	}
	
	public int getMinorityClass(Instances pData){
		int minIndex = 0;
		int[] classCounts = pData.attributeStats(pData.classIndex() ).nominalCounts;
		int min = classCounts[0];
		for( int i = 0; i < classCounts.length; i++ ) {
			if( classCounts[i] != 0 && classCounts[i] < min ) {
				min = classCounts[i];
				minIndex = i;
			}
		}
		return minIndex;
	}

	public void SaveResults2(String pS10, String pD10, String pM10, String pSR, String pDR, String pMR, String pSH,
			String pDH, String pMH,	double pFmeasure1, double pFmeasure2, double pFmeasure3, String string, String string2) {
		String usuario=System.getProperty("user.name");
		File file =new File("C:/Users/"+usuario+"/Desktop/EvaluationBaseline.txt");
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Users/"+usuario+"/Desktop/EvaluationBaseline.txt"));
			PrintWriter wr = new PrintWriter(bw); 
			wr.append("10-fold crossvalidation");
			wr.append(pS10);
			wr.append("Fmeasure = "+pFmeasure1);
			wr.append(pD10);
			wr.append(pM10);
			
			wr.append("Rebsustitution error");
			wr.append(pSR);
			wr.append("Fmeasure = "+pFmeasure2);
			wr.append(pDR);
			wr.append(pMR);
			
			wr.append("Hold-Out");
			wr.append(pSH);
			wr.append("Fmeasure = "+pFmeasure3);
			wr.append(pDH);
			wr.append(pMH);
			
			wr.close();
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
