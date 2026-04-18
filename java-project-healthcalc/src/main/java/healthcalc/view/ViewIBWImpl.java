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
		
		JLabel lblTitulo = new JLabel("Peso corporal ideal (IBW)");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		add(panel, BorderLayout.CENTER);
				
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0, 10));
		panel.add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new GridLayout(2, 2, 10, 15));
		panel_1.add(panel_2, BorderLayout.NORTH); 
		
		JLabel lblNewLabel_2 = new JLabel("Altura (cm):");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_2);
		
		txtAltura = new JTextField();
		panel_2.add(txtAltura);
		txtAltura.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Género:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_4);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panel_2.add(panel_3);
		
		rbHombre = new JRadioButton("Hombre");
		rbHombre.setSelected(true);
		buttonGroup.add(rbHombre);
		panel_3.add(rbHombre);
		
		JRadioButton rbMujer = new JRadioButton("Mujer");
		buttonGroup.add(rbMujer);
		panel_3.add(rbMujer);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(new BorderLayout(0, 5));
		panel_1.add(panel_4, BorderLayout.CENTER);
		
		lblMensajeError = new JLabel("");
		lblMensajeError.setForeground(Color.RED);
		lblMensajeError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeError.setBackground(Color.WHITE);
		panel_4.add(lblMensajeError, BorderLayout.NORTH);

		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblResultado = new JLabel("IBW: ---");
		lblResultado.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblResultado.setBorder(new CompoundBorder(new LineBorder(new Color(64, 64, 64), 2), new EmptyBorder(5, 5, 5, 5)));
		panel_5.add(lblResultado);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		add(panel_6, BorderLayout.SOUTH);
		
		btnCalcular = new JButton("Calcular");
		panel_6.add(btnCalcular);
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
		lblResultado.setText("IBW: ---");
	}

	@Override
	public void setController(ActionListener ctr) {
		btnCalcular.addActionListener(ctr);
		btnCalcular.setActionCommand("CalcularIBW");
	}
}