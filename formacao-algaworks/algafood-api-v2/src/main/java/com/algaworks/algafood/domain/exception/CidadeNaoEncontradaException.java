package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

  public CidadeNaoEncontradaException(String mensagem) {
    super(mensagem);
  }
  
  public CidadeNaoEncontradaException (Long estadoId) {
    this(String.format("Não existe um cadastro de cidade com código %d", estadoId));
  }
  
}
