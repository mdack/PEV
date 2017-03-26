package pevolp1.algoritmo.cromosoma;

import pevolp1.algoritmo.gen.Gen;

public class CromosomaP1F3 extends Cromosoma {
	private static final double X_MIN = -3;
	private static final double X_MAX = 12.1;
	private static final double Y_MIN = 4.1;
	private static final double Y_MAX = 5.8;
	
	public CromosomaP1F3(){}
	
	public CromosomaP1F3(double tolerancia){
		N_GENES=2;
		genes = new Gen[N_GENES];
		
		for(int i = 0; i < N_GENES; i++){	
			int longGen = longitudGen(tolerancia, i);
			this.genes[i] = new Gen(longGen);
			setLongitud(getLongitud() + longGen);
		}
	}
	
	/**
	 * Calcula la longitud del cromosoma
	 * @param precision Precisión o tolerancia que se utiliza para calcula la longitud del cromosoma
	 * @return Longitud que tendrá la cadena binaria
	 */
	public int longitudGen(double precision, int gen){
		double longAux = 0;
		
		switch(gen){
		case 0:
			longAux = (Math.log10((1+((X_MAX - X_MIN) / precision))) / Math.log10(2)); //Calcula la longitud exacta
			break;
		case 1:
			longAux = (Math.log10((1+((Y_MAX - Y_MIN) / precision))) / Math.log10(2)); //Calcula la longitud exacta
			break;
		}

		int p_ent = (int) longAux; //Nos quedamos con la parte entera
		double p_dec = longAux - p_ent; //Obtenemos la parte decimal
		
		if(p_dec > 0) p_ent++;
		
		return p_ent;
	}
	
	public double fenotipo(int pos) {
		double valor;
		fenotipo = 0;
		Gen gen = this.genes[pos];
		
		switch(pos){
		case 0:
			valor = X_MIN + ((X_MAX - X_MIN) * bin_dec(gen)) / (Math.pow(2, gen.getLongAlelo()) - 1);
			fenotipo += valor;
			break;
		case 1:
			valor = Y_MIN + ((Y_MAX - Y_MIN) * bin_dec(gen)) / (Math.pow(2, gen.getLongAlelo()) - 1);
			fenotipo += valor;
			break;
		}

		return fenotipo;
	}

	@Override
	public double evalua() {
		double x = 0;
		double y = 0;
		
		x = fenotipo(0);
		y = fenotipo(1);
				
		double fit = 21.5 + x * Math.sin((4*Math.PI*x)) + y * Math.sin((20*Math.PI*y));	
		this.fitness = fit;
		
		return fit;
	}

	@Override
	public String toString() {
		String cadena = "";
		
		cadena += ("x = " + fenotipo(0) + "\n");
		cadena += ("y = " + fenotipo(1) + "\n");
		cadena += ("f(x,y) = " + fitness_bruto + "\n");
		
		return cadena;
	}

	@Override
	public Cromosoma copia() {
		CromosomaP1F3 aux = new CromosomaP1F3();
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
