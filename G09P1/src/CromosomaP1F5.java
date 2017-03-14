
public class CromosomaP1F5 extends Cromosoma {
	
	public static final int N_GENES = 2;
	private static final double X_MIN = -10;
	private static final double X_MAX = 10;
	private static final int MAX_I = 5;
		
	public CromosomaP1F5(double tolerancia){
		genes = new Gen[N_GENES];

		for(int i = 0; i < N_GENES; i++){	
			int longGen = longitudGen(tolerancia);
			this.genes[i] = new Gen(longGen);
			longitud += longGen;
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
				
		suma1 = sumatorio(x1);
		suma2 = sumatorio(x2);
		
		return (suma1 * suma2);
	}

	private double sumatorio(double x) {
		double suma = 0;
		
		for(int i = 0; i < MAX_I; i++){
			suma += (i * Math.cos((i + 1) * x + i));
		}
		
		return suma;
	}

}
