package br.com.projeto_poo.model;

public class Alimento {
    private String nome;
    private double calorias;
    private double proteinas;
    private double carboidratos;
    private double gorduras;
    private String categoria;
    private double porcaoPadrao;
    private long id;

    public Alimento() {
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getCalorias() {
        return calorias;
    }
    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public double getProteinas() {
        return proteinas;
    }
    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }

    public double getCarboidratos() {
        return carboidratos;
    }
    public void setCarboidratos(double carboidratos) {
        this.carboidratos = carboidratos;
    }

    public double getGorduras() {
        return gorduras;
    }
    public void setGorduras(double gorduras) {
        this.gorduras = gorduras;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPorcaoPadrao() {
        return porcaoPadrao;
    }
    public void setPorcaoPadrao(double porcaoPadrao) {
        this.porcaoPadrao = porcaoPadrao;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
}