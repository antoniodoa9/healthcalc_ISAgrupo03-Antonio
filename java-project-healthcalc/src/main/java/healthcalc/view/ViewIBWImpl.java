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
				
		JPanel panelContenedor = new JPanel();
		panelContenedor.setLayout(new BorderLayout(0, 10));
		panelEnvoltorio.add(panelContenedor);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2, 10, 15));
		panelContenedor.add(panel, BorderLayout.NORTH); 
		
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
		
		JPanel panel_AbajoFormulario = new JPanel();
		panel_AbajoFormulario.setLayout(new BorderLayout(0, 5));
		panelContenedor.add(panel_AbajoFormulario, BorderLayout.CENTER);
		
		lblMensajeError = new JLabel("");
		lblMensajeError.setForeground(Color.RED);
		lblMensajeError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeError.setBackground(Color.WHITE);
		panel_AbajoFormulario.add(lblMensajeError, BorderLayout.NORTH);

		JPanel panel_Resultado = new JPanel();
		panel_AbajoFormulario.add(panel_Resultado, BorderLayout.CENTER);
		panel_Resultado.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblResultado = new JLabel("PCI: ---");
		lblResultado.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblResultado.setBorder(new CompoundBorder(new LineBorder(new Color(64, 64, 64), 2), new EmptyBorder(5, 5, 5, 5)));
		panel_Resultado.add(lblResultado);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		add(panel_2, BorderLayout.SOUTH);
		
		btnCalcular = new JButton("Calcular");
		panel_2.add(btnCalcular);
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
		lblResultado.setText(String.format("IBW: %.2f kg", pci));
		lblMensajeError.setText("");
	}

	@Override
	public void setMessage(String msg) {
		lblMensajeError.setText(msg);
		lblResultado.setText("IBW:---");
	}

	@Override
	public void setController(ActionListener ctr) {
		btnCalcular.addActionListener(ctr);
		btnCalcular.setActionCommand("CalcularIBW");
	}
}