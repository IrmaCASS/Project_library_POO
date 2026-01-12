package br.com.projeto_poo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Refeicao extends Entidade{
    private LocalDate data;
    private LocalTime hora;
    private List<Alimento> alimentos;
    private Usuario usuario;
    private Double totalCalorias;
    private String tipo;
    private long id;

    public Refeicao(){

    }

    public Refeicao(List<Alimento> alimentos, Usuario usuario, double totalCalorias, String tipo) {
        this.alimentos = alimentos;
        this.usuario = usuario;
        this.totalCalorias = totalCalorias;
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public List<Alimento> getAlimentos() {
        return alimentos;
    }
    public void setAlimentos(List<Alimento> alimentos) {
        this.alimentos = alimentos;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getTotalCalorias() {
        return totalCalorias;
    }
    public void setTotalCalorias(Double totalCalorias) {
        this.totalCalorias = totalCalorias;
    }

    public String getTipo(){
        return this.tipo;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId(){
        return this.id;
    }
}
