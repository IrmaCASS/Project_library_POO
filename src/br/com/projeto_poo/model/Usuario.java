package br.com.projeto_poo.model;
import java.util.Date;
public class Usuario {
    private String nome;
    private int idade;
    private float peso;
    private int altura;
    private String sexo;
    private float porcentagemGordura;
    private float massaCorporal;
    private float meta;
    private Date dataCadastro;
    private String email;
    private String senha;

    // Construtor padrÃ£o
    public Usuario() {
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public float getPorcentagemGordura() {
        return porcentagemGordura;
    }

    public void setPorcentagemGordura(float porcentagemGordura) {
        this.porcentagemGordura = porcentagemGordura;
    }

    public float getMassaCorporal() {
        return massaCorporal;
    }

    public void setMassaCorporal(float massaCorporal) {
        this.massaCorporal = massaCorporal;
    }

    public float getMeta() {
        return meta;
    }

    public void setMeta(float meta) {
        this.meta = meta;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}