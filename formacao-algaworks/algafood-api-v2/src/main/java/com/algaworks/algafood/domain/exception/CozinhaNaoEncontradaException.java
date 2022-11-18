package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

  public CozinhaNaoEncontradaException(String mensagem) {
    super(mensagem);
  }
  
}