package com.unipampa.padel.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "circuito")
@NamedQueries({
    @NamedQuery(name = "Circuito.findAll", query = "SELECT c FROM Circuito c")
    , @NamedQuery(name = "Circuito.findById", query = "SELECT c FROM Circuito c WHERE c.id = :id")
    , @NamedQuery(name = "Circuito.findByNome", query = "SELECT c FROM Circuito c WHERE c.nome = :nome")})
public class Circuito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    
    @JoinColumn(name = "gerente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Gerente gerente;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "circuito")
    private List<Etapa> etapaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "circuito1")
    private List<Ranking> rankList;

    public Circuito() {
    }

    public Circuito(int id) {
        this.id = id;
    }

    public Circuito(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    public List<Etapa> getEtapaList() {
        return etapaList;
    }

    public void setEtapaList(List<Etapa> etapaList) {
        this.etapaList = etapaList;
    }

    public List<Ranking> getRankList() {
        return rankList;
    }

    public void setRankingList(List<Ranking> rankList) {
        this.rankList = rankList;
    }

    
}