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

import com.padel.apirest.models.Categoria;
import com.padel.apirest.models.Circuito;
import com.padel.apirest.models.Dupla;
import com.padel.apirest.models.Etapa;
import com.padel.apirest.models.Ranking;
import com.padel.apirest.repository.CircuitoRepository;
import com.padel.apirest.response.CategoriaResponse;
import com.padel.apirest.response.CircuitoResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Circuito")
public class CircuitoResource {
	
	@Autowired
	CircuitoRepository circuitoRepository;
	
	@ApiOperation(value="Retorna um lista de Circuito")
	@GetMapping("/circuitos")
	public List<CircuitoResponse> listaCircuito(){ 
		List<Circuito> circuitoList = circuitoRepository.findAll();
		ArrayList<CircuitoResponse> circuitoResponseList = new ArrayList<>();
		
		for(Circuito circuito : circuitoList) {
			CircuitoResponse circuitoResponse = new CircuitoResponse();
			
			circuitoResponse.setGerente(circuito.getGerente().getId());
			circuitoResponse.setIdcircuito(circuito.getId());
			circuitoResponse.setNome(circuito.getNome());
			
			ArrayList<Integer> rankIds = new ArrayList<>();
			for(Ranking rank : circuito.getRankList()) {
				rankIds.add(rank.getAtleta1().getId());
				rankIds.add(rank.getCircuito1().getId());
				rankIds.add(rank.getCategoria().getId());
				rankIds.add(rank.getPontosrank());
			}
			
			circuitoResponse.setRankingList(rankIds);
			
			ArrayList<Integer> etapaIds = new ArrayList<>();
			for(Etapa etapa : circuito.getEtapaList()) {
				etapaIds.add(etapa.getId());
			}
			circuitoResponse.setEtapaList(etapaIds);
			
			circuitoResponseList.add(circuitoResponse);
			
		}
		
		return circuitoResponseList;
	}
	
	@ApiOperation(value="Retorna um circuito unica")
	@GetMapping("/circuito/{id}")
	public CircuitoResponse listaCircuitoUnico(@PathVariable(value="id") int id){
		Circuito circuito = circuitoRepository.findById(id);
		CircuitoResponse circuitoResponse = new CircuitoResponse();
		
		circuitoResponse.setGerente(circuito.getGerente().getId());
		circuitoResponse.setIdcircuito(circuito.getId());
		circuitoResponse.setNome(circuito.getNome());
		
		ArrayList<Integer> rankIds = new ArrayList<>();
		for(Ranking rank : circuito.getRankList()) {
			rankIds.add(rank.getAtleta1().getId());
			rankIds.add(rank.getCircuito1().getId());
			rankIds.add(rank.getCategoria().getId());
			rankIds.add(rank.getPontosrank());
		}
		
		circuitoResponse.setRankingList(rankIds);
		
		ArrayList<Integer> etapaIds = new ArrayList<>();
		for(Etapa etapa : circuito.getEtapaList()) {
			etapaIds.add(etapa.getId());
		}
		circuitoResponse.setEtapaList(etapaIds);
		
		return circuitoResponse;
	}
	
	@ApiOperation(value="Salva um circuito")
	@PostMapping("/circuito")
	public Circuito salvaCircuito(@RequestBody @Valid Circuito circuito) {
		return circuitoRepository.save(circuito);
	}
	
	@ApiOperation(value="Deleta um circuito")
	@DeleteMapping("/circuito")
	public void deletaCircuito(@RequestBody @Valid  Circuito circuito) {
		circuitoRepository.delete(circuito);
	}
	
	@ApiOperation(value="Atualiza um circuito")
	@PutMapping("/circuito")
	public Circuito atualizaCircuito(@RequestBody @Valid  Circuito circuito) {
		return circuitoRepository.save(circuito);
	}
	 

}
