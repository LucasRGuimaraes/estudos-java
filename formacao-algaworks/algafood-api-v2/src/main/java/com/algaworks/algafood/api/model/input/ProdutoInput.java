package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {

  @NotBlank
  private String nome;

  @NotBlank
  private String descricao;

  @PositiveOrZero(message = "{Preco.invalido}")
  @NotNull
  private BigDecimal preco;

  @NotNull
  private Boolean ativo;

}
