package com.padel.apirest.response;

import java.io.Serializable;
import java.util.List;

public class CircuitoDTO implements Serializable {

    
    private int id;
    
    
    private String nome;
    
    
    private int gerente;
    
    
    private List<Integer> etapaList;
    
    private List<Integer> rankList;

    public CircuitoDTO() {
    }

    public CircuitoDTO(int id) {
        this.id = id;
    }

    public CircuitoDTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setIdcircuito(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getGerente() {
        return gerente;
    }

    public void setGerente(int gerente) {
        this.gerente = gerente;
    }

    public List<Integer> getEtapaList() {
        return etapaList;
    }

    public void setEtapaList(List<Integer> etapaList) {
        this.etapaList = etapaList;
    }

    public List<Integer> getRankList() {
        return rankList;
    }

    public void setRankingList(List<Integer> rankList) {
        this.rankList = rankList;
    }

    
}