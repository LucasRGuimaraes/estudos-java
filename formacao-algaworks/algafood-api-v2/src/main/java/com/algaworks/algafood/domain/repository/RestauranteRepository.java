package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
    JpaSpecificationExecutor<Restaurante> {

  // @Query("from Restaurante r join r.cozinha join r.formasPagamento")
  List<Restaurante> findAll();

  List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

  // @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
  List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);

  // List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);

  List<Restaurante> findTop2ByNomeContaining(String nome);

  Optional<Restaurante> findFirstByNomeContaining(String nome);

  int countByCozinhaId(Long cozinha);
}
