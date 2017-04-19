package pevolp2.algoritmo.cruce;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class CodOrdinal extends Cruce {
	
	public CodOrdinal(){}
	
	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,	Cromosoma hijo2) {
		int[] l1 = new int[padre1.getNGenes()];
		int[] l2 = new int[padre2.getNGenes()];
		
		inicializArray(l1, padre1.getNGenes());
		inicializArray(l2, padre2.getNGenes());
		
		int[] aux1 = obtienePosiciones(padre1, l1);
		int[] aux2 = obtienePosiciones(padre2, l2);
		
		cruceSimple(aux1, aux2);
		
		inicializArray(l1, padre1.getNGenes());
		inicializArray(l2, padre2.getNGenes());
		
		llenaHijo(l1, aux1, hijo1);
		llenaHijo(l2, aux2, hijo2);
		
	}

	private void llenaHijo(int[] l, int[] aux, Cromosoma hijo) {

		for(int i = 0; i < aux.length; i++){
			hijo.getGenes()[i].setAlelo(obtieneElemento(l, aux[i]));
		}
	}

	private int obtieneElemento(int[] l, int pos) {
		int p = -1;
		int j = 0;
		int i = 0;
		
		while(i < l.length && p == -1){
			if(pos == j && l[i] != -1){
				p = l[i];
				l[i] = -1;
			}
			if(l[i] != -1){
				j++;
			}
			i++;
		}
		
		return p;
	}

	private void cruceSimple(int[] aux1, int[] aux2) {
		int punto_cruce = (int) (Math.random()*(aux1.length-1) + 1);
		int temp;
		
		for(int i = punto_cruce; i < aux1.length; i++){
			temp = aux1[i];
			aux1[i] = aux2[i];
			aux2[i] = temp;
		}
	}

	private int[] obtienePosiciones(Cromosoma padre, int[] l) {
		int[] posiciones = new int[l.length];
		
		for(int i = 0; i < padre.getNGenes(); i++){
			int p = buscaElemento(l, padre.getGenes()[i].getAlelo());
			posiciones[i] = p;
		}
		
		return posiciones;
	}

	private int buscaElemento(int[] l, int gen) {
		int p = -1;
		int j = 0;
		int i = 0;
		
		while(i < l.length && p == -1){
			if(gen == l[i]){
				l[i] = -1;
				p = j;
			}
			else{
				if(l[i] != -1) j++;
				i++;
			}
		}
		
		return p;
	}

	private void inicializArray(int[] l, int fin) {
		for(int i = 0; i < fin; i++){
			l[i] = i+1; 
		}
		
	}
		
}
