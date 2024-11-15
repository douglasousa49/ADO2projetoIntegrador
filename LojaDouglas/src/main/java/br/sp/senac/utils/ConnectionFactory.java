package br.sp.senac.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/BaseDouglas";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    public static Connection getConnection() {
        
        Connection conexao = null;
        
        try {
        	
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (SQLException ex) {
            System.out.println("Erro ao abrir a conexão");
            throw new RuntimeException("Erro ao abrir conexão", ex);
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro ao registrar o driver do JDBC");
            throw new RuntimeException("Erro ao registrar driver do JDBC", ex);
        }
        
        return conexao;
    }
}