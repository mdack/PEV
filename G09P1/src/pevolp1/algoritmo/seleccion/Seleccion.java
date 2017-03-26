package pevolp1.algoritmo.seleccion;

import pevolp1.algoritmo.cromosoma.Cromosoma;

public abstract class Seleccion {
	
	protected int funcion;
	
	public Seleccion(int func){
		funcion = func;
	}
	
	public abstract Cromosoma[] selecciona(Cromosoma[] poblacion, int tamPob);
	
}
