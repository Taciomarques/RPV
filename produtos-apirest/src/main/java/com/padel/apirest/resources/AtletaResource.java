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

import com.padel.apirest.models.Atleta;
import com.padel.apirest.models.Dupla;
import com.padel.apirest.models.Ranking;
import com.padel.apirest.repository.AtletaRepository;
import com.padel.apirest.response.AtletaDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Atletas")
public class AtletaResource {
	
	@Autowired
	AtletaRepository atletaRepository;
	
	@ApiOperation(value="Retorna uma lista de Atletas")
	@GetMapping("/atletas")
	public List<AtletaDTO> listaAtletas(){ 
		List<Atleta> atl = atletaRepository.findAll();
		ArrayList<AtletaDTO> atlResponse = new ArrayList<AtletaDTO>();
		for(Atleta atleta : atl) {
			AtletaDTO atlR = new AtletaDTO();
			atlR.setCpf(atleta.getCpf());
			atlR.setEmail(atleta.getEmail());
			atlR.setId(atleta.getId());
			atlR.setNome(atleta.getNome());
			atlR.setNumCel(atleta.getNumCel());
			ArrayList<Integer> rankIds = new ArrayList<>();
			for(Ranking rank : atleta.getRankList()) {
				rankIds.add(rank.getRankingPK().getAtleta());
				rankIds.add(rank.getRankingPK().getCircuito());
			}
			atlR.setRankList(rankIds);
			
			ArrayList<Integer> duplaIds = new ArrayList<>();
			for(Dupla dupla : atleta.getDuplas()) {
				duplaIds.add(dupla.getId());
			}
			atlR.setDuplas(duplaIds);
			
			atlResponse.add(atlR);
			
		}
		return atlResponse;
		
	}
	
	@ApiOperation(value="Retorna um atleta unico")
	@GetMapping("/atleta/{id}")
	public AtletaDTO listaAtletaUnico(@PathVariable(value="id") int id){
		Atleta atleta = atletaRepository.findById(id);
		AtletaDTO atletaResponse = new AtletaDTO();
		
		atletaResponse.setCpf(atleta.getCpf());
		atletaResponse.setEmail(atleta.getEmail());
		atletaResponse.setId(atleta.getId());
		atletaResponse.setNome(atleta.getNome());
		atletaResponse.setNumCel(atleta.getNumCel());
		ArrayList<Integer> rankIds = new ArrayList<>();
		for(Ranking rank : atleta.getRankList()) {
			rankIds.add(rank.getRankingPK().getAtleta());
			rankIds.add(rank.getRankingPK().getCircuito());
		}
		atletaResponse.setRankList(rankIds);
		
		ArrayList<Integer> duplaIds = new ArrayList<>();
		for(Dupla dupla : atleta.getDuplas()) {
			duplaIds.add(dupla.getId());
		}
		atletaResponse.setDuplas(duplaIds);
		
		return atletaResponse;
	}
	
	@ApiOperation(value="Salva um atleta")
	@PostMapping("/atleta")
	public Atleta salvaAtleta(@RequestBody @Valid Atleta atleta) {
		return atletaRepository.save(atleta);
	}
	
	@ApiOperation(value="Deleta um atleta")
	@DeleteMapping("/atleta")
	public void deletaAtleta(@RequestBody @Valid  Atleta atleta) {
		atletaRepository.delete(atleta);
	}
	
	@ApiOperation(value="Atualiza um atleta")
	@PutMapping("/atleta")
	public Atleta atualizaAtleta(@RequestBody @Valid  Atleta atleta) {
		return atletaRepository.save(atleta);
	}
	 

}
