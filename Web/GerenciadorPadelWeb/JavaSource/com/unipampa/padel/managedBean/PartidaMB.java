package com.unipampa.padel.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Chave;
import com.unipampa.padel.model.Partida;

@ManagedBean(name = "partidaMB")
@SessionScoped
public class PartidaMB implements Serializable {

	private List<Partida> partidas = null;
	private List<Partida> todasPartidas = null;

	private int selectDia;	

	public void methodAjax() {

		try {
			
			if(todasPartidas == null) {
				Partidas p = new Partidas();
				p.geraGradePartidas();
				todasPartidas = p.getPartidasProntas();
			}
			ArrayList<Partida> partidaList = (ArrayList<Partida>) todasPartidas;
			partidas = new ArrayList<Partida>();
			if (partidaList != null) {

				for (Partida ps : partidaList) {
					System.out.println(ps.getDataHora().getDay()+" == "+selectDia);
					if (ps.getDataHora().getDay() == selectDia) {
						System.out.println(ps.getNome());
						partidas.add(ps);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}

	public List<Partida> getTodasPartidas() {
		return todasPartidas;
	}

	public void setTodasPartidas(List<Partida> todasPartidas) {
		this.todasPartidas = todasPartidas;
	}

	public int getSelectDia() {
		return selectDia;
	}

	public void setSelectDia(int selectDia) {
		this.selectDia = selectDia;
	}
	
	
}
