package pevolp2.algoritmo.mutacion;

import pevolp2.algoritmo.cromosoma.*;

public abstract class Mutacion {
	
	protected double prob_mutacion;
	protected int n;
	
	public Mutacion(double prob) {
		this.prob_mutacion = prob;
	}
	
	public abstract void mutar(Cromosoma[] poblacion);
}
