package br.com.projeto_poo.view;

import br.com.projeto_poo.controller.UsuarioController;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaCadastroUsuario extends JFrame {
    private JTextField txtNome, txtIdade, txtPeso, txtAltura, txtGordura, txtMassa, txtMeta, txtEmail;
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

        // ... (Seus campos JLabel e JTextField anteriores)
        painelPrincipal.add(new JLabel("Nome:")); txtNome = new JTextField(); painelPrincipal.add(txtNome);
        painelPrincipal.add(new JLabel("Idade:")); txtIdade = new JTextField(); painelPrincipal.add(txtIdade);
        painelPrincipal.add(new JLabel("Peso (kg):")); txtPeso = new JTextField(); painelPrincipal.add(txtPeso);
        painelPrincipal.add(new JLabel("Altura (m):")); txtAltura = new JTextField(); painelPrincipal.add(txtAltura);
        painelPrincipal.add(new JLabel("Sexo:")); cbSexo = new JComboBox<>(new String[]{"Masculino", "Feminino", "Outro"}); painelPrincipal.add(cbSexo);
        painelPrincipal.add(new JLabel("% Gordura:")); txtGordura = new JTextField(); painelPrincipal.add(txtGordura);
        painelPrincipal.add(new JLabel("Massa Corporal:")); txtMassa = new JTextField(); painelPrincipal.add(txtMassa);
        painelPrincipal.add(new JLabel("Meta:")); txtMeta = new JTextField(); painelPrincipal.add(txtMeta);

        // CAMPO E-MAIL COM VALIDAÇÃO VISUAL
        painelPrincipal.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        txtEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (isEmailValido(txtEmail.getText())) {
                    txtEmail.setBorder(BorderFactory.createLineBorder(new Color(46, 139, 87), 1)); // Verde
                } else {
                    txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                }
            }
        });
        painelPrincipal.add(txtEmail);

        painelPrincipal.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        painelPrincipal.add(txtSenha);

        // Restrições Numéricas
        txtIdade.setDocument(new NumericDocument(false));
        txtPeso.setDocument(new NumericDocument(true));
        txtAltura.setDocument(new NumericDocument(true));
        txtGordura.setDocument(new NumericDocument(true));
        txtMassa.setDocument(new NumericDocument(true));

        btnSalvar = new JButton("Cadastrar");
        btnLimpar = new JButton("Limpar");

        painelPrincipal.add(btnLimpar);
        painelPrincipal.add(btnSalvar);
        add(painelPrincipal);

        btnSalvar.addActionListener(e -> acaoSalvar());
        btnLimpar.addActionListener(e -> limparCampos());
    }

    // --- NOVA LÓGICA DE VALIDAÇÃO REGEX ---
    private boolean isEmailValido(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }

    private void acaoSalvar() {
        String email = txtEmail.getText().trim();

        // Validar e-mail antes de chamar o controller
        if (!isEmailValido(email)) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um e-mail válido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return;
        }

        try {
            controller.cadastrarUsuario(
                    txtNome.getText(),
                    Integer.parseInt(txtIdade.getText()),
                    Float.parseFloat(txtPeso.getText()),
                    Float.parseFloat(txtAltura.getText()),
                    cbSexo.getSelectedItem().toString(),
                    Float.parseFloat(txtGordura.getText()),
                    Float.parseFloat(txtMassa.getText()),
                    txtMeta.getText(),
                    email,
                    new String(txtSenha.getPassword()),
                    this
            );
            JOptionPane.showMessageDialog(this, "Sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
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
        txtEmail.setText("");
        txtEmail.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border")); // Reseta a borda
        txtSenha.setText("");
    }

    private static class NumericDocument extends PlainDocument {
        private final boolean allowDecimal;
        public NumericDocument(boolean allowDecimal) { this.allowDecimal = allowDecimal; }
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            String regex = allowDecimal ? "[0-9.,]" : "[0-9]";
            if (str.matches(regex)) super.insertString(offs, str, a);
        }
    }
}