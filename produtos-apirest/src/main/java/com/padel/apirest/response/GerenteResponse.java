package com.padel.apirest.response;

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


public class GerenteResponse implements Serializable{
	
    private int id;
	
    
    private String login;
    
    
    private String senha;
    
    
    private String nome;
    
    private List<Integer> circuitoList;

    public GerenteResponse() {
    }

    public GerenteResponse(String login, String senha, String nome) {
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

    public List<Integer> getCircuitoList() {
        return circuitoList;
    }

    public void setCircuitoList(List<Integer> circuitoList) {
        this.circuitoList = circuitoList;
    }

}
