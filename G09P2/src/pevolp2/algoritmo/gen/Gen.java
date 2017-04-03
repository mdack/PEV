package pevolp2.algoritmo.gen;
import java.util.Random;


public class Gen {

	private boolean[] gen;
	private int longitud;
	
	public Gen(){}
	
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
	
	public void setPosAlelo(int pos, boolean value){
		this.gen[pos] = value;
	}
	
	public boolean getPosAlelo(int pos){
		return this.gen[pos];
	}
	public boolean[] getAlelo(){ return gen; }
	public int getLongAlelo(){ return longitud; }
	public Gen copia(){
		Gen aux = new Gen();
		aux.longitud = this.longitud;
		aux.gen = new boolean[longitud];
		for(int i = 0; i < longitud; i++) aux.gen[i] = this.gen[i];
		return aux;
	}
}
