import java.util.Random;


public class Gen {

	private boolean[] alelo;
	private int longAlelo;
	
	public Gen(int tamGen){
		alelo = new boolean[tamGen];
		longAlelo = tamGen;
		for(int i = 0; i < tamGen; i++){
			Random rnd = new Random();
			float random = rnd.nextFloat();
			if(1-random > 0.5f){
				alelo[i] = false;
			}
			else
			{
				alelo[i] = true;
			}
		}
	}
	
	public boolean[] getAlelo(){ return alelo; }
	public int getLongAlelo(){ return longAlelo; }
}
