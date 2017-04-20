package pevolp2.algoritmo.cruce;

import java.util.Random;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class OX extends Cruce {
	
	private static int puntoA;
	private static int puntoB;

	public OX() {
		puntoA = 0;
		puntoB = 0;
	}

	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		generaPuntos(padre1.getNGenes());	
		
		int[] p1 = new int[padre1.getNGenes()];
		int[] p2 = new int[padre1.getNGenes()];

		llenaArray(p1, padre2);
		llenaArray(p2, padre1);
		
		completaArray(p1, padre1);
		completaArray(p2, padre2);

		genesHijo(p1, hijo1);
		genesHijo(p2, hijo2);
	}

	private void genesHijo(int[] p, Cromosoma hijo) {
		
		for(int i = 0; i< p.length; i++){
			hijo.getGenes()[i].setAlelo(p[i]);;
		}
	}

	private void completaArray(int[] p, Cromosoma padre) {
		int k = puntoB;
		boolean enc = false;
		int elem = 0;
		int limit = padre.getNGenes();
		
		for(int i = puntoB; i < p.length; i++){
			
			while(k < limit && !enc){
				elem = padre.getGenes()[k].getAlelo();
				if(!buscaElemento(p, elem)) enc = true;
				k++;
				if(k == padre.getNGenes()){
					k = 0;
					limit = puntoB;
				}
			}

			if(enc) p[i] = elem;
			enc = false;
		}
		
		for(int i = 0; i < puntoA; i++){
			while(k < limit && !enc){
				elem = padre.getGenes()[k].getAlelo();
				if(!buscaElemento(p, elem)) enc = true;
				k++;
				if(k == padre.getNGenes()){
					k = 0;
					limit = puntoB;
				}
			}

			if(enc) p[i] = elem;
			enc = false;
		}
		
	}

	private boolean buscaElemento(int[] p, int elem) {
		boolean enc = false;
		int i = puntoA;
	
		while(i < puntoB && !enc){
			if(p[i] == elem) enc = true;
			i++;
		}
		return enc;
	}

	private void llenaArray(int[] p, Cromosoma padre) {
		
		for(int i = puntoA; i < puntoB; i++){
			p[i] = padre.getGenes()[i].getAlelo();
			
		}
	}

	private void generaPuntos(int longitud) {
		int fin = longitud - 2;
		Random rnd = new Random();
		
		puntoA = (int) ((rnd.nextDouble() * fin) + 1);
		
		fin = longitud - 1;
		int ini = puntoA + 1;
		if(fin == ini)
			puntoB = fin;
		else{
			do{
				puntoB = (int) ((rnd.nextDouble() * fin) + ini);
			}while(puntoB >= longitud);
		}
		
	}
	
	
	
}
