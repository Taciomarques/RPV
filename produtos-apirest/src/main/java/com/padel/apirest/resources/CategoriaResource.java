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
import com.padel.apirest.models.Categoria;
import com.padel.apirest.models.Dupla;
import com.padel.apirest.models.Ranking;
import com.padel.apirest.repository.CategoriaRepository;
import com.padel.apirest.response.AtletaDTO;
import com.padel.apirest.response.CategoriaDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Categorias")
public class CategoriaResource {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@ApiOperation(value="Retorna uma lista de Categorias")
	@GetMapping("/categorias")
	public List<CategoriaDTO> listaCategorias(){
		List<Categoria> cList = categoriaRepository.findAll();
		ArrayList<CategoriaDTO> catResponseList = new ArrayList<>();
		for(Categoria cat : cList) {
			CategoriaDTO catResponse = new CategoriaDTO();
			catResponse.setId(cat.getId());
			catResponse.setNome(cat.getNome());
			
			ArrayList<Integer> rankIds = new ArrayList<>();
			for(Ranking rank : cat.getRankList()) {
				rankIds.add(rank.getRankingPK().getAtleta());
//				rankIds.add(rank.getRankingPK().getCircuito());
			}
			
			catResponse.setRankList(rankIds);
			
			ArrayList<Integer> duplaIds = new ArrayList<>();
			for(Dupla dupla : cat.getDuplaList()) {
				duplaIds.add(dupla.getId());
			}
			catResponse.setDuplaList(duplaIds);
			
			catResponseList.add(catResponse);
			
		}
		
		return catResponseList;
	}
	
	@ApiOperation(value="Retorna uma categoria unica")
	@GetMapping("/categoria/{id}")
	public CategoriaDTO listaCategoriaUnico(@PathVariable(value="id") int id){
		Categoria categoria = categoriaRepository.findById(id);
		CategoriaDTO categoriaResponse = new CategoriaDTO();
		categoriaResponse.setId(categoria.getId());
		categoriaResponse.setNome(categoria.getNome());
		
		ArrayList<Integer> rankIds = new ArrayList<>();
		for(Ranking rank : categoria.getRankList()) {
			rankIds.add(rank.getRankingPK().getAtleta());
//			rankIds.add(rank.getRankingPK().getCircuito());
		}
		
		categoriaResponse.setRankList(rankIds);
		
		ArrayList<Integer> duplaIds = new ArrayList<>();
		for(Dupla dupla : categoria.getDuplaList()) {
			duplaIds.add(dupla.getId());
		}
		categoriaResponse.setDuplaList(duplaIds);
		
		
		return categoriaResponse;
	}
	
	@ApiOperation(value="Salva uma categoria")
	@PostMapping("/categoria")
	public Categoria salvaCategoria(@RequestBody @Valid Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	@ApiOperation(value="Deleta uma categoria")
	@DeleteMapping("/categoria")
	public void deletaCategoria(@RequestBody @Valid  Categoria categoria) {
		categoriaRepository.delete(categoria);
	}
	
	@ApiOperation(value="Atualiza uma categoria")
	@PutMapping("/categoria")
	public Categoria atualizaCategoria(@RequestBody @Valid  Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	 

}
