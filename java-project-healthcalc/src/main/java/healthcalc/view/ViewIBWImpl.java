package healthcalc.view;

import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;

public class ViewIBWImpl extends JPanel implements ViewIBW {

	private static final long serialVersionUID = 1L;
	private JTextField txtAltura;
	private JRadioButton rbHombre;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblResultado;
	private JLabel lblMensajeError;
	private JButton btnCalcular;
	
	public ViewIBWImpl() {
		setLayout(new BorderLayout(10, 10));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		
		JLabel lblTitulo = new JLabel("Peso corporal ideal (Fórmula de Lorentz)");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, BorderLayout.NORTH);
		
		JPanel panelEnvoltorio = new JPanel();
		panelEnvoltorio.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		add(panelEnvoltorio, BorderLayout.CENTER);
				
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2, 10, 15));
		panelEnvoltorio.add(panel); 
		
		JLabel lblNewLabel_2 = new JLabel("Altura (cm):");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		txtAltura = new JTextField();
		panel.add(txtAltura);
		txtAltura.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Género:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_4);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panel.add(panel_1);
		
		rbHombre = new JRadioButton("Hombre");
		rbHombre.setSelected(true);
		buttonGroup.add(rbHombre);
		panel_1.add(rbHombre);
		
		JRadioButton rbMujer = new JRadioButton("Mujer");
		buttonGroup.add(rbMujer);
		panel_1.add(rbMujer);
		
		JLabel lblNewLabel_5 = new JLabel("PCI:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_5);
		
		lblResultado = new JLabel("---");
		lblResultado.setBorder(new CompoundBorder(new LineBorder(new Color(64, 64, 64), 2), new EmptyBorder(5, 5, 5, 5)));
		lblResultado.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblResultado);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(5, 5));
		
		lblMensajeError = new JLabel("");
		lblMensajeError.setForeground(Color.RED);
		lblMensajeError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeError.setBackground(Color.WHITE);
		panel_2.add(lblMensajeError, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.CENTER);
		
		btnCalcular = new JButton("Calcular");
		panel_3.add(btnCalcular);
	}

	@Override
	public double getHeightValue() {
		return Double.parseDouble(txtAltura.getText().trim().replace(",", "."));	
	}

	@Override
	public char getGender() {
		return rbHombre.isSelected() ? 'M' : 'W';
	}

	@Override
	public void setResult(double pci) {
		lblResultado.setText(String.format("%.2f kg", pci));
		lblMensajeError.setText("");
	}

	@Override
	public void setMessage(String msg) {
		lblMensajeError.setText(msg);
		lblResultado.setText("---");
	}

	@Override
	public void setController(ActionListener ctr) {
		btnCalcular.addActionListener(ctr);
		btnCalcular.setActionCommand("CalcularIBW");
	}
}