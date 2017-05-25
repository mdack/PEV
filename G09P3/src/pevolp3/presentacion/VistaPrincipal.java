package pevolp3.presentacion;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import org.math.plot.Plot2DPanel;

public class VistaPrincipal extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lnGen, ltamPob, lproCruce, lproMutacion, lfuncion, lelitismo, lseleccion, ltipoMut, lif, ltipoCreacion, lprofundidad;
	private JTextField tnGen, ttamPob, tproCruce, tproMutacion, tProfundidad;
	private JComboBox<String> cFuncion, cseleccion, celitismo, cmutacion, cif, ccreacion;
	private JButton button;
	private static JTextArea area;
	private JScrollPane scroll;
	private static JProgressBar progressBar;
	private static Plot2DPanel plot;
	private JPanel window, panelS, panelE, panelW;
	
	
	private static double[][] mejoresAbs;
	private static double[][] mejoresGen;
	private static double[][] mediasGen;
	private static int numGeneraciones;
	
	public VistaPrincipal(){
		initComponents();
		
		addTop();	
		addCenter();		
		addWest();
		addEast();
		addBottom();
		
		this.add(window);
	}
	
	public static void addText(String string){
		area.setText(string);
	}
	
	public static void addData(double mejorAbs, double mejorGen, double mediaGen)
	{
		double[][] newMejoresAbs = new double[numGeneraciones+1][2];
		double[][] newMejoresGen = new double[numGeneraciones+1][2];
		double[][] newMediasGen = new double[numGeneraciones+1][2];
		int p = 0, r;
		for(int i = 0; i < numGeneraciones; i++)
		{	
			p += 100/numGeneraciones;
			r = 100 % numGeneraciones;
			progressBar.setValue(p+r);
			newMejoresAbs[i][0] = mejoresAbs[i][0];
			newMejoresAbs[i][1] = mejoresAbs[i][1];
			newMejoresGen[i][0] = mejoresGen[i][0];
			newMejoresGen[i][1] = mejoresGen[i][1];
			newMediasGen[i][0] = mediasGen[i][0];
			newMediasGen[i][1] = mediasGen[i][1];
		}
		newMejoresAbs[numGeneraciones][0] = numGeneraciones;
		newMejoresAbs[numGeneraciones][1] = mejorAbs;
		newMejoresGen[numGeneraciones][0] = numGeneraciones;
		newMejoresGen[numGeneraciones][1] = mejorGen;
		newMediasGen[numGeneraciones][0] = numGeneraciones;
		newMediasGen[numGeneraciones][1] = mediaGen;
		
		mejoresAbs = newMejoresAbs;
		mejoresGen = newMejoresGen;
		mediasGen = newMediasGen;
		plot.removeAllPlots();
		plot.addLinePlot("Mejor absoluto", mejoresAbs);
		plot.addLinePlot("Mejor de generacion", mejoresGen);
		plot.addLinePlot("Media de generacion", mediasGen);
		numGeneraciones++;
	}

	private void initComponents() {
		this.setTitle("Practica 3 - PEV");
		this.setResizable(false);
		this.setMinimumSize(new Dimension(1200,650));	
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setLocationRelativeTo(null);
		
		numGeneraciones = 0;
		
		window = new JPanel();
		window.setLayout(new BorderLayout());
		panelE = new JPanel();
		panelW = new JPanel();
		panelS = new JPanel();
		
		//label
		lnGen = new JLabel("Número de Generaciones:");
		ltamPob = new JLabel("Tamaño de la Población:");
		lproCruce = new JLabel("Probabilidad de cruce:");
		lproMutacion = new JLabel("Probabilidad de Mutación:");
		lfuncion = new JLabel("Tipo de multiplexor:");
		lseleccion = new JLabel("Tipo de selección");
		lelitismo = new JLabel("Selección por elitismo");	
		ltipoMut = new JLabel("Tipo mutación: ");
		lif = new JLabel("¿Función IF?: ");
		ltipoCreacion = new JLabel("Creación de árboles:");
		lprofundidad = new JLabel("Máxima Profundidad: ");
	
		//texto
		tnGen = new JTextField();
		tnGen.setText("100");
		ttamPob = new JTextField();
		ttamPob.setText("100");
		tproCruce = new JTextField();
		tproCruce.setText("60");
		tproMutacion = new JTextField();
		tproMutacion.setText("10");
		this.tProfundidad = new JTextField();
		tProfundidad.setText("4");

		
		//combo
		cFuncion = new JComboBox<String>();
		cFuncion.addItem("6 Entradas");
		cFuncion.addItem("11 Entradas");
		
	
		cseleccion = new JComboBox<String>();
		cseleccion.addItem("Ruleta");
		cseleccion.addItem("Estocástico");
		cseleccion.addItem("T-Determinístico");
		cseleccion.addItem("T-Probabilístico");
		
		celitismo = new JComboBox<String>();
		celitismo.addItem("No");
		celitismo.addItem("Si");
		
		cmutacion = new JComboBox<String>();
		cmutacion.addItem("Funcional");
		cmutacion.addItem("Terminal");
		cmutacion.addItem("Árbol");
		cmutacion.addItem("Permutación");
				
		cif = new JComboBox<String>();
		cif.addItem("No");
		cif.addItem("Si");
		
		ccreacion = new JComboBox<String>();
		ccreacion.addItem("Creciente");
		ccreacion.addItem("Completa");
		ccreacion.addItem("Ramped H&H");
		
		//Grafica
		plot = new Plot2DPanel();
		
		//textArea
		area = new JTextArea();
		area.setColumns(20);
		area.setRows(31);
		scroll = new JScrollPane(area);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//boton
		button = new JButton("Comenzar");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				numGeneraciones = 0;
				plot.removeAllPlots();
				mejoresAbs = null;
				mejoresGen = null;
				mediasGen = null;
				new ALVistaPrincipal(tProfundidad, tnGen, ttamPob, tproCruce, tproMutacion, cif, cseleccion, celitismo, cFuncion, cmutacion, ccreacion).action();
				plot.setFixedBounds(0, 0, Integer.parseInt(tnGen.getText()));
			}
		});
		
		//barra de progreso
		progressBar = new JProgressBar();
		progressBar.setForeground(Color.BLUE);
	}

	private void addEast() {
		panelE.setLayout(new FlowLayout());
		panelE.setBorder(BorderFactory.createTitledBorder("Descripción"));
		
		panelE.add(scroll);
		
		window.add(panelE, BorderLayout.EAST);		
	}

	private void addWest() {
		panelW.setLayout(new GridLayout(11, 2, 25, 25));
		panelW.setBorder(BorderFactory.createTitledBorder("Parámetros"));
		
		panelW.add(lnGen);
		panelW.add(tnGen);
		
		panelW.add(ltamPob);
		panelW.add(ttamPob);
				
		panelW.add(lfuncion);
		panelW.add(cFuncion);
		
		panelW.add(lif);
		panelW.add(cif);
		
		panelW.add(ltipoCreacion);
		panelW.add(ccreacion);
		
		panelW.add(lprofundidad);
		panelW.add(tProfundidad);
		
		panelW.add(ltipoMut);
		panelW.add(cmutacion);
		
		panelW.add(lproCruce);
		panelW.add(tproCruce);
		
		panelW.add(lproMutacion);
		panelW.add(tproMutacion);
		
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
		
		panelS.add(button);
		
		window.add(panelS, BorderLayout.SOUTH);
	}

	private void addTop() {
		 progressBar.setStringPainted(true);
		 Border border = BorderFactory.createTitledBorder("Progreso");
		 progressBar.setBorder(border);
		 
		 window.add(progressBar, BorderLayout.NORTH);

	}

}