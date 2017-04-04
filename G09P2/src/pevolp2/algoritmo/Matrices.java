package pevolp2.algoritmo;
import java.io.File;
import java.util.Scanner;

public class Matrices {
	
	final static String ARCH_0 = "data/ajuste.dat";
	final static String ARCH_1 = "data/datos12.dat";
	final static String ARCH_2 = "data/datos15.dat";
	final static String ARCH_3 = "data/datos30.dat";
	
	private int tamano;
	private int[][] flujo;
	private int[][] distancias;

	public Matrices() {
		tamano = 0;
		flujo = null;
		distancias = null;
	}
	
	public Matrices(int tipoFich){
		leeData(tipoFich);
	}
	
	private void leeData(int tipo){
		File archivo = null;
	    Scanner in = null;

	    try {
	     
	    	String file = chooseFile(tipo);
	        archivo = new File (file);
	        
	        in = new Scanner(archivo);
	  
	         this.tamano = in.nextInt();
	         
	         flujo = new int[tamano][tamano];
	         distancias = new int[tamano][tamano];
	         llenaMatriz(flujo, in);
	         llenaMatriz(distancias, in);
	            
	      }
	      catch(Exception e){
	    	  System.out.println(e.getMessage());
	      }finally{
	    	  try {
					if (in != null)
						in.close();
				} catch (Exception ex) {
					System.out.println("ERROR: " + ex.getMessage());
				}
	      }
	}

	
	
	private void llenaMatriz(int[][] m, Scanner in) {
        for(int i = 0; i < tamano; i++){
       	 for(int j = 0; j < tamano; j++){
       		m[i][j] = in.nextInt(); 

       	 } 
       }
	}

	private String chooseFile(int archivo) {
		String nombFile = " ";
		switch(archivo){
		case 0:
			nombFile = ARCH_0;
			break;
		case 1:
			nombFile = ARCH_1;
			break;
		case 2:
			nombFile = ARCH_2;
			break;
		case 3:
			nombFile = ARCH_3;
			break;
		}
		return nombFile;
	}

}
