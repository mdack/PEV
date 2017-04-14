package pevolp2.algoritmo.cruce;

import java.util.ArrayList;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class OX extends Cruce {
	
	private int puntoA;
	private int puntoB;

	public OX() {
		puntoA = 0;
		puntoB = 0;
	}

	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		generaPuntos(padre1.getLongitud());	
		
		ArrayList<Integer> p1 = new ArrayList<Integer>();
		llenaArray(p1, padre2);
		ArrayList<Integer> p2 = new ArrayList<Integer>();
		llenaArray(p2, padre1);
		
		completaArrayFinal(p1, padre1);
		completaArrayInicio(p1, padre1);
		completaArrayFinal(p2, padre2);
		completaArrayInicio(p2, padre2);
	}

	private void completaArrayInicio(ArrayList<Integer> p, Cromosoma padre) {
		int k = 0;
		for(int i = 0; i < puntoB; i++){
			if(!buscaElemento(p, padre.getGenes()[i].getAlelo()[0])){
				p.add(k, padre.getGenes()[i].getAlelo()[0]);
				k++;
			}
		}
	}

	private void completaArrayFinal(ArrayList<Integer> p, Cromosoma padre) {
		int k = puntoB;
		for(int i = puntoB; i < padre.getNGenes(); i++){
			if(!buscaElemento(p, padre.getGenes()[i].getAlelo()[0])){
				p.add(k, padre.getGenes()[i].getAlelo()[0]);
				k++;
			}
		}
		
	}

	private boolean buscaElemento(ArrayList<Integer> p, int elem) {
		boolean enc = false;
		int i = puntoA;
	
		while(i < puntoB && !enc){
			if(p.get(i) == elem) enc = true;
			i++;
		}
		return false;
	}

	private void llenaArray(ArrayList<Integer> p, Cromosoma padre) {
		
		for(int i = puntoA; i < puntoB; i++){
			p.add(i, padre.getGenes()[i].getAlelo()[0]);
		}
	}

	private void generaPuntos(int longitud) {
		puntoA = (int) ((Math.random() * longitud) + 1);
		
		while(puntoA < puntoB){
			puntoB = (int) ((Math.random() * longitud) + 1);
		}
		
	}
	
	
	
}
