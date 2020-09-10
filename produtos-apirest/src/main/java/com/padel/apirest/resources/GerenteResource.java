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

import com.padel.apirest.models.Circuito;
import com.padel.apirest.models.Gerente;
import com.padel.apirest.repository.GerenteRepository;
import com.padel.apirest.response.GerenteResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Gerente")
public class GerenteResource {
	
	@Autowired
	GerenteRepository gerenteRepository;
	
	@ApiOperation(value="Retorna um lista de Gerente")
	@GetMapping("/gerentes")
	public List<GerenteResponse> listaGerente(){ 
		List<Gerente> gerenteList = gerenteRepository.findAll();
		ArrayList<GerenteResponse> gerenteResponseList = new ArrayList<>();
		for(Gerente gerente: gerenteList) {
			GerenteResponse gerenteResponse = new GerenteResponse();
			gerenteResponse.setId(gerente.getId());
			gerenteResponse.setLogin(gerente.getLogin());
			gerenteResponse.setNome(gerente.getNome());
			gerenteResponse.setSenha(gerente.getSenha());
			ArrayList<Integer> circuitoIds = new ArrayList<>();
			for(Circuito circuito: gerente.getCircuitoList()) {
				circuitoIds.add(circuito.getId());
			}
			gerenteResponse.setCircuitoList(circuitoIds);
			gerenteResponseList.add(gerenteResponse);
		}
		return gerenteResponseList;
	}
	
	@ApiOperation(value="Retorna um gerente unico")
	@GetMapping("/gerente/{id}")
	public GerenteResponse listaGerenteUnico(@PathVariable(value="id") int id){
		Gerente gerente = gerenteRepository.findById(id);
		GerenteResponse gerenteResponse = new GerenteResponse();
		gerenteResponse.setId(gerente.getId());
		gerenteResponse.setLogin(gerente.getLogin());
		gerenteResponse.setNome(gerente.getNome());
		gerenteResponse.setSenha(gerente.getSenha());
		ArrayList<Integer> circuitoIds = new ArrayList<>();
		for(Circuito circuito: gerente.getCircuitoList()) {
			circuitoIds.add(circuito.getId());
		}
		gerenteResponse.setCircuitoList(circuitoIds);
		
		return gerenteResponse;
	}
	
	@ApiOperation(value="Salva um gerente")
	@PostMapping("/gerente")
	public Gerente salvaGerente(@RequestBody @Valid Gerente gerente) {
		return gerenteRepository.save(gerente);
	}
	
	@ApiOperation(value="Deleta um gerente")
	@DeleteMapping("/gerente")
	public void deletaGerente(@RequestBody @Valid  Gerente gerente) {
		gerenteRepository.delete(gerente);
	}
	
	@ApiOperation(value="Atualiza um gerente")
	@PutMapping("/gerente")
	public Gerente atualizaGerente(@RequestBody @Valid  Gerente gerente) {
		return gerenteRepository.save(gerente);
	}
	 

}
