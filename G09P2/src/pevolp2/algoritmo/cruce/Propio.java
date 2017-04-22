package pevolp2.algoritmo.cruce;

import pevolp2.algoritmo.cromosoma.Cromosoma;

/**
 * Fija la primera y la ultima posición, 
 * a partir de eso llena el segmento de genes centra con los genes del otro padre
 * según su orden y si están o no en el hijo que se está procesando.
 */
public class Propio extends Cruce {
	
	public Propio() {
	}

	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		int[] p1 = new int[padre1.getNGenes()];
		int[] p2 = new int[padre1.getNGenes()];

		p1[0] = padre1.getGenes()[0].getAlelo();
		p1[padre1.getNGenes()-1] = padre1.getGenes()[padre1.getNGenes()-1].getAlelo();
		p2[0] = padre2.getGenes()[0].getAlelo();
		p2[padre2.getNGenes()-1] = padre2.getGenes()[padre2.getNGenes()-1].getAlelo();
		
		completaArray(p1, padre2);
		completaArray(p2, padre1);
		
		llenaHijo(p1, hijo1);
		llenaHijo(p2, hijo2);
				
		hijo1.setFitness_bruto(hijo1.evalua());
		hijo2.setFitness_bruto(hijo2.evalua());
	}
	
	private void llenaHijo(int[] p, Cromosoma hijo) {
		
		for(int i = 0; i < p.length; i++){
			hijo.getGenes()[i].setAlelo(p[i]);
		}
		
	}

	private void completaArray(int[] p, Cromosoma padre) {
		int j = 1;
		for(int i = 0; i < padre.getNGenes() && j < p.length-1; i++){
			int elem = padre.getGenes()[i].getAlelo();
			if(!buscaElemento(p, elem)){
				p[j] = elem;
				j++;
			}
		}
		
	}

	private boolean buscaElemento(int[] p, int elem) {
		int i = 0;
		boolean enc = false;
		
		while(i < p.length && !enc){
			if(elem == p[i]){
				enc = true;
			}else{
				i++;
			}
		}
		return enc;
	}

}
