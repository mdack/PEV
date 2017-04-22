package pevolp2.algoritmo.mutacion;

import java.util.Arrays;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class Heuristica extends Mutacion {
	
	private static final int n = 3;
	
	private static int[] mejor; //Guarda al mejor permutación
	private static double mejor_aptitud; //Guarda el mejor fitness del cromosoma
	private static int[] lugares; //Guardo posiciones del cromosoma seleccionadas para mutar
	
	public Heuristica(double prob) {
		super(prob);
		mejor = new int[n];
		mejor_aptitud = 10000000;
		lugares = new int[n];
	}


	@Override
	public void mutar(Cromosoma[] pob) {
		double prob;
		for(int i = 0; i < pob.length; i++){
			prob = Math.random();
			if(prob < prob_mutacion){
				Cromosoma crom = pob[i].copia();
				int[] genes = new int[n];
				
				obtieneGenes(genes, crom);
				Arrays.sort(genes);
				
		        do {
		            for(int j = 0; j < n; j++){
		            	crom.getGenes()[lugares[j]].setAlelo(genes[j]);
		            }
		            double m = crom.evalua();	        	
		        	if(m < mejor_aptitud){
		        		mejor_aptitud = m;
		        		for(int j = 0; j < n; j++){
		        			mejor[j] = genes[j];
		        		}
		        		
		        	}
		        	
		        } while (nextPermutation(genes));
		        
				for(int j = 0; j < n; j++){
					crom.getGenes()[lugares[j]].setAlelo(mejor[j]);
				}
				
				crom.setFitness_bruto(crom.evalua());
				pob[i] = crom.copia();
				cleanAtributos();
			}
		}
	}

	private void cleanAtributos() {
		mejor = new int[n];
		lugares = new int[n];
		mejor_aptitud = 10000000;
	}


	private void obtieneGenes(int[] genes, Cromosoma crom) {
		int pos;
		boolean[] posiciones = new boolean[crom.getNGenes()];
		
		for(int i = 0; i < crom.getNGenes(); i++){
			posiciones[i] = false;
		}
		
		for(int i = 0; i < n; i++){
			pos = (int) (Math.random()*crom.getNGenes());
			while(posiciones[pos]){
				pos = (int) (Math.random()*crom.getNGenes());
			}
			lugares[i] = pos;
			genes[i] = crom.getGenes()[pos].getAlelo();
			posiciones[pos] = true;
		}
		Arrays.sort(lugares);
	}
	
	 private static boolean nextPermutation(int[] array) {

	        int i = array.length - 1;
	        while (i > 0 && array[i - 1] >= array[i]) {
	            i--;
	        }

	        if (i <= 0) {
	            return false;
	        }

	        int j = array.length - 1;
	        while (array[j] <= array[i - 1]) {
	            j--;
	        }

	        int temp = array[i - 1];
	        array[i - 1] = array[j];
	        array[j] = temp;

	        j = array.length - 1;
	        while (i < j) {
	            temp = array[i];
	            array[i] = array[j];
	            array[j] = temp;
	            i++;
	            j--;
	        }
	        return true;
	    }

}
