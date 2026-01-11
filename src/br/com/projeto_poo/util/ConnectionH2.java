package br.com.projeto_poo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionH2 {
    // Configurações do H2 (pode ser em memória ou arquivo)
    private static final String URL = "jdbc:h2:./database/projeto_db;" +
            "DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:Banco_de_dados.sql'";
    private static final String USER = "sa";
    private static final String PASS = "";
    private static final String DRIVER = "org.h2.Driver";

    public static Connection getConnection() throws SQLException {
        try {
            // Carrega o driver dinamicamente
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver H2 não encontrado no classpath.", e);
        }
    }
}