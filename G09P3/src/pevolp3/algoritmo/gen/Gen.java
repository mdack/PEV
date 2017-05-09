package pevolp3.algoritmo.gen;
import java.util.Random;


public class Gen {

	private int gen;
	private int longitud;
	
	public Gen(){}
	
	public Gen(int n){
		longitud = n;
		
		Random rnd = new Random();
		int random = rnd.nextInt(n)+1;
		gen = random;
		
	}
		
	public void setAlelo(int alelo){
		this.gen = alelo;
	}
	
	public int getAlelo(){ return gen; }
	public int getLongAlelo(){ return longitud; }
	
	public Gen copia(){
		Gen aux = new Gen();
		aux.longitud = this.longitud;
		aux.gen = this.gen;
		return aux;
	}
}
