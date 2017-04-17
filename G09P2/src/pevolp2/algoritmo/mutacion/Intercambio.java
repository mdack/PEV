package pevolp2.algoritmo.mutacion;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class Intercambio extends Mutacion {
	
	public Intercambio(){}
	
	@Override
	public void mutar(Cromosoma crom) {
		int pos1 = (int) (Math.random()*crom.getNGenes());
		int pos2 = (int) (Math.random()*crom.getNGenes());
		
		while(pos1 == pos2){
			pos2 = (int) (Math.random()*crom.getNGenes());
		}
		
		int temp = crom.getGenes()[pos2].getAlelo();
		crom.getGenes()[pos2].setAlelo(crom.getGenes()[pos1].getAlelo());
		crom.getGenes()[pos1].setAlelo(temp);
	}

}
