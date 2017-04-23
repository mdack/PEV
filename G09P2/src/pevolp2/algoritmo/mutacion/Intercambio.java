package pevolp2.algoritmo.mutacion;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class Intercambio extends Mutacion {
	
	
	public Intercambio(double prob) {
		super(prob);
	}

	@Override
	public int mutar(Cromosoma[] pob) {
		int mutaciones = 0;
		double prob;
		for(int i = 0; i < pob.length; i++){
			prob = Math.random();
			
			if(prob < prob_mutacion){
				mutaciones++;
				Cromosoma crom = pob[i];
				
				int pos1 = (int) (Math.random()*crom.getNGenes());
				int pos2 = (int) (Math.random()*crom.getNGenes());
				
				while(pos1 == pos2){
					pos2 = (int) (Math.random()*crom.getNGenes());
				}
				
				int temp = crom.getGenes()[pos2].getAlelo();
				crom.getGenes()[pos2].setAlelo(crom.getGenes()[pos1].getAlelo());
				crom.getGenes()[pos1].setAlelo(temp);
				
				crom.setFitness_bruto(crom.evalua());
				pob[i] = crom.copia();
			}
		}
		return mutaciones;
	}

}
