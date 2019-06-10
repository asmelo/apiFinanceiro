package com.asmelo.apiFinanceiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Lancamento {

    @Id
    @GeneratedValue
    @Column(name = "idlancamento")
    private Integer id;
    @Column(name = "idconta")
    private Integer idconta;
    @Column(name = "idsubcategoria")
    private String subcategoria;
    @Column(name = "cdtipo")
    private Integer cdtipo;
    @Column(name = "dtlancamento")
    private Date dtlancamento;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "referenciaofx")
    private String referenciaofx;


    public Lancamento() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdconta() {
        return idconta;
    }

    public void setIdconta(Integer idconta) {
        this.idconta = idconta;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public Integer getCdtipo() {
        return cdtipo;
    }

    public void setCdtipo(Integer cdtipo) {
        this.cdtipo = cdtipo;
    }

    public Date getDtlancamento() {
        return dtlancamento;
    }

    public void setDtlancamento(Date dtlancamento) {
        this.dtlancamento = dtlancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getReferenciaofx() {
        return referenciaofx;
    }

    public void setReferenciaofx(String referenciaofx) {
        this.referenciaofx = referenciaofx;
    }
}
