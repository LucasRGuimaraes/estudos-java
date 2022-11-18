package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

  @Autowired
  private RestauranteRepository restauranteRepository;

  @Autowired
  private RestauranteService restauranteService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Restaurante> listar() {
    return restauranteRepository.findAll();
  }

  @GetMapping("/{restauranteId}")
  public Restaurante buscar(@PathVariable Long restauranteId) {
    return restauranteService.findOrFailure(restauranteId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Restaurante adicionar(@RequestBody Restaurante restaurante) {
    try {
      return restauranteService.salvar(restaurante);
    } catch (CozinhaNaoEncontradaException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }

  @PutMapping("/{restauranteId}")
  public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
    Restaurante restauranteAtualizado = restauranteService.findOrFailure(restauranteId);

    BeanUtils.copyProperties(restaurante, restauranteAtualizado, "id", "formasPagamento", "endereco",
        "dataCadastro", "produtos");

    try {
      return restauranteService.salvar(restauranteAtualizado);

    } catch (CozinhaNaoEncontradaException e) {
      throw new NegocioException(e.getMessage());
    }
  }

  @DeleteMapping("/{restauranteId}")
  public void remover(@PathVariable Long restauranteId) {
    restauranteService.remover(restauranteId);
  }

  @PatchMapping("/{restauranteId}")
  public Restaurante atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
    Restaurante restauranteAtual = restauranteService.findOrFailure(restauranteId);

    Restaurante novoRestaurante = merge(campos, restauranteAtual);

    return atualizar(restauranteId, novoRestaurante);
  }

  private Restaurante merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
    ObjectMapper objectMapper = new ObjectMapper();
    Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

    camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
      Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
      field.setAccessible(true);

      Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

      ReflectionUtils.setField(field, restauranteDestino, novoValor);
    });

    return restauranteDestino;
  }

}