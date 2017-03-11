import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.math.plot.Plot2DPanel;

public class VistaPrincipal extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lnGen, ltamPob, lproCruce, lproMutacion, lprecision, lfuncion, lvalorN,lelitismo, lseleccion;
	private JTextField tnGen, ttamPob, tproCruce, tproMutacion, tprecision;
	private JComboBox<String> cFuncion, cseleccion, cvalorN, celitismo;
	private JButton button;
	private JTextArea area;
	private JScrollPane scroll;
	private JProgressBar progressBar;
	Plot2DPanel plot;
	JPanel window;
	
	public VistaPrincipal(){
		initComponents();
		
		addTop();	
		addCenter();		
		addWest();
		addEast();
		addBottom();
		
		this.add(window);
	}

	private void initComponents() {
		this.setTitle("Practica 1 - PEV");
		this.setMinimumSize(new Dimension(1000,700));	
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setLocationRelativeTo(null);
				
		window = new JPanel();
		window.setLayout(new BorderLayout());
		
		//label
		lnGen = new JLabel("N�mero de Generaciones:");
		ltamPob = new JLabel("Tama�o de la Poblaci�n:");
		lproCruce = new JLabel("Probabilidad de cruce:");
		lproMutacion = new JLabel("Probabilidad de Mutaci�n:");
		lprecision = new JLabel("Precisi�n:");
		lfuncion = new JLabel("Funci�n a optimizar:");
		lvalorN = new JLabel("Valor de n:");
		lseleccion = new JLabel("Tipo de selecci�n");
		lelitismo = new JLabel("Selecci�n por elitismo");
		
	
		//texto
		tnGen = new JTextField();
		ttamPob = new JTextField();
		tproCruce = new JTextField();
		tproMutacion = new JTextField();
		tprecision = new JTextField();
		
		//combos
		cFuncion = new JComboBox<String>();
		cFuncion.addItem("Funci�n 1");
		cFuncion.addItem("Funci�n 2");
		cFuncion.addItem("Funci�n 3");
		cFuncion.addItem("Funci�n 4");
		cFuncion.addItem("Funci�n 5");
		
		cseleccion = new JComboBox<String>();
		cseleccion.addItem("Ruleta");
		cseleccion.addItem("Torneo");
		
		cvalorN = new JComboBox<String>();
		cvalorN.addItem("1");
		
		celitismo = new JComboBox<String>();
		celitismo.addItem("Si");
		celitismo.addItem("No");
		
		//Grafica
		plot = new Plot2DPanel();
		
		//textArea
		area = new JTextArea();
		area.setColumns(20);
		area.setRows(40);
		scroll = new JScrollPane();
		scroll.setViewportView(area);
		
		//boton
		button = new JButton("Comenzar");
		
		//barra de progreso
		progressBar = new JProgressBar();

	}

	private void addEast() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		panel.add(area);
		
		window.add(panel, BorderLayout.EAST);		
	}

	private void addWest() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(9, 2, 25, 25));
		
		panel.add(lnGen);
		panel.add(tnGen);
		
		panel.add(ltamPob);
		panel.add(ttamPob);
		
		panel.add(lproCruce);
		panel.add(tproCruce);
		
		panel.add(lproMutacion);
		panel.add(tproMutacion);
		
		panel.add(lprecision);
		panel.add(tprecision);
		
		panel.add(lfuncion);
		panel.add(cFuncion);
		
		panel.add(lvalorN);
		panel.add(cvalorN);
		
		panel.add(lseleccion);
		panel.add(cseleccion);
		
		panel.add(lelitismo);
		panel.add(celitismo);
		
		window.add(panel, BorderLayout.WEST);
	}

	private void addCenter() {
		plot.addLegend("SOUTH");
		
		window.add(plot, BorderLayout.CENTER);
	}

	private void addBottom() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		panel.add(button);
		button.addActionListener(new ALVistaPrincipal(tnGen, ttamPob,tproCruce, tproMutacion, tprecision, cseleccion, celitismo, this));
		
		window.add(panel, BorderLayout.SOUTH);
	}

	private void addTop() {
		 progressBar.setValue(25);
		 progressBar.setStringPainted(true);
		 Border border = BorderFactory.createTitledBorder("Reading...");
		 progressBar.setBorder(border);
		 
		 window.add(progressBar, BorderLayout.NORTH);

	}

}