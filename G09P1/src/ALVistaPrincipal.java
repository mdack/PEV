import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ALVistaPrincipal{
	
	int nGeneracion, tamPoblacion, funcion;
	double probCruce;
	double precision;
	double probMutacion;
	boolean esRuleta = true, elitismo = true;
	
	
	public ALVistaPrincipal(JTextField tnGen, JTextField ttamPob, JTextField tproCruce, JTextField tproMutacion,
			JTextField tprecision, JComboBox<String> cseleccion,JComboBox<String> celitismo,   JComboBox<String> cfuncion) {
		try{
			nGeneracion = Integer.parseInt(tnGen.getText());
			tamPoblacion = Integer.parseInt(ttamPob.getText());
			probCruce = Double.parseDouble(tproCruce.getText());
			precision = Double.parseDouble(tprecision.getText());
			if(probCruce < 100 && probCruce > 0){
				probMutacion = Double.parseDouble(tproMutacion.getText());
				if(probMutacion < 100 && probMutacion > 0){
					probCruce = probCruce/100;
					probMutacion = probMutacion/100;
					if(cseleccion.getSelectedIndex() == 1){
						esRuleta = false;
					}
					if(celitismo.getSelectedIndex() == 1){
						elitismo = false;
					}
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
		int i = 0;
		AGenetico algoritmo = null;
		switch(funcion){
		case 0:
			algoritmo = new AGeneticoP1F1(tamPoblacion, nGeneracion, probCruce, probMutacion, precision);
			break;
		case 1:
			break;
		case 2:
			algoritmo = new AGeneticoP1F3(tamPoblacion, nGeneracion, probCruce, probMutacion, precision);
			algoritmo.inicializar();
			algoritmo.evaluar();
			while(i < nGeneracion){
				algoritmo.seleccion(0);
			}
			break;
		case 3:
			break;
		case 4:
			break;
		
	}
	}
}
