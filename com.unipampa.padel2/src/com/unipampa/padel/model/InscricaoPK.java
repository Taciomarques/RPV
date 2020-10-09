package com.unipampa.padel.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InscricaoPK implements Serializable {
	
	@Basic(optional = false)
    @Column(name = "dupla", nullable = false)
    private int dupla;
    @Basic(optional = false)
    @Column(name = "etapa", nullable = false, length = 3)
    private int etapa;

    public InscricaoPK() {
    }

    public InscricaoPK(int dupla, int etapa) {
        this.dupla = dupla;
        this.etapa = etapa;
    }

    public int getDupla() {
        return dupla;
    }

    public void setDupla(int dupla) {
        this.dupla = dupla;
    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

}
