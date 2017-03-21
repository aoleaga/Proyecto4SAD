package arff2bow;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class StringToVector {
	private static StringToVector miStringToVector = new StringToVector();
	private StringToWordVector filter;

	private StringToVector() {
		filter = new StringToWordVector();

	}

	public static StringToVector getStringToVector(){
		return miStringToVector;
	}
	
	public Filter getStringToVectorFilter(Instances pInstances) throws Exception {
		try {
			filter.setTFTransform(false);
			filter.setIDFTransform(false);
			// Sets the range of attributes to use in the calculation of the distance
			filter.setAttributeIndices("1");
			// Consideramos identicas las palabras mayusculas y minusculas
			filter.setLowerCaseTokens(true);
			// Set the MinTermFreq value
			filter.setMinTermFreq(1);
			// Utilizaremos valores enteros que indican el número de veces que aparece una palabra
			filter.setOutputWordCounts(true);
			filter.setStemmer(null);
			// Para limitar  el número de palabras
			filter.setWordsToKeep(1000);
			filter.setInputFormat(pInstances);
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error durante el filtrado");
			e.printStackTrace();
		}
		return filter;
	}
}
