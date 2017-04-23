package pevolp2.algoritmo.mutacion;

import pevolp2.algoritmo.cromosoma.*;

public abstract class Mutacion {
	
	protected double prob_mutacion;
	
	public Mutacion(double prob) {
		this.prob_mutacion = prob;
	}
	
	public abstract int mutar(Cromosoma[] poblacion);
}
