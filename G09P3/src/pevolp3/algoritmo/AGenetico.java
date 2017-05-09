package pevolp3.algoritmo;
import java.util.Random;
import java.util.Stack;

import pevolp3.algoritmo.cromosoma.Cromosoma;
import pevolp3.algoritmo.cromosoma.CromosomaP2;
import pevolp3.algoritmo.cruce.CX;
import pevolp3.algoritmo.cruce.CodOrdinal;
import pevolp3.algoritmo.cruce.Cruce;
import pevolp3.algoritmo.cruce.ERX;
import pevolp3.algoritmo.cruce.OX;
import pevolp3.algoritmo.cruce.OXOrden;
import pevolp3.algoritmo.cruce.OXPosiciones;
import pevolp3.algoritmo.cruce.PMX;
import pevolp3.algoritmo.cruce.Propio;
import pevolp3.algoritmo.mutacion.Heuristica;
import pevolp3.algoritmo.mutacion.Insercion;
import pevolp3.algoritmo.mutacion.Intercambio;
import pevolp3.algoritmo.mutacion.Inversion;
import pevolp3.algoritmo.mutacion.MutPropia;
import pevolp3.algoritmo.mutacion.Mutacion;
import pevolp3.algoritmo.seleccion.Estocastico;
import pevolp3.algoritmo.seleccion.Ruleta;
import pevolp3.algoritmo.seleccion.Torneo;
import pevolp3.presentacion.VistaPrincipal;
//hola
public class AGenetico {
	
	private static int[][] flujos;
	private static int[][] distancias;
	private static int n;
	
	private Cromosoma[] poblacion, elite; // Poblacion
	private int tamPob; // Tamaño de la poblacion
	private int numMaxGen; // Numero maximo de generaciones
	private Cromosoma elMejor; // Mejor cromosoma de la poblacion
	private Cromosoma elPeor;
	private Cromosoma elMejorAbs;
	private Cromosoma elPeorAbs;
	private int posMejor; // Posicion en la poblacion
	private int posPeor;
	private double probCruce; // Probabilidad de cruce
	private double probMut; // Probabilidad de mutacion
	private double probOperador; // Probabilidad de efecto de operador especial
	private double mejorAbs; // Fitness mejor absoluto.
	private double peorAbs;
	private int tamElite;
	private int tipoCruce;
	private boolean maximizar;
	private double sumaMedias;
	private int totalCruces;
	private int totalMutaciones;
	private int totalInversiones;
	
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
		sumaMedias = 0;
		totalCruces = 0;
		totalMutaciones = 0;
		totalInversiones = 0;
	}
	
	public void inicializar() {
		
		this.poblacion = new Cromosoma[tamPob];

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
		double worstFitness = Double.MIN_VALUE;
		double puntAcum = 0;

		for(int i = 0; i < tamPob; i++){
			double fit = poblacion[i].getFitness_bruto();
			fitnessBrutoAcum += fit;
			if(fit < bestFitness){
				bestFitness = fit;
				this.posMejor = i;
			}
			if(fit > worstFitness){
				worstFitness = fit;
				this.posPeor = i;
			}
			poblacion[i].setPunt(poblacion[i].getFitness_bruto()/sumaAdap);
			puntAcum += poblacion[i].getFitness_bruto()/sumaAdap;
			poblacion[i].setPuntAcum(puntAcum);
		}
		this.elMejor = this.poblacion[posMejor].copia();
		this.elPeor = this.poblacion[posMejor].copia();
		if(bestFitness < this.mejorAbs) {
			this.mejorAbs = bestFitness;
			elMejorAbs = poblacion[posMejor].copia();
		}
		if(worstFitness > this.peorAbs){
			this.peorAbs = worstFitness;
			this.elPeorAbs = poblacion[posPeor].copia();
		}
		
		media = fitnessBrutoAcum/tamPob;
		sumaMedias += media;
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
		int numMutaciones = 0;
		switch(tipo){
		case 0:
		{
			m = new Insercion(probMut);
			numMutaciones = m.mutar(poblacion);
			break;
		}
		case 1:
		{
			m = new Intercambio(probMut);
			numMutaciones = m.mutar(poblacion);
			break;
		}
		case 2:
		{
			m = new Inversion(probMut);
			numMutaciones = m.mutar(poblacion);
			break;
		}
		case 3:
		{
			m = new Heuristica(probMut);
			numMutaciones = m.mutar(poblacion);
			break;
		}
		case 4:
		{
			m = new MutPropia(probMut);
			numMutaciones = m.mutar(poblacion);
			break;
		}
		}
		this.totalMutaciones += numMutaciones;
	}
	
	public int getPosMejor(){
		return this.posMejor;
	}
	
	public int getNumMaxGen(){
		return this.numMaxGen;
	}
	
	public String toString() {
		String cadena = " ";
		
		cadena += "* Mejor absoluto: \n";
		cadena += "  - Permutación: ";
		cadena += elMejorAbs.toString() + "\n";
		cadena += "  - Coste óptimo: ";
		cadena += this.mejorAbs + "\n";
		cadena += "\n";
		
		cadena += "* Peor absoluto: \n";
		cadena += "  - Permutación: ";
		cadena += elPeorAbs.toString() + "\n";
		cadena += "  - Coste óptimo: ";
		cadena += this.peorAbs + "\n";
		cadena += "\n";
		
		cadena += "Media de fitness: \n";
		cadena += this.sumaMedias/this.numMaxGen + "\n\n";
		
		cadena += "Numero total de cruces: \n";
		cadena += this.totalCruces + "\n\n";
		
		cadena += "Numero total de mutaciones: \n";
		cadena += this.totalMutaciones + "\n\n";
		
		cadena += "Numero total de inversiones: \n";
		cadena += this.totalInversiones + "\n\n";
		
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
			this.totalCruces++;
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
				totalInversiones++;
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

}