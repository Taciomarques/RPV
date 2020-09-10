package com.padel.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padel.apirest.models.Quadra;

public interface QuadraRepository extends JpaRepository<Quadra, Long>{
	Quadra findById(int id);

}
