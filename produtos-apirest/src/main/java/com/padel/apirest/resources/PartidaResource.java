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

import com.padel.apirest.models.Dupla;
import com.padel.apirest.models.Gerente;
import com.padel.apirest.models.Partida;
import com.padel.apirest.repository.PartidaRepository;
import com.padel.apirest.response.PartidaResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Partida")
public class PartidaResource {
	
	@Autowired
	PartidaRepository partidaRepository;
	
	@ApiOperation(value="Retorna uma lista de Partida")
	@GetMapping("/partidas")
	public List<PartidaResponse> listaPartida(){
		List<Partida> partidaList = partidaRepository.findAll();
		ArrayList<PartidaResponse> partidaResponseList = new ArrayList<>();
		for(Partida partida: partidaList) {
			PartidaResponse partidaResponse = new PartidaResponse();
			partidaResponse.setDataHora(partida.getDataHora());
			partidaResponse.setEtapa(partida.getEtapa().getId());
			partidaResponse.setId(partida.getId());
			partidaResponse.setNome(partida.getNome());
			partidaResponse.setPontosDupla1(partida.getPontosDupla1());
			partidaResponse.setPontosDupla2(partida.getPontosDupla2());
			partidaResponse.setQuadra(partida.getQuadra().getId());
			ArrayList<Integer> duplaIds = new ArrayList<>();
			for(Dupla dupla: partida.getDuplaList()) {
				duplaIds.add(dupla.getId());
			}
			partidaResponse.setDuplaList(duplaIds);
			partidaResponseList.add(partidaResponse);
			
		}
		return partidaResponseList;
	}
	
	@ApiOperation(value="Retorna um partida unica")
	@GetMapping("/partida/{id}")
	public PartidaResponse listaGerenteUnico(@PathVariable(value="id") int id){
		Partida partida = partidaRepository.findById(id);
		PartidaResponse partidaResponse = new PartidaResponse();
		partidaResponse.setDataHora(partida.getDataHora());
		partidaResponse.setEtapa(partida.getEtapa().getId());
		partidaResponse.setId(partida.getId());
		partidaResponse.setNome(partida.getNome());
		partidaResponse.setPontosDupla1(partida.getPontosDupla1());
		partidaResponse.setPontosDupla2(partida.getPontosDupla2());
		partidaResponse.setQuadra(partida.getQuadra().getId());
		ArrayList<Integer> duplaIds = new ArrayList<>();
		for(Dupla dupla: partida.getDuplaList()) {
			duplaIds.add(dupla.getId());
		}
		partidaResponse.setDuplaList(duplaIds);
		
		return partidaResponse;
	}
	
	@ApiOperation(value="Salva uma partida")
	@PostMapping("/partida")
	public Partida salvaPartida(@RequestBody @Valid Partida partida) {
		return partidaRepository.save(partida);
	}
	
	@ApiOperation(value="Deleta uma partida")
	@DeleteMapping("/partida")
	public void deletaPartida(@RequestBody @Valid  Partida partida) {
		partidaRepository.delete(partida);
	}
	
	@ApiOperation(value="Atualiza uma partida")
	@PutMapping("/partida")
	public Partida atualizaPartida(@RequestBody @Valid  Partida partida) {
		return partidaRepository.save(partida);
	}
	 

}
