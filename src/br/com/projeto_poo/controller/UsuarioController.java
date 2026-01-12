package br.com.projeto_poo.controller;

import br.com.projeto_poo.dao.UsuarioDAO;
import br.com.projeto_poo.model.Usuario;
import br.com.projeto_poo.view.TelaMonitoramento;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Date;

public class UsuarioController
{
    private UsuarioDAO dao = new UsuarioDAO();

    public void efetuarLogin(String email, String senha, JFrame telaLogin)
    {
        Usuario usuario = dao.validarLogin(email, senha);

        if (usuario != null)
        {
            new TelaMonitoramento(usuario).setVisible(true);
            if (telaLogin != null) telaLogin.dispose();
        } else
        {
            JOptionPane.showMessageDialog(telaLogin,
                    "E-mail ou senha incorretos!",
                    "Erro de Autenticação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cadastrarUsuario(String nome, int idade, float peso, float altura, String sexo,
                                 float gordura, float massa, String meta, String email, String senha, JFrame tela)
    {
        try {
            Usuario u = new Usuario();
            u.setNome(nome);
            u.setIdade(idade);
            u.setPeso(peso);

            // CORREÇÃO DA ALTURA:
            // Se o usuário digitou 1.75, multiplicamos por 100 para salvar 175 (cm)
            // Math.round evita erros de arredondamento em floats.
            int alturaCentimetros = Math.round(altura * 100);

            u.setAltura(alturaCentimetros);
            u.setSexo(sexo);
            u.setPorcentagemGordura(gordura);
            u.setMassaCorporal(massa);
            u.setMeta(Float.parseFloat(meta.replace(",", ".")));
            u.setEmail(email);
            u.setSenha(senha);
            u.setDataCadastro(new Date());

            dao.salvar(u);

            JOptionPane.showMessageDialog(tela, "Cadastro realizado com sucesso!");

            if (tela != null) tela.dispose();
            new TelaMonitoramento(u).setVisible(true);

        } catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(tela, "Erro nos campos numéricos. Verifique se usou ponto em vez de vírgula.");
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(tela, "Erro ao cadastrar: " + e.getMessage());
        }
    }
}