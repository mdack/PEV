package pevolp2.algoritmo.cruce;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class CX extends Cruce {
	private int posA;
	private int posB;
	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,	Cromosoma hijo2) {
		iniPosiciones(padre1, padre2);

	}
	
	private void iniPosiciones(Cromosoma padre1, Cromosoma padre2) {
		posA = buscaElemento(padre1, padre2.getGenes()[0].getAlelo()[0]);
		
	}

	private int buscaElemento(Cromosoma padre, int elem) {
		int i = 0;
		boolean enc = false;
		
		while(i < padre.getGenes()[0].getLongAlelo() && !enc){
			
		}
		return 0;
	}

}
