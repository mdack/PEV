
public abstract class Cromosoma {
	
	protected double fitness;
	protected double punt;
	protected double puntAcum;
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
	
	/**
	 * Convierte el contenido de la cadena binaria que lleva el gen en su valor decimal
	 * @param gen Gen que se traducirá a decimal
	 * @return Valor decimal de la cadena binaria
	 */
	public double bin_dec(Gen gen) {
		int longGen = gen.getLongAlelo();
		int pos = 0;
		double valor = 0;
		
		for(int i = longGen; i >= 0; i--){
			double num = (float)Math.pow(2, pos);
			pos++;
			valor += num;
		}
		
		return valor;
	}
	
	public abstract double fenotipo(int pos);
	public abstract double evalua(int pos);
}
