package com.padel.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padel.apirest.models.Atleta;

public interface AtletaRepository extends JpaRepository<Atleta, Long>{
	Atleta findById(int id);

}
