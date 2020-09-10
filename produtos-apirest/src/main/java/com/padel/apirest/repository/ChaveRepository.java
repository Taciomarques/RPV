package com.padel.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padel.apirest.models.Chave;

public interface ChaveRepository extends JpaRepository<Chave, Long>{
	Chave findById(int id);

}
