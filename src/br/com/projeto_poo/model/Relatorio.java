package br.com.projeto_poo.model;

public class Relatorio {
    private String periodo;
    private double totalCalorias;
    private double evolucaoPeso;
    private String statusMeta;
    private Usuario usuario;

    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public double getTotalCalorias() {
        return totalCalorias;
    }
    public void setTotalCalorias(double totalCalorias) {
        this.totalCalorias = totalCalorias;
    }

    public double getEvolucaoPeso() {
        return evolucaoPeso;
    }
    public void setEvolucaoPeso(double evolucaoPeso) {
        this.evolucaoPeso = evolucaoPeso;
    }

    public String getStatusMeta() {
        return statusMeta;
    }
    public void setStatusMeta(String statusMeta) {
        this.statusMeta = statusMeta;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
