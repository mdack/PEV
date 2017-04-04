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
		
		completaArray(p1, padre1);
		
	}

	private void completaArray(ArrayList<Integer> p1, Cromosoma padre) {
		
		for(int i = puntoB; i < padre.getNGenes(); i++){
		}
		
	}

	private void llenaArray(ArrayList<Integer> p1, Cromosoma padre) {
		
		for(int i = puntoA; i < puntoB; i++){
			p1.add(i, padre.getGenes()[i].getAlelo()[0]);
		}
	}

	private void generaPuntos(int longitud) {
		puntoA = (int) ((Math.random() * longitud) + 1);
		
		while(puntoA < puntoB){
			puntoB = (int) ((Math.random() * longitud) + 1);
		}
		
	}
	
	
	
}
