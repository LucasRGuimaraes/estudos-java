package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.dto.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoDTOAssembler {

  @Autowired
  private ModelMapper modelMapper;

  public PermissaoDTO toDTO(Permissao permissao) {
    return modelMapper.map(permissao, PermissaoDTO.class);
  }

  public List<PermissaoDTO> toCollectionDTO(Collection<Permissao> permissaos) {
    return permissaos.stream()
        .map(permissao -> toDTO(permissao))
        .collect(Collectors.toList());
  }

}
