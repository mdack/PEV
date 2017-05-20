package pevolp3.algoritmo.cromosoma;

import pevolp3.algoritmo.arbol.Arbol;

public class Cromosoma {
	
	public static final String terminales[] = { "A0", "A1", "D0", "D1", "D2", "D3" };
	public static final String funciones[] = { "AND", "OR", "NOT", "IF" };
	
	private Arbol arbol;
	private double fitness;
	private double fitness_bruto; //Aptitud antes de transformarla
	private double punt;
	private double puntAcum;
	private double fenotipo;
	
	public Cromosoma(int profundidad, int tipoCreacion, boolean useIf) {
		arbol = new Arbol(profundidad, useIf);
		arbol.inicializacionCompleta(0);
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double getPunt() {
		return punt;
	}
	public void setPunt(double d) {
		this.punt = d;
	}
	public double getPuntAcum() {
		return puntAcum;
	}
	public void setPuntAcum(double puntAcum) {
		this.puntAcum = puntAcum;
	}
	
	public double getFitness_bruto() {
		return fitness_bruto;
	}
	public void setFitness_bruto(double fitness_bruto) {
		this.fitness_bruto = fitness_bruto;
	}

	public double fenotipo() {
		return 0;
	}

	public double evalua() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Cromosoma copia() {
		// TODO Auto-generated method stub
		return null;
	}

}
