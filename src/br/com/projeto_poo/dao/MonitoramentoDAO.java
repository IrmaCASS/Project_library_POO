package br.com.projeto_poo.dao;

import br.com.projeto_poo.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MonitoramentoDAO {

    /**
     * MODIFICAR: Executa o comando UPDATE no banco de dados.
     * Isolamento total: a View e o Controller não veem este SQL.
     */
    public void atualizarRefeicao(String tipo, String novaDesc, double novasKcal) {
        String sql = "UPDATE refeicoes SET descricao = ?, calorias = ? WHERE tipo_refeicao = ? AND usuario_id = ?";

        // Usa o Singleton ConnectionFactory para obter a conexão
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novaDesc);
            stmt.setDouble(2, novasKcal);
            stmt.setString(3, tipo);

            stmt.executeUpdate();
            System.out.println("DAO: Refeição atualizada com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar no DAO: " + e.getMessage());
            throw new RuntimeException("Erro de persistência ao editar refeição.", e);
        }
    }

    /**
     * EXCLUIR: Executa o comando DELETE no banco de dados.
     */
    public void excluirRefeicao(String tipo, int usuarioId) {
        String sql = "DELETE FROM refeicoes WHERE tipo_refeicao = ? AND usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            stmt.setInt(2, usuarioId);

            stmt.executeUpdate();
            System.out.println("DAO: Refeição excluída com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao excluir no DAO: " + e.getMessage());
            throw new RuntimeException("Erro de persistência ao remover refeição.", e);
        }
    }

}