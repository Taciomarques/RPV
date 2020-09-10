package com.padel.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padel.apirest.models.Etapa;

public interface EtapaRepository extends JpaRepository<Etapa, Long>{
	Etapa findById(int id);

}
