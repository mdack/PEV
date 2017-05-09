package pevolp3.algoritmo.seleccion;

import pevolp3.algoritmo.cromosoma.Cromosoma;

public abstract class Seleccion {
	
	public Seleccion(){
	
	}
	
	public abstract Cromosoma[] selecciona(Cromosoma[] poblacion, int tamPob);
	
}
