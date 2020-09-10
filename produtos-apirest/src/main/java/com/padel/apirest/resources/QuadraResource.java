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
import com.padel.apirest.models.Partida;
import com.padel.apirest.models.Quadra;
import com.padel.apirest.repository.QuadraRepository;
import com.padel.apirest.response.QuadraResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Quadra")
public class QuadraResource {
	
	@Autowired
	QuadraRepository quadraRepository;
	
	@ApiOperation(value="Retorna uma lista de Quadra")
	@GetMapping("/quadras")
	public List<QuadraResponse> listaQuadra(){ 
		List<Quadra> quadraList = quadraRepository.findAll();
		ArrayList<QuadraResponse> quadraResponseList = new ArrayList<>();
		for(Quadra quadra: quadraList) {
			QuadraResponse quadraResponse = new QuadraResponse();
			quadraResponse.setId(quadra.getId());
			quadraResponse.setEtapa(quadra.getEtapa().getId());
			ArrayList<Integer> partidaIds = new ArrayList<>();
			for(Partida partida: quadra.getPartidaList()) {
				partidaIds.add(partida.getId());
			}
			quadraResponse.setPartidaList(partidaIds);
			quadraResponseList.add(quadraResponse);
		}
		return quadraResponseList;
	}
	
	@ApiOperation(value="Retorna um quadra unica")
	@GetMapping("/quadra/{id}")
	public QuadraResponse listaQuadraUnico(@PathVariable(value="id") int id){
		Quadra quadra = quadraRepository.findById(id);
		QuadraResponse quadraResponse = new QuadraResponse();
		quadraResponse.setId(quadra.getId());
		quadraResponse.setEtapa(quadra.getEtapa().getId());
		ArrayList<Integer> partidaIds = new ArrayList<>();
		for(Partida partida: quadra.getPartidaList()) {
			partidaIds.add(partida.getId());
		}
		quadraResponse.setPartidaList(partidaIds);
		
		return quadraResponse;
	}
	
	@ApiOperation(value="Salva uma quadra")
	@PostMapping("/quadra")
	public Quadra salvaQuadra(@RequestBody @Valid Quadra quadra) {
		return quadraRepository.save(quadra);
	}
	
	@ApiOperation(value="Deleta uma quadra")
	@DeleteMapping("/quadra")
	public void deletaQuadra(@RequestBody @Valid  Quadra quadra) {
		quadraRepository.delete(quadra);
	}
	
	@ApiOperation(value="Atualiza uma quadra")
	@PutMapping("/quadra")
	public Quadra atualizaQuadra(@RequestBody @Valid  Quadra quadra) {
		return quadraRepository.save(quadra);
	}
	 

}
