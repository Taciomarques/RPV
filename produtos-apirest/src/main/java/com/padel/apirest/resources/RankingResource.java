package com.padel.apirest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.padel.apirest.models.Gerente;
import com.padel.apirest.models.Ranking;
import com.padel.apirest.repository.RankingRepository;
import com.padel.apirest.response.RankingDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Ranking")
public class RankingResource {
	
	@Autowired
	RankingRepository rankingRepository;
	
	@ApiOperation(value="Retorna uma lista de Ranking")
	@GetMapping("/rankings")
	public List<RankingDTO> listaRanking(){
		List<Ranking> rankingList = rankingRepository.findAll();
		ArrayList<RankingDTO> rankingResponseList = new ArrayList<>();
		for(Ranking ranking: rankingList) {
			RankingDTO rankingResponse = new RankingDTO();
			rankingResponse.setAtleta1(ranking.getAtleta1().getId());
			rankingResponse.setCategoria(ranking.getCategoria().getId());
			rankingResponse.setCircuito1(ranking.getCircuito1().getId());
			rankingResponse.setPontosrank(ranking.getPontosrank());
			
			rankingResponseList.add(rankingResponse);
		}
		return rankingResponseList;
	}
	
	@ApiOperation(value="Salva uma ranking")
	@PostMapping("/ranking")
	public Ranking salvaRanking(@RequestBody @Valid Ranking ranking) {
		return rankingRepository.save(ranking);
	}
	
	@ApiOperation(value="Deleta uma ranking")
	@DeleteMapping("/ranking")
	public void deletaRanking(@RequestBody @Valid  Ranking ranking) {
		rankingRepository.delete(ranking);
	}
	
	@ApiOperation(value="Atualiza uma ranking")
	@PutMapping("/ranking")
	public Ranking atualizaRanking(@RequestBody @Valid  Ranking ranking) {
		return rankingRepository.save(ranking);
	}
	 

}
