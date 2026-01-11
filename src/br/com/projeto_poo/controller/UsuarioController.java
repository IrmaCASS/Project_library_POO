package br.com.projeto_poo.controller;

import br.com.projeto_poo.dao.UsuarioDAO;
import br.com.projeto_poo.model.Usuario;
import br.com.projeto_poo.view.TelaMonitoramento;
import javax.swing.JFrame;
import java.util.Date;

public class UsuarioController {
    private UsuarioDAO dao = new UsuarioDAO();

    public void cadastrarUsuario(String nome, int idade, float peso, float altura, String sexo,
                                 float gordura, float massa, String meta, String email, String senha, JFrame tela) {

        Usuario u = new Usuario();
        u.setNome(nome); u.setIdade(idade); u.setPeso(peso); u.setAltura((int)altura);
        u.setSexo(sexo); u.setPorcentagemGordura(gordura); u.setMassaCorporal(massa);
        u.setMeta(Float.parseFloat(meta)); u.setEmail(email); u.setSenha(senha);
        u.setDataCadastro(new Date());

        // SALVA NO BANCO E GERA O ID
        dao.salvar(u);

        // FECHA A TELA ATUAL E ABRE A PRÓXIMA PASSANDO O USUÁRIO (O argumento esperado!)
        if (tela != null) tela.dispose();
        new TelaMonitoramento(u).setVisible(true);
    }
}