package model;

import java.math.BigDecimal;

public class Produto {
    private Long id;
    private String nomeProduto;
    private BigDecimal valorProduto;
    private String marcaProduto;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public BigDecimal getValorProduto() {
        return valorProduto;
    }
    public void setValorProduto(BigDecimal valorProduto) {
        this.valorProduto = valorProduto;
    }
    public String getMarcaProduto() {
        return marcaProduto;
    }
    public void setMarcaProduto(String marcaProduto) {
        this.marcaProduto = marcaProduto;
    }


    public Produto(Long id, String nomeProduto, String marcaProduto, BigDecimal valorProduto) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.marcaProduto = marcaProduto;
    }
    
    public Produto() {
        
    }

}




