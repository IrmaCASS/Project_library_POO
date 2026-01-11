package br.com.projeto_poo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import br.com.projeto_poo.controller;

public class TelaCadastroUsuario extends JFrame {

    // Componentes da Interface
    private JTextField txtNome, txtIdade, txtPeso, txtAltura, txtGordura, txtMassa, txtMeta, txtData, txtEmail;
    private JPasswordField txtSenha;
    private JComboBox<String> cbSexo;
    private JButton btnSalvar, btnLimpar;

    private UsuarioController controller;

    public TelaCadastroUsuario() {
        this.controller = new UsuarioController();
        setTitle("Cadastro de Usuário");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridLayout(12, 2, 10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Adicionando Labels e Campos
        painelPrincipal.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelPrincipal.add(txtNome);

        painelPrincipal.add(new JLabel("Idade:"));
        txtIdade = new JTextField();
        painelPrincipal.add(txtIdade);

        painelPrincipal.add(new JLabel("Peso (kg):"));
        txtPeso = new JTextField();
        painelPrincipal.add(txtPeso);

        painelPrincipal.add(new JLabel("Altura (m):"));
        txtAltura = new JTextField();
        painelPrincipal.add(txtAltura);

        painelPrincipal.add(new JLabel("Sexo:"));
        cbSexo = new JComboBox<>(new String[]{"Masculino", "Feminino", "Outro"});
        painelPrincipal.add(cbSexo);

        painelPrincipal.add(new JLabel("% Gordura:"));
        txtGordura = new JTextField();
        painelPrincipal.add(txtGordura);

        painelPrincipal.add(new JLabel("Massa Corporal:"));
        txtMassa = new JTextField();
        painelPrincipal.add(txtMassa);

        painelPrincipal.add(new JLabel("Meta:"));
        txtMeta = new JTextField();
        painelPrincipal.add(txtMeta);

        painelPrincipal.add(new JLabel("Data Cadastro (dd/mm/aaaa):"));
        txtData = new JTextField();
        painelPrincipal.add(txtData);

        painelPrincipal.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        painelPrincipal.add(txtEmail);

        painelPrincipal.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        painelPrincipal.add(txtSenha);

        // Botões
        btnSalvar = new JButton("Cadastrar");
        btnLimpar = new JButton("Limpar");

        painelPrincipal.add(btnLimpar);
        painelPrincipal.add(btnSalvar);

        add(painelPrincipal);

        // Evento do Botão Salvar
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoSalvar();
            }
        });

        btnLimpar.addActionListener(e -> limparCampos());
    }

    private void acaoSalvar() {
        try {
            // Captura os dados da View e envia para o Controller
            controller.cadastrarUsuario(
                    txtNome.getText(),
                    Integer.parseInt(txtIdade.getText()),
                    Double.parseDouble(txtPeso.getText()),
                    Double.parseDouble(txtAltura.getText()),
                    cbSexo.getSelectedItem().toString(),
                    Double.parseDouble(txtGordura.getText()),
                    Double.parseDouble(txtMassa.getText()),
                    txtMeta.getText(),
                    txtData.getText(),
                    txtEmail.getText(),
                    new String(txtSenha.getPassword())
            );

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            limparCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique se os campos numéricos estão corretos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtIdade.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        txtGordura.setText("");
        txtMassa.setText("");
        txtMeta.setText("");
        txtData.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
    }

    public static void main(String[] args) {
        // Executa a tela
        SwingUtilities.invokeLater(() -> {
            new TelaCadastroUsuario().setVisible(true);
        });
    }
}
