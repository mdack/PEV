package pevolp3.algoritmo;
import java.util.Random;
import pevolp3.algoritmo.arbol.Arbol;
import pevolp3.algoritmo.cromosoma.Cromosoma;
import pevolp3.algoritmo.cruce.Cruce;
import pevolp3.algoritmo.mutacion.Funcional;
import pevolp3.algoritmo.mutacion.Mutacion;
import pevolp3.algoritmo.mutacion.Permutacion;
import pevolp3.algoritmo.mutacion.Terminal;
import pevolp3.algoritmo.mutacion.XArbol;
import pevolp3.algoritmo.seleccion.Estocastico;
import pevolp3.algoritmo.seleccion.Ruleta;
import pevolp3.algoritmo.seleccion.Torneo;
import pevolp3.presentacion.VistaPrincipal;

public class AGenetico {
	
	private static int PROFUNDIDAD;
	
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
	private double mejorAbs; // Fitness mejor absoluto.
	private double peorAbs;
	private int tamElite;
	private int tipoCreacion;
	private int tipoMultiplexor;
	private boolean useIF;
	private double sumaMedias;
	private int totalCruces;
	private int totalMutaciones;
	
	public AGenetico(int poblacion, int generaciones, double porcCruces, double porcMutacion, boolean elitismo, int multiplexor, int tmutacion,int tinicializacion, boolean useIf, int profundidad){
		tamPob = poblacion;
		numMaxGen = generaciones;
		probCruce = porcCruces;
		probMut = porcMutacion;
		mejorAbs = Double.MAX_VALUE;
		tipoCreacion = tinicializacion;
		tipoMultiplexor = multiplexor;
		if(elitismo){
			tamElite = (int) (tamPob * 0.02);
			elite = new Cromosoma[tamElite];
		}
	
		sumaMedias = 0;
		totalCruces = 0;
		totalMutaciones = 0;
		useIF = useIf;
		PROFUNDIDAD = profundidad;
		if(tipoMultiplexor == 0) Cromosoma.terminales = Cromosoma.terminales6.clone();
		else Cromosoma.terminales = Cromosoma.terminales11.clone();
	}
	
	public void inicializar() {
		
		this.poblacion = new Cromosoma[tamPob];
		
		if(tipoCreacion != 2){
			poblacion[0] = new Cromosoma(PROFUNDIDAD, tipoCreacion, useIF, tipoMultiplexor);
	    	for(int k = 1; k < tamPob; k++){
	    		Cromosoma c = new Cromosoma(PROFUNDIDAD, tipoCreacion, useIF, tipoMultiplexor);   
	    		poblacion[k] = c.copia();
	    		
	    	}
		}else{
			int grupos = PROFUNDIDAD - 1;
			int prof = 2;
			int tamGrupo = tamPob/grupos + 1;
			int primGrupo = 0;
			int ultGrupo = 0;
			int mitadGrupo = 0;
			int k = 0;
			for(int i = 0; i < grupos; i++){
				primGrupo = k;
				ultGrupo = tamGrupo * (i+1);
				mitadGrupo = tamGrupo / 2 + primGrupo;
				while(k <= ultGrupo && k < tamPob){
					if(k <= mitadGrupo){
						Cromosoma c = new Cromosoma(prof, 0, useIF, tipoMultiplexor);
						poblacion[k] = c.copia();
					}
					else{
						Cromosoma c = new Cromosoma(prof, 1, useIF, tipoMultiplexor);
						poblacion[k] = c.copia();
					}
					k++;
				}
				prof++;
			}
		}


	}


	public double evaluar(){
		double sumaAdap;
		sumaAdap = revisar_adaptacion_minimizar();
		double media;
		double mediaTam;
		double tamAcum = 0;
		
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
			poblacion[i].setPunt(poblacion[i].getFitness()/sumaAdap);
			puntAcum += poblacion[i].getFitness()/sumaAdap;
			tamAcum += poblacion[i].getArbol().getNumNodos();
			poblacion[i].setPuntAcum(puntAcum);
		}
		this.elMejor = this.poblacion[posMejor].copia();
		this.setElPeor(this.poblacion[posMejor].copia());
		if(bestFitness < this.mejorAbs) {
			this.mejorAbs = bestFitness;
			elMejorAbs = poblacion[posMejor].copia();
		}
		if(worstFitness > this.peorAbs){
			this.peorAbs = worstFitness;
			this.elPeorAbs = poblacion[posPeor].copia();
		}
		
		media = fitnessBrutoAcum/tamPob;
		mediaTam = tamAcum/tamPob;
		sumaMedias += media;
		System.out.println(mediaTam);
		VistaPrincipal.addData(mejorAbs, elMejor.getFitness_bruto(), fitnessBrutoAcum/tamPob);
		
		return media;
	}
	
	public void Bloating(int tipo){
		
		if(tipo != 2){
		 double media = media();
		 double mediaTam = mediaTam();
		 
		 bloatingControl(media, mediaTam, tipo, 2);
		}
	}
	
	private double media(){
		double sum = 0;
		
		for(int i = 0; i < tamPob; i++)
			sum += poblacion[i].getFitness_bruto();
		
		return (sum / tamPob);
	}
	
	private double mediaTam(){
		double sum = 0;
		
		for(int i = 0; i < tamPob; i++)
			sum += poblacion[i].getArbol().getNumNodos();
		
		return (sum / tamPob);
	}
	
	public double revisar_adaptacion_minimizar(){
		double cmax = -1000000;
		
		for(int i = 0; i < tamPob; i++)
		{
			double fit = poblacion[i].getFitness();
			if(fit > cmax) cmax = fit;
		}
		double sumaAdap = 0;
		for(int i = 0; i < tamPob; i++)
		{
			double adapFit = cmax - poblacion[i].getFitness();
			sumaAdap += adapFit;
			poblacion[i].setFitness(adapFit);
		}
		return sumaAdap;
	}
	
	public void mutacion(int tipo) {
		Mutacion m = null;
		int numMutaciones = 0;
		switch(tipo){
		case 0:
		{
			m = new Funcional(probMut);
			numMutaciones = m.mutar(poblacion);
			break;
		}
		case 1:
		{
			m = new Terminal(probMut);
			numMutaciones = m.mutar(poblacion);
			break;
		}
		case 2:
		{
			m = new XArbol(probMut);
			numMutaciones = m.mutar(poblacion);
			break;
		}
		case 3:
		{
			m = new Permutacion(probMut);
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
		cadena += "  - Árbol: ";
		cadena += elMejorAbs.toString() + "\n";
		cadena += "  - Coste óptimo: ";
		cadena += this.mejorAbs + "\n";
		cadena += "\n";
		
		cadena += "* Peor absoluto: \n";
		cadena += "  - Árbol: ";
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
			Cruce c = new Cruce();
			Cromosoma[] hijos = c.cruzar(poblacion[padre1], poblacion[padre2]);
			nuevaPob[padre1] = hijos[0].copia();
			nuevaPob[padre2] = hijos[1].copia();
			this.totalCruces++;
		}
		for(int i = 0; i < tamPob; i++)
		{
			if(nuevaPob[i] == null) nuevaPob[i] = poblacion[i];
		}
		for(int i = 0; i < tamPob; i++)
			poblacion[i] = nuevaPob[i];
	}
		
	
	private void bloatingControl(double media, double mediaTam, int tipo, int n){
		if(tipo == 0)
		{
			tarpeianBloating(mediaTam, n);
		}
		else
			penaltyBloating(media, mediaTam);
	}
	
	private void penaltyBloating(double media, double mediaTam){
		double var = 0;
		double cov = 0;
		double k = 0;
		for(int i = 0; i < tamPob; i++)
		{
			Cromosoma c = poblacion[i];
			cov += (c.getFitness_bruto() - media) * (c.getArbol().getNumNodos() - mediaTam);
			var += (c.getArbol().getNumNodos() - mediaTam)*(c.getArbol().getNumNodos() - mediaTam);
		}
		var = var / tamPob;
		cov = cov / tamPob;
		k = cov / var;
		if(k != k) k = 0; // Comprobar que k es un numero (comprueba que k no tiene valor NaN).
		for(int i = 0; i < tamPob; i++)
		{
			Cromosoma c = poblacion[i];
			double fitness = c.getFitness_bruto() - k * c.getArbol().getNumNodos();
			c.setFitness(fitness);
		}
	}
	
	private void tarpeianBloating(double media, int n){
		double prob = 1 / n;
		Random rnd = new Random();
		for(int i = 0; i < tamPob; i++){
			Arbol a = poblacion[i].getArbol();
			if(a.getNumNodos() > media)
			{
				double p = rnd.nextDouble();
				if(p < prob)
				{
					poblacion[i] = new Cromosoma(PROFUNDIDAD, tipoCreacion, useIF, tipoMultiplexor);
				}
			}
		}
	}

	public Cromosoma getElPeor() {
		return elPeor;
	}

	public void setElPeor(Cromosoma elPeor) {
		this.elPeor = elPeor;
	}
	
}