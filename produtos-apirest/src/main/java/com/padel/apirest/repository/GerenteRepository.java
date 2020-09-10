package com.padel.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padel.apirest.models.Gerente;

public interface GerenteRepository extends JpaRepository<Gerente, Long>{
	Gerente findById(int id);

}
