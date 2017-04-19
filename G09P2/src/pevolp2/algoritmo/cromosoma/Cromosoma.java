package pevolp2.algoritmo.cromosoma;

import pevolp2.algoritmo.gen.Gen;

public abstract class Cromosoma {
	
	protected double fitness;
	protected double fitness_bruto; //Aptitud antes de transformarla
	protected double punt;
	protected double puntAcum;
	protected Gen[] genes;
	protected double fenotipo;
	private int longitud;
	protected static int N_GENES;
	
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
	public Gen[] getGenes() {
		return genes;
	}
	public void setGenes(Gen[] genes) {
		this.genes = genes;
	}
	
	public int getNGenes(){
		return N_GENES;	
	}
	public double getFitness_bruto() {
		return fitness_bruto;
	}
	public void setFitness_bruto(double fitness_bruto) {
		this.fitness_bruto = fitness_bruto;
	}
	
	public abstract double fenotipo(int pos);
	public abstract double evalua();
	public abstract Cromosoma copia();
	public int getLongitud() {
		return longitud;
	}
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	

	@Override
	public String toString() {
		String cadena = "";
		for(int i = 0; i < N_GENES-1; i++){
			cadena += " " + genes[i].getAlelo() + " -";
		}
		cadena += " " + genes[N_GENES-1].getAlelo();
		return cadena;
	}

}
