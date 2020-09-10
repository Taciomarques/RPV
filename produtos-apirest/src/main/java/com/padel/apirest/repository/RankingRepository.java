package com.padel.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padel.apirest.models.Ranking;

public interface RankingRepository extends JpaRepository<Ranking, Long>{

}
