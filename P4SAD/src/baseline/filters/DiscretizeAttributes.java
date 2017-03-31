package baseline.filters;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

public class DiscretizeAttributes {

	private Discretize discretizeFilter;
	private boolean optionsSet;
	
	public DiscretizeAttributes(){
		optionsSet=false;
		discretizeFilter = new Discretize();
	}
	
	public Discretize getFilter(){
		return discretizeFilter;
	}
	
	/**
	 * Applies DiscretizeAttributes filter
	 * @param pData
	 * @return
	 */
	public Instances filter(Instances pData){
		
		Instances newData = null;
		
		try {
			
			if(!optionsSet){
				String[] filterOption=new String[5];
				filterOption[0]="-B";	//Number of intervals
				filterOption[1]="10";
				filterOption[2]="-R";	//Range of attributes
				filterOption[3]="first-last"; 
				filterOption[4]="-F";	//equal-frecuency
				discretizeFilter.setOptions(filterOption);
			}	
			
			newData=Filter.useFilter(pData, discretizeFilter);		
		} catch (Exception e) {
			System.out.println("There was a problem using the filter 'DiscretizeAttributes', returning original data");
			newData = pData;
		}

		return newData;
	}
	
	
	/**
	 * Set the Instance Input Format for the filter
	 * @param pData
	 */
	public void setInputFormat(Instances pData)
	{
		try {
			discretizeFilter.setInputFormat(pData);
		} catch (Exception e) {
			System.out.println("There was a problem setting Input Format to DiscretizeAttributes Filter");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Configure filter options
	 * @param pSetInterval
	 * @param pNumberIntervals
	 * @param pSetRange
	 * @param pNumRangeAttributes
	 * @param pFrecuency
	 */
	public void setOptions(String pSetInterval,String pNumberIntervals,String pSetRange,String pNumRangeAttributes,String pFrecuency){
		try {
			String[] filterOption=new String[5];
			filterOption[0]=pSetInterval;	//Number of intervals
			filterOption[1]=pNumberIntervals;
			filterOption[2]=pSetRange;	//Range of attributes
			filterOption[3]=pNumRangeAttributes; 
			filterOption[4]=pFrecuency;	//equal-frecuency	
			discretizeFilter.setOptions(filterOption);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
