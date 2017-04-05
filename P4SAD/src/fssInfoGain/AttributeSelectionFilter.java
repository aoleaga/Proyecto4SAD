package fssInfoGain;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class AttributeSelectionFilter {

		private static AttributeSelectionFilter miAttributeSelectionFilter = new AttributeSelectionFilter();
		private AttributeSelection aSelection;
		private InfoGainAttributeEval infoGain;
		private Ranker ranker;
	
		private AttributeSelectionFilter(){
			aSelection = new AttributeSelection();
			infoGain = new InfoGainAttributeEval();
			// ranks the attributes according to the chosen quality metric, and keeps those meeting some criterion (like e.g. having a value over a predefined threshold)
			ranker = new Ranker();
		}
		
		public static AttributeSelectionFilter getAttributeSelectionFilter(){
			return miAttributeSelectionFilter;
		}
		
		public Instances useAttributeSelectionFilter(Instances pInstances){
			Instances dataFiltered = null;
			// Establecemos los parámetros para el Ranker
			// the number of attributes to keep, an Integer number that is -1 (all) by default.
			int numToSelect = -2;
			// minimum value that an attribute has to get in the evaluator in order to be kept. The default value for this property is the minimum Long integer in Java.
			double thresHold = 0.005;
			try {
				ranker.setNumToSelect(numToSelect);
				ranker.setThreshold(thresHold);
				
				// Ejecutamos el filtro
				aSelection.setEvaluator(infoGain);
				aSelection.setSearch(ranker);
				aSelection.setInputFormat(pInstances);
				dataFiltered = Filter.useFilter(pInstances,aSelection);
			} catch (Exception e) {
				System.out.println("Ha ocurrido un error durante el filtrado");
				e.printStackTrace();
			}
			
			return dataFiltered;
		}		
}
