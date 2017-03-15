
public class CromosomaP1F2 extends Cromosoma {
	
	public static final int N_GENES = 2;
	private static final double X_MIN = -512;
	private static final double X_MAX = 512;
	
	public CromosomaP1F2(double tolerancia){
		genes = new Gen[N_GENES];
		
		for(int i = 0; i < N_GENES; i++){	
			int longGen = longitudGen(tolerancia);
			this.genes[i] = new Gen(longGen);
			longitud += longGen;
		}		
		this.fenotipo = fenotipo(0);
		this.fitness = evalua();
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

	@Override
	public double evalua() {
		double x1 = fenotipo(0);
		double x2 = fenotipo(1);
		
		double fit = (-(x2 + 47)*Math.sin(Math.sqrt(Math.abs(x2+(x1/2)+47)))) - (x1*Math.sin(Math.sqrt(Math.abs(x1 - (x2+47)))));
		this.fitness = fit;
		return fit;
	}

	@Override
	public String toString() {
		String cadena = "";
		cadena += ("x1 = " + fenotipo(0) + "\n");
		cadena += ("x2 = " + fenotipo(1) + "\n");
		cadena += ("f(x1,x2) = " + this.fitness + "\n");
		return cadena;
	}

}
