package br.com.projeto_poo.model;

import java.time.LocalDate;

public class Meta {

    // Atributos privados
    private String tipo; // Ex: perdaPeso, ganhoMassa, manutencao
    private double valor;
    private LocalDate prazo;
    private String usuario; // Pode ser alterado para um objeto da classe Usuario

    // Construtor Padrão
    public Meta() {
    }

    // Getters e Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
