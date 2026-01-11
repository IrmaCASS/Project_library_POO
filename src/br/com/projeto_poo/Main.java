package br.com.projeto_poo;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import br.com.projeto_poo.view.TelaCadastroUsuario;

public class Main {
    public static void main(String[] args) {
        // Tenta aplicar o visual nativo do sistema operacional (Windows, Linux ou Mac)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicia a aplicação pela primeira tela: Cadastro
        SwingUtilities.invokeLater(() -> {
            TelaCadastroUsuario telaInicial = new TelaCadastroUsuario();
            telaInicial.setVisible(true);
        });
    }
}