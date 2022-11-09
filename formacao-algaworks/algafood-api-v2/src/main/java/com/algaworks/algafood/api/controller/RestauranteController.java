package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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
  public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
    Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);

    if (restaurante.isPresent()) {
      return ResponseEntity.ok(restaurante.get());
    }

    return ResponseEntity.notFound().build();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
    try {
      restaurante = restauranteService.salvar(restaurante);
      return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
    
    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{restauranteId}")
  public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
    Optional<Restaurante> restauranteAtualizada = restauranteRepository.findById(restauranteId);

    if (restauranteAtualizada.isPresent()) {
      BeanUtils.copyProperties(restaurante, restauranteAtualizada.get(), "id");

      try {
        restauranteService.salvar(restauranteAtualizada.get());
  
      } catch(EntidadeNaoEncontradaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
      
      return ResponseEntity.ok(restauranteAtualizada.get());
    }
    
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{restauranteId}")
  public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {
    try {
      restauranteService.remover(restauranteId);
      return ResponseEntity.noContent().build();

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.notFound().build();

    } catch (EntidadeEmUsoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }
  }
  
  @PatchMapping("/{restauranteId}")
  public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,@RequestBody Map<String, Object> campos){
    
    Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
    
    if(restauranteAtual.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
   
    Restaurante novoRestaurante = merge(campos, restauranteAtual.get());
    
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