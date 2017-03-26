package pevolp1.algoritmo.seleccion;

import pevolp1.algoritmo.cromosoma.Cromosoma;

public class Estocastico extends Seleccion {

	public Estocastico(int func) {
		super(func);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void selecciona(Cromosoma[] poblacion, int tamPob) {
		int[] sel_super = new int[tamPob];
		double suma = getRandom(tamPob);
		int pos_super = 0;
		
		for(int i = 0; i < tamPob; i++){
			while((suma > poblacion[pos_super].getPuntAcum()) && (pos_super < tamPob)){
				pos_super++;
				sel_super[i] = pos_super;
			}
			suma += (1.0/tamPob);
		}
		
		//Se genera la población intermedia
		Cromosoma[] nuevaPob = creaPoblacion(tamPob);
		for(int i = 0; i < tamPob; i++){
			nuevaPob[i] = poblacion[sel_super[i]].copia();
		}
		
		poblacion = nuevaPob;

	}
	
	private double getRandom(int tamPob) {
		double n = Math.random();
		double marca = 1.0 / tamPob;
		
		while(n > marca){
			n = Math.random();
		}
		
		return n;
	}

}
