package com.padel.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padel.apirest.models.Dupla;

public interface DuplaRepository extends JpaRepository<Dupla, Long>{
	Dupla findById(int id);

}
