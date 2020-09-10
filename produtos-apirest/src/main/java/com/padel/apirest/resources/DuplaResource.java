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
import com.padel.apirest.models.Chave;
import com.padel.apirest.models.Dupla;
import com.padel.apirest.models.Inscricao;
import com.padel.apirest.models.Partida;
import com.padel.apirest.repository.DuplaRepository;
import com.padel.apirest.response.DuplaResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Duplas")
public class DuplaResource {
	
	@Autowired
	DuplaRepository duplaRepository;
	
	@ApiOperation(value="Retorna uma lista de Duplas")
	@GetMapping("/duplas")
	public List<DuplaResponse> listaDuplas(){ 
		List<Dupla> duplaList = duplaRepository.findAll();
		ArrayList<DuplaResponse> duplaResponseList = new ArrayList<>();
		for(Dupla dupla: duplaList) {
			DuplaResponse duplaResponse = new DuplaResponse();
			ArrayList<Integer> atletaIds = new ArrayList<>();
//			for(Atleta atleta: dupla.getAtletaList()) {
//				atletaIds.add(atleta.getId());
//			}
			atletaIds.add(dupla.getAtletaList().get(0).getId());
			atletaIds.add(dupla.getAtletaList().get(1).getId());
			duplaResponse.setAtletaList(atletaIds);
			duplaResponse.setCategoria(dupla.getCategoria().getId());
			ArrayList<Integer> chaveIds = new ArrayList<>();
			for(Chave chave: dupla.getChaves()) {
				atletaIds.add(chave.getId());
			}
			duplaResponse.setChaves(chaveIds);
			duplaResponse.setId(dupla.getId());
			duplaResponse.setImpedimento(dupla.getImpedimento());
			ArrayList<Integer> inscIds = new ArrayList<>();
			for(Inscricao insc: dupla.getInscList()) {
				inscIds.add(insc.getDupla1().getId());
				inscIds.add(insc.getEtapa1().getId());
			}
			duplaResponse.setInscList(inscIds);
			duplaResponse.setNome(dupla.getNome());
			duplaResponse.setPontosRank(dupla.getPontosRank());
			duplaResponse.setSuplente(dupla.isSuplente());
		    ArrayList<Integer> partidaIds = new ArrayList<>();
		    for(Partida partida: dupla.getPartidaList()) {
		    	partidaIds.add(partida.getId());
		    }
		    duplaResponse.setPartidaList(partidaIds);
		    duplaResponseList.add(duplaResponse);
			}
		
		return duplaResponseList;
	}
	
	@ApiOperation(value="Retorna um dupla unico")
	@GetMapping("/dupla/{id}")
	public DuplaResponse listaDuplaUnico(@PathVariable(value="id") int id){
		Dupla dupla = duplaRepository.findById(id);		
		DuplaResponse duplaResponse = new DuplaResponse();
		ArrayList<Integer> atletaIds = new ArrayList<>();
//		for(Atleta atleta: dupla.getAtletaList()) {
//			atletaIds.add(atleta.getId());
//		}
		atletaIds.add(dupla.getAtletaList().get(0).getId());
		atletaIds.add(dupla.getAtletaList().get(1).getId());
		duplaResponse.setAtletaList(atletaIds);
		duplaResponse.setCategoria(dupla.getCategoria().getId());
		ArrayList<Integer> chaveIds = new ArrayList<>();
		for(Chave chave: dupla.getChaves()) {
			atletaIds.add(chave.getId());
		}
		duplaResponse.setChaves(chaveIds);
		duplaResponse.setId(dupla.getId());
		duplaResponse.setImpedimento(dupla.getImpedimento());
		ArrayList<Integer> inscIds = new ArrayList<>();
		for(Inscricao insc: dupla.getInscList()) {
			inscIds.add(insc.getDupla1().getId());
			inscIds.add(insc.getEtapa1().getId());
		}
		duplaResponse.setInscList(inscIds);
		duplaResponse.setNome(dupla.getNome());
		duplaResponse.setPontosRank(dupla.getPontosRank());
		duplaResponse.setSuplente(dupla.isSuplente());
	    ArrayList<Integer> partidaIds = new ArrayList<>();
	    for(Partida partida: dupla.getPartidaList()) {
	    	partidaIds.add(partida.getId());
	    }
	    duplaResponse.setPartidaList(partidaIds);

		
		return duplaResponse;
	}
	
	@ApiOperation(value="Salva um dupla")
	@PostMapping("/dupla")
	public Dupla salvaDupla(@RequestBody @Valid Dupla dupla) {
		return duplaRepository.save(dupla); 
	}
	
	@ApiOperation(value="Deleta um dupla")
	@DeleteMapping("/dupla")
	public void deletaDupla(@RequestBody @Valid  Dupla dupla) {
		duplaRepository.delete(dupla);
	}
	
	@ApiOperation(value="Atualiza um dupla")
	@PutMapping("/dupla")
	public Dupla atualizaDupla(@RequestBody @Valid  Dupla dupla) {
		return duplaRepository.save(dupla);
	}
	 

}
