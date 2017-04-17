package pevolp2.algoritmo.mutacion;

import java.util.ArrayList;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class Heuristica extends Mutacion {
	
	private ArrayList<Integer> mejor;
	private double mejor_aptitud;
	private int[] lugares;
	
	public Heuristica(double prob, int n) {
		super(prob);
		mejor = new ArrayList<Integer>();
		mejor_aptitud = Double.MAX_VALUE;
		lugares = new int[n];
	}


	@Override
	public void mutar(Cromosoma[] pob) {
		
		for(int i = 0; i < pob.length; i++){
			Cromosoma crom = pob[i];
			ArrayList<Integer> genes = new ArrayList<Integer>();
			ArrayList<Integer> aux = new ArrayList<Integer>();
			obtieneGenes(genes, crom);
			int m = n;
			permuta(genes, aux, crom, m);
			
			for(int j = 0; j < n; j++){
				crom.getGenes()[lugares[j]].setAlelo(mejor.get(j));
			}
			crom.setFitness_bruto(crom.evalua());
			pob[i] = crom.copia();
		}
	}



	private void obtieneGenes(ArrayList<Integer> genes, Cromosoma crom) {
		int pos;
		boolean[] posiciones = new boolean[crom.getNGenes()];
		
		for(int i = 0; i < crom.getNGenes(); i++){
			posiciones[i] = false;
		}
		
		for(int i = 0; i < n; i++){
			pos = (int) (Math.random()*crom.getNGenes());
			while(posiciones[pos]){
				pos = (int) (Math.random()*crom.getNGenes());
			}
			lugares[i] = pos;
			genes.add(crom.getGenes()[pos].getAlelo());
			posiciones[pos] = true;
		}
		
	}


	private void permuta(ArrayList<Integer> list, ArrayList<Integer> aux, Cromosoma crom, int m) {
		
		if(m == 0){
			for(int i = 0; i < aux.size(); i++){
				crom.getGenes()[lugares[i]].setAlelo(aux.get(i));
			}
			double fit = crom.evalua();
			if(fit < mejor_aptitud){
				this.mejor_aptitud = fit;
				mejor.clear();
				for(int i = 0; i < aux.size(); i++){
					mejor.add(aux.get(i));
				}
			}
		}else{
			for(int i = 0; i < list.size(); i++){
				if(!aux.contains(list.get(i))){
					aux.add(list.get(i));
					permuta(list, aux, crom, m-1);
				}
			}
		}
	}

}
