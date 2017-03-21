package fssTFIDF;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Tfidf {
	
	private static Tfidf miTfidf = new Tfidf();
	private StringToWordVector filter;

	private Tfidf() {
		filter = new StringToWordVector();

	}
	
	public static Tfidf getTfidf(){
		return miTfidf;
	}

	public Filter getTFIDFFilter(Instances pInstances) {

		try {
			// Hay que elegir una de las dos
			filter.setTFTransform(true);
			filter.setIDFTransform(true);
			filter.setAttributeIndices("1");
			filter.setMinTermFreq(1);
			filter.setStemmer(null);
			filter.setOutputWordCounts(true);
			filter.setLowerCaseTokens(true);
			filter.setWordsToKeep(200);
			filter.setInputFormat(pInstances);
		} catch (Exception e) {
			System.out.println("El filtro no se ha podido aplicar");
			e.printStackTrace();
		}
		return filter;
	}

}
