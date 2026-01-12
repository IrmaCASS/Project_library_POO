package br.com.projeto_poo.view;

import br.com.projeto_poo.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class TelaPerfil extends JFrame
{
    public TelaPerfil(Usuario usuario)
    {
        setTitle("Meu Perfil Nutricional");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas esta janela
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel Principal com espaçamento lateral
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        painel.setBackground(Color.WHITE);

        // Ícone de Perfil (Usando texto/emoji para simplificar)
        JLabel lblIcone = new JLabel("👤");
        lblIcone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        lblIcone.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblNome = new JLabel(usuario.getNome().toUpperCase());
        lblNome.setFont(new Font("Arial", Font.BOLD, 18));
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNome.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        painel.add(lblIcone);
        painel.add(lblNome);
        painel.add(new JSeparator());
        painel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Adicionando os campos de dados
        painel.add(criarLinhaInformacao("E-mail:", usuario.getEmail()));
        painel.add(criarLinhaInformacao("Idade:", usuario.getIdade() + " anos"));
        painel.add(criarLinhaInformacao("Peso Atual:", usuario.getPeso() + " kg"));
        painel.add(criarLinhaInformacao("Altura:", (usuario.getAltura() / 100.0) + " m"));
        painel.add(criarLinhaInformacao("Sexo:", usuario.getSexo()));
        painel.add(criarLinhaInformacao("% Gordura:", usuario.getPorcentagemGordura() + "%"));
        painel.add(criarLinhaInformacao("Massa Corporal:", usuario.getMassaCorporal() + " kg"));

        // BLOCO DA META (Conforme sua lógica: Peso * 33)
        double meta = usuario.getPeso() * 33;
        JPanel painelMeta = new JPanel();
        painelMeta.setBackground(new Color(240, 248, 255));
        painelMeta.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180)));

        JLabel lblMetaDiaria = new JLabel(String.format("Meta Diária: %.2f kcal", meta));
        lblMetaDiaria.setFont(new Font("Arial", Font.BOLD, 15));
        lblMetaDiaria.setForeground(new Color(70, 130, 180));

        painelMeta.add(lblMetaDiaria);

        painel.add(Box.createRigidArea(new Dimension(0, 20)));
        painel.add(painelMeta);

        add(painel);
    }

    // Método auxiliar para criar as linhas de dados esteticamente
    private JPanel criarLinhaInformacao(String rotulo, String valor)
    {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setMaximumSize(new Dimension(400, 30));

        JLabel lblRotulo = new JLabel(rotulo);
        lblRotulo.setFont(new Font("Arial", Font.BOLD, 12));
        lblRotulo.setForeground(Color.GRAY);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 13));

        p.add(lblRotulo, BorderLayout.WEST);
        p.add(lblValor, BorderLayout.EAST);
        return p;
    }
}