package getARFF;

import java.awt.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instances;

public class MainGetARFF {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		GetARFF gA = new GetARFF();
		System.out.println("Numero de parametros: "+args.length);
		System.out.println(args[0]);
		if (args.length > 2) { //si hay más de 3 parámetros
			System.out.println("Hay demasiados parámetros, solo es necesario 2");
		} else if (args.length == 0) { //si no hay parámetros      
			System.out.println("Escriba como argumentos la dirección del fichero que queremos usar y la dirección donde ubicar el nuevo fichero resultante");
		}
		else{
			Path source = Paths.get(args[0]);
			if(Files.probeContentType(source).equals(("application/vnd.ms-excel"))){
				System.out.println(".csv SIII");
				if(args[0].contains("tweetSentiment.dev.")){
					gA.generarArffCSV(args[0], args[1],"tweetSentimentDev");
				}
				else if(args[0].contains("tweetSentiment.test_blind.")){
					gA.generarArffCSV(args[0], args[1],"tweetSentimentTest");
				}
				else if(args[0].contains("tweetSentiment.train.")){
					gA.generarArffCSV(args[0], args[1],"tweetSentimentTrain");
				}
			}
			else if(Files.probeContentType(source).equals(("text/plain"))){
				System.out.println(".txt SIII");
				if(args[0].contains(".dev.")){
					gA.generarARFFtxt(args[0], args[1], "smsSPAMdev");
				}
				else if(args[0].contains(".test_blind.")){
					gA.generarARFFtxt(args[0], args[1], "smsSPAMtest");
				}
				else if(args[0].contains(".train.")){
					gA.generarARFFtxt(args[0], args[1], "smsSPAMtrain");
				}
			}
			else{
				File dir = new File(args[0]);
				if(dir.list().length==2){
					System.out.println("Tiene dos carpetas por lo tanto dos clases");
					Instances dataset2 = gA.createDataset(args[0]+"/pos","pos");
				    Instances dataset = gA.createDataset(args[0]+"/neg","neg");
					System.out.println("instancias totales: " + dataset.numInstances());
				    FileWriter grabador = new FileWriter(args[1]);
				    grabador.write(dataset.toString());
				    grabador.close();
				}
				else{
			 		Instances dataset = gA.createDataset(args[0],"pos");
			 		dataset.deleteAttributeAt(0);
			 	    ArrayList valores = new ArrayList(2);
		    	    valores.add("pos");
		    	    valores.add("neg");
		    	    Attribute valor = new Attribute("valor", valores);
			 		dataset.insertAttributeAt(valor,0);
			 		System.out.println("instancias totales: " + dataset.numInstances());
			 	    FileWriter grabador = new FileWriter(args[1]);
			 	    grabador.write(dataset.toString());
			 	    grabador.close();
				}
			}
		}
	}

}
