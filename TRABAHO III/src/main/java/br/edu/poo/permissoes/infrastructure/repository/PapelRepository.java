package br.edu.poo.permissoes.infrastructure.repository;
import br.edu.poo.permissoes.domain.role.*;
import br.edu.poo.permissoes.domain.permission.IdPermissao;
import java.util.Optional;
import java.util.Set;
public interface PapelRepository {
    void salvar(Papel papel);
    Optional<Papel> buscarPorId(IdPapel id);
    void associarPermissao(IdPapel papelId, IdPermissao permissaoId);
    void removerPermissao(IdPapel papelId, IdPermissao permissaoId);
    Set<IdPermissao> listarPermissoesDoPapel(IdPapel papelId);
}