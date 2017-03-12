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
	JPanel window, panelN, panelS, panelE, panelW;
	
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
		this.setResizable(false);
		this.setMinimumSize(new Dimension(1500,650));	
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setLocationRelativeTo(null);
				
		window = new JPanel();
		window.setLayout(new BorderLayout());
		panelE = new JPanel();
		panelN = new JPanel();
		panelW = new JPanel();
		panelS = new JPanel();
		
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
		area.setRows(31);
		scroll = new JScrollPane();
		scroll.setViewportView(area);
		
		//boton
		button = new JButton("Comenzar");
		
		//barra de progreso
		progressBar = new JProgressBar();
	}

	private void addEast() {
		panelE.setLayout(new FlowLayout());
		panelE.setBorder(BorderFactory.createTitledBorder("Descripci�n"));
		
		panelE.add(area);
		
		window.add(panelE, BorderLayout.EAST);		
	}

	private void addWest() {
		panelW.setLayout(new GridLayout(9, 2, 35, 35));
		panelW.setBorder(BorderFactory.createTitledBorder("Par�metros"));
		
		panelW.add(lnGen);
		panelW.add(tnGen);
		
		panelW.add(ltamPob);
		panelW.add(ttamPob);
		
		panelW.add(lproCruce);
		panelW.add(tproCruce);
		
		panelW.add(lproMutacion);
		panelW.add(tproMutacion);
		
		panelW.add(lprecision);
		panelW.add(tprecision);
		
		panelW.add(lfuncion);
		panelW.add(cFuncion);
		
		panelW.add(lvalorN);
		panelW.add(cvalorN);
		
		panelW.add(lseleccion);
		panelW.add(cseleccion);
		
		panelW.add(lelitismo);
		panelW.add(celitismo);
		
		window.add(panelW, BorderLayout.WEST);
	}

	private void addCenter() {
		plot.addLegend("SOUTH");
		
		window.add(plot, BorderLayout.CENTER);
	}

	private void addBottom() {
		panelS.setLayout(new FlowLayout());
		
		button.addActionListener(new ALVistaPrincipal(tnGen, ttamPob,tproCruce, tproMutacion, tprecision, cseleccion, celitismo, cFuncion, this));
		panelS.add(button);
		
		window.add(panelS, BorderLayout.SOUTH);
	}

	private void addTop() {
		 progressBar.setValue(25);
		 progressBar.setStringPainted(true);
		 Border border = BorderFactory.createTitledBorder("Reading...");
		 progressBar.setBorder(border);
		 
		 window.add(progressBar, BorderLayout.NORTH);

	}

}