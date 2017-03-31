package baseline.filters;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.RemoveUseless;

public class RemoveUselessAttributes {

	private RemoveUseless removeUselessFilter;
	private boolean optionsSet;
	
	public RemoveUselessAttributes(){
		removeUselessFilter = new RemoveUseless();
		optionsSet = false;
	}
	
	public RemoveUseless getFilter(){
		return removeUselessFilter;
	}
	
	
	/**
	 * Applies RemoveUselessAttributes filter
	 * @param pData
	 * @return
	 */
	public Instances filter(Instances pData){

		Instances newData = null;
		
		try {
			if(optionsSet==false)
			{
			String[] filterOptions=new String[2];
			filterOptions[0]="-M";
			filterOptions[1]="95";	//Maximum variance percentage allowed (default 99)
			removeUselessFilter.setOptions(filterOptions);
			}
			newData = Filter.useFilter(pData, removeUselessFilter);
			
		} catch (Exception e) {
			System.out.println("There was a problem using the filter 'Remove Useless Attributes', returning original data");
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
			removeUselessFilter.setInputFormat(pData);
		} catch (Exception e) {
			System.out.println("There was a problem setting Input Format to Remove Useless Filter");
			e.printStackTrace();
		}
	}
	
	/**
	 * Set options for the filter. If not set, default values will be used.
	 * @param pMode Example "-M" 
	 * @param pPercentage Example "95"
	 */
	public void setOptions(String pMode,String pPercentage){

		try {
			String[] filterOptions=new String[2];
			filterOptions[0]=pMode;
			filterOptions[1]=pPercentage;	//Maximum variance percentage allowed (default 99)
			removeUselessFilter.setOptions(filterOptions);
			optionsSet=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
