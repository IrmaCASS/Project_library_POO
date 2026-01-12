package br.com.projeto_poo.dao;

import br.com.projeto_poo.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonitoramentoDAO {

    /**
     * NOVO: Busca refeições de um usuário em uma data específica.
     * @param data Formato esperado: "yyyy-MM-dd"
     */
    public List<String[]> buscarPorData(int usuarioId, String data) {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT tipo_refeicao, descricao, calorias FROM refeicoes WHERE usuario_id = ? AND data_refeicao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setDate(2, Date.valueOf(data)); // Converte String para o tipo Date do SQL
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("tipo_refeicao"),
                        rs.getString("descricao"),
                        String.valueOf(rs.getDouble("calorias"))
                });
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar refeições por data: " + e.getMessage());
        }
        return lista;
    }

    /**
     * MODIFICADO: Salva a refeição incluindo a data atual automaticamente.
     */
    public void salvarRefeicao(String tipo, String desc, double kcal, int usuarioId) {
        // Assume-se que a coluna data_refeicao existe na tabela
        String sql = "INSERT INTO refeicoes (tipo_refeicao, descricao, calorias, usuario_id, data_refeicao) VALUES (?, ?, ?, ?, CURRENT_DATE)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            stmt.setString(2, desc);
            stmt.setDouble(3, kcal);
            stmt.setInt(4, usuarioId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    /**
     * Busca todas as refeições (Geral).
     */
    public List<String[]> buscarPorUsuario(int usuarioId) {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT tipo_refeicao, descricao, calorias FROM refeicoes WHERE usuario_id = ? ORDER BY id DESC";

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
            e.printStackTrace();
        }
        return lista;
    }

    public void excluirRefeicao(String tipo, int usuarioId) {
        String sql = "DELETE FROM refeicoes WHERE tipo_refeicao = ? AND usuario_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            stmt.setInt(2, usuarioId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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