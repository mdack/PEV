package pevolp2.algoritmo.cruce;

import java.util.ArrayList;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class CodOrdinal extends Cruce {
	
	public CodOrdinal(){}
	
	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,	Cromosoma hijo2) {
		ArrayList<Integer> l1 = new ArrayList<Integer>();
		ArrayList<Integer> l2 = new ArrayList<Integer>();
		
		inicializArray(l1, padre1);
		inicializArray(l2, padre2);
		
		ordena(l1);
		ordena(l2);
		
		int[] aux1 = obtienePosiciones(padre1, l1);
		int[] aux2 = obtienePosiciones(padre2, l2);
		
		cruceSimple(aux1, aux2);
		
		inicializArray(l1, padre1);
		inicializArray(l2, padre2);
		
		ordena(l1);
		ordena(l2);
		
		llenaHijo(l1, aux1, hijo1);
		llenaHijo(l2, aux2, hijo2);
	}

	private void llenaHijo(ArrayList<Integer> l, int[] aux, Cromosoma hijo) {
		
		for(int i = 0; i < aux.length; i++){
			hijo.getGenes()[i].setAlelo(l.get(aux[i]));
			l.remove(i);
		}
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

	private int[] obtienePosiciones(Cromosoma padre, ArrayList<Integer> l) {
		int[] posiciones = new int[l.size()];
		
		for(int i = 0; i < padre.getNGenes(); i++){
			int p = buscaElemento(l, padre.getGenes()[i].getAlelo());
			posiciones[i] = p;
		}
		
		return null;
	}

	private int buscaElemento(ArrayList<Integer> l, int gen) {
		int p = -1;
		int i = 0;
		
		while(i < l.size() && p == -1){
			if(gen == l.get(i)){
				p = i;
			}
			else{
				i++;
			}
		}
		
		if(p != -1){
			l.remove(p);
		}
		return p;
	}

	private void inicializArray(ArrayList<Integer> l, Cromosoma padre) {
		for(int i = 0; i < padre.getNGenes(); i++){
			l.add(i, padre.getGenes()[i].getAlelo());
		}
		
	}
	
	private void ordena(ArrayList<Integer> list){
		int temp;
		for(int i = 0; i < list.size(); i++){
			for(int j=i+1; j < list.size(); j++){
				if(list.get(i) < list.get(j)){
						temp = list.get(i);
						list.add(i, list.get(j));
						list.add(j, temp);
				}
			}
		}
	}
	
	
}
