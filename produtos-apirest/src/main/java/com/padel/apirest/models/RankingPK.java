package com.padel.apirest.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RankingPK implements Serializable {

	@Basic(optional = false)
    @Column(name = "atleta")
    private int atleta;
    @Basic(optional = false)
    @Column(name = "circuito")
    private int circuito;

    public RankingPK() {
    }

    public RankingPK(int atleta, int circuito) {
        this.atleta = atleta;
        this.circuito = circuito;
    }

    public int getAtleta() {
        return atleta;
    }

    public void setAtleta(int atleta) {
        this.atleta = atleta;
    }

    public int getCircuito() {
        return circuito;
    }

    public void setCircuito(int circuito) {
        this.circuito = circuito;
    }
}
