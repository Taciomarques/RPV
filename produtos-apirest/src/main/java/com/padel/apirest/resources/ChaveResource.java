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

import com.padel.apirest.models.Chave;
import com.padel.apirest.models.Dupla;
import com.padel.apirest.models.Etapa;
import com.padel.apirest.repository.ChaveRepository;
import com.padel.apirest.response.ChaveDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Chave")
public class ChaveResource {
	
	@Autowired
	ChaveRepository chaveRepository;
	
	@ApiOperation(value="Retorna uma lista de Chave")
	@GetMapping("/chaves")
	public List<ChaveDTO> listaChave(){
		List<Chave> chaveList = chaveRepository.findAll();
		ArrayList<ChaveDTO> chaveResponseList = new ArrayList<>();
		for(Chave chave: chaveList) {
			ChaveDTO chaveResponse = new ChaveDTO();
			chaveResponse.setId(chave.getId());
			chaveResponse.setNome(chave.getNome());
			ArrayList<Integer> duplaIds = new ArrayList<>(); 
			for(Dupla dupla: chave.getDuplaList()) {
				duplaIds.add(dupla.getId());
			}
			chaveResponse.setDuplaList(duplaIds);
			ArrayList<Integer> etapaIds = new ArrayList<>();
			for(Etapa etapa: chave.getEtapaList()) {
				etapaIds.add(etapa.getId());
			}
			chaveResponse.setEtapaList(etapaIds);
			chaveResponseList.add(chaveResponse);
		}
		return chaveResponseList;
	}
	
	@ApiOperation(value="Retorna uma chave unica")
	@GetMapping("/chave/{id}")
	public ChaveDTO listaChaveUnico(@PathVariable(value="id") int id){
		Chave chave = chaveRepository.findById(id);
		ChaveDTO chaveResponse = new ChaveDTO();
		chaveResponse.setId(chave.getId());
		chaveResponse.setNome(chave.getNome());
		ArrayList<Integer> duplaIds = new ArrayList<>(); 
		for(Dupla dupla: chave.getDuplaList()) {
			duplaIds.add(dupla.getId());
		}
		chaveResponse.setDuplaList(duplaIds);
		ArrayList<Integer> etapaIds = new ArrayList<>();
		for(Etapa etapa: chave.getEtapaList()) {
			etapaIds.add(etapa.getId());
		}
		chaveResponse.setEtapaList(etapaIds);
		
		return chaveResponse;
	}
	
	@ApiOperation(value="Salva uma chave")
	@PostMapping("/chave")
	public Chave salvaChave(@RequestBody @Valid Chave chave) {
		return chaveRepository.save(chave);
	}
	
	@ApiOperation(value="Deleta uma chave")
	@DeleteMapping("/chave")
	public void deletaChave(@RequestBody @Valid  Chave chave) {
		chaveRepository.delete(chave);
	}
	
	@ApiOperation(value="Atualiza uma chave")
	@PutMapping("/chave")
	public Chave atualizaChave(@RequestBody @Valid  Chave chave) {
		return chaveRepository.save(chave);
	}
	 

}
