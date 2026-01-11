package br.com.projeto_poo.dao;

import br.com.projeto_poo.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonitoramentoDAO {

    /**
     * Busca todas as refeições de um usuário específico.
     * Essencial para carregar a lista ao abrir a TelaMonitoramento.
     */
    public List<String[]> buscarPorUsuario(int usuarioId) {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT tipo_refeicao, descricao, calorias FROM refeicoes WHERE usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("tipo_refeicao"),
                        rs.getString("descricao"),
                        String.valueOf(rs.getDouble("calorias"))
                });
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar refeições: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Insere uma nova refeição no banco de dados.
     */
    public void salvarRefeicao(String tipo, String desc, double kcal, int usuarioId) {
        String sql = "INSERT INTO refeicoes (tipo_refeicao, descricao, calorias, usuario_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            stmt.setString(2, desc);
            stmt.setDouble(3, kcal);
            stmt.setInt(4, usuarioId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove uma refeição baseada no tipo e no ID do usuário.
     */
    public void excluirRefeicao(String tipo, int usuarioId) {
        String sql = "DELETE FROM refeicoes WHERE tipo_refeicao = ? AND usuario_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            stmt.setInt(2, usuarioId);

            int rows = stmt.executeUpdate();
            System.out.println("DAO: Refeição excluída. Linhas afetadas: " + rows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza a descrição e as calorias de uma refeição existente.
     */
    public void atualizarRefeicao(String tipo, String desc, double kcal, int usuarioId) {
        String sql = "UPDATE refeicoes SET descricao = ?, calorias = ? WHERE tipo_refeicao = ? AND usuario_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, desc);
            stmt.setDouble(2, kcal);
            stmt.setString(3, tipo);
            stmt.setInt(4, usuarioId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}