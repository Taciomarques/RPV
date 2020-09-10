package com.unipampa.padel.model;

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

@Entity
@Table(name = "inscricao")
@NamedQueries({
    @NamedQuery(name = "Inscricao.findAll", query = "SELECT i FROM Inscricao i")
    , @NamedQuery(name = "Inscricao.findByDupla", query = "SELECT i FROM Inscricao i WHERE i.inscricaoPK.dupla = :dupla")
    , @NamedQuery(name = "Inscricao.findByEtapa", query = "SELECT i FROM Inscricao i WHERE i.inscricaoPK.etapa = :etapa")
    , @NamedQuery(name = "Inscricao.findByHorainsc", query = "SELECT i FROM Inscricao i WHERE i.horainsc = :horainsc")})
public class Inscricao implements Serializable{
	
	@EmbeddedId
	protected InscricaoPK inscricaoPK;
	
	@Basic(optional = false)
    @Column(name = "horainsc")
    @Temporal(TemporalType.TIMESTAMP)
	private Date horainsc;
	
    
	@JoinColumn(name = "dupla", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Dupla dupla1;
	
    
    @JoinColumn(name = "etapa", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Etapa etapa1;

	public Date getHorainsc() {
		return horainsc;
	}

	public void setHorainsc(Date horainsc) {
		this.horainsc = horainsc;
	}
	

	public InscricaoPK getInscricaoPK() {
		return inscricaoPK;
	}

	public void setInscricaoPK(InscricaoPK inscricaoPK) {
		this.inscricaoPK = inscricaoPK;
	}

	public Dupla getDupla1() {
		return dupla1;
	}

	public void setDupla1(Dupla dupla1) {
		this.dupla1 = dupla1;
	}

	public Etapa getEtapa1() {
		return etapa1;
	}

	public void setEtapa1(Etapa etapa1) {
		this.etapa1 = etapa1;
	}
	
	
	
	

}
