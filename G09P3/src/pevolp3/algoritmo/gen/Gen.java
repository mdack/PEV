package pevolp3.algoritmo.gen;

public class Gen {

	private Arbol gen;
	private int longitud;
	
	public Gen(){}
	
	public Gen(int n){
		longitud = 0;
		gen = new Arbol();
		
	}
		
	public void setAlelo(int alelo){
//		this.gen = alelo;
	}
	
	public Arbol getAlelo(){ return gen; }
	public int getLongAlelo(){ return longitud; }
	
	public Gen copia(){
		Gen aux = new Gen();
		aux.longitud = this.longitud;
		aux.gen = this.gen;
		return aux;
	}
}
