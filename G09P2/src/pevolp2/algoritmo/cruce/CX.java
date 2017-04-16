package pevolp2.algoritmo.cruce;

import pevolp2.algoritmo.cromosoma.Cromosoma;

/*
 * Si el primer elemento es el mismo en los dos que hago??
 * Si el segundo elemento perteneciente al padre 2 está antes del puntoA qué hago
 */
public class CX extends Cruce {
	private int posA;
	private int posB;
	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,	Cromosoma hijo2) {
		if(iniPosiciones(padre1, padre2)){
			llenaHijo(0, posA, hijo1, padre1);
			llenaHijo(0, posA, hijo2, padre2);
			hijo1.getGenes()[posA].setAlelo(padre1.getGenes()[posA].getAlelo());
			hijo1.getGenes()[posA].setAlelo(padre2.getGenes()[posA].getAlelo());
			llenaHijo(posA+1, posB, hijo1, padre2);
			llenaHijo(posA+1, posB, hijo2, padre1);
			hijo1.getGenes()[posB].setAlelo(padre1.getGenes()[posB].getAlelo());
			hijo1.getGenes()[posB].setAlelo(padre2.getGenes()[posB].getAlelo());
			if(posB < padre1.getNGenes()-1){
				llenaHijo(posB+1, padre1.getNGenes(), hijo1, padre2);
				llenaHijo(posB+1, padre2.getNGenes(), hijo2, padre1);
			}
		}

	}

	private void llenaHijo(int ini, int fin, Cromosoma hijo, Cromosoma padre) {
		
		for(int i = ini; i < fin; i++){
			hijo.getGenes()[i].setAlelo(padre.getGenes()[i].getAlelo());
		}
		
	}

	private boolean iniPosiciones(Cromosoma padre1, Cromosoma padre2) {
		
		if(padre1.getGenes()[0].getAlelo() != padre2.getGenes()[0].getAlelo()){
			posA = buscaElemento(padre1, padre2.getGenes()[0].getAlelo());
			int aux = buscaElemento(padre1, padre2.getGenes()[posA].getAlelo());
			if(aux > posA){
				posB = aux;
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}

	private int buscaElemento(Cromosoma padre, int elem) {
		int i = 0;
		boolean enc = false;
		
		while(i < padre.getNGenes() && !enc){
			if(elem == padre.getGenes()[i].getAlelo()){
				enc = true;
			}else{
				i++;
			}
		}
		return i;
	}

}
