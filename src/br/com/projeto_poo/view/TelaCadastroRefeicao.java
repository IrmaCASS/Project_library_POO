package br.com.projeto_poo.view;

import br.com.projeto_poo.controller.RefeicaoController;
import br.com.projeto_poo.model.Alimento;
import br.com.projeto_poo.model.Refeicao;
import br.com.projeto_poo.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TelaCadastroRefeicao extends JFrame
{
    private JComboBox<String> cbTipoRefeicao;
    private JTable tabelaAlimentos;
    private DefaultTableModel tableModel;
    private JLabel lblTotalCalorias;
    private double totalCaloriasAcumuladas = 0;

    private Long usuarioIdLogado = 1L;
    private TelaMonitoramento parent;

    public TelaCadastroRefeicao(TelaMonitoramento parent)
    {
        this.parent = parent;
        setTitle("Cadastro de Refeição");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Painel Superior: Tipo de Refeição ---
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        painelSuperior.setBorder(BorderFactory.createTitledBorder("Dados da Refeição"));

        painelSuperior.add(new JLabel("Tipo de Refeição:"));
        cbTipoRefeicao = new JComboBox<>(new String[]
                {
                "Café da Manhã",
                "Almoço",
                "Jantar",
                "Lanche da Manhã",
                "Lanche da Tarde"
        });
        cbTipoRefeicao.setPreferredSize(new Dimension(200, 30));
        painelSuperior.add(cbTipoRefeicao);

        // --- Painel Central: Tabela de Alimentos ---
        JPanel painelCentral = new JPanel(new BorderLayout(5, 5));
        painelCentral.setBorder(BorderFactory.createTitledBorder("Alimentos da Refeição"));

        String[] colunas = {"Nome", "Calorias (kcal)", "Proteínas (g)", "Carbos (g)", "Gorduras (g)"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaAlimentos = new JTable(tableModel);
        tabelaAlimentos.setRowHeight(25);
        tabelaAlimentos.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelaAlimentos);
        painelCentral.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões da tabela
        JPanel painelBotoesTabela = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton btnAddAlimento = new JButton("➕ Adicionar Alimento");
        btnAddAlimento.setBackground(new Color(52, 152, 219));
        btnAddAlimento.setForeground(Color.WHITE);
        btnAddAlimento.setFocusPainted(false);
        btnAddAlimento.setOpaque(true);
        btnAddAlimento.setBorderPainted(false);
        btnAddAlimento.addActionListener(this::abrirDialogoAlimento);

        JButton btnRemoverAlimento = new JButton("➖ Remover Selecionado");
        btnRemoverAlimento.setBackground(new Color(231, 76, 60));
        btnRemoverAlimento.setForeground(Color.WHITE);
        btnRemoverAlimento.setFocusPainted(false);
        btnRemoverAlimento.setOpaque(true);
        btnRemoverAlimento.setBorderPainted(false);
        btnRemoverAlimento.addActionListener(e -> removerAlimentoSelecionado());

        painelBotoesTabela.add(btnAddAlimento);
        painelBotoesTabela.add(btnRemoverAlimento);
        painelCentral.add(painelBotoesTabela, BorderLayout.SOUTH);

        // --- Painel Inferior: Totais e Salvar ---
        JPanel painelInferior = new JPanel(new BorderLayout(10, 10));
        painelInferior.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // Lado esquerdo: Total de calorias
        lblTotalCalorias = new JLabel("Total Calorias: 0.00 kcal");
        lblTotalCalorias.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotalCalorias.setForeground(new Color(46, 204, 113));

        // Lado direito: Botões de ação
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton btnLimpar = new JButton("🗑 Limpar Tudo");
        btnLimpar.setPreferredSize(new Dimension(130, 35));
        btnLimpar.setBackground(new Color(149, 165, 166));
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFocusPainted(false);
        btnLimpar.setOpaque(true);
        btnLimpar.setBorderPainted(false);
        btnLimpar.addActionListener(e -> limparFormulario());

        JButton btnSalvar = new JButton("💾 Salvar Refeição");
        btnSalvar.setPreferredSize(new Dimension(150, 35));
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setOpaque(true);
        btnSalvar.setBorderPainted(false);
        btnSalvar.addActionListener(e -> salvarRefeicao());

        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSalvar);

        painelInferior.add(lblTotalCalorias, BorderLayout.WEST);
        painelInferior.add(painelBotoes, BorderLayout.EAST);

        // Adicionando ao Frame
        add(painelSuperior, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private void abrirDialogoAlimento(ActionEvent e)
    {
        JTextField nome = new JTextField();
        JTextField cal = new JTextField();
        JTextField prot = new JTextField();
        JTextField carb = new JTextField();
        JTextField gord = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Nome do Alimento:"));
        panel.add(nome);
        panel.add(new JLabel("Calorias (kcal):"));
        panel.add(cal);
        panel.add(new JLabel("Proteínas (g):"));
        panel.add(prot);
        panel.add(new JLabel("Carboidratos (g):"));
        panel.add(carb);
        panel.add(new JLabel("Gorduras (g):"));
        panel.add(gord);

        int opcao = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Adicionar Novo Alimento",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcao == JOptionPane.OK_OPTION)
        {
            try
            {
                String nomeAlimento = nome.getText().trim();
                if (nomeAlimento.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nome do alimento não pode estar vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double calorias = Double.parseDouble(cal.getText().trim());
                double proteinas = Double.parseDouble(prot.getText().trim());
                double carboidratos = Double.parseDouble(carb.getText().trim());
                double gorduras = Double.parseDouble(gord.getText().trim());

                tableModel.addRow(new Object[]
                        {
                        nomeAlimento,
                        String.format("%.2f", calorias),
                        String.format("%.2f", proteinas),
                        String.format("%.2f", carboidratos),
                        String.format("%.2f", gorduras)
                });
                atualizarTotalCalorias(calorias);
            } catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerAlimentoSelecionado()
    {
        int linhaSelecionada = tabelaAlimentos.getSelectedRow();
        if (linhaSelecionada >= 0)
        {
            String caloriasStr = tableModel.getValueAt(linhaSelecionada, 1).toString();

            Locale brasil = new Locale("pt", "BR");
            NumberFormat nf = NumberFormat.getInstance(brasil);

            double calorias = 0;
            try {calorias = nf.parse(caloriasStr).doubleValue();}
            catch (ParseException e) {throw new RuntimeException(e);}
            totalCaloriasAcumuladas -= calorias;

            tableModel.removeRow(linhaSelecionada);
            lblTotalCalorias.setText(String.format("Total Calorias: %.2f kcal", totalCaloriasAcumuladas));
        } else
        {
            JOptionPane.showMessageDialog(this, "Selecione um alimento para remover!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void atualizarTotalCalorias(double cal)
    {
        totalCaloriasAcumuladas += cal;
        lblTotalCalorias.setText(String.format("Total Calorias: %.2f kcal", totalCaloriasAcumuladas));
    }
    private void limparFormulario()
    {
        cbTipoRefeicao.setSelectedIndex(0);
        tableModel.setRowCount(0);
        totalCaloriasAcumuladas = 0;
        lblTotalCalorias.setText("Total Calorias: 0.00 kcal");
    }
    private void salvarRefeicao()
    {
        // Validação
        if (tableModel.getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos um alimento à refeição!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try
        {
            // Capturar data e hora atuais automaticamente
            LocalDateTime agora = LocalDateTime.now();
            DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

            String data = agora.format(formatoData);
            String hora = agora.format(formatoHora);
            String tipoRefeicao = cbTipoRefeicao.getSelectedItem().toString();

            // Montar a descrição com data, hora e todos os alimentos
            StringBuilder descricao = new StringBuilder();
            descricao.append("Data: ").append(data).append(" às ").append(hora).append("\n");
            descricao.append("Alimentos:\n");

            String totalCaloriasStr = String.format("%.2f", totalCaloriasAcumuladas);

            List<Alimento> alimentos = new ArrayList<Alimento>();

            for (int i = 0; i < tableModel.getRowCount(); i++)
            {
                String nomeAlimento = tableModel.getValueAt(i, 0).toString();
                String calorias = tableModel.getValueAt(i, 1).toString();
                String proteinas = tableModel.getValueAt(i, 2).toString();
                String carboidratos = tableModel.getValueAt(i, 3).toString();
                String gorduras = tableModel.getValueAt(i, 4).toString();

                Locale brasil = new Locale("pt", "BR");
                NumberFormat nf = NumberFormat.getInstance(brasil);

                double proteinasDouble = nf.parse(proteinas).doubleValue();
                double carboidratosDouble = nf.parse(carboidratos).doubleValue();
                double gordurasDouble = nf.parse(gorduras).doubleValue();
                double caloriasDouble = nf.parse(calorias).doubleValue();


                Alimento alimento = new Alimento(nomeAlimento, proteinasDouble, carboidratosDouble,
                        gordurasDouble, caloriasDouble );
                alimentos.add(alimento);

                descricao.append(String.format(
                        "- %s: %s kcal (Prot: %sg, Carb: %sg, Gord: %sg)\n",
                        nomeAlimento, calorias, proteinas, carboidratos, gorduras
                ));
            }

            Usuario usuarioLogado = new Usuario();
            usuarioLogado.setId(usuarioIdLogado);

            Refeicao refeicao = new Refeicao(alimentos, usuarioLogado,  0, tipoRefeicao);
            refeicao.setData(LocalDate.now());
            refeicao.setHora(LocalTime.now());

            // Chamar o controller com os parâmetros corretos
            RefeicaoController controller = new RefeicaoController();
            controller.salvarRefeicao(refeicao);

            JOptionPane.showMessageDialog(
                    this,
                    "Refeição cadastrada com sucesso!\n" +
                            "Tipo: " + tipoRefeicao + "\n" +
                            "Data/Hora: " + data + " " + hora + "\n" +
                            "Total de Calorias: " + totalCaloriasStr + " kcal",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limparFormulario();

            this.parent.carregarDadosIniciais();

            this.dispose();

        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao salvar refeição: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }
}