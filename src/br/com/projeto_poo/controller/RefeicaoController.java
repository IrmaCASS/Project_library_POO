/*package br.com.projeto_poo.controller;

import br.com.projeto_poo.dao.RefeicaoDAO;
import br.com.projeto_poo.dao.AlimentoDAO;
import br.com.projeto_poo.model.Refeicao;
import br.com.projeto_poo.model.Alimento;
import br.com.projeto_poo.model.Usuario;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class RefeicaoController {

    private RefeicaoDAO refeicaoDAO;
    private AlimentoDAO alimentoDAO;

    public RefeicaoController() {
        this.refeicaoDAO = new RefeicaoDAO();
        this.alimentoDAO = new AlimentoDAO();
    }

    // ==================== SALVAR REFEIÇÃO ====================

    /**
     * Salva uma nova refeição
     * @param tipo Tipo da refeição (Café da Manhã, Almoço, etc.)
     * @param descricao Descrição com os alimentos
     * @param totalCalorias Total de calorias como String
     * @param usuarioId ID do usuário
     * @return true se salvou com sucesso

    public boolean salvarRefeicao(String tipo, String descricao, String totalCalorias, int usuarioId) {
        try {
            // Validações
            if (tipo == null || tipo.trim().isEmpty()) {
                exibirErro("O tipo da refeição não pode estar vazio!");
                return false;
            }

            if (usuarioId <= 0) {
                exibirErro("ID do usuário inválido!");
                return false;
            }

            // Converter calorias
            double calorias;
            try {
                calorias = Double.parseDouble(totalCalorias.replace(",", "."));
            } catch (NumberFormatException e) {
                exibirErro("Valor de calorias inválido: " + totalCalorias);
                return false;
            }

            // Criar objeto Refeicao
            Refeicao refeicao = new Refeicao();
            refeicao.setTipo(tipo);
            refeicao.setData(LocalDate.now());
            refeicao.setHora(LocalTime.now());
            refeicao.setTotalCalorias(calorias);

            // Criar objeto Usuario
            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            refeicao.setUsuario(usuario);

            // Extrair alimentos da descrição (se necessário)
            refeicao.setAlimentos(new ArrayList<>()); // Por enquanto vazio

            // Salvar no banco
            Long id = refeicaoDAO.salvar(refeicao);

            if (id != null) {
                System.out.println("Refeição salva com sucesso! ID: " + id);
                return true;
            } else {
                exibirErro("Falha ao salvar refeição no banco de dados.");
                return false;
            }

        } catch (Exception e) {
            exibirErro("Erro ao salvar refeição: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Salva uma refeição completa com lista de alimentos
     * @param tipo Tipo da refeição
     * @param alimentos Lista de alimentos
     * @param usuarioId ID do usuário
     * @return ID da refeição criada ou null se falhar

    public Long salvarRefeicaoCompleta(String tipo, List<Alimento> alimentos, int usuarioId) {
        try {
            // Validações
            if (tipo == null || tipo.trim().isEmpty()) {
                exibirErro("O tipo da refeição não pode estar vazio!");
                return null;
            }

            if (alimentos == null || alimentos.isEmpty()) {
                exibirErro("A refeição deve conter pelo menos um alimento!");
                return null;
            }

            if (usuarioId <= 0) {
                exibirErro("ID do usuário inválido!");
                return null;
            }

            // Calcular total de calorias
            double totalCalorias = 0;
            for (Alimento alimento : alimentos) {
                totalCalorias += alimento.getCalorias();
            }

            // Criar objeto Refeicao
            Refeicao refeicao = new Refeicao();
            refeicao.setTipo(tipo);
            refeicao.setData(LocalDate.now());
            refeicao.setHora(LocalTime.now());
            refeicao.setTotalCalorias(totalCalorias);
            refeicao.setAlimentos(alimentos);

            // Criar objeto Usuario
            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            refeicao.setUsuario(usuario);

            // Salvar no banco
            Long id = refeicaoDAO.salvar(refeicao);

            if (id != null) {
                System.out.println("Refeição salva com sucesso! ID: " + id);
                return id;
            } else {
                exibirErro("Falha ao salvar refeição no banco de dados.");
                return null;
            }

        } catch (Exception e) {
            exibirErro("Erro ao salvar refeição: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // ==================== BUSCAR/LISTAR REFEIÇÕES ====================

    /**
     * Busca uma refeição por ID

    public Refeicao buscarRefeicaoPorId(Long id) {
        try {
            if (id == null || id <= 0) {
                exibirErro("ID inválido!");
                return null;
            }

            Refeicao refeicao = refeicaoDAO.buscarPorId(id);

            if (refeicao == null) {
                System.out.println("Refeição não encontrada com ID: " + id);
            }

            return refeicao;

        } catch (Exception e) {
            exibirErro("Erro ao buscar refeição: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lista todas as refeições de um usuário

    public List<Refeicao> listarRefeicoesPorUsuario(int usuarioId) {
        try {
            if (usuarioId <= 0) {
                exibirErro("ID do usuário inválido!");
                return new ArrayList<>();
            }

            List<Refeicao> refeicoes = refeicaoDAO.buscarPorUsuario(usuarioId);
            System.out.println("Encontradas " + refeicoes.size() + " refeições para o usuário " + usuarioId);
            return refeicoes;

        } catch (Exception e) {
            exibirErro("Erro ao listar refeições: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lista refeições por tipo

    public List<Refeicao> listarPorTipo(int usuarioId, String tipo) {
        try {
            if (usuarioId <= 0) {
                exibirErro("ID do usuário inválido!");
                return new ArrayList<>();
            }

            if (tipo == null || tipo.trim().isEmpty()) {
                exibirErro("Tipo da refeição não pode estar vazio!");
                return new ArrayList<>();
            }

            return refeicaoDAO.buscarPorTipo(usuarioId, tipo);

        } catch (Exception e) {
            exibirErro("Erro ao listar refeições por tipo: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lista refeições de uma data específica

    public List<Refeicao> listarPorData(int usuarioId, String dataStr) {
        try {
            if (usuarioId <= 0) {
                exibirErro("ID do usuário inválido!");
                return new ArrayList<>();
            }

            // Converter String para LocalDate
            LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            return refeicaoDAO.buscarPorData(usuarioId, data);

        } catch (DateTimeParseException e) {
            exibirErro("Formato de data inválido! Use: AAAA-MM-DD");
            return new ArrayList<>();
        } catch (Exception e) {
            exibirErro("Erro ao listar refeições por data: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lista refeições de um período

    public List<Refeicao> listarPorPeriodo(int usuarioId, String dataInicioStr, String dataFimStr) {
        try {
            if (usuarioId <= 0) {
                exibirErro("ID do usuário inválido!");
                return new ArrayList<>();
            }

            // Converter Strings para LocalDate
            LocalDate dataInicio = LocalDate.parse(dataInicioStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate dataFim = LocalDate.parse(dataFimStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            return refeicaoDAO.buscarPorPeriodo(usuarioId, dataInicio, dataFim);

        } catch (DateTimeParseException e) {
            exibirErro("Formato de data inválido! Use: AAAA-MM-DD");
            return new ArrayList<>();
        } catch (Exception e) {
            exibirErro("Erro ao listar refeições por período: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lista todas as refeições do sistema

    public List<Refeicao> listarTodasRefeicoes() {
        try {
            return refeicaoDAO.listarTodas();
        } catch (Exception e) {
            exibirErro("Erro ao listar todas as refeições: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ==================== ATUALIZAR REFEIÇÃO ====================

    /**
     * Atualiza o tipo e calorias de uma refeição

    public boolean atualizarRefeicao(Long id, String novoTipo, String novasTotalCalorias) {
        try {
            // Validações
            if (id == null || id <= 0) {
                exibirErro("ID da refeição inválido!");
                return false;
            }

            if (novoTipo == null || novoTipo.trim().isEmpty()) {
                exibirErro("O tipo da refeição não pode estar vazio!");
                return false;
            }

            // Converter calorias
            double calorias;
            try {
                calorias = Double.parseDouble(novasTotalCalorias.replace(",", "."));
            } catch (NumberFormatException e) {
                exibirErro("Valor de calorias inválido: " + novasTotalCalorias);
                return false;
            }

            // Atualizar no banco
            boolean sucesso = refeicaoDAO.atualizar(id, novoTipo, calorias);

            if (sucesso) {
                System.out.println("Refeição atualizada com sucesso!");
                return true;
            } else {
                exibirErro("Falha ao atualizar refeição. Verifique se o ID existe.");
                return false;
            }

        } catch (Exception e) {
            exibirErro("Erro ao atualizar refeição: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Atualiza apenas o tipo da refeição

    public boolean atualizarTipo(Long id, String novoTipo) {
        try {
            // Buscar refeição atual para manter as calorias
            Refeicao refeicao = refeicaoDAO.buscarPorId(id);

            if (refeicao == null) {
                exibirErro("Refeição não encontrada!");
                return false;
            }

            return refeicaoDAO.atualizar(id, novoTipo, refeicao.getTotalCalorias());

        } catch (Exception e) {
            exibirErro("Erro ao atualizar tipo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ==================== EXCLUIR REFEIÇÃO ====================

    /**
     * Exclui uma refeição por ID

    public boolean excluirRefeicao(Long id) {
        try {
            if (id == null || id <= 0) {
                exibirErro("ID da refeição inválido!");
                return false;
            }

            boolean sucesso = refeicaoDAO.excluir(id);

            if (sucesso) {
                System.out.println("Refeição excluída com sucesso!");
                return true;
            } else {
                exibirErro("Falha ao excluir refeição. Verifique se o ID existe.");
                return false;
            }

        } catch (Exception e) {
            exibirErro("Erro ao excluir refeição: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Exclui uma refeição com confirmação

    public boolean excluirRefeicaoComConfirmacao(Long id, JFrame parent) {
        try {
            // Buscar informações da refeição
            Refeicao refeicao = refeicaoDAO.buscarPorId(id);

            if (refeicao == null) {
                exibirErro("Refeição não encontrada!");
                return false;
            }

            // Solicitar confirmação
            String mensagem = String.format(
                    "Tem certeza que deseja excluir esta refeição?\n\n" +
                            "Tipo: %s\n" +
                            "Data: %s\n" +
                            "Hora: %s\n" +
                            "Calorias: %.2f kcal",
                    refeicao.getTipo(),
                    refeicao.getData(),
                    refeicao.getHora(),
                    refeicao.getTotalCalorias()
            );

            int confirmacao = JOptionPane.showConfirmDialog(
                    parent,
                    mensagem,
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                return excluirRefeicao(id);
            } else {
                System.out.println("Exclusão cancelada pelo usuário.");
                return false;
            }

        } catch (Exception e) {
            exibirErro("Erro ao excluir refeição: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ==================== ANÁLISES E RELATÓRIOS ====================

    /**
     * Calcula o total de calorias consumidas em um dia

    public double calcularCaloriasDoDia(int usuarioId, String dataStr) {
        try {
            LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return refeicaoDAO.calcularCaloriasDoDia(usuarioId, data);
        } catch (DateTimeParseException e) {
            exibirErro("Formato de data inválido! Use: AAAA-MM-DD");
            return 0.0;
        } catch (Exception e) {
            exibirErro("Erro ao calcular calorias: " + e.getMessage());
            e.printStackTrace();
            return 0.0;
        }
    }

    /**
     * Conta quantas refeições um usuário tem registradas

    public int contarRefeicoes(int usuarioId) {
        try {
            return refeicaoDAO.contarPorUsuario(usuarioId);
        } catch (Exception e) {
            exibirErro("Erro ao contar refeições: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Gera um resumo das refeições do dia

    public String gerarResumoDodia(int usuarioId, String dataStr) {
        try {
            LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<Refeicao> refeicoes = refeicaoDAO.buscarPorData(usuarioId, data);

            if (refeicoes.isEmpty()) {
                return "Nenhuma refeição registrada nesta data.";
            }

            StringBuilder resumo = new StringBuilder();
            resumo.append("=== RESUMO DO DIA ").append(dataStr).append(" ===\n\n");

            double totalCalorias = 0;

            for (Refeicao ref : refeicoes) {
                resumo.append(String.format("⏰ %s - %s\n",
                        ref.getHora().format(DateTimeFormatter.ofPattern("HH:mm")),
                        ref.getTipo()));
                resumo.append(String.format("   Calorias: %.2f kcal\n", ref.getTotalCalorias()));

                if (ref.getAlimentos() != null && !ref.getAlimentos().isEmpty()) {
                    resumo.append("   Alimentos:\n");
                    for (Alimento alim : ref.getAlimentos()) {
                        resumo.append(String.format("   - %s (%.2f kcal)\n",
                                alim.getNome(), alim.getCalorias()));
                    }
                }
                resumo.append("\n");
                totalCalorias += ref.getTotalCalorias();
            }

            resumo.append("─────────────────────────────\n");
            resumo.append(String.format("TOTAL DO DIA: %.2f kcal\n", totalCalorias));
            resumo.append(String.format("Refeições registradas: %d\n", refeicoes.size()));

            return resumo.toString();

        } catch (DateTimeParseException e) {
            return "Erro: Formato de data inválido! Use: AAAA-MM-DD";
        } catch (Exception e) {
            return "Erro ao gerar resumo: " + e.getMessage();
        }
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Exibe mensagem de erro

    private void exibirErro(String mensagem) {
        System.err.println("ERRO: " + mensagem);
        // Você pode descomentar a linha abaixo para exibir em JOptionPane
        // JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Valida se uma String pode ser convertida para double

    private boolean isNumeroValido(String valor) {
        try {
            Double.parseDouble(valor.replace(",", "."));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}*/

package br.com.projeto_poo.controller;

import br.com.projeto_poo.dao.RefeicaoDAO;
import br.com.projeto_poo.dao.AlimentoDAO;
import br.com.projeto_poo.model.Refeicao;
import br.com.projeto_poo.model.Alimento;
import javax.swing.*;
import java.util.List;

public class RefeicaoController
{
    private RefeicaoDAO refeicaoDAO;
    private AlimentoDAO alimentoDAO;

    public RefeicaoController()
    {
        this.refeicaoDAO = new RefeicaoDAO();
        this.alimentoDAO = new AlimentoDAO();
    }

    public Long salvarRefeicao(Refeicao refeicao)
    {
        try
        {
            if (refeicao == null) {throw new IllegalArgumentException("Refeição não pode ser nula!");}
            if (!validarRefeicao(refeicao)) {return null;}

            // Calcular total de calorias baseado nos alimentos
            double totalCalorias = 0;
            for (Alimento alimento : refeicao.getAlimentos())
            {
                if (alimento != null) {totalCalorias += alimento.getCalorias();}
            }
            // Configurar a refeição
            refeicao.setTotalCalorias(totalCalorias);
            // salva na quele trem (Banco de dados)
            Long id = refeicaoDAO.salvar(refeicao);
            if (id != null) {refeicao.setId(id);}

            return id;
        } catch (Exception e)
        {
            System.err.println("Erro ao salvar refeição: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //Modifica uma refeição existente e a lista de alimentos no banco de dados
    public boolean modificarRefeicao(Refeicao refeicao) {
        try {
            // Validações
            if (refeicao == null) {
                throw new IllegalArgumentException("Refeição não pode ser nula!");
            }

            if (refeicao.getId() <= 0) {
                throw new IllegalArgumentException("ID da refeição inválido para modificação!");
            }
            if (!validarRefeicao(refeicao)) {return false;}

            boolean sucesso = refeicaoDAO.atualizar(
                    refeicao.getId(),
                    refeicao.getTipo(),
                    refeicao.getTotalCalorias()
            );
            return sucesso;

        } catch (Exception e)
        {
            System.err.println("Erro ao modificar refeição: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

     // Lista todas as refeições de um usuário específico do banco de dados
    public List<Refeicao> listarRefeicoes(Long usuarioId) {
        try {
            // Validação
            if (usuarioId <= 0) {
                throw new IllegalArgumentException("ID do usuário inválido!");
            }

            // REALMENTE BUSCAR DO BANCO DE DADOS VIA DAO
            List<Refeicao> refeicoes = refeicaoDAO.buscarPorUsuario(usuarioId);

            return refeicoes;

        } catch (Exception e) {
            System.err.println("Erro ao listar refeições: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Retorna lista vazia em caso de erro
        }
    }

    //Exclui uma refeição pelo seu ID do banco de dados
    public boolean excluirRefeicao(long refeicaoId)
    {
        try
        {
            // Validação
            if (refeicaoId <= 0) {throw new IllegalArgumentException("ID da refeição inválido!");}

            // REALMENTE EXCLUIR DO BANCO DE DADOS VIA DAO
            boolean sucesso = refeicaoDAO.excluir(refeicaoId);
            return sucesso;

        } catch (Exception e)
        {
            System.err.println("Erro ao excluir refeição: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ==================== MÉTODOS AUXILIARES ====================
    //Valida os dados básicos de uma refeição
    private boolean validarRefeicao(Refeicao refeicao)
    {
        if (refeicao.getTipo() == null || refeicao.getTipo().trim().isEmpty())
        {
            throw new IllegalArgumentException("O tipo da refeição não pode estar vazio!");
        }
        if (refeicao.getData() == null)
        {
            throw new IllegalArgumentException("A data da refeição não pode ser nula!");
        }
        if (refeicao.getHora() == null)
        {
            throw new IllegalArgumentException("A hora da refeição não pode ser nula!");
        }
        if (refeicao.getUsuario() == null || refeicao.getUsuario().getId() <= 0)
        {
            throw new IllegalArgumentException("Usuário inválido ou não informado!");
        }
        return true;
    }

    //Método opcional para usar com interface gráfica
    public void exibirErroGUI(String mensagem, JFrame parent)
    {
        JOptionPane.showMessageDialog(parent, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void exibirSucessoGUI(String mensagem, JFrame parent)
    {
        JOptionPane.showMessageDialog(parent, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}