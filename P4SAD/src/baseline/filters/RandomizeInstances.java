package baseline.filters;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;

public class RandomizeInstances {

	private Randomize randomizeFilter = null;
	
	public Randomize getFilter(){
		return randomizeFilter;
	}
	
	
	/**
	 * Applies RandomizeInstances filter 
	 * @param pData
	 * @return
	 */
	public Instances filter(Instances pData){
		Randomize filter = null;
		Instances newData = null;
		
		try {
			
			filter = new Randomize();
			filter.setInputFormat(pData);
			newData = Filter.useFilter(pData, filter);
			
		} catch (Exception e) {
			System.out.println("There was a problem using the filter 'Randomize Instances', returning original data");
			newData = pData;
		}
		
		randomizeFilter = filter;
		return newData;
	}
}
