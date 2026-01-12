package br.com.projeto_poo.controller;

import br.com.projeto_poo.dao.MonitoramentoDAO;
import java.util.List;

public class MonitoramentoController {
    private MonitoramentoDAO dao = new MonitoramentoDAO();

    // 1. Lista refeições filtrando por usuário e data
    public List<String[]> listarRefeicoesPorData(int usuarioId, String data) {
        return dao.buscarPorData(usuarioId, data);
    }

    // 2. Calcula o total de calorias de uma lista de refeições (Calculadora)
    public double calcularTotalCalorias(List<String[]> refeicoes) {
        double total = 0;
        for (String[] r : refeicoes) {
            try {
                // O índice [2] corresponde ao valor das calorias no array
                total += Double.parseDouble(r[2].replace(",", "."));
            } catch (NumberFormatException e) {
                System.err.println("Erro ao somar caloria: " + e.getMessage());
            }
        }
        return total;
    }

    // Mantendo os métodos anteriores com melhoria no tratamento de números
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

    // Caso precise listar tudo sem filtro de data (para compatibilidade)
    public List<String[]> listarRefeicoes(int usuarioId) {
        return dao.buscarPorUsuario(usuarioId);
    }
}