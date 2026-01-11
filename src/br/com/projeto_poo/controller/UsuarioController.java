package br.com.projeto_poo.controller;

import br.com.projeto_poo.dao.UsuarioDAO;
import br.com.projeto_poo.model.Usuario;
import java.util.Date;

public class UsuarioController {
    private UsuarioDAO dao;

    public UsuarioController() {
        // O Controller depende do DAO para persistir os dados
        this.dao = new UsuarioDAO();
    }

    public void cadastrarUsuario(String nome, int idade, float peso, float altura, String sexo,
                                 float porcentagemGordura, float massaCorporal, String meta,
                                 String email, String senha) throws Exception {

        // 1. Lógica de Validação (Controller)
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O emaill é obrigatório.");
        }if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("O senha é obrigatório.");
        }if (idade == 0.0) {
            throw new IllegalArgumentException("O idade é obrigatório.");
        }if (sexo == null || sexo.trim().isEmpty()) {
            throw new IllegalArgumentException("O senha é obrigatório.");
        }if (peso == 0.0) {
            throw new IllegalArgumentException("O peso é obrigatório.");
        }if (altura == 0.0) {
            throw new IllegalArgumentException("O idade é obrigatório.");
        }

        // 2. Criação do Objeto Model (Model)
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setIdade(idade);
        novoUsuario.setPeso(peso);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha);
        novoUsuario.setDataCadastro(new Date());
        novoUsuario.setMassaCorporal(massaCorporal);
        novoUsuario.getMeta();
        novoUsuario.setSexo(sexo);
        novoUsuario.setPorcentagemGordura(porcentagemGordura);
        // adicionar os demais campos

        // 3. Persistência(DAO)
        dao.salvar(novoUsuario);
    }
}