package com.asmelo.apiFinanceiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Subcategoria {

    @Id
    @GeneratedValue
    @Column(name = "idsubcategoria")
    private Integer id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "idcategoria")
    private Integer idcategoria;
    @Column(name = "cdtipo")
    private Integer cdtipo;
    @Column(name = "flativo")
    private Integer flativo;

    public Subcategoria() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Integer getCdtipo() {
        return cdtipo;
    }

    public void setCdtipo(Integer cdtipo) {
        this.cdtipo = cdtipo;
    }

    public Integer getFlativo() {
        return flativo;
    }

    public void setFlativo(Integer flativo) {
        this.flativo = flativo;
    }
}
