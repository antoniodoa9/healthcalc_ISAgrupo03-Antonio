package healthcalc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.text.View;

import healthcalc.controller.CtrHB;
import healthcalc.controller.CtrIBW;
import healthcalc.view.ViewHBImpl;
import healthcalc.view.ViewIBWImpl;

import java.awt.BorderLayout;

public class MainGUI {

	private JFrame frmHealthcalc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frmHealthcalc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHealthcalc = new JFrame();
		frmHealthcalc.setTitle("HealthCalc");
		frmHealthcalc.setBounds(100, 100, 450, 400);
		frmHealthcalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmHealthcalc.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		HealthCalc model = new HealthCalcImpl();
		// ViewBMIImpl viewBMI = new ViewBMIImpl();
		ViewHBImpl viewHB = new ViewHBImpl();
		ViewIBWImpl viewIBW = new ViewIBWImpl();
		// CtrBMI ctrBMI = new CtrBMI(model, viewBMI);
		CtrIBW ctrIBW = new CtrIBW(model, viewIBW);
		CtrHB ctrHB = new CtrHB(model, viewHB);
		// viewBMI.setController(ctrBMI);
		viewIBW.setController(ctrIBW);
		viewHB.setController(ctrHB);
		// tabbedPane.addTab("BMI", viewBMI);
		tabbedPane.addTab("IBW", viewIBW);
		tabbedPane.addTab("TMB", viewHB);
		
	}

}
