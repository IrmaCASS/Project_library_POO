package br.com.projeto_poo.view;

import br.com.projeto_poo.controller.MonitoramentoController;
import br.com.projeto_poo.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TelaMonitoramento extends JFrame {
    private MonitoramentoController controller = new MonitoramentoController();
    private JPanel painelRefeicoes;
    private Usuario usuarioLogado;

    private JLabel lblTotalConsumido;
    private JLabel lblMetaDiaria;
    private double totalCalorias = 0.0;

    public TelaMonitoramento(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("Monitoramento Nutricional");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- CABEÇALHO ---
        JPanel painelSuperior = new JPanel(new GridLayout(2, 1));
        painelSuperior.setBackground(new Color(70, 130, 180));

        // Linha 1: Nome, Botão Perfil e Data
        JPanel linha1 = new JPanel(new BorderLayout());
        linha1.setOpaque(false);
        linha1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel lblUser = new JLabel("Usuário: " + usuario.getNome());
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));

        // BOTÃO VER PERFIL (Adicionado aqui)
        JButton btnPerfil = new JButton("Ver Perfil");
        btnPerfil.setFocusPainted(false);
        btnPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPerfil.addActionListener(e -> new TelaPerfil(usuarioLogado).setVisible(true));

        String dataHoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        JLabel lblData = new JLabel("Data: " + dataHoje);
        lblData.setForeground(Color.WHITE);

        linha1.add(lblUser, BorderLayout.WEST);
        linha1.add(btnPerfil, BorderLayout.CENTER); // O botão fica centralizado na barra superior
        linha1.add(lblData, BorderLayout.EAST);

        // Linha 2: Resumo de Calorias
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        linha2.setOpaque(false);

        lblTotalConsumido = new JLabel("Consumido: 0.00 kcal");
        lblTotalConsumido.setForeground(Color.YELLOW);
        lblTotalConsumido.setFont(new Font("Arial", Font.BOLD, 16));

        double meta = usuario.getPeso() * 33;
        lblMetaDiaria = new JLabel(String.format("Meta Diária: %.2f kcal", meta));
        lblMetaDiaria.setForeground(Color.WHITE);

        linha2.add(lblTotalConsumido);
        linha2.add(lblMetaDiaria);

        painelSuperior.add(linha1);
        painelSuperior.add(linha2);
        add(painelSuperior, BorderLayout.NORTH);

        // --- CORPO (REFEIÇÕES) ---
        painelRefeicoes = new JPanel();
        painelRefeicoes.setLayout(new BoxLayout(painelRefeicoes, BoxLayout.Y_AXIS));
        add(new JScrollPane(painelRefeicoes), BorderLayout.CENTER);

        // --- RODAPÉ ---
        JButton btnNovo = new JButton("Adicionar Nova Refeição");
        btnNovo.setFont(new Font("Arial", Font.BOLD, 13));
        btnNovo.addActionListener(e -> {
            String tipo = JOptionPane.showInputDialog(this, "Tipo (ex: Almoço):");
            String desc = JOptionPane.showInputDialog(this, "Descrição:");
            String kcal = JOptionPane.showInputDialog(this, "Calorias:");

            if (tipo != null && desc != null && kcal != null) {
                try {
                    controller.adicionarRefeicao(tipo, desc, kcal, (int) usuarioLogado.getId());
                    adicionarLinhaRefeicao(tipo, desc, kcal);
                    atualizarCalculadora(Double.parseDouble(kcal.replace(",", ".")));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro nos valores informados.");
                }
            }
        });
        add(btnNovo, BorderLayout.SOUTH);

        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        if (usuarioLogado != null) {
            List<String[]> dados = controller.listarRefeicoes((int) usuarioLogado.getId());
            totalCalorias = 0;
            for (String[] r : dados) {
                adicionarLinhaRefeicao(r[0], r[1], r[2]);
                totalCalorias += Double.parseDouble(r[2]);
            }
            lblTotalConsumido.setText(String.format("Consumido: %.2f kcal", totalCalorias));
        }
    }

    private void atualizarCalculadora(double valor) {
        totalCalorias += valor;
        lblTotalConsumido.setText(String.format("Consumido: %.2f kcal", totalCalorias));
        double meta = usuarioLogado.getPeso() * 33;
        lblTotalConsumido.setForeground(totalCalorias > meta ? Color.ORANGE : Color.YELLOW);
    }

    private void adicionarLinhaRefeicao(String tipo, String desc, String kcal) {
        AtomicReference<String> descRef = new AtomicReference<>(desc);
        AtomicReference<String> kcalRef = new AtomicReference<>(kcal);

        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha.setBorder(BorderFactory.createEtchedBorder());
        linha.setMaximumSize(new Dimension(750, 50));

        double vKcal = Double.parseDouble(kcalRef.get().replace(",", "."));
        JLabel lbl = new JLabel(String.format("<html><b>%s</b>: %s (%.2f kcal)</html>", tipo, descRef.get(), vKcal));
        lbl.setPreferredSize(new Dimension(450, 30));

        JButton btnEdit = new JButton("Modificar");
        btnEdit.addActionListener(e -> {
            String nDesc = JOptionPane.showInputDialog(this, "Nova descrição:", descRef.get());
            String nKcal = JOptionPane.showInputDialog(this, "Novas calorias:", kcalRef.get());

            if (nDesc != null && nKcal != null) {
                double valorAntigo = Double.parseDouble(kcalRef.get().replace(",", "."));
                double valorNovo = Double.parseDouble(nKcal.replace(",", "."));
                controller.modificarRefeicao(tipo, nDesc, nKcal, (int) usuarioLogado.getId());
                atualizarCalculadora(valorNovo - valorAntigo);
                descRef.set(nDesc);
                kcalRef.set(nKcal);
                lbl.setText(String.format("<html><b>%s</b>: %s (%.2f kcal)</html>", tipo, nDesc, valorNovo));
            }
        });

        JButton btnDel = new JButton("Excluir");
        btnDel.addActionListener(e -> {
<<<<<<< HEAD
            // Definindo as opções personalizadas
            Object[] opcoes = {"Sim", "Não"};

=======
            // Definimos os textos dos botões em um array
            Object[] opcoes = {"Sim", "Não"};

            // Usamos o showOptionDialog para ter controle total sobre os botões
>>>>>>> a77dc080149cb695f1cafebcf91b477817826b49
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    "Deseja realmente excluir esta refeição?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
<<<<<<< HEAD
                    opcoes,
                    opcoes[0]
            );

            if (escolha == JOptionPane.YES_OPTION) {
                double valorExcluido = Double.parseDouble(kcalRef.get().replace(",", "."));
=======
                    opcoes, // Nomes dos botões personalizados
                    opcoes[0] // Botão padrão focado
            );

            // No showOptionDialog, o índice 0 corresponde à primeira opção do array ("Sim")
            if (escolha == 0) {
>>>>>>> a77dc080149cb695f1cafebcf91b477817826b49
                controller.excluirRefeicao(tipo, (int) usuarioLogado.getId());

                atualizarCalculadora(-valorExcluido);

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