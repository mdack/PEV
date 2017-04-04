package pevolp2.algoritmo.cruce;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public abstract class Cruce {

	public Cruce() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2);
	
}
