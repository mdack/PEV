import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ALVistaPrincipal{
	
	int nGeneracion, tamPoblacion, funcion, n, tipoSel;
	double probCruce;
	double precision;
	double probMutacion;
	boolean elitismo = true;
	
	public ALVistaPrincipal(JTextField tnGen, JTextField ttamPob, JTextField tproCruce, JTextField tproMutacion,
			JTextField tprecision, JComboBox<String> cseleccion,JComboBox<String> celitismo,   JComboBox<String> cfuncion, JTextField tvalorN) {
		try{
			nGeneracion = Integer.parseInt(tnGen.getText());
			tamPoblacion = Integer.parseInt(ttamPob.getText());
			probCruce = Double.parseDouble(tproCruce.getText());
			precision = Double.parseDouble(tprecision.getText());
			n = Integer.parseInt(tvalorN.getText());
			
			if(probCruce < 100 && probCruce > 0){
				probMutacion = Double.parseDouble(tproMutacion.getText());
				if(probMutacion < 100 && probMutacion > 0){
					probCruce = probCruce/100;
					probMutacion = probMutacion/100;
					if(celitismo.getSelectedIndex() == 0){
						elitismo = false;
					}
					tipoSel = cseleccion.getSelectedIndex();
					funcion = cfuncion.getSelectedIndex();	
					
				}else{
					JOptionPane.showMessageDialog(new JFrame(),
						    "El porcentaje debe de ser entre 0 y 100%",
						    "Probabilidad de Cruce incorrecta",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				JOptionPane.showMessageDialog(new JFrame(),
					    "El porcentaje debe de ser entre 0 y 100%",
					    "Probabilidad de Cruce incorrecta",
					    JOptionPane.ERROR_MESSAGE);
			}
			
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(new JFrame(),
				    "Campos incorrectos",
				    "¡Deben ser números!",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

	public void action() {
		AGenetico AG = null;
		switch(funcion){
		case 0:
			AG = new AGeneticoP1F1(tamPoblacion, nGeneracion, probCruce, probMutacion, precision, elitismo, tipoSel);
			break;
		case 1:
			AG = new AGeneticoP1F2(tamPoblacion, nGeneracion, probCruce, probMutacion, precision, elitismo, tipoSel);
			break;
		case 2:
			AG = new AGeneticoP1F3(tamPoblacion, nGeneracion, probCruce, probMutacion, precision,elitismo, tipoSel);
			break;
		case 3:
			break;
		case 4:
			AG = new AGeneticoP1F5(tamPoblacion, nGeneracion, probCruce, probMutacion, precision,elitismo, tipoSel);
			break;
		
		}
		algoritmoGenetico(AG, funcion+1);
	}

	private void algoritmoGenetico(AGenetico aG, int f) {
		String cadena = "";
		cadena += "***************** Función " + f + " *********************\n";
		
		aG.inicializar();
		aG.evaluar();
		for(int i = 0; i < aG.getNumMaxGen(); i++)
		{	
			cadena += "--------------------------------------------------------------\n";
			cadena += ("* Generación " + (i+1) + "\n");
			cadena += "--------------------------------------------------------------\n";
			cadena += aG.toString();
			aG.seleccion(tipoSel);
			aG.reproduccion();
			aG.mutacion();
			aG.evaluar();
		}
		VistaPrincipal.addText(cadena);
	}
}
