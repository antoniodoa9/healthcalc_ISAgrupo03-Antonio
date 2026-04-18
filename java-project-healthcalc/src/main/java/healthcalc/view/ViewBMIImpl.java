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
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;

public class ViewBMIImpl extends JPanel implements ViewBMI {

    private static final long serialVersionUID = 1L;
    private JTextField txtPeso;
    private JTextField txtAltura;
    private JLabel lblMensajeError;
    private JLabel lblResultado;
    private JLabel lblClasificacion;
    private JButton btnCalcular;

    public ViewBMIImpl() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Índice de masa corporal (BMI)");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelEnvoltorio = new JPanel();
        panelEnvoltorio.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(panelEnvoltorio, BorderLayout.CENTER);
        
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(new BorderLayout(0, 10));
        panelEnvoltorio.add(panelContenedor);
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(2, 2, 10, 15));
        panelContenedor.add(panelFormulario, BorderLayout.NORTH);
        
        JLabel lblPeso = new JLabel("Peso (kg):");
        lblPeso.setHorizontalAlignment(SwingConstants.CENTER);
        panelFormulario.add(lblPeso);
        
        txtPeso = new JTextField();
        panelFormulario.add(txtPeso);
        txtPeso.setColumns(10);
        
        JLabel lblAltura = new JLabel("Altura (m):");
        lblAltura.setHorizontalAlignment(SwingConstants.CENTER);
        panelFormulario.add(lblAltura);
        
        txtAltura = new JTextField();
        panelFormulario.add(txtAltura);
        txtAltura.setColumns(10);
        
        JPanel panelAbajoFormulario = new JPanel();
        panelAbajoFormulario.setLayout(new BorderLayout(0, 5));
        panelContenedor.add(panelAbajoFormulario, BorderLayout.CENTER);
        
        lblMensajeError = new JLabel("");
        lblMensajeError.setForeground(Color.RED);
        lblMensajeError.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensajeError.setBackground(Color.WHITE);
        panelAbajoFormulario.add(lblMensajeError, BorderLayout.NORTH);
        
        JPanel panelResultadosEnvoltorio = new JPanel();
        panelResultadosEnvoltorio.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelAbajoFormulario.add(panelResultadosEnvoltorio, BorderLayout.CENTER);
        
        JPanel panelResultado = new JPanel();
        panelResultado.setLayout(new GridLayout(2, 1, 0, 10));
        panelResultadosEnvoltorio.add(panelResultado);
        
        lblResultado = new JLabel("BMI: ---");
        lblResultado.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblResultado.setBorder(new CompoundBorder(new LineBorder(new Color(64, 64, 64), 2), new EmptyBorder(5, 5, 5, 5)));
        panelResultado.add(lblResultado);
        
        lblClasificacion = new JLabel("Clasificación: ---");
        lblClasificacion.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblClasificacion.setBorder(new CompoundBorder(new LineBorder(new Color(64, 64, 64), 2), new EmptyBorder(5, 5, 5, 5)));
        panelResultado.add(lblClasificacion);
        
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(panelBoton, BorderLayout.SOUTH);
        
        btnCalcular = new JButton("Calcular");
        panelBoton.add(btnCalcular);
    }
    
    @Override
    public double getWeight() {
        return Double.parseDouble(txtPeso.getText().trim().replace(",", "."));
    }

    @Override
    public double getHeightValue() {
        return Double.parseDouble(txtAltura.getText().trim().replace(",", "."));
    }

    @Override
    public void setResult(double bmi, String classification) {
        lblResultado.setText(String.format("BMI: %.2f", bmi));
        lblClasificacion.setText("Clasificación: " + classification);
        lblMensajeError.setText(""); 
    }

    @Override
    public void setMessage(String msg) {
        lblMensajeError.setText(msg);
        lblResultado.setText("BMI: ---");
        lblClasificacion.setText("Clasificación: ---");
    }

    @Override
    public void setController(ActionListener ctr) {
        btnCalcular.addActionListener(ctr);
        btnCalcular.setActionCommand("CalcularBMI");
    }
}