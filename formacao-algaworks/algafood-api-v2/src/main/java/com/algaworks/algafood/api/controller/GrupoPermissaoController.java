package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.model.dto.PermissaoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private PermissaoDTOAssembler permissaoDTOAssembler;

	@GetMapping
	public List<PermissaoDTO> listar(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.findOrFailure(grupoId);
		return permissaoDTOAssembler.toCollectionDTO(grupo.getPermissoes());
	}

	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.associarPermissao(grupoId, permissaoId);
	}

	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.desassociarPermissao(grupoId, permissaoId);
	}

}