package pevolp2.algoritmo.seleccion;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public abstract class Seleccion {
	
	public Seleccion(){
	
	}
	
	public abstract Cromosoma[] selecciona(Cromosoma[] poblacion, int tamPob);
	
}
