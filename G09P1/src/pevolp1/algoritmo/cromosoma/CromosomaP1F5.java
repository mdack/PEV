package pevolp1.algoritmo.cromosoma;

import pevolp1.algoritmo.gen.Gen;

public class CromosomaP1F5 extends Cromosoma {
	
	private static final double X_MIN = -10;
	private static final double X_MAX = 10;
	private static final int MAX_I = 5;
	
	public CromosomaP1F5(){}
		
	public CromosomaP1F5(double tolerancia){
		N_GENES = 2;
		genes = new Gen[N_GENES];

		for(int i = 0; i < N_GENES; i++){	
			int longGen = longitudGen(tolerancia);
			this.genes[i] = new Gen(longGen);
			setLongitud(getLongitud() + longGen);
		}
	}
	
	/**
	 * Calcula la longitud del cromosoma
	 * @param precision Precisión o tolerancia que se utiliza para calcula la longitud del cromosoma
	 * @return Longitud que tendrá la cadena binaria
	 */
	public int longitudGen(double precision){
		double longAux = (Math.log10((1+((X_MAX - X_MIN) / precision))) / Math.log10(2)); //Calcula la longitud exacta
		int p_ent = (int) longAux; //Nos quedamos con la parte entera
		double p_dec = longAux - p_ent; //Obtenemos la parte decimal
		
		if(p_dec > 0) p_ent++;
		
		return p_ent;
	}
	
	@Override
	public double fenotipo(int pos) {
		double valor;
		fenotipo = 0;
		Gen gen = this.genes[pos];
		
		valor = X_MIN + ((X_MAX - X_MIN) * bin_dec(gen)) / (Math.pow(2, gen.getLongAlelo()) - 1);
		fenotipo += valor;

		return fenotipo;
	}

	@Override
	public double evalua() {
		double suma1, suma2;
		double x1 = fenotipo(0);
		double x2 = fenotipo(1);
		double fit; 
		
		suma1 = sumatorio(x1);
		suma2 = sumatorio(x2);
		fit = (suma1 * suma2);
		fitness = fit;
		
		return fit;
	}

	private double sumatorio(double x) {
		double suma = 0;
		
		for(int i = 1; i <= MAX_I; i++){
			suma += (i * Math.cos((i + 1) * x + i));
		}
		
		return suma;
	}

	@Override
	public String toString() {
		String cadena = "";
		
		cadena += ("x1 = " + fenotipo(0) + "\n");
		cadena += ("x2 = " + fenotipo(1) + "\n");
		cadena += ("f(xi) = " + fitness + "\n");
		
		return cadena;
	}

	@Override
	public Cromosoma copia() {
		CromosomaP1F5 aux = new CromosomaP1F5();
		aux.fitness = this.fitness;
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
