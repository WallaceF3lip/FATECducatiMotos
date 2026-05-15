/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ducatimotos;

import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author walla
 */
public class DucatiMotos {
    //Criação de Atributos
    private int ID;
    private int Ano;
    private int Cilindrada;
    private double Preço;
    private String Cor;
    private String Modelo;

    private boolean resultCreate;
    private boolean resultRead;
    private boolean resultUpdate;
    private boolean resultDelete;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAno() {
        return Ano;
    }

    public void setAno(int Ano) {
        this.Ano = Ano;
    }

    public int getCilindrada() {
        return Cilindrada;
    }

    public void setCilindrada(int Cilindrada) {
        this.Cilindrada = Cilindrada;
    }

    public double getPreço() {
        return Preço;
    }

    public void setPreço(double Preço) {
        this.Preço = Preço;
    }

    public String getCor() {
        return Cor;
    }

    public void setCor(String Cor) {
        this.Cor = Cor;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String Modelo) {
        this.Modelo = Modelo;
    }

    public ArrayList<DucatiMotos> readMotos() {
        Conecxao db = new Conecxao();
        ArrayList<DucatiMotos> listaMotos = new ArrayList<>();
        
        try {
            db.openConnection();

            String query = "SELECT * FROM Motos";
            db.resultSet = db.stmt.executeQuery(query);

            while (db.resultSet.next()) {
                DucatiMotos moto = new DucatiMotos();
                moto.setID(db.resultSet.getInt("ID"));
                moto.setModelo(db.resultSet.getString("Modelo"));
                moto.setCor(db.resultSet.getString("Cor"));
                moto.setAno(db.resultSet.getInt("Ano"));
                moto.setCilindrada(db.resultSet.getInt("Cilindrada"));
                moto.setPreço(db.resultSet.getDouble("Preco"));
                listaMotos.add(moto);
            }
          
            db.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        return listaMotos;
    }

    public boolean readMotoByID(int ID){
        Conecxao db = new Conecxao();
        
        try {
            db.openConnection();

            String query = "SELECT * FROM Motos WHERE ID = " + ID;
            db.resultSet = db.stmt.executeQuery(query);

            if (db.resultSet.next()) {
                this.setID(db.resultSet.getInt("ID"));
                this.setModelo(db.resultSet.getString("Modelo"));
                this.setCor(db.resultSet.getString("Cor"));
                this.setAno(db.resultSet.getInt("Ano"));
                this.setCilindrada(db.resultSet.getInt("Cilindrada"));
                this.setPreço(db.resultSet.getDouble("Preco"));
                resultRead = true;
            } else {
                resultRead = false;
            }
          
            db.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            resultRead = false;
        }
        
        return resultRead;
    }

    public boolean creatMoto(String Modelo, String Cor, int Ano, int Cilindrada, double Preco){
        Conecxao db = new Conecxao();
        
        try {
            db.openConnection();

            // Locale.US garante ponto decimal no %f (evita vírgula do pt-BR que quebra o SQL)
            String query = String.format(Locale.US,
                    "INSERT INTO Motos (Modelo, Cor, Ano, Cilindrada, Preco) VALUES ('%s', '%s', %d, %d, %.2f)",
                    Modelo, Cor, Ano, Cilindrada, Preco);
            db.stmt.execute(query);

            resultCreate = true;
          
            db.closeConnection();
        } catch (Exception e) {
            System.out.println("Erro ao inserir moto: "+e.getMessage());
            resultCreate = false;
        }
        
        return resultCreate;
    }

    public boolean updateMoto(int ID, String Modelo, String Cor, int Ano, int Cilindrada, double Preco) {
    Conecxao db = new Conecxao();
    
        try {
            db.openConnection();

            String query = String.format(Locale.US,
                    "UPDATE Motos SET Modelo = '%s', Cor = '%s', Ano = %d, Cilindrada = %d, Preco = %.2f WHERE ID = %d",
                    Modelo, Cor, Ano, Cilindrada, Preco, ID);
            
            db.stmt.execute(query);

            resultUpdate = true;
            db.closeConnection();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar moto: " + e.getMessage());
            resultUpdate = false;
        }
        
        return resultUpdate;
    }

    public boolean deleteMoto(int ID) {
        Conecxao db = new Conecxao();
        
        try {
            db.openConnection();

            String query = String.format("DELETE FROM Motos WHERE ID = %d", ID);
            db.stmt.execute(query);

            resultDelete = true;
            db.closeConnection();
        } catch (Exception e) {
            System.out.println("Erro ao deletar moto: " + e.getMessage());
            resultDelete = false;
        }
        
        return resultDelete;
    }
}
