package pevolp2.algoritmo;
import java.util.Random;
import java.util.Stack;

import pevolp2.algoritmo.cromosoma.Cromosoma;
import pevolp2.algoritmo.cromosoma.CromosomaP2;
import pevolp2.algoritmo.cruce.CX;
import pevolp2.algoritmo.cruce.CodOrdinal;
import pevolp2.algoritmo.cruce.Cruce;
import pevolp2.algoritmo.cruce.ERX;
import pevolp2.algoritmo.cruce.OX;
import pevolp2.algoritmo.cruce.OXOrden;
import pevolp2.algoritmo.cruce.OXPosiciones;
import pevolp2.algoritmo.cruce.PMX;
import pevolp2.algoritmo.cruce.Propio;
import pevolp2.algoritmo.mutacion.Heuristica;
import pevolp2.algoritmo.mutacion.Insercion;
import pevolp2.algoritmo.mutacion.Intercambio;
import pevolp2.algoritmo.mutacion.Inversion;
import pevolp2.algoritmo.mutacion.MutPropia;
import pevolp2.algoritmo.mutacion.Mutacion;
import pevolp2.algoritmo.seleccion.Estocastico;
import pevolp2.algoritmo.seleccion.Ruleta;
import pevolp2.algoritmo.seleccion.Torneo;
import pevolp2.presentacion.VistaPrincipal;
//hola
public class AGenetico {
	
	private static int[][] flujos;
	private static int[][] distancias;
	private static int n;
	
	private Cromosoma[] poblacion, elite; // Poblacion
	private int tamPob; // Tamaño de la poblacion
	private int numMaxGen; // Numero maximo de generaciones
	private Cromosoma elMejor; // Mejor cromosoma de la poblacion
	private Cromosoma elMejorAbs;
	private int posMejor; // Posicion en la poblacion
	private double probCruce; // Probabilidad de cruce
	private double probMut; // Probabilidad de mutacion
	private double probOperador; // Probabilidad de efecto de operador especial
	private double mejorAbs; // Fitness mejor absoluto.
	private int tamElite;
	private int tipoCruce;
	private boolean maximizar;
	
	public AGenetico(int poblacion, int generaciones, double porcCruces, double porcMutacion, double porcOperador, boolean elitismo,int arch, int tcruce){
		tamPob = poblacion;
		numMaxGen = generaciones;
		probCruce = porcCruces;
		probMut = porcMutacion;
		probOperador = porcOperador;
		tipoCruce = tcruce;
		maximizar = false;
		mejorAbs = Double.MAX_VALUE;
		if(elitismo){
			tamElite = (int) (tamPob * 0.02);
			elite = new Cromosoma[tamElite];
		}
	
		Matrices m = new Matrices(arch);
		flujos = m.getFlujo();
		distancias = m.getDistancias();
		n = m.getTamano();
	}
	
	public void inicializar() {
		
		this.poblacion = new Cromosoma[tamPob];
//		int factorial = getFactorial(n); //Cantidad de permutaciones que se podrán utilizar
//		int tam; //Esto es para pruebas
//		if(n == 5)
//			tam = factorial;
//		else
//			tam = tamPob*10;
//		int[][] permutaciones = new int[tam][n]; //Aquí estarán todas las permutaciones posibles
//		int[] aux = new int[n];//Auxiliar que contendrá una permutación 
//		
//		//Inicializamos
//		for(int i = 0; i < n; i++){
//			aux[i] = i+1;
//			permutaciones[0][i] = i+1;
//		}
//		
//		//Generamos permutaciones
//		int j = 1;
//		boolean next = true;
//		while(next && j < tamPob){
//			next = nextPermutation(aux);
//			
//			if(next) {
//				for(int k = 0; k < n; k++){
//					permutaciones[j][k] = aux[k];
//				}
//			}
//			j++;
//		}
//		
//		boolean[] perm = new boolean[factorial/3]; //Para cromprobar que no se eligan las misma permutaciones
//		int pos;
//		for(int i = 0; i < tamPob && i < tam; i++){
//			poblacion[i] = new CromosomaP2(n);
//			do{ //Se elije al azar una permutación válida
//				pos = (int) (Math.random()*tamPob);
//			}while(perm[pos]);
//			
//			//Se copia la permutacion elegida en los genes de ese cromosoma 
//			for(int k = 0; k < n; k++){
//				poblacion[i].getGenes()[k].setAlelo(permutaciones[pos][k]);
//			}
//			//recalculamos el fitness
//			poblacion[i].setFitness_bruto(poblacion[i].evalua());
//			//marcamos esa posicion a true para no volver a coger esa permutacion
//			perm[pos] = true;
//		}
		poblacion[0] = new CromosomaP2(n);
    	for(int k = 1; k < tamPob; k++){
    		Cromosoma c = new CromosomaP2(n);
    		
    		while(buscaIndividuo(c)){
    			c = new CromosomaP2(n);
    		}
    		
    		poblacion[k] = c.copia();
    		
    	}


	}

	private boolean buscaIndividuo(Cromosoma c) {
		boolean enc = false;
		int i = 0;
		int j = 0;
		while(i < tamPob && !enc && poblacion[i] != null){
			enc = true;
			while(j < n && enc){
				if(c.getGenes()[j].getAlelo() != poblacion[i].getGenes()[j].getAlelo()) enc = false;    
				j++;
			}
			i++;
			j = 0;
			
		}
		return enc;
	}

	public double evaluar(){
		double sumaAdap;
		sumaAdap = revisar_adaptacion_minimizar();
		double media;
		
		double fitnessBrutoAcum = 0; // Para calcular la media mas tarde.
		double bestFitness = Double.MAX_VALUE;
		double puntAcum = 0;

		for(int i = 0; i < tamPob; i++){
			double fit = poblacion[i].getFitness_bruto();
			fitnessBrutoAcum += fit;
			if(fit < bestFitness){
				bestFitness = fit;
				this.posMejor = i;
			}
			poblacion[i].setPunt(poblacion[i].getFitness_bruto()/sumaAdap);
			puntAcum += poblacion[i].getFitness_bruto()/sumaAdap;
			poblacion[i].setPuntAcum(puntAcum);
		}
		this.elMejor = this.poblacion[posMejor].copia();
		if(bestFitness < this.mejorAbs) {
			this.mejorAbs = bestFitness;
			elMejorAbs = poblacion[posMejor].copia();
		}
		
		media = fitnessBrutoAcum/tamPob;
		VistaPrincipal.addData(mejorAbs, elMejor.getFitness_bruto(), fitnessBrutoAcum/tamPob);
		
		return media;
	}
	
	public double revisar_adaptacion_minimizar(){
		double cmax = -1000000;
		
		for(int i = 0; i < tamPob; i++)
		{
			double fit = poblacion[i].getFitness_bruto();
			if(fit > cmax) cmax = fit;
		}
		double sumaAdap = 0;
		for(int i = 0; i < tamPob; i++)
		{
			double adapFit = cmax - poblacion[i].getFitness_bruto();
			sumaAdap += adapFit;
			poblacion[i].setFitness(adapFit);
		}
		return sumaAdap;
	}
	
	public void mutacion(int tipo) {
		Mutacion m;
		switch(tipo){
		case 0:
		{
			m = new Insercion(probMut);
			m.mutar(poblacion);
			break;
		}
		case 1:
		{
			m = new Intercambio(probMut);
			m.mutar(poblacion);
			break;
		}
		case 2:
		{
			m = new Inversion(probMut);
			m.mutar(poblacion);
			break;
		}
		case 3:
		{
			m = new Heuristica(probMut);
			m.mutar(poblacion);
			break;
		}
		case 4:
		{
			m = new MutPropia(probMut);
			m.mutar(poblacion);
			break;
		}
		}
	}
	
	public int getPosMejor(){
		return this.posMejor;
	}
	
	public int getNumMaxGen(){
		return this.numMaxGen;
	}
	
	public String toString() {
		String cadena = " ";
		
		cadena += "* Mejor de generación: \n";
		cadena += "  - Permutación: ";
		cadena += this.elMejor.toString() + "\n";
		cadena += "  - Coste óptimo: ";
		cadena += elMejor.getFitness_bruto() + "\n";
		cadena += "\n";
		
		cadena += "* Mejor absoluto: \n";
		cadena += "  - Permutación: ";
		cadena += elMejorAbs.toString() + "\n";
		cadena += "  - Coste óptimo: ";
		cadena += this.mejorAbs + "\n";
		cadena += "\n";
		
		return cadena;
	}

	public void seleccionaElite() {
		ordenaPoblacion();
		int pos_elite = tamPob - tamElite;
		
		for(int i = 0; i < tamElite; i++){
			elite[i] = poblacion[pos_elite].copia();
			pos_elite++;
		}
	}

	private void ordenaPoblacion() {
		Cromosoma temp = null;
		for(int i = 0; i < tamPob; i++){
			for(int j=i+1; j < tamPob; j++){
				if(poblacion[i].getFitness_bruto() < poblacion[j].getFitness_bruto()){
					temp = poblacion[i];
					poblacion[i] = poblacion[j];
					poblacion[j] = temp;
				}
			}
		}
	}

	public void insertaElite() {
		ordenaPoblacion();
		
		for(int i = 0; i < tamElite; i++){
			poblacion[i] = elite[i].copia();
		}
	}
	
	public void seleccion(int tipo) {
		if(tipo == 0){
			poblacion = new Ruleta().selecciona(poblacion, tamPob);
		}else if(tipo == 1){
			poblacion = new Estocastico().selecciona(poblacion, tamPob);
		}else{
			poblacion = new Torneo(tipo).selecciona(poblacion, tamPob);
		}
	}


	public void reproduccion(){
		int selCruce[] = new int[tamPob];
		
		int numSelCruce = 0;
		double prob;
		Cromosoma hijo1 = nuevoCromosoma();
		Cromosoma hijo2 = nuevoCromosoma();
		Random rnd = new Random();
		
		for(int i = 0; i < tamPob; i++){
			prob = rnd.nextDouble();
			if(prob < probCruce){
				selCruce[numSelCruce] = i;
				numSelCruce++;
			}
		}
		if((numSelCruce % 2) == 1) numSelCruce--;
		Cromosoma[] nuevaPob = new Cromosoma[tamPob];
		
		for(int i = 0; i < numSelCruce; i += 2)
		{
			int padre1 = selCruce[i];
			int padre2 = selCruce[i+1];
			cruce(poblacion[padre1], poblacion[padre2], hijo1, hijo2, tipoCruce);
			nuevaPob[padre1] = hijo1.copia();
			nuevaPob[padre2] = hijo2.copia();
		}
		for(int i = 0; i < tamPob; i++)
		{
			if(nuevaPob[i] == null) nuevaPob[i] = poblacion[i].copia();
		}
		for(int i = 0; i < tamPob; i++)
			poblacion[i] = nuevaPob[i].copia();
	}
		
	private Cromosoma nuevoCromosoma() {
		return new CromosomaP2(n);
	}

	private void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2, int tipo)
	{
		Cruce c;
		switch(tipo)
		{
		case 0:
		{
			c = new PMX();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 1:
		{
			c = new OX();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 2:
		{
			c = new OXPosiciones();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 3:
		{
			c = new OXOrden();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 4:
		{
			c = new CX();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 5:
		{
			c = new ERX();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 6:
		{
			c = new CodOrdinal();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 7:
		{
			c = new Propio();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		}
		hijo1 = hijo1.copia();
		hijo2 = hijo2.copia();
	}
	
	public void operadorEspecial(){
		Random rnd = new Random();
		for(int i = 0; i < poblacion.length; i++)
		{
			double p = rnd.nextDouble();
			if(p < probOperador)
			{
				Cromosoma c = poblacion[i];
				int puntoA = rnd.nextInt(c.getNGenes());
				int puntoB = rnd.nextInt(c.getNGenes() - puntoA);
				puntoB += puntoA+1;
				Stack<Integer> s = new Stack<Integer>();
				for(int j = puntoA; j < puntoB; j++)
				{
					s.push(c.getGenes()[j].getAlelo());
				}
				for(int j = puntoA; j < puntoB; j++)
				{
					c.getGenes()[j].setAlelo(s.pop());
				}
				c.setFitness_bruto(c.evalua());
				if(c.getFitness_bruto() < poblacion[i].getFitness_bruto())
					poblacion[i] = c.copia();
			}
		}
	}
	
    public static int getFactorial (int n){
		int result;
		
		if(n==1||n==0)
			return 1;
 
		result = getFactorial(n-1)*n;
		return result;
	}

	public boolean isMaximizar() {
		return maximizar;
	}

	public static int[][] getFlujos() {
		return flujos;
	}

	public static int[][] getDistancias() {
		return distancias;
	}
	
	 private static boolean nextPermutation(int[] array) {

	        int i = array.length - 1;
	        while (i > 0 && array[i - 1] >= array[i]) {
	            i--;
	        }

	        if (i <= 0) {
	            return false;
	        }

	        int j = array.length - 1;
	        while (array[j] <= array[i - 1]) {
	            j--;
	        }

	        int temp = array[i - 1];
	        array[i - 1] = array[j];
	        array[j] = temp;

	        j = array.length - 1;
	        while (i < j) {
	            temp = array[i];
	            array[i] = array[j];
	            array[j] = temp;
	            i++;
	            j--;
	        }
	        return true;
	    }
}