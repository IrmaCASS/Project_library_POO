package br.com.projeto_poo.dao;

import br.com.projeto_poo.model.Alimento;
import br.com.projeto_poo.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlimentoDAO
{
    //Salva um novo alimento no banco de dados
    public Long salvar(Alimento alimento)
    {
        String sql = "INSERT INTO alimento (nome, calorias, proteinas, carboidratos, gorduras, categoria, porcao_padrao) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, alimento.getNome());
            stmt.setDouble(2, alimento.getCalorias());
            stmt.setDouble(3, alimento.getProteinas());
            stmt.setDouble(4, alimento.getCarboidratos());
            stmt.setDouble(5, alimento.getGorduras());

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0)
            {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next())
                {
                    Long id = rs.getLong(1);
                    alimento.setId(id);
                    System.out.println("Alimento salvo com sucesso! ID: " + id);
                    return id;
                }
            }
            
        } catch (SQLException e)
        {
            System.err.println("Erro ao salvar alimento: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    //Busca um alimento por ID
    public Alimento buscarPorId(Long id) {
        String sql = "SELECT * FROM alimento WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {return montarAlimento(rs);}

        } catch (SQLException e)
        {
            System.err.println("Erro ao buscar alimento por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    //Busca um alimento por nome (exato)
    public Alimento buscarPorNome(String nome)
    {
        String sql = "SELECT * FROM alimento WHERE nome = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {return montarAlimento(rs);}

        } catch (SQLException e)
        {
            System.err.println("Erro ao buscar alimento por nome: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    //Busca alimentos por nome parcial (LIKE)
    public List<Alimento> buscarPorNomeParcial(String nomeParcial)
    {
        String sql = "SELECT * FROM alimento WHERE nome LIKE ? ORDER BY nome";
        List<Alimento> alimentos = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, "%" + nomeParcial + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {alimentos.add(montarAlimento(rs));}

        } catch (SQLException e) {
            System.err.println("Erro ao buscar alimentos por nome parcial: " + e.getMessage());
            e.printStackTrace();
        }
        return alimentos;
    }
    
    //Busca alimentos por categoria
    public List<Alimento> buscarPorCategoria(String categoria) {
        String sql = "SELECT * FROM alimento WHERE categoria = ? ORDER BY nome";
        List<Alimento> alimentos = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, categoria);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {alimentos.add(montarAlimento(rs));}
            
        } catch (SQLException e)
        {
            System.err.println("Erro ao buscar alimentos por categoria: " + e.getMessage());
            e.printStackTrace();
        }
        return alimentos;
    }
    
    //Lista todos os alimentos
    public List<Alimento> listarTodos() {
        String sql = "SELECT * FROM alimento ORDER BY nome";
        List<Alimento> alimentos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())
        {
            while (rs.next()) {alimentos.add(montarAlimento(rs));}

        } catch (SQLException e)
        {
            System.err.println("Erro ao listar alimentos: " + e.getMessage());
            e.printStackTrace();
        }
        return alimentos;
    }
    
    //Atualiza um alimento existente

    public boolean atualizar(Alimento alimento)
    {
        String sql = "UPDATE alimento SET nome = ?, calorias = ?, proteinas = ?, " +
                    "carboidratos = ?, gorduras = ?, categoria = ?, porcao_padrao = ? " +
                    "WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, alimento.getNome());
            stmt.setDouble(2, alimento.getCalorias());
            stmt.setDouble(3, alimento.getProteinas());
            stmt.setDouble(4, alimento.getCarboidratos());
            stmt.setDouble(5, alimento.getGorduras());
            stmt.setLong(8, alimento.getId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Alimento atualizado com sucesso!");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar alimento: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

     //Exclui um alimento por ID
    public boolean excluir(Long id)
    {
        String sql = "DELETE FROM alimento WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0)
            {
                System.out.println("Alimento excluído com sucesso!");
                return true;
            }
        } catch (SQLException e)
        {
            System.err.println("Erro ao excluir alimento: " + e.getMessage());
            System.err.println("O alimento pode estar vinculado a refeições existentes.");
            e.printStackTrace();
        }
        return false;
    }
    
    //Verifica se um alimento com determinado nome já existe
    public boolean existePorNome(String nome)
    {
        String sql = "SELECT COUNT(*) as total FROM alimento WHERE nome = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {return rs.getInt("total") > 0;}
            
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência de alimento: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    //Conta quantos alimentos existem no banco
    public int contarTotal()
    {
        String sql = "SELECT COUNT(*) as total FROM alimento";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())
        {
            if (rs.next()) {return rs.getInt("total");}
            
        } catch (SQLException e) {
            System.err.println("Erro ao contar alimentos: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    //Monta um objeto Alimento a partir do ResultSet
    private Alimento montarAlimento(ResultSet rs) throws SQLException
    {
        Alimento alimento = new Alimento();
        alimento.setId(rs.getLong("id"));
        alimento.setNome(rs.getString("nome"));
        alimento.setCalorias(rs.getDouble("calorias"));
        alimento.setProteinas(rs.getDouble("proteinas"));
        alimento.setCarboidratos(rs.getDouble("carboidratos"));
        alimento.setGorduras(rs.getDouble("gorduras"));
        return alimento;
    }
}