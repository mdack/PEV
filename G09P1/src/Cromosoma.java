import java.util.Collection;


public abstract class Cromosoma {

	protected float fenotipo;
	public float getFenotipo() {
		return fenotipo;
	}
	public void setFenotipo(float fenotipo) {
		this.fenotipo = fenotipo;
	}
	public float getFitness() {
		return fitness;
	}
	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
	public float getPunt() {
		return punt;
	}
	public void setPunt(float punt) {
		this.punt = punt;
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
	protected float fitness;
	protected float punt;
	protected float puntAcum;
	protected Gen[] genes;
	
	public abstract float fenotipo();
	public abstract float evalua();
}
