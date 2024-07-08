package clases_estudiantes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class form_estudiantes extends Estudiantes
{

    public JPanel mainPanel;
    private JButton boton_registrar;
    private JTextField campo_nombre;
    private JTextField campo_apellido;
    private JTextField campo_cedula;
    private JTextField campo_nota_1;
    private JTextField campo_nota_2;
    private JButton mostrar_registros;

    String url = "jdbc:mysql://localhost:3306/estudiantes";
    String user = "root";
    String password = "1899";
    String sql = "INSERT INTO estudiantes (nombre, apellido, cedula, correo, nota_1, nota_2, promedio) VALUES (?, ?, ?, ?, ?, ?, ?)";


    public form_estudiantes() {
        boton_registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Estudiantes estudiante = new Estudiantes();

                if (campo_nombre.getText().isEmpty() || campo_apellido.getText().isEmpty() || campo_cedula.getText().isEmpty()
                        || campo_nota_1.getText().isEmpty() || campo_nota_2.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Por favor, llene todos los campos.");
                    return;
                }

                if (campo_cedula.getText().length() != 10)
                {
                    JOptionPane.showMessageDialog(null, "Ingrese una cédula válida.");
                    return;
                }

                if (Double.parseDouble(campo_nota_1.getText()) < 0 || Double.parseDouble(campo_nota_1.getText()) > 20)
                {
                    JOptionPane.showMessageDialog(null, "Ingrese una nota válida.");
                    return;
                }

                if (Double.parseDouble(campo_nota_2.getText()) < 0 || Double.parseDouble(campo_nota_2.getText()) > 20)
                {
                    JOptionPane.showMessageDialog(null, "Ingrese una nota válida.");
                    return;
                }

                estudiante.setNombre(campo_nombre.getText());
                estudiante.setApellido(campo_apellido.getText());
                estudiante.setCedula(campo_cedula.getText());
                estudiante.setNota_1(Double.parseDouble(campo_nota_1.getText()));
                estudiante.setNota_2(Double.parseDouble(campo_nota_2.getText()));
                estudiante.generarCorreo();
                estudiante.calcularPromedio();

                try
                {
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    PreparedStatement declaracion = conexion.prepareStatement(sql);
                    declaracion.setString(1, estudiante.getNombre());
                    declaracion.setString(2, estudiante.getApellido());
                    declaracion.setString(3, estudiante.getCedula());
                    declaracion.setString(4, estudiante.getCorreo());
                    declaracion.setDouble(5, estudiante.getNota_1());
                    declaracion.setDouble(6, estudiante.getNota_2());
                    declaracion.setDouble(7, estudiante.getPromedio());
                    declaracion.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Estudiante registrado correctamente.");
                    conexion.close();
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null, "Error al registrar el estudiante." + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        mostrar_registros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFrame frame = new JFrame("Buscador de Estudiantes");
                frame.setContentPane(new form_busqueda().mainPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}