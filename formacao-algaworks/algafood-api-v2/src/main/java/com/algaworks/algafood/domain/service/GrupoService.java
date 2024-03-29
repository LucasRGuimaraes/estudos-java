package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class GrupoService {

  private static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA = "Não existe um cadastro de grupo com código %d";
  private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Grupo de código %d não pode ser removida, pois está em uso";

  @Autowired
  private GrupoRepository grupoRepository;

  @Autowired
  private PermissaoService permissaoService;

  @Transactional
  public Grupo salvar(Grupo grupo) {
    return grupoRepository.save(grupo);
  }

  @Transactional
  public void remover(Long grupoId) {
    try {
      grupoRepository.deleteById(grupoId);
      grupoRepository.flush();

    } catch (EmptyResultDataAccessException e) {
      throw new GrupoNaoEncontradoException(
          String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, grupoId));

    } catch (DataIntegrityViolationException e) {
      throw new EntidadeEmUsoException(
          String.format(MSG_FORMA_PAGAMENTO_EM_USO, grupoId));
    }
  }

  @Transactional
  public void associarPermissao(Long grupoId, Long permissaoId) {
    Grupo grupo = findOrFailure(grupoId);
    Permissao permissao = permissaoService.findOrFailure(permissaoId);

    grupo.adicionarPermissao(permissao);
  }

  @Transactional
  public void desassociarPermissao(Long grupoId, Long permissaoId) {
    Grupo grupo = findOrFailure(grupoId);
    Permissao permissao = permissaoService.findOrFailure(permissaoId);

    grupo.removerPermissao(permissao);
  }

  public Grupo findOrFailure(Long grupoId) {
    return grupoRepository.findById(grupoId)
        .orElseThrow(() -> new GrupoNaoEncontradoException(
            String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, grupoId)));
  }

}
