package baseline.filters;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class SelectAttributes {

	private AttributeSelection selectAttributesFilter;
	
	public SelectAttributes(){
		selectAttributesFilter = new AttributeSelection();
		
	}
	
	public AttributeSelection getFilter(){
		return selectAttributesFilter;
	}
	
	
	/**
	 * Applies SelectAttributes filter
	 * @param pData
	 * @return
	 */
	public Instances filter(Instances pData){
		CfsSubsetEval eval = new CfsSubsetEval();
		BestFirst search=new BestFirst();

		//2.1 Get new data set with the attribute sub-set
		Instances newData = null;
		try {
			selectAttributesFilter.setEvaluator(eval);
			selectAttributesFilter.setSearch(search);
			newData = Filter.useFilter(pData, selectAttributesFilter);
		} catch (Exception e) {
			
			System.out.println("There was a problem using the filter 'Select Attributes', returning original data");
			newData = pData;
			e.printStackTrace();
		}
		return newData;
	}
	
	
	/**
	 * Set the Instance Input Format for the filter
	 * @param pData
	 */
	public void setInputFormat(Instances pData){
		try {
			selectAttributesFilter.setInputFormat(pData);
		} catch (Exception e) {
			System.out.println("There was a problem setting Input Format to SelectAttributes filter");
			e.printStackTrace();
		}
	}
	
}
