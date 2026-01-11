package br.com.projeto_poo.dao;

import br.com.projeto_poo.util.ConnectionFactory;
import br.com.projeto_poo.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    // Comandos SQL
    private final String INSERT = "INSERT INTO USUARIO (nome, idade, peso, altura, sexo, porcentagem_gordura, massa_corporal, meta, data_cadastro, email, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM USUARIO";
    private final String UPDATE = "UPDATE USUARIO SET nome=?, idade=?, peso=?, altura=?, sexo=?, porcentagem_gordura=?, massa_corporal=?, meta=?, email=?, senha=? WHERE id=?";
    private final String DELETE = "DELETE FROM USUARIO WHERE id=?";

    public void salvar(Usuario usuario) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {

            preencherStatement(ps, usuario);
            // Para o insert, incluímos a data de cadastro
            ps.setTimestamp(9, new Timestamp(usuario.getDataCadastro().getTime()));
            ps.setString(10, usuario.getEmail());
            ps.setString(11, usuario.getSenha());

            ps.executeUpdate();
            System.out.println("Usuário salvo com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id")); // Adicione o id na classe Model
                u.setNome(rs.getString("nome"));
                u.setIdade(rs.getInt("idade"));
                u.setPeso(rs.getFloat("peso"));
                u.setAltura(rs.getInt("altura"));
                u.setSexo(rs.getString("sexo"));
                u.setPorcentagemGordura(rs.getFloat("porcentagem_gordura"));
                u.setMassaCorporal(rs.getFloat("massa_corporal"));
                u.setMeta(rs.getFloat("meta"));
                u.setDataCadastro(rs.getTimestamp("data_cadastro"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                usuarios.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage());
        }

        return usuarios;
    }

    public void atualizar(Usuario usuario) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            preencherStatement(ps, usuario);
            ps.setString(9, usuario.getEmail());
            ps.setString(10, usuario.getSenha());
            ps.setLong(11, usuario.getId()); // Filtro do WHERE

            ps.executeUpdate();
            System.out.println("Usuário ID " + usuario.getId() + " atualizado.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Usuário ID " + id + " excluído.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário: " + e.getMessage());
        }
    }

    // Helper para evitar repetição de código
    private void preencherStatement(PreparedStatement ps, Usuario u) throws SQLException {
        ps.setString(1, u.getNome());
        ps.setInt(2, u.getIdade());
        ps.setFloat(3, u.getPeso());
        ps.setInt(4, u.getAltura());
        ps.setString(5, u.getSexo());
        ps.setFloat(6, u.getPorcentagemGordura());
        ps.setFloat(7, u.getMassaCorporal());
        ps.setFloat(8, u.getMeta());
    }
}

