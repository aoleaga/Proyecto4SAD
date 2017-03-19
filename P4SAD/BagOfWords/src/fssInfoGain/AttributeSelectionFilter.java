package fssInfoGain;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.supervised.attribute.AttributeSelection;

public class AttributeSelectionFilter {

		private static AttributeSelectionFilter miAttributeSelectionFilter = new AttributeSelectionFilter();
		private AttributeSelection aSelection;
		private InfoGainAttributeEval infoGain;
		private Ranker ranker;
	
		private AttributeSelectionFilter(){
			aSelection = new AttributeSelection();
			infoGain = new InfoGainAttributeEval();
			ranker = new Ranker();
		}
		
		public static AttributeSelectionFilter getAttributeSelectionFilter(){
			return miAttributeSelectionFilter;
		}
		
		public AttributeSelection getAttributeSelectionFilter(Instances pInstances){
			// Establecemos los parámetros para el Ranker
			//Numero de atributos que se desea mantener (-1 = todos)
			int numToSelect = -1; //TODO CUAL ES EL MEJOR PARAMETRO Y PORQUE
			double thresHold = 0.000001; //TODO CUAL ES EL MEJOR PARAMETRO Y PORQUE
			try {
				ranker.setNumToSelect(numToSelect);
				ranker.setThreshold(thresHold);
				
				// Ejecutamos el filtro
				aSelection.setEvaluator(infoGain);
				aSelection.setSearch(ranker);
				aSelection.setInputFormat(pInstances);
			} catch (Exception e) {
				System.out.println("Ha ocurrido un error durante el filtrado");
				e.printStackTrace();
			}
			
			return aSelection;
		}		
}
