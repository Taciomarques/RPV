package com.padel.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padel.apirest.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	Categoria findById(int id);

}
