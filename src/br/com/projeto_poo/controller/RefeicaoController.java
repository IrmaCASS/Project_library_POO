package br.com.projeto_poo.controller;

import br.com.projeto_poo.dao.RefeicaoDAO;

import java.util.List;

public class RefeicaoController {
    RefeicaoDAO dao = new RefeicaoDAO();
    public void adicionarRefeicao(String tipo, String desc, String kcal, int usuarioId) {
        try {
            double valorKcal = Double.parseDouble(kcal.replace(",", "."));
            dao.salvarRefeicao(tipo, desc, valorKcal, usuarioId);
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter calorias: " + e.getMessage());
        }
    }

    public void adicionarRefeicao(String tipo, String kcal, int usuarioId) {
        try {
            double valorKcal = Double.parseDouble(kcal.replace(",", "."));
            dao.salvarRefeicao(tipo, valorKcal, usuarioId);
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

    // Caso precise listar tudo sem filtro de data (para compatibilidade)
    public List<String[]> listarRefeicoes(int usuarioId) {
        return dao.buscarPorUsuario(usuarioId);
    }
}
