
public class CromosomaP1F1 extends Cromosoma {
	
	public static final int numGenes = 1;
	
	public CromosomaP1F1(){
		this.genes = new Gen[numGenes];
		float fenotipo = 251f;
		while(fenotipo < -250f || fenotipo > 250f)
		{
			for(int i = 0; i < numGenes; i++)
			{
				this.genes[i] = new Gen(9);
				boolean[] alelo = this.genes[i].getAlelo();
				int longAlelo = this.genes[i].getLongAlelo();
				longAlelo--;
				while(longAlelo >= 0)
				{
					if(alelo[longAlelo]){
						float num = (float)Math.pow(2, longAlelo);
						fenotipo = fenotipo + num;
					}
					longAlelo--;
				}
			}
			fenotipo = fenotipo - 250;
		}
		this.fenotipo = fenotipo;
		
	}

	@Override
	public float fenotipo() {
		return this.fenotipo;
	}

	@Override
	public float evalua() {
		float fit = (float)(Math.abs(fenotipo * Math.sin(Math.sqrt(Math.abs(fenotipo)))) * -1);
		this.fitness = fit;
		return fit;
	}

}
