import java.util.Random;


public class Gen {

	private boolean[] gen;
	private int longitud;
	
	public Gen(int tamGen){
		gen = new boolean[tamGen];
		longitud = tamGen;
		
		for(int i = 0; i < tamGen; i++){
			Random rnd = new Random();
			float random = rnd.nextFloat();
			
			if(1-random > 0.5f){
				gen[i] = false;
			}
			else
			{
				gen[i] = true;
			}
		
		}
	}
	
	public void setAlelo(boolean[] alelo){
		this.gen = alelo;
	}
	
	public boolean[] getAlelo(){ return gen; }
	public int getLongAlelo(){ return longitud; }
}
