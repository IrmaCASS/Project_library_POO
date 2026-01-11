package br.com.projeto_poo.model;

import java.time.LocalDateTime;

public class FotoAlimento {
    private byte[] imagem; // Armazena os dados binários da imagem
    private String alimentoReconhecido;
    private double caloriasEstimadas;
    private LocalDateTime data;
    private String usuario;

    public FotoAlimento() {
    }

    public byte[] getImagem() {
        return imagem;
    }
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getAlimentoReconhecido() {
        return alimentoReconhecido;
    }
    public void setAlimentoReconhecido(String alimentoReconhecido) {
        this.alimentoReconhecido = alimentoReconhecido;
    }

    public double getCaloriasEstimadas() {
        return caloriasEstimadas;
    }
    public void setCaloriasEstimadas(double caloriasEstimadas) {
        this.caloriasEstimadas = caloriasEstimadas;
    }

    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}