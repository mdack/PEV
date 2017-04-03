package pevolp2.algoritmo.seleccion;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public abstract class Seleccion {
	
	protected int funcion;
	
	public Seleccion(int func){
		funcion = func;
	}
	
	public abstract Cromosoma[] selecciona(Cromosoma[] poblacion, int tamPob);
	
}
