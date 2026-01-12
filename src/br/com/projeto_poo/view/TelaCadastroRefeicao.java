package br.com.projeto_poo.view;
import br.com.projeto_poo.controller.RefeicaoController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TelaCadastroRefeicao extends JFrame {
    private JTextField txtData, txtHora, txtUsuario;
    private JTable tabelaAlimentos;
    private DefaultTableModel tableModel;
    private JLabel lblTotalCalorias;
    private double totalCaloriasAcumuladas = 0;

    public TelaCadastroRefeicao() {
        setTitle("Cadastro de Refeição");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Painel Superior: Dados da Refeição ---
        JPanel painelSuperior = new JPanel(new GridLayout(3, 2, 5, 5));
        painelSuperior.setBorder(BorderFactory.createTitledBorder("Dados Gerais"));

        painelSuperior.add(new JLabel(" Data (AAAA-MM-DD):"));
        txtData = new JTextField();
        painelSuperior.add(txtData);

        painelSuperior.add(new JLabel(" Hora (HH:MM):"));
        txtHora = new JTextField();
        painelSuperior.add(txtHora);

        painelSuperior.add(new JLabel(" Usuário:"));
        txtUsuario = new JTextField();
        painelSuperior.add(txtUsuario);

        // --- Painel Central: Tabela de Alimentos ---
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBorder(BorderFactory.createTitledBorder("Alimentos da Refeição"));

        String[] colunas = {"Nome", "Calorias (kcal)", "Proteínas (g)", "Carbos (g)", "Gorduras (g)"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaAlimentos = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tabelaAlimentos);
        painelCentral.add(scrollPane, BorderLayout.CENTER);

        // Botão para adicionar alimento à tabela
        JButton btnAddAlimento = new JButton("Adicionar Alimento");
        btnAddAlimento.addActionListener(this::abrirDialogoAlimento);
        painelCentral.add(btnAddAlimento, BorderLayout.SOUTH);

        // --- Painel Inferior: Totais e Salvar ---
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotalCalorias = new JLabel("Total Calorias: 0.00 kcal");
        lblTotalCalorias.setFont(new Font("SansSerif", Font.BOLD, 14));

        JButton btnSalvar = new JButton("Salvar Refeição");
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.addActionListener(e -> salvarRefeicao());

        painelInferior.add(lblTotalCalorias);
        painelInferior.add(new JSeparator(SwingConstants.VERTICAL));
        painelInferior.add(btnSalvar);

        // Adicionando ao Frame
        add(painelSuperior, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private void abrirDialogoAlimento(ActionEvent e) {
        // Simulação de um sub-formulário rápido para entrada de dados do alimento
        JTextField nome = new JTextField();
        JTextField cal = new JTextField();
        JTextField prot = new JTextField();
        JTextField carb = new JTextField();
        JTextField gord = new JTextField();

        Object[] mensagem = {
                "Nome do Alimento:", nome,
                "Calorias:", cal,
                "Proteínas:", prot,
                "Carboidratos:", carb,
                "Gorduras:", gord
        };

        int opcao = JOptionPane.showConfirmDialog(null, mensagem, "Novo Alimento", JOptionPane.OK_CANCEL_OPTION);

        if (opcao == JOptionPane.OK_OPTION) {
            try {
                double c = Double.parseDouble(cal.getText());
                tableModel.addRow(new Object[]{
                        nome.getText(), c, prot.getText(), carb.getText(), gord.getText()
                });
                atualizarTotalCalorias(c);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valores numéricos inválidos!");
            }
        }
    }

    private void atualizarTotalCalorias(double cal) {
        totalCaloriasAcumuladas += cal;
        lblTotalCalorias.setText(String.format("Total Calorias: %.2f kcal", totalCaloriasAcumuladas));
    }

    private void salvarRefeicao() {
        RefeicaoController controller = new RefeicaoController();
        controller.adicionarRefeicao();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroRefeicao().setVisible(true));
    }
}