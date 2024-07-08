package clases_estudiantes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class form_busqueda {
    public JPanel mainPanel;
    private JButton buscar_registros;
    private JTextField campo_cedula;
    private JTextArea datos_estudiantes;

    String url = "jdbc:mysql://localhost:3306/estudiantes";
    String user = "root";
    String password = "1899";
    String sql = "SELECT * FROM estudiantes WHERE cedula = ?";

    public form_busqueda() {
        buscar_registros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(campo_cedula.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Por favor, llene todos los campos.");
                    return;
                } else if (campo_cedula.getText().length() != 10)
                {
                    JOptionPane.showMessageDialog(null, "Ingrese una cédula válida.");
                    return;
                }

                try
                {
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    PreparedStatement sentencia = conexion.prepareStatement(sql);
                    sentencia.setString(1, campo_cedula.getText());
                    ResultSet resultado = sentencia.executeQuery();

                    if(resultado.next())
                    {
                        String nombre = resultado.getString("nombre");
                        String apellido = resultado.getString("apellido");
                        String cedula = resultado.getString("cedula");
                        String correo = resultado.getString("correo");
                        Double nota_1 = resultado.getDouble("nota_1");
                        Double nota_2 = resultado.getDouble("nota_2");
                        Double promedio = resultado.getDouble("promedio");

                        JOptionPane.showMessageDialog(null, "Estudiante encontrado" +
                                "\n" + "Nombre: " + nombre + "\n" + "Apellido: " + apellido + "\n" + "Cédula: " +
                                "" + cedula + "\n" + "Correo: " + correo + "\n" + "Nota 1: " + nota_1 + "\n" +
                                "Nota 2: " + nota_2 + "\n" + "Promedio: " + promedio);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "No se encontraron registros.");
                    }

                    conexion.close();
                } catch (SQLException ex)
                {
                    ex.printStackTrace();
                }


            }
        });
    }
}
