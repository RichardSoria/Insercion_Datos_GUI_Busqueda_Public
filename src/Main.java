import java.util.Scanner;
import java.sql.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner entrada = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/estudiantes";
        String user = "root";
        String password = "1899";

        String sql = "INSERT INTO estudiantes (nombre, apellido, cedula, correo, nota_1, nota_2, promedio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String opcion;

        while (true)
        {
            System.out.println("Presione 1 para ingresar un nuevo estudiante.");
            System.out.println("Presione 0 para salir.");
            System.out.print("Ingrese una opción: ");
            opcion = entrada.nextLine();

            if (opcion.equals("1"))
            {
                Estudiantes estudiante = new Estudiantes();
                System.out.print("Ingrese el nombre del estudiante: ");
                estudiante.setNombre(entrada.nextLine());
                System.out.print("Ingrese el apellido del estudiante: ");
                estudiante.setApellido(entrada.nextLine());

                String campo_cedula;

                while (true)
                {
                    System.out.print("Ingrese el cédula del estudiante: ");
                    campo_cedula = entrada.nextLine();

                    if (campo_cedula.length() == 10)
                    {
                        estudiante.setCedula(campo_cedula);
                        break;
                    }
                    else
                    {
                        System.out.println("Ingrese una cédula válida.");
                    }
                }

                Double campo_nota;

                while (true)
                {
                    System.out.print("Ingrese el nota 1 del estudiante: ");
                    campo_nota = entrada.nextDouble();

                    if (campo_nota >= 0 && campo_nota <= 20)
                    {
                        estudiante.setNota_1(campo_nota);
                        break;
                    }
                    else
                    {
                        System.out.println("Ingrese una nota válida.");
                    }
                }

                while (true)
                {
                    System.out.print("Ingrese el nota 2 del estudiante: ");
                    campo_nota = entrada.nextDouble();

                    if (campo_nota >= 0 && campo_nota <= 20)
                    {
                        estudiante.setNota_2(campo_nota);
                        break;
                    }
                    else
                    {
                        System.out.println("Ingrese una nota válida.");
                    }
                }

                estudiante.generarCorreo();
                estudiante.calcularPromedio();

                entrada.nextLine();

                try (Connection connection = DriverManager.getConnection(url, user, password);
                    PreparedStatement statement = connection.prepareStatement(sql))
                {
                    statement.setString(1, estudiante.getNombre());
                    statement.setString(2, estudiante.getApellido());
                    statement.setString(3, estudiante.getCedula());
                    statement.setString(4, estudiante.getCorreo());
                    statement.setDouble(5, estudiante.getNota_1());
                    statement.setDouble(6, estudiante.getNota_2());
                    statement.setDouble(7, estudiante.getPromedio());
                    statement.executeUpdate();

                    System.out.println("¡Estudiante registrado con éxito!");
                }
                catch (SQLException e)
                {
                    System.out.println("Error al registrar el estudiante: " + e.getMessage());
                }
            }
            else if (opcion.equals("0"))
            {
                break;
            }
            else
            {
                System.out.println("Ingrese un opción válida.");
            }
        }
    }
}