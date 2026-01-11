package br.com.projeto_poo.view;

import br.com.projeto_poo.controller.MonitoramentoController;
import br.com.projeto_poo.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TelaMonitoramento extends JFrame {
    private MonitoramentoController controller = new MonitoramentoController();
    private JPanel painelRefeicoes;
    private Usuario usuarioLogado;

    public TelaMonitoramento(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("Monitoramento - " + (usuario != null ? usuario.getNome() : "Usuário"));
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Cabeçalho
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(new Color(70, 130, 180));
        JLabel lblUser = new JLabel("Bem-vindo, " + (usuario != null ? usuario.getNome() : ""));
        lblUser.setForeground(Color.WHITE);
        painelTopo.add(lblUser);
        add(painelTopo, BorderLayout.NORTH);

        // Painel de Refeições
        painelRefeicoes = new JPanel();
        painelRefeicoes.setLayout(new BoxLayout(painelRefeicoes, BoxLayout.Y_AXIS));
        add(new JScrollPane(painelRefeicoes), BorderLayout.CENTER);

        // Botão Adicionar no Rodapé
        JButton btnNovo = new JButton("Adicionar Nova Refeição");
        btnNovo.addActionListener(e -> {
            String tipo = JOptionPane.showInputDialog(this, "Tipo:");
            String desc = JOptionPane.showInputDialog(this, "Descrição:");
            String kcal = JOptionPane.showInputDialog(this, "Calorias:");

            if (tipo != null && desc != null && kcal != null) {
                controller.adicionarRefeicao(tipo, desc, kcal, (int) usuarioLogado.getId());
                adicionarLinhaRefeicao(tipo, desc, kcal);
            }
        });
        add(btnNovo, BorderLayout.SOUTH);

        // CARREGA OS DADOS ASSIM QUE A TELA ABRE
        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        if (usuarioLogado != null) {
            List<String[]> dados = controller.listarRefeicoes((int) usuarioLogado.getId());
            for (String[] r : dados) {
                adicionarLinhaRefeicao(r[0], r[1], r[2]);
            }
        }
    }

    private void adicionarLinhaRefeicao(String tipo, String desc, String kcal) {
        AtomicReference<String> descRef = new AtomicReference<>(desc);
        AtomicReference<String> kcalRef = new AtomicReference<>(kcal);

        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha.setBorder(BorderFactory.createEtchedBorder());
        linha.setMaximumSize(new Dimension(700, 50));

        double vKcal = Double.parseDouble(kcalRef.get().replace(",", "."));
        JLabel lbl = new JLabel(String.format("<html><b>%s</b>: %s (%.2f kcal)</html>", tipo, descRef.get(), vKcal));
        lbl.setPreferredSize(new Dimension(400, 30));

        JButton btnEdit = new JButton("Modificar");
        btnEdit.addActionListener(e -> {
            String nDesc = JOptionPane.showInputDialog(this, "Nova descrição:", descRef.get());
            String nKcal = JOptionPane.showInputDialog(this, "Novas calorias:", kcalRef.get());

            if (nDesc != null && nKcal != null) {
                controller.modificarRefeicao(tipo, nDesc, nKcal, (int) usuarioLogado.getId());
                descRef.set(nDesc);
                kcalRef.set(nKcal);
                double v = Double.parseDouble(nKcal.replace(",", "."));
                lbl.setText(String.format("<html><b>%s</b>: %s (%.2f kcal)</html>", tipo, nDesc, v));
            }
        });

        JButton btnDel = new JButton("Excluir");
        btnDel.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Excluir?") == JOptionPane.YES_OPTION) {
                controller.excluirRefeicao(tipo, (int) usuarioLogado.getId());
                painelRefeicoes.remove(linha);
                painelRefeicoes.revalidate();
                painelRefeicoes.repaint();
            }
        });

        linha.add(lbl); linha.add(btnEdit); linha.add(btnDel);
        painelRefeicoes.add(linha);
        painelRefeicoes.revalidate();
        painelRefeicoes.repaint();
    }
}