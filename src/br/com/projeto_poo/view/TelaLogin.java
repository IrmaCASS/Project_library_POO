package br.com.projeto_poo.view;

import br.com.projeto_poo.controller.UsuarioController;
import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame
{
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar, btnIrCadastro;
    private UsuarioController controller;

    public TelaLogin()
    {
        this.controller = new UsuarioController();

        setTitle("Login - Monitoramento Nutricional");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }
    private void inicializarComponentes()
    {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        painel.setBackground(new Color(245, 245, 245));

        JLabel lblTitulo = new JLabel("BEM-VINDO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(new Color(70, 130, 180));

        txtEmail = new JTextField();
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        txtEmail.setBorder(BorderFactory.createTitledBorder("E-mail"));

        txtSenha = new JPasswordField();
        txtSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha"));

        // --- BOTÃO ENTRAR (CORRIGIDO) ---
        btnEntrar = new JButton("ENTRAR");
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cores
        btnEntrar.setBackground(new Color(70, 130, 180));
        btnEntrar.setForeground(Color.WHITE);

        // Propriedades para forçar a cor no Windows/Linux
        btnEntrar.setOpaque(true);
        btnEntrar.setContentAreaFilled(true); // Garante que o fundo seja preenchido
        btnEntrar.setBorderPainted(false);    // Remove bordas que podem causar o efeito branco

        btnEntrar.setFocusPainted(false);
        btnEntrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnIrCadastro = new JButton("Ainda não tenho conta");
        btnIrCadastro.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIrCadastro.setContentAreaFilled(false);
        btnIrCadastro.setBorderPainted(false);
        btnIrCadastro.setForeground(Color.GRAY);
        btnIrCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painel.add(lblTitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 40)));
        painel.add(txtEmail);
        painel.add(Box.createRigidArea(new Dimension(0, 15)));
        painel.add(txtSenha);
        painel.add(Box.createRigidArea(new Dimension(0, 30)));
        painel.add(btnEntrar);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(btnIrCadastro);

        add(painel);
        // Eventos
        btnEntrar.addActionListener(e ->
        {
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            if (email.isEmpty() || senha.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }
            controller.efetuarLogin(email, senha, this);
        });

        btnIrCadastro.addActionListener(e ->
        {
            new TelaCadastroUsuario().setVisible(true);
            this.dispose();
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}