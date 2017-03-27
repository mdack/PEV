package pevolp1.algoritmo.cromosoma;

import pevolp1.algoritmo.gen.Gen;

public class CromosomaP1F1 extends Cromosoma {
	
	private static final double X_MIN = -250;
	private static final double X_MAX = 250;
	
	public CromosomaP1F1(){}
	
	public CromosomaP1F1(double tolerancia){
		N_GENES = 1;
		genes = new Gen[N_GENES];
				
		for(int i = 0; i < N_GENES; i++){	
			int longGen = longitudGen(tolerancia);
			this.genes[i] = new Gen(longGen);
			setLongitud(getLongitud() + longGen);
		}		
		this.fenotipo = fenotipo(0);
		this.fitness_bruto = evalua();
	}
	
	
	/**
	 * Método que obtiene el fenotipo de un individuo
	 */
	public double fenotipo(int pos) {
		double valor;
		fenotipo = 0;
		
		Gen gen = this.genes[pos];
		valor = X_MIN + ((X_MAX - X_MIN) * bin_dec(gen)) / (Math.pow(2, gen.getLongAlelo()) - 1);
		fenotipo += valor;

		return fenotipo;
	}
	
	
	/**
	 * Calcula la longitud del cromosoma
	 * @param precision Precisión o tolerancia que se utiliza para calcula la longitud del cromosoma
	 * @return Longitud que tendrá la cadena binaria
	 */
	public int longitudGen(double precision){
		double longAux = (Math.log10((1+((X_MAX - X_MIN) / precision))) / Math.log10(2)); //Calcula la longitud exacta
		int p_ent = (int) longAux; // Nos quedamos con la parte entera
		double p_dec = longAux - p_ent; //Obtenemos la parte decimal
		
		if(p_dec > 0) p_ent++;
		
		return p_ent;
	}

	/**
	 * Obtiene el valor de la función a optimizar
	 */
	public double evalua() {
		double f = fenotipo(0); //Obtenemos fenotipo
		
		double fit = ((Math.abs(f * Math.sin(Math.sqrt(Math.abs(f)))) * -1));
		
		this.fitness_bruto = fit;
		
		return fit;
	}


	@Override
	public String toString() {
		String cadena = " ";
		cadena += ("x = " + fenotipo(0) + "\n");
		cadena += ("f(x) = " + fitness_bruto + "\n");
		
		return cadena;
	}
	
	/**
	 * Devuelve una copia de este cromosoma.
	 */
	public Cromosoma copia()
	{
		CromosomaP1F1 aux = new CromosomaP1F1();
		aux.fitness = this.fitness;
		aux.fitness_bruto = this.fitness_bruto;
		aux.fenotipo = this.fenotipo;
		aux.setLongitud(this.getLongitud());
		aux.punt = this.punt;
		aux.puntAcum = this.puntAcum;
		aux.genes = new Gen[N_GENES];
		for(int i = 0; i < N_GENES; i++)
		{
			aux.genes[i] = this.genes[i].copia();
		}
		return aux;
	}

}
