/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ducatimotos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author walla
 */
public class Conecxao {
    // Atributos de conecxcao ligados as Lib's importadas
    public Connection con = null;
    public Statement stmt = null;
    public ResultSet resultSet = null;

    // Atributos de conecxao
    private final String servidor = "jdbc:mysql://127.0.0.1:3306/db_DucatiMotos"; // Serviddor de db
    private final String usuario = "root"; // Usuario do db
    private final String senha = ""; // Senha do db
    private final String driver = "com.mysql.cj.jdbc.Driver"; // Driver de conecxao

    // Método de abertura de conecxao com db
    public Connection openConnection() {
        try{
            Class.forName(driver); // Driver de utilização

            // Atritutos de conecxao
            con = DriverManager.getConnection(servidor, usuario, senha);
            stmt = con.createStatement();

            System.out.println("Conecxão aberta com sucesso!");
        } catch ( ClassNotFoundException | SQLException e ) {
            System.out.println("Erro ao acessar db, verifique! " + e.getMessage());
        }
        return con; // Return de conecxao
    }

    // Método de fechamento de conecxao com db
    public void closeConnection() {
        try{
            con.close();

            System.out.println("Conecxão finalizada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao encerrar conecxão, verifique! " + e.getMessage());
        }
    }
}
