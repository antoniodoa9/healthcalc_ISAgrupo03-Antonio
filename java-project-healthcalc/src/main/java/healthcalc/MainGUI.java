package healthcalc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
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
		frmHealthcalc.setBounds(100, 100, 600, 500);
		frmHealthcalc.setLocationRelativeTo(null);
		frmHealthcalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmHealthcalc.getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}

}
