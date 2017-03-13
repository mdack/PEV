
public class CromosomaP1F5 extends Cromosoma {
	
	public static final int N_GENES = 2;
	private static final double X_MIN = -10;
	private static final double X_MAX = 10;
		
	public CromosomaP1F5(double tolerancia){
		genes = new Gen[N_GENES];

		for(int i = 0; i < N_GENES; i++){	
			int longGen = longitudGen(tolerancia);
			this.genes[i] = new Gen(longGen);
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evalua(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
