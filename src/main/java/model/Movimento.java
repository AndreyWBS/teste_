package model;

import java.util.Date;

// Classe que representa uma movimentação de estoque (entrada ou saída)
public class Movimento {
    private int id;
    private int produtoId;
    private int quantidade;
    private String tipo; // ENTRADA ou SAIDA
    private Date dataMovimentacao;

    public Movimento() {}

    public Movimento(int produtoId, int quantidade, String tipo, Date dataMovimentacao) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.dataMovimentacao = dataMovimentacao;
    }

    public Movimento(int id, int produtoId, int quantidade, String tipo, Date dataMovimentacao) {
        this.id = id;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.dataMovimentacao = dataMovimentacao;
    }

    // getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Date getDataMovimentacao() {
        return dataMovimentacao;
    }
    public void setDataMovimentacao(Date dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }
}