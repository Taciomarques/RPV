package com.padel.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padel.apirest.models.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Long>{
	Partida findById(int id);

}
