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
        
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(panel, BorderLayout.CENTER);
        
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new BorderLayout(0, 10));
        panel.add(panel_1);
        
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new GridLayout(2, 2, 10, 15));
        panel_1.add(panel_2, BorderLayout.NORTH);
        
        JLabel lblNewLabel = new JLabel("Peso (kg):");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(lblNewLabel);
        
        txtPeso = new JTextField();
        panel_2.add(txtPeso);
        txtPeso.setColumns(13);
        
        JLabel lblNewLabel_1 = new JLabel("Altura (m):");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(lblNewLabel_1);
        
        txtAltura = new JTextField();
        panel_2.add(txtAltura);
        txtAltura.setColumns(13);
        
        JPanel panel_3 = new JPanel();
        panel_3.setLayout(new BorderLayout(0, 5));
        panel_1.add(panel_3, BorderLayout.CENTER);
        
        lblMensajeError = new JLabel("");
        lblMensajeError.setForeground(Color.RED);
        lblMensajeError.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensajeError.setBackground(Color.WHITE);
        panel_3.add(lblMensajeError, BorderLayout.NORTH);
        
        JPanel panel_4 = new JPanel();
        panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel_3.add(panel_4, BorderLayout.CENTER);
        
        JPanel panel_5 = new JPanel();
        panel_5.setLayout(new GridLayout(2, 1, 0, 10));
        panel_4.add(panel_5);
        
        lblResultado = new JLabel("BMI: ---");
        lblResultado.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblResultado.setBorder(new CompoundBorder(new LineBorder(new Color(64, 64, 64), 2), new EmptyBorder(5, 5, 5, 5)));
        panel_5.add(lblResultado);
        
        lblClasificacion = new JLabel("Clasificación: ---");
        lblClasificacion.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblClasificacion.setBorder(new CompoundBorder(new LineBorder(new Color(64, 64, 64), 2), new EmptyBorder(5, 5, 5, 5)));
        panel_5.add(lblClasificacion);
        
        JPanel panel_6 = new JPanel();
        panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(panel_6, BorderLayout.SOUTH);
        
        btnCalcular = new JButton("Calcular");
        panel_6.add(btnCalcular);
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