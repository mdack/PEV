package pevolp2.algoritmo.cruce;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class CX extends Cruce {
	private static int ini;
	
	public CX(){
		ini = 0;
	}
	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,	Cromosoma hijo2) {	
		ini = padre1.getGenes()[0].getAlelo();
		
		if(ini != padre2.getGenes()[0].getAlelo()){
			inicializarHijo(hijo1);
			inicializarHijo(hijo2);
			
			hijo1.getGenes()[0].setAlelo(ini);
			hijo2.getGenes()[0].setAlelo(padre2.getGenes()[0].getAlelo());
			cruzarAux(padre1, padre2, hijo1, hijo2, padre2.getGenes()[0].getAlelo());
		}
	}

	private void cruzarAux(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2, int elem) {
		
		int pos = buscaElemento(padre1, elem);
		int n = padre2.getGenes()[pos].getAlelo();
		
		hijo1.getGenes()[pos].setAlelo(padre1.getGenes()[pos].getAlelo());
		hijo2.getGenes()[pos].setAlelo(n);
		
		if(n == ini){
			llenaHijo(hijo1, padre2);
			llenaHijo(hijo2, padre1);
		}else{
			cruzarAux(padre1, padre2, hijo1, hijo2, n);
		}
	}

	private void inicializarHijo(Cromosoma hijo) {
		
		for(int i = 0; i < hijo.getNGenes(); i++){
			hijo.getGenes()[i].setAlelo(0);
		}
		
	}
	
	private void llenaHijo(Cromosoma hijo, Cromosoma padre) {
		
		for(int i = 0; i < hijo.getNGenes(); i++){
			if(hijo.getGenes()[i].getAlelo() == 0)
				hijo.getGenes()[i].setAlelo(padre.getGenes()[i].getAlelo());
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
