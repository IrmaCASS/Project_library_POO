package br.com.projeto_poo.dao;

import br.com.projeto_poo.util.ConnectionFactory;
import br.com.projeto_poo.model.Usuario;
import java.sql.*;

public class UsuarioDAO
{
    private final String INSERT = "INSERT INTO usuarios (nome, idade, peso, altura, sexo, porcentagem_gordura, massa_corporal, meta, data_cadastro, email, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void criarTabelas()
    {
        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuarios (id INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(100), idade INT, peso FLOAT, altura INT, sexo VARCHAR(20), porcentagem_gordura FLOAT, massa_corporal FLOAT, meta FLOAT, data_cadastro TIMESTAMP, email VARCHAR(100) UNIQUE, senha VARCHAR(100))";

        // MODIFICADO: Adicionada a coluna data_refeicao para o controle diário
        String sqlRefeicoes = "CREATE TABLE IF NOT EXISTS refeicoes (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "tipo_refeicao VARCHAR(50), " +
                "descricao VARCHAR(255), " +
                "calorias DOUBLE, " +
                "usuario_id INT, " +
                "data_refeicao DATE DEFAULT CURRENT_DATE, " +
                "FOREIGN KEY (usuario_id) REFERENCES usuarios(id))";

        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement())
        {
            stmt.execute(sqlUsuario);
            stmt.execute(sqlRefeicoes);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Valida o login e retorna o objeto Usuario completo.
    public Usuario validarLogin(String email, String senha)
    {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setIdade(rs.getInt("idade"));
                u.setPeso(rs.getFloat("peso"));
                u.setAltura(rs.getInt("altura"));
                u.setSexo(rs.getString("sexo"));
                u.setPorcentagemGordura(rs.getFloat("porcentagem_gordura"));
                u.setMassaCorporal(rs.getFloat("massa_corporal"));
                u.setMeta(rs.getFloat("meta"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                return u;
            }
        } catch (SQLException e) {e.printStackTrace();}

        return null; // Retorna null se não encontrar o usuário
    }

    public void salvar(Usuario u)
    {
        criarTabelas();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, u.getNome());
            ps.setInt(2, u.getIdade());
            ps.setFloat(3, u.getPeso());
            ps.setInt(4, u.getAltura());
            ps.setString(5, u.getSexo());
            ps.setFloat(6, u.getPorcentagemGordura());
            ps.setFloat(7, u.getMassaCorporal());
            ps.setFloat(8, u.getMeta());
            ps.setTimestamp(9, new Timestamp(u.getDataCadastro().getTime()));
            ps.setString(10, u.getEmail());
            ps.setString(11, u.getSenha());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) u.setId(rs.getLong(1));
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}