package br.com.projeto_poo.controller;

import br.com.projeto_poo.dao.MonitoramentoDAO;

public class MonitoramentoController {

    private MonitoramentoDAO dao;

    public MonitoramentoController() {
        this.dao = new MonitoramentoDAO(); // DAO isola o SQL
    }

    public void modificarRefeicao(String tipo, String novaDesc, String novaKcal) {
        // 1. Validação
        if(novaDesc.isEmpty()) return;

        // 2. Chama o DAO para fazer o UPDATE no banco
        dao.atualizarRefeicao(tipo, novaDesc, Double.parseDouble(novaKcal));

        // 3. Notificar Observadores (Padrão Observer)
        // Notificador.notificarMudanca();
    }

    public void excluirRefeicao(String tipo) {
        // 1. Chama o DAO para fazer o DELETE


        // 2. Notificar Observadores
        System.out.println("Refeição " + tipo + " removida do banco.");
    }
}