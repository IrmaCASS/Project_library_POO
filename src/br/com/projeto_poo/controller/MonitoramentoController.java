package br.com.projeto_poo.controller;

import br.com.projeto_poo.dao.MonitoramentoDAO;
import java.util.List; // Não esqueça deste import!

public class MonitoramentoController {
    private MonitoramentoDAO dao = new MonitoramentoDAO();

    // ESTE É O MÉTODO QUE ESTAVA FALTANDO:
    public List<String[]> listarRefeicoes(int usuarioId) {
        return dao.buscarPorUsuario(usuarioId);
    }

    public void adicionarRefeicao(String tipo, String desc, String kcal, int usuarioId) {
        try {
            double valorKcal = Double.parseDouble(kcal.replace(",", "."));
            dao.salvarRefeicao(tipo, desc, valorKcal, usuarioId);
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter calorias: " + e.getMessage());
        }
    }

    public void excluirRefeicao(String tipo, int usuarioId) {
        dao.excluirRefeicao(tipo, usuarioId);
    }

    public void modificarRefeicao(String tipo, String novaDesc, String novaKcal, int usuarioId) {
        try {
            double kcal = Double.parseDouble(novaKcal.replace(",", "."));
            dao.atualizarRefeicao(tipo, novaDesc, kcal, usuarioId);
        } catch (NumberFormatException e) {
            System.err.println("Erro: Valor de calorias inválido.");
        }
    }
}