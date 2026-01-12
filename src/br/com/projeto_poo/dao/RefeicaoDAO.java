package br.com.projeto_poo.dao;

import br.com.projeto_poo.model.Refeicao;
import br.com.projeto_poo.model.Alimento;
import br.com.projeto_poo.model.Usuario;
import br.com.projeto_poo.util.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RefeicaoDAO {
    
    private AlimentoDAO alimentoDAO = new AlimentoDAO();
    
    /**
     * Salva uma refeição completa (refeição + alimentos + relacionamento)
     * Utiliza transação para garantir integridade
     */
    public Long salvar(Refeicao refeicao) {
        String sqlRefeicao = "INSERT INTO refeicao (data_registro, hora_registro, usuario_id, total_calorias, tipo) " +
                            "VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Inicia transação
            
            // 1. Inserir a refeição
            Long refeicaoId;
            try (PreparedStatement stmt = conn.prepareStatement(sqlRefeicao, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setDate(1, Date.valueOf(refeicao.getData()));
                stmt.setTime(2, Time.valueOf(refeicao.getHora()));
                stmt.setLong(3, refeicao.getUsuario().getId());
                stmt.setDouble(4, refeicao.getTotalCalorias());
                stmt.setString(5, refeicao.getTipo());
                
                stmt.executeUpdate();
                
                ResultSet rs = stmt.getGeneratedKeys();
                if (!rs.next()) {
                    throw new SQLException("Falha ao obter ID da refeição");
                }
                refeicaoId = rs.getLong(1);
            }
            
            // 2. Processar alimentos
            if (refeicao.getAlimentos() != null && !refeicao.getAlimentos().isEmpty()) {
                for (Alimento alimento : refeicao.getAlimentos()) {
                    // Buscar ou criar alimento
                    Long alimentoId = buscarOuCriarAlimento(conn, alimento);
                    
                    // Vincular alimento à refeição
                    vincularAlimentoRefeicao(conn, refeicaoId, alimentoId);
                }
            }
            
            conn.commit(); // Confirma transação
            System.out.println("Refeição salva com sucesso! ID: " + refeicaoId);
            return refeicaoId;
            
        } catch (SQLException e) {
            System.err.println("Erro ao salvar refeição: " + e.getMessage());
            e.printStackTrace();
            
            if (conn != null) {
                try {
                    conn.rollback(); // Desfaz transação em caso de erro
                    System.err.println("Transação revertida.");
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação: " + ex.getMessage());
                }
            }
            return null;
            
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Busca uma refeição por ID com todos os alimentos
     */
    public Refeicao buscarPorId(Long id) {
        String sql = "SELECT r.*, u.nome as usuario_nome, u.email as usuario_email " +
                    "FROM refeicao r " +
                    "INNER JOIN usuario u ON r.usuario_id = u.id " +
                    "WHERE r.id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Refeicao refeicao = montarRefeicao(rs);
                
                // Buscar alimentos da refeição
                refeicao.setAlimentos(buscarAlimentosDaRefeicao(conn, id));
                
                return refeicao;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar refeição por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Busca todas as refeições de um usuário
     */
    public List<Refeicao> buscarPorUsuario(int usuarioId) {
        String sql = "SELECT r.*, u.nome as usuario_nome, u.email as usuario_email " +
                    "FROM refeicao r " +
                    "INNER JOIN usuario u ON r.usuario_id = u.id " +
                    "WHERE r.usuario_id = ? " +
                    "ORDER BY r.data_registro DESC, r.hora_registro DESC";
        
        List<Refeicao> refeicoes = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Refeicao refeicao = montarRefeicao(rs);
                refeicao.setAlimentos(buscarAlimentosDaRefeicao(conn, refeicao.getId()));
                refeicoes.add(refeicao);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar refeições do usuário: " + e.getMessage());
            e.printStackTrace();
        }
        
        return refeicoes;
    }
    
    /**
     * Busca refeições por tipo (Café da Manhã, Almoço, etc.)
     */
    public List<Refeicao> buscarPorTipo(int usuarioId, String tipo) {
        String sql = "SELECT r.*, u.nome as usuario_nome, u.email as usuario_email " +
                    "FROM refeicao r " +
                    "INNER JOIN usuario u ON r.usuario_id = u.id " +
                    "WHERE r.usuario_id = ? AND r.tipo = ? " +
                    "ORDER BY r.data_registro DESC, r.hora_registro DESC";
        
        List<Refeicao> refeicoes = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            stmt.setString(2, tipo);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Refeicao refeicao = montarRefeicao(rs);
                refeicao.setAlimentos(buscarAlimentosDaRefeicao(conn, refeicao.getId()));
                refeicoes.add(refeicao);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar refeições por tipo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return refeicoes;
    }
    
    /**
     * Busca refeições por data específica
     */
    public List<Refeicao> buscarPorData(int usuarioId, LocalDate data) {
        String sql = "SELECT r.*, u.nome as usuario_nome, u.email as usuario_email " +
                    "FROM refeicao r " +
                    "INNER JOIN usuario u ON r.usuario_id = u.id " +
                    "WHERE r.usuario_id = ? AND r.data_registro = ? " +
                    "ORDER BY r.hora_registro";
        
        List<Refeicao> refeicoes = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            stmt.setDate(2, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Refeicao refeicao = montarRefeicao(rs);
                refeicao.setAlimentos(buscarAlimentosDaRefeicao(conn, refeicao.getId()));
                refeicoes.add(refeicao);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar refeições por data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return refeicoes;
    }
    
    /**
     * Busca refeições por período
     */
    public List<Refeicao> buscarPorPeriodo(int usuarioId, LocalDate dataInicio, LocalDate dataFim) {
        String sql = "SELECT r.*, u.nome as usuario_nome, u.email as usuario_email " +
                    "FROM refeicao r " +
                    "INNER JOIN usuario u ON r.usuario_id = u.id " +
                    "WHERE r.usuario_id = ? AND r.data_registro BETWEEN ? AND ? " +
                    "ORDER BY r.data_registro DESC, r.hora_registro DESC";
        
        List<Refeicao> refeicoes = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            stmt.setDate(2, Date.valueOf(dataInicio));
            stmt.setDate(3, Date.valueOf(dataFim));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Refeicao refeicao = montarRefeicao(rs);
                refeicao.setAlimentos(buscarAlimentosDaRefeicao(conn, refeicao.getId()));
                refeicoes.add(refeicao);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar refeições por período: " + e.getMessage());
            e.printStackTrace();
        }
        
        return refeicoes;
    }
    
    /**
     * Lista todas as refeições
     */
    public List<Refeicao> listarTodas() {
        String sql = "SELECT r.*, u.nome as usuario_nome, u.email as usuario_email " +
                    "FROM refeicao r " +
                    "INNER JOIN usuario u ON r.usuario_id = u.id " +
                    "ORDER BY r.data_registro DESC, r.hora_registro DESC";
        
        List<Refeicao> refeicoes = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Refeicao refeicao = montarRefeicao(rs);
                refeicao.setAlimentos(buscarAlimentosDaRefeicao(conn, refeicao.getId()));
                refeicoes.add(refeicao);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar todas as refeições: " + e.getMessage());
            e.printStackTrace();
        }
        
        return refeicoes;
    }
    
    /**
     * Atualiza informações básicas da refeição (tipo e calorias)
     */
    public boolean atualizar(Long id, String novoTipo, double novasTotalCalorias) {
        String sql = "UPDATE refeicao SET tipo = ?, total_calorias = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, novoTipo);
            stmt.setDouble(2, novasTotalCalorias);
            stmt.setLong(3, id);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Refeição atualizada com sucesso!");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar refeição: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Exclui uma refeição e seus relacionamentos
     */
    public boolean excluir(Long id) {
        Connection conn = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            
            // 1. Excluir relacionamentos na tabela refeicao_alimentos
            String sqlRelacionamentos = "DELETE FROM refeicao_alimentos WHERE refeicao_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlRelacionamentos)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
            
            // 2. Excluir a refeição
            String sqlRefeicao = "DELETE FROM refeicao WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlRefeicao)) {
                stmt.setLong(1, id);
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected == 0) {
                    throw new SQLException("Refeição não encontrada");
                }
            }
            
            conn.commit();
            System.out.println("Refeição excluída com sucesso!");
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir refeição: " + e.getMessage());
            e.printStackTrace();
            
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação: " + ex.getMessage());
                }
            }
            return false;
            
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Calcula o total de calorias consumidas por um usuário em uma data
     */
    public double calcularCaloriasDoDia(int usuarioId, LocalDate data) {
        String sql = "SELECT SUM(total_calorias) as total FROM refeicao " +
                    "WHERE usuario_id = ? AND data_registro = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            stmt.setDate(2, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao calcular calorias do dia: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    /**
     * Conta quantas refeições um usuário registrou
     */
    public int contarPorUsuario(int usuarioId) {
        String sql = "SELECT COUNT(*) as total FROM refeicao WHERE usuario_id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao contar refeições: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // ==================== MÉTODOS AUXILIARES PRIVADOS ====================
    
    /**
     * Busca ou cria um alimento (evita duplicatas)
     */
    private Long buscarOuCriarAlimento(Connection conn, Alimento alimento) throws SQLException {
        // Verificar se alimento já existe pelo nome
        String sqlBusca = "SELECT id FROM alimento WHERE nome = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlBusca)) {
            stmt.setString(1, alimento.getNome());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getLong("id");
            }
        }
        
        // Se não existe, criar novo
        String sqlInsert = "INSERT INTO alimento (nome, calorias, proteinas, carboidratos, gorduras, categoria, porcao_padrao) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, alimento.getNome());
            stmt.setDouble(2, alimento.getCalorias());
            stmt.setDouble(3, alimento.getProteinas());
            stmt.setDouble(4, alimento.getCarboidratos());
            stmt.setDouble(5, alimento.getGorduras());
            stmt.setString(6, alimento.getCategoria() != null ? alimento.getCategoria() : "Geral");
            stmt.setDouble(7, alimento.getPorcaoPadrao() > 0 ? alimento.getPorcaoPadrao() : 100.0);
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        
        throw new SQLException("Falha ao criar alimento");
    }
    
    /**
     * Vincula um alimento a uma refeição na tabela intermediária
     */
    private void vincularAlimentoRefeicao(Connection conn, Long refeicaoId, Long alimentoId) throws SQLException {
        String sql = "INSERT INTO refeicao_alimentos (refeicao_id, alimento_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, refeicaoId);
            stmt.setLong(2, alimentoId);
            stmt.executeUpdate();
        }
    }
    
    /**
     * Busca todos os alimentos vinculados a uma refeição
     */
    private List<Alimento> buscarAlimentosDaRefeicao(Connection conn, Long refeicaoId) throws SQLException {
        String sql = "SELECT a.* FROM alimento a " +
                    "INNER JOIN refeicao_alimentos ra ON a.id = ra.alimento_id " +
                    "WHERE ra.refeicao_id = ?";
        
        List<Alimento> alimentos = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, refeicaoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Alimento alimento = new Alimento();
                alimento.setId(rs.getLong("id"));
                alimento.setNome(rs.getString("nome"));
                alimento.setCalorias(rs.getDouble("calorias"));
                alimento.setProteinas(rs.getDouble("proteinas"));
                alimento.setCarboidratos(rs.getDouble("carboidratos"));
                alimento.setGorduras(rs.getDouble("gorduras"));
                alimento.setCategoria(rs.getString("categoria"));
                alimento.setPorcaoPadrao(rs.getDouble("porcao_padrao"));
                alimentos.add(alimento);
            }
        }
        
        return alimentos;
    }
    
    /**
     * Monta um objeto Refeicao a partir do ResultSet
     */
    private Refeicao montarRefeicao(ResultSet rs) throws SQLException {
        Refeicao refeicao = new Refeicao();
        refeicao.setId(rs.getLong("id"));
        refeicao.setData(rs.getDate("data_registro").toLocalDate());
        refeicao.setHora(rs.getTime("hora_registro").toLocalTime());
        refeicao.setTotalCalorias(rs.getDouble("total_calorias"));
        refeicao.setTipo(rs.getString("tipo"));
        
        // Criar objeto Usuario básico
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("usuario_id"));
        usuario.setNome(rs.getString("usuario_nome"));
        usuario.setEmail(rs.getString("usuario_email"));
        refeicao.setUsuario(usuario);
        
        return refeicao;
    }
}