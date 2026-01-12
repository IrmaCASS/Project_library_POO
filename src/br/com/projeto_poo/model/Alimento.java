package br.com.projeto_poo.model;

public class Alimento extends Entidade{
    private String nome;
    private double calorias;
    private double proteinas;
    private double carboidratos;
    private double gorduras;
    private Long id;

    public Alimento() {
    }

    public Alimento(String nome, double calorias, double proteinas, double carboidratos, double gorduras) {
        this.nome = nome;
        this.calorias = calorias;
        this.proteinas = proteinas;
        this.carboidratos = carboidratos;
        this.gorduras = gorduras;
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

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId(){
        return this.id;
    }
}