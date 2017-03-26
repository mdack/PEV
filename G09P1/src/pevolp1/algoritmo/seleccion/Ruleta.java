package pevolp1.algoritmo.seleccion;


import pevolp1.algoritmo.cromosoma.*;

public class Ruleta extends Seleccion {

	public Ruleta(int func) {
		super(func);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void selecciona(Cromosoma[] poblacion, int tamPob) {
		int[] sel_super = new int[tamPob];
		double prob;
		int pos_super;
		
		for(int i = 0; i < tamPob; i++){
			prob = Math.random();
			pos_super = 0;
			
			while((prob > poblacion[pos_super].getPuntAcum()) && (pos_super < tamPob)){
				pos_super++;
				sel_super[i] = pos_super;
			}
		}
		//Se genera la población intermedia
		Cromosoma[] nuevaPob = creaPoblacion(tamPob);
		for(int i = 0; i < tamPob; i++){
			nuevaPob[i] = poblacion[sel_super[i]].copia();
		}
	}

	
}
