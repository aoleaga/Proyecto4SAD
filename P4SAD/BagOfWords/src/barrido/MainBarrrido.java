package barrido;

import java.util.Random;

import arff2bow.Data;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class MainBarrrido {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Data dataClass = new Data();
		Instances data = dataClass.loadFile(args[0]);
		int clase = data.attribute("clase").index();
		data.setClassIndex(clase);
		NaiveBayes nb=new NaiveBayes();
		Evaluation evaluator = new Evaluation(data);
		
		evaluator.crossValidateModel(nb, data, 10, new Random(1));
		
		System.out.println(evaluator.toSummaryString());
	}

}
