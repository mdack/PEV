package pevolp2.algoritmo.mutacion;

import java.util.ArrayList;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class Heuristica extends Mutacion {


	@Override
	public void mutar(Cromosoma crom) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		inicializaArray(crom, list);
		
		permuta(list, crom, list.size());
	}


	private void permuta(ArrayList<Integer> list, Cromosoma crom, int n) {
		
		if(n == 0){
			for(int i = 0; i < list.size(); i++){
				crom.getGenes()[i].setAlelo(list.get(i));
			}
		}else{
			for(int i = 0; i < list.size(); i++){
				//if(!list.contains())
			}
		}
	}


	private void inicializaArray(Cromosoma crom, ArrayList<Integer> list) {
		for(int i = 0; i < crom.getNGenes(); i++){
			list.add(i, crom.getGenes()[i].getAlelo());
		}
		
	}

}
