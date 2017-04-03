package pevolp2.algoritmo.gen;
import java.util.Random;


public class Gen {

	private int[] gen;
	private int longitud;
	
	public Gen(){}
	
	public Gen(int tamGen, int n){
		gen = new int[tamGen];
		longitud = tamGen;
		
		for(int i = 0; i < tamGen; i++){
			Random rnd = new Random();
			int random = rnd.nextInt(n);
			gen[i] = random;
		
		}
	}
	
	public void setAlelo(int[] alelo){
		this.gen = alelo;
	}
	
	public void setPosAlelo(int pos, int value){
		this.gen[pos] = value;
	}
	
	public int getPosAlelo(int pos){
		return this.gen[pos];
	}
	public int[] getAlelo(){ return gen; }
	public int getLongAlelo(){ return longitud; }
	public Gen copia(){
		Gen aux = new Gen();
		aux.longitud = this.longitud;
		aux.gen = new int[longitud];
		for(int i = 0; i < longitud; i++) aux.gen[i] = this.gen[i];
		return aux;
	}
}
