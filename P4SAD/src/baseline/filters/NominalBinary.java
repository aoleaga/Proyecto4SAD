package baseline.filters;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.NominalToBinary;

public class NominalBinary {

	private NominalToBinary nominalBinaryFilter = null;
	
	public NominalBinary()
	{
		
		nominalBinaryFilter = new NominalToBinary();
	}
	
	public NominalToBinary getFilter(){
		return nominalBinaryFilter;
	}
	
	/**
	 * Applies NominalBinary filter
	 * @param pData
	 * @return
	 */
	public Instances filter(Instances pData){

		Instances newData=null;
		
		try {
		
			newData = Filter.useFilter(pData, nominalBinaryFilter);
			
		} catch (Exception e) {
			System.out.println("There was a problem using the filter 'NominalBinary', returning original data");
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
			nominalBinaryFilter.setInputFormat(pData);
		} catch (Exception e) {
			System.out.println("There was a problem setting Input Format to Remove Useless Filter");
			e.printStackTrace();
		}
	}
	
}
