package pevolp2.algoritmo;
import java.io.File;
import java.util.Scanner;

public class Matrices {
	
	final static String ARCH_0 = "ajuste.dat";
	final static String ARCH_1 = "datos12.dat";
	final static String ARCH_2 = "datos15.dat";
	final static String ARCH_3 = "datos30.dat";
	
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
	        
	         // Lectura del fichero
	         String linea;
	         this.tamano = in.nextInt();
	         
	         in.nextLine();
	         
	         for(int i = 0; i < tamano; i++){
	        	 for(int j = 0; j < tamano; j++){
	        		flujo[i][j] = in.nextInt(); 
	        	 } 
	         }
	            
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	    	  try {
					if (in != null)
						in.close();
				} catch (Exception ex) {
					System.out.println("ERROR: " + ex.getMessage());
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
