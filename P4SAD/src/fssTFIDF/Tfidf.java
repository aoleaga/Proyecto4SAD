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

	public Instances useTFIDFFilter(Instances pInstances) {
		Instances dataFiltered = null;
		try {
			// Establecemos TFTransform y IFDTransform a true
			filter.setTFTransform(true);
			filter.setIDFTransform(true);
			filter.setAttributeIndices("1");
			filter.setMinTermFreq(1);
			filter.setStemmer(null);
			filter.setOutputWordCounts(true);
			filter.setLowerCaseTokens(true);
			filter.setWordsToKeep(1000);
			filter.setInputFormat(pInstances);
			dataFiltered = Filter.useFilter(pInstances,filter);
		} catch (Exception e) {
			System.out.println("El filtro no se ha podido aplicar");
			e.printStackTrace();
		}
		return dataFiltered;
	}

}
