
public class CromosomaP1F1 extends Cromosoma {
	
	public static final int N_GENES = 1;
	private static final float X_MIN = -250f;
	private static final float X_MAX = 250f;
	
	public CromosomaP1F1(double tolerancia){
		genes = new Gen[N_GENES];

		for(int i = 0; i < N_GENES; i++){	
			int longGen = longitudGen(tolerancia);
			this.genes[i] = new Gen(longGen);
		}		
	}
	
	private int longitudGen(double precision){
		double longAux = (Math.log10((1+((X_MAX - X_MIN) / precision))) / Math.log10(2)); //Calcula la longitud exacta
		int p_ent = (int) longAux; //Nos quedamos con la parte entera
		double p_dec = longAux - p_ent; //Obtenemos la parte decimal
		
		if(p_dec > 0) p_ent++;
		
		return p_ent;
	}

	public double fenotipo() {
		double valor;
		fenotipo = 0;
		
		for(int i = 0; i < N_GENES; i++){
			Gen gen = this.genes[i];
			valor = X_MIN + ((X_MAX - X_MIN) * bin_dec(gen)) / (Math.pow(2, gen.getLongAlelo()) - 1);
			fenotipo += valor;
		}
		return this.fenotipo;
	}

	private double bin_dec(Gen gen) {
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

	@Override
	public float evalua() {
		float fit = (float)(Math.abs(fenotipo * Math.sin(Math.sqrt(Math.abs(fenotipo)))) * -1);
		this.fitness = fit;
		return fit;
	}

}
