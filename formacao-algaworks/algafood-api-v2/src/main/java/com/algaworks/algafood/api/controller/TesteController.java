package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {
  
  @Autowired
  private CozinhaRepository cozinhaRepository;
  
  @Autowired
  private RestauranteRepository restauranteRepository;
  
  @GetMapping("/cozinhas/por-nome")
  public List<Cozinha> cozinhasPorNome(String nome){
    return cozinhaRepository.findAllByNomeContaining(nome);
  }
  
  @GetMapping("/cozinhas/unico-por-nome")
  public Optional<Cozinha> cozinhaPorNome(String nome){
    return cozinhaRepository.findOneByNome(nome);
  }
  
  @GetMapping("/cozinhas/exists")
  public boolean cozinhaExistsPorNome(String nome){
    return cozinhaRepository.existsByNome(nome);
  }
  
  @GetMapping("/cozinhas/primeiro")
  public Optional<Cozinha> cozinhaExistsPorNome(){
    return cozinhaRepository.buscarPrimeiro();
  }
  
  @GetMapping("/restaurantes/por-taxa-frete")
  public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
    return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
  }
  
  @GetMapping("/restaurantes/por-nome")
  public List<Restaurante> restaurantePorNomeEId(String nome, Long cozinhaId){
    return restauranteRepository.consultarPorNome(nome, cozinhaId);
  }
  
  @GetMapping("/restaurantes/primeiro-por-nome")
  public Optional<Restaurante> restaurantePrimeiroPorNome(String nome){
    return restauranteRepository.findFirstByNomeContaining(nome);
  }
  
  @GetMapping("/restaurantes/top2-por-nome")
  public List<Restaurante> restauranteTop2PorNome(String nome){
    return restauranteRepository.findTop2ByNomeContaining(nome);
  }
  
  @GetMapping("/restaurantes/count-por-cozinha")
  public int restaurantesCountPorCozinha(Long cozinhaId){
    return restauranteRepository.countByCozinhaId(cozinhaId);
  }
  
  @GetMapping("/restaurantes/por-nome-e-frete")
  public List<Restaurante> restaurantePorNomeFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
    return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
  }
  
  @GetMapping("/restaurantes/com-frete-gratis")
  public List<Restaurante> restauranteComFreteGratis(String nome){
    return restauranteRepository.findComFreteGratis(nome);
  }
  
  @GetMapping("/restaurantes/primeiro")
  public Optional<Restaurante> restaurantePrimeiro(){
    return restauranteRepository.buscarPrimeiro();
  }
  
}
