package pevolp3.algoritmo.seleccion;

import pevolp3.algoritmo.cromosoma.Cromosoma;

public class Estocastico extends Seleccion {
	
	private double marca;
	
	public Estocastico() {
	}

	@Override
	public Cromosoma[] selecciona(Cromosoma[] poblacion, int tamPob) {
		int[] sel_super = new int[tamPob];
		marca = 1.0 / tamPob;
		double suma = getRandom(tamPob);
		int pos_super = 0;
		
		for(int i = 0; i < tamPob; i++){
			while((suma > poblacion[pos_super].getPuntAcum()) && (pos_super < tamPob)){
				pos_super++;
				sel_super[i] = pos_super;
			}
			suma += marca;
		}
		
		//Se genera la población intermedia
		Cromosoma[] nuevaPob = new Cromosoma[tamPob];
		for(int i = 0; i < tamPob; i++){
			nuevaPob[i] = poblacion[sel_super[i]].copia();
		}
		
		for(int i = 0; i < tamPob; i++){
			poblacion[i] = nuevaPob[i].copia();
		}
		return poblacion;
	}
	
	private double getRandom(int tamPob) {
		double n = Math.random();
		
		while(n > marca){
			n = Math.random();
		}
		
		return n;
	}

}
