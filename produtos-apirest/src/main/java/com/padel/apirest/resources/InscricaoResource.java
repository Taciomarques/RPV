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

import com.padel.apirest.models.Inscricao;
import com.padel.apirest.repository.InscricaoRepository;
import com.padel.apirest.response.InscricaoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Inscricao")
public class InscricaoResource {
	
	@Autowired
	InscricaoRepository inscricaoRepository;
	
	@ApiOperation(value="Retorna uma lista de Inscricao")
	@GetMapping("/inscricoes")
	public List<InscricaoDTO> listaInscricao(){ 
		List<Inscricao> inscricaoList = inscricaoRepository.findAll();
		ArrayList<InscricaoDTO> inscricaoResponseList = new ArrayList<>();
		for(Inscricao insc: inscricaoList) {
			InscricaoDTO inscricaoResponse = new InscricaoDTO();
			inscricaoResponse.setDupla1(insc.getDupla1().getId());
			inscricaoResponse.setEtapa1(insc.getEtapa1().getId());
			inscricaoResponse.setHorainsc(insc.getHorainsc());
			
			inscricaoResponseList.add(inscricaoResponse);
		}
		
		return inscricaoResponseList;
	}
	
	@ApiOperation(value="Salva uma inscricao")
	@PostMapping("/inscricao")
	public Inscricao salvaInscricao(@RequestBody @Valid Inscricao inscricao) {
		return inscricaoRepository.save(inscricao);
	}
	
	@ApiOperation(value="Deleta uma inscricao")
	@DeleteMapping("/inscricao")
	public void deletaInscricao(@RequestBody @Valid  Inscricao inscricao) {
		inscricaoRepository.delete(inscricao);
	}
	
	@ApiOperation(value="Atualiza uma inscricao")
	@PutMapping("/inscricao")
	public Inscricao atualizaInscricao(@RequestBody @Valid  Inscricao inscricao) {
		return inscricaoRepository.save(inscricao);
	}
	 

}
