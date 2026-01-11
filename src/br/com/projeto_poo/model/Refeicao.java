package br.com.projeto_poo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Refeicao {
    private LocalDate data;
    private LocalTime hora;
    private List<Alimento> alimentos;
    private Usuario usuario;
    private double totalCalorias;

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

    public double getTotalCalorias() {
        return totalCalorias;
    }
    public void setTotalCalorias(double totalCalorias) {
        this.totalCalorias = totalCalorias;
    }
}
