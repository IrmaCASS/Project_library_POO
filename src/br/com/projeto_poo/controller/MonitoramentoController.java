package br.com.projeto_poo.controller;

import br.com.projeto_poo.dao.RefeicaoDAO;
import br.com.projeto_poo.model.Refeicao;
import java.util.List;

public class MonitoramentoController {
    private RefeicaoDAO refeicaoDao = new RefeicaoDAO();

    public double calcularTotalCalorias(List<String[]> refeicoes)
    {
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
    public void excluirRefeicao(Long refeicaoId) {
        refeicaoDao.excluir(refeicaoId);
    }

    public void modificarRefeicao(Long refeicaoId, String tipo, String novaKcal)
    {
        try {
            double kcal = Double.parseDouble(novaKcal.replace(",", "."));
            refeicaoDao.atualizar(refeicaoId, tipo, kcal);
        } catch (NumberFormatException e) {
            System.err.println("Erro: Valor de calorias inválido.");
        }
    }
    public List<Refeicao> listarRefeicoes(Long usuarioId) {
        return refeicaoDao.buscarPorUsuario(usuarioId);
    }
}