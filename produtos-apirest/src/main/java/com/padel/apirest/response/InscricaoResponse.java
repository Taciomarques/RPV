package com.padel.apirest.response;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class InscricaoResponse implements Serializable{
	
//	protected InscricaoPK inscricaoPK;
	
	private Date horainsc;
	
    
	private int dupla1;
	
    
	private int etapa1;

	public Date getHorainsc() {
		return horainsc;
	}

	public void setHorainsc(Date horainsc) {
		this.horainsc = horainsc;
	}
	

//	public InscricaoPK getInscricaoPK() {
//		return inscricaoPK;
//	}
//
//	public void setInscricaoPK(InscricaoPK inscricaoPK) {
//		this.inscricaoPK = inscricaoPK;
//	}

	public int getDupla1() {
		return dupla1;
	}

	public void setDupla1(int dupla1) {
		this.dupla1 = dupla1;
	}

	public int getEtapa1() {
		return etapa1;
	}

	public void setEtapa1(int etapa1) {
		this.etapa1 = etapa1;
	}
	
	
	
	

}
