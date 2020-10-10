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
import com.padel.apirest.models.Etapa;
import com.padel.apirest.models.Inscricao;
import com.padel.apirest.models.Partida;
import com.padel.apirest.models.Quadra;
import com.padel.apirest.repository.EtapaRepository;
import com.padel.apirest.response.EtapaDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Etapa")
public class EtapaResource {
	
	@Autowired
	EtapaRepository etapaRepository;
	
	@ApiOperation(value="Retorna uma lista de Etapa")
	@GetMapping("/etapas")
	public List<EtapaDTO> listaEtapa(){
		List<Etapa> etapaList = etapaRepository.findAll();
		ArrayList<EtapaDTO> etapaResponseList = new ArrayList<>();
		for(Etapa etapa: etapaList) {
			EtapaDTO etapaResponse = new EtapaDTO();
			etapaResponse.setCircuito(etapa.getCircuito().getId());
			etapaResponse.setDataFinal(etapa.getDataFinal());
			etapaResponse.setDataInicial(etapa.getDataInicial());
			etapaResponse.setId(etapa.getId());
			etapaResponse.setNome(etapa.getNome());
			
			ArrayList<Integer> chaveIds = new ArrayList<>();
			for(Chave chave: etapa.getChaves()) {
				chaveIds.add(chave.getId());
			}
			etapaResponse.setChaves(chaveIds);
			
			ArrayList<Integer> inscricaoIds = new ArrayList<>();
			for(Inscricao inscricao: etapa.getInscList()) {
				inscricaoIds.add(inscricao.getDupla1().getId());
//				inscricaoIds.add(inscricao.getEtapa1().getId());
			}
			etapaResponse.setInscList(inscricaoIds);
			
			ArrayList<Integer> partidaIds = new ArrayList<>();
			for(Partida partida: etapa.getPartidas()) {
				partidaIds.add(partida.getId());
			}
			etapaResponse.setPartidas(partidaIds);
			
			ArrayList<Integer> quadraIds = new ArrayList<>();
			for(Quadra quadra: etapa.getQuadras()) {
				quadraIds.add(quadra.getId());
			}
			etapaResponse.setQuadras(quadraIds);
			
			etapaResponseList.add(etapaResponse);
		}
		return etapaResponseList;
	}
	
	@ApiOperation(value="Retorna uma etapa unica")
	@GetMapping("/etapa/{id}")
	public EtapaDTO listaEtapaUnico(@PathVariable(value="id") int id){
		Etapa etapa = etapaRepository.findById(id);
		EtapaDTO etapaResponse = new EtapaDTO();
		etapaResponse.setCircuito(etapa.getCircuito().getId());
		etapaResponse.setDataFinal(etapa.getDataFinal());
		etapaResponse.setDataInicial(etapa.getDataInicial());
		etapaResponse.setId(etapa.getId());
		etapaResponse.setNome(etapa.getNome());
		
		ArrayList<Integer> chaveIds = new ArrayList<>();
		for(Chave chave: etapa.getChaves()) {
			chaveIds.add(chave.getId());
		}
		etapaResponse.setChaves(chaveIds);
		
		ArrayList<Integer> inscricaoIds = new ArrayList<>();
		for(Inscricao inscricao: etapa.getInscList()) {
			inscricaoIds.add(inscricao.getDupla1().getId());
//			inscricaoIds.add(inscricao.getEtapa1().getId());
		}
		etapaResponse.setInscList(inscricaoIds);
		
		ArrayList<Integer> partidaIds = new ArrayList<>();
		for(Partida partida: etapa.getPartidas()) {
			partidaIds.add(partida.getId());
		}
		etapaResponse.setPartidas(partidaIds);
		
		ArrayList<Integer> quadraIds = new ArrayList<>();
		for(Quadra quadra: etapa.getQuadras()) {
			quadraIds.add(quadra.getId());
		}
		etapaResponse.setQuadras(quadraIds);

		return etapaResponse;
	}
	
	@ApiOperation(value="Salva uma etapa")
	@PostMapping("/etapa")
	public Etapa salvaEtapa(@RequestBody @Valid Etapa etapa) {
		return etapaRepository.save(etapa);
	}
	
	@ApiOperation(value="Deleta uma etapa")
	@DeleteMapping("/etapa")
	public void deletaEtapa(@RequestBody @Valid  Etapa etapa) {
		etapaRepository.delete(etapa);
	}
	
	@ApiOperation(value="Atualiza uma etapa")
	@PutMapping("/etapa")
	public Etapa atualizaEtapa(@RequestBody @Valid  Etapa etapa) {
		return etapaRepository.save(etapa);
	}
	 

}
