package br.com.projeto_poo.view;

import javax.swing.*;
import java.awt.*;
import br.com.projeto_poo.controller.MonitoramentoController;

public class TelaMonitoramento extends JFrame {

    private MonitoramentoController controller;
    private JPanel painelRefeicoes;

    public TelaMonitoramento() {
        this.controller = new MonitoramentoController();
        setTitle("Gerenciar Refeições Diárias");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Cabeçalho
        JLabel lblTitulo = new JLabel("Minhas Refeições", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        // Lista de Refeições (Simulando uma lista que vem do Banco)
        painelRefeicoes = new JPanel();
        painelRefeicoes.setLayout(new BoxLayout(painelRefeicoes, BoxLayout.Y_AXIS));

        // Exemplo de como as refeições seriam renderizadas
        adicionarLinhaRefeicao("Café da Manhã", "Pão e Café", "250");
        adicionarLinhaRefeicao("Almoço", "Arroz, Feijão e Frango", "600");

        JScrollPane scroll = new JScrollPane(painelRefeicoes);
        add(scroll, BorderLayout.CENTER);

        // Botão para Adicionar Nova (opcional)
        JButton btnNovo = new JButton("Adicionar Nova Refeição");
        add(btnNovo, BorderLayout.SOUTH);
    }

    private void adicionarLinhaRefeicao(String tipo, String desc, String kcal) {
        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblInfo = new JLabel(String.format("<html><b>%s</b>: %s (%s kcal)</html>", tipo, desc, kcal));
        lblInfo.setPreferredSize(new Dimension(350, 30));

        JButton btnEditar = new JButton("Modificar");
        JButton btnExcluir = new JButton("Excluir");

        // Ação de Modificar
        btnEditar.addActionListener(e -> {
            String novaDesc = JOptionPane.showInputDialog(this, "Nova descrição:", desc);
            if (novaDesc != null) {
                controller.modificarRefeicao(tipo, novaDesc, kcal);
                // No padrão Observer, isso dispararia um refresh automático
                JOptionPane.showMessageDialog(this, "Refeição atualizada!");
            }
        });

        // Ação de Excluir
        btnExcluir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o " + tipo + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                controller.excluirRefeicao(tipo);
                painelRefeicoes.remove(linha);
                painelRefeicoes.revalidate();
                painelRefeicoes.repaint();
            }
        });

        linha.add(lblInfo);
        linha.add(btnEditar);
        linha.add(btnExcluir);
        painelRefeicoes.add(linha);
    }
}
