package pevolp2.algoritmo.cruce;

import java.util.ArrayList;
import java.util.Random;

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
		generaPuntos(padre1.getNGenes());	
		
		ArrayList<Integer> p1 = new ArrayList<Integer>(padre1.getNGenes());
		llenaArray(p1, padre2);
		ArrayList<Integer> p2 = new ArrayList<Integer>(padre2.getNGenes());
		llenaArray(p2, padre1);
		
		completaArrayFinal(p1, padre1);
		completaArrayInicio(p1, padre1);
		completaArrayFinal(p2, padre2);
		completaArrayInicio(p2, padre2);
		
		genesHijo(p1, hijo1);
		genesHijo(p2, hijo2);
	}

	private void genesHijo(ArrayList<Integer> p, Cromosoma hijo) {
		
		for(int i = 0; i< p.size(); i++){
			hijo.getGenes()[i].setAlelo(p.get(i));;
		}
	}

	private void completaArrayInicio(ArrayList<Integer> p, Cromosoma padre) {
		int k = 0;
		for(int i = 0; i < puntoB; i++){
			if(!buscaElemento(p, padre.getGenes()[i].getAlelo())){
				p.add(k, padre.getGenes()[i].getAlelo());
				k++;
			}
		}
	}

	private void completaArrayFinal(ArrayList<Integer> p, Cromosoma padre) {
		int k = puntoB;
		for(int i = puntoB; i < padre.getNGenes(); i++){
			if(!buscaElemento(p, padre.getGenes()[i].getAlelo())){
				p.add(k, padre.getGenes()[i].getAlelo());
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
		return enc;
	}

	private void llenaArray(ArrayList<Integer> p, Cromosoma padre) {
		
		for(int i = 0; i < padre.getNGenes(); i++){
			p.add(i, 0);
		}
		
		for(int i = puntoA; i < puntoB; i++){
			p.add(i, padre.getGenes()[i].getAlelo());
		}
	}

	private void generaPuntos(int longitud) {
		Random rnd = new Random();
		int p1 = longitud - 2;
		puntoA = rnd.nextInt(p1)+1;
		
		do{
		int aux = rnd.nextInt(longitud-puntoA);
		puntoB = puntoA + aux;
		}while(puntoB == puntoA);
		
	}
	
	
	
}
