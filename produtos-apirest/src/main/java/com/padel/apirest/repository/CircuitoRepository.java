package com.padel.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padel.apirest.models.Circuito;

public interface CircuitoRepository extends JpaRepository<Circuito, Long>{
	Circuito findById(int id);

}
