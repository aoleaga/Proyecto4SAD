package baseline.filters;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class NormalizeAttributes {
	
	private Normalize normalizeAttributes = null;
	
	public NormalizeAttributes()
	{
		normalizeAttributes = new Normalize();
	}
	
	public Normalize getFilter(){
		return normalizeAttributes;
	}
	
	
	/**
	 * Applies NormalizeAttributes filter
	 * @param pData
	 * @return
	 */
	public Instances filter(Instances pData){
		
		Instances newData = null;
		
		try {
			newData = Filter.useFilter(pData, normalizeAttributes);
					
		} catch (Exception e) {
			System.out.println("There was a problem using the filter 'NormalizeAttributes', returning original data");
			newData = pData;
		}
		return newData;
	}
	
	
	/**
	 * Set the Instance Input Format for the filter
	 * @param pData
	 */
	public void setInputFormat(Instances pData){
		try {
			normalizeAttributes.setInputFormat(pData);
		} catch (Exception e) {
			System.out.println("There was a problem setting Input Format to Remove Useless Filter");
		}
	}
	
}
