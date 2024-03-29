package com.algaworks.algafood.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String senha;

  @JsonIgnore
  @Column(nullable = false, columnDefinition = "timestamp")
  private OffsetDateTime dataCadastro;

  @JsonIgnore
  @ManyToMany
  @JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "grupo_id"))
  private Set<Grupo> grupos = new HashSet<>();

  @PrePersist
  private void prePersistent() {
    this.dataCadastro = OffsetDateTime.now();
  }

  public boolean senhaCoincideCom(String senha) {
    return getSenha().equals(senha);
  }

  public boolean senhaNaoCoincideCom(String senha) {
    return !senhaCoincideCom(senha);
  }

  public boolean adicionarGrupo(Grupo grupo) {
		return getGrupos().add(grupo);
	}

	public boolean removerGrupo(Grupo grupo) {
		return getGrupos().remove(grupo);
	}

}
