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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "gerente")
@NamedQueries({
    @NamedQuery(name = "Gerente.findAll", query = "SELECT g FROM Gerente g")
    , @NamedQuery(name = "Gerente.findById", query = "SELECT g FROM Gerente g WHERE g.id = :id")
    , @NamedQuery(name = "Gerente.findByLogin", query = "SELECT g FROM Gerente g WHERE g.login = :login")
    , @NamedQuery(name = "Gerente.findBySenha", query = "SELECT g FROM Gerente g WHERE g.senha = :senha")
    , @NamedQuery(name = "Gerente.findByNome", query = "SELECT g FROM Gerente g WHERE g.nome = :nome")})
public class Gerente implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
	
    @Basic(optional = false)
    @Column(name = "login", nullable = false, length = 15)
    private String login;
    
    @Basic(optional = false)
    @Column(name = "senha", nullable = false, length = 12)
    private String senha;
    
    @Basic(optional = false)
    @Column(name = "nome", nullable = false, length = 20)
    private String nome;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gerente")
    private List<Circuito> circuitoList;

    public Gerente() {
    }

    public Gerente(String login, String senha, String nome) {
        super();
        this.login = login;
        this.senha = senha;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Circuito> getCircuitoList() {
        return circuitoList;
    }

    public void setCircuitoList(List<Circuito> circuitoList) {
        this.circuitoList = circuitoList;
    }

}
