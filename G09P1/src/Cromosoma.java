
public abstract class Cromosoma {
	
	protected double fitness;
	protected double punt;
	protected float puntAcum;
	protected Gen[] genes;
	protected double fenotipo;
	
	public double getFitness() {
		return fitness;
	}
	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
	public double getPunt() {
		return punt;
	}
	public void setPunt(double d) {
		this.punt = d;
	}
	public float getPuntAcum() {
		return puntAcum;
	}
	public void setPuntAcum(float puntAcum) {
		this.puntAcum = puntAcum;
	}
	public Gen[] getGenes() {
		return genes;
	}
	public void setGenes(Gen[] genes) {
		this.genes = genes;
	}
	
	public abstract double fenotipo(int pos);
	public abstract double evalua(int pos);
}
