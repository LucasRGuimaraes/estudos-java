package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler {

  @Autowired
  private ModelMapper modelMapper;

  public UsuarioDTO toDTO(Usuario usuario) {
    return modelMapper.map(usuario, UsuarioDTO.class);
  }

  public List<UsuarioDTO> toCollectionDTO(Collection<Usuario> usuarios) {
    return usuarios.stream()
        .map(usuario -> toDTO(usuario))
        .collect(Collectors.toList());
  }

}
