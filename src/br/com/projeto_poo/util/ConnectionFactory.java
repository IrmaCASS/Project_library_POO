package br.com.projeto_poo.util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

    /**
     * Retorna uma conexão ativa.
     * Encapsula a SQLException em uma RuntimeException para simplificar a chamada na View/Controller.
     */
    public static Connection getConnection() {
        try {
            return ConnectionH2.getConnection();
        } catch (SQLException e) {
            System.err.println("Falha na conexão: " + e.getMessage());
            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
    }
}