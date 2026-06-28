package br.edu.poo.permissoes.infrastructure.repository;
import br.edu.poo.permissoes.domain.role.*;
import br.edu.poo.permissoes.domain.permission.IdPermissao;
import java.util.*;
public class PapelRepositoryEmMemoria implements PapelRepository {
    private final Map<IdPapel, Papel> banco = new HashMap<>();
    private final Map<IdPapel, Set<IdPermissao>> associacoes = new HashMap<>();
    @Override public void salvar(Papel p) {
        banco.put(p.id(), p);
        associacoes.putIfAbsent(p.id(), new HashSet<>());
    }
    @Override public Optional<Papel> buscarPorId(IdPapel id) { return Optional.ofNullable(banco.get(id)); }
    @Override public void associarPermissao(IdPapel pId, IdPermissao permId) { associacoes.getOrDefault(pId, new HashSet<>()).add(permId); }
    @Override public void removerPermissao(IdPapel pId, IdPermissao permId) { associacoes.getOrDefault(pId, new HashSet<>()).remove(permId); }
    @Override public Set<IdPermissao> listarPermissoesDoPapel(IdPapel pId) { return Collections.unmodifiableSet(associacoes.getOrDefault(pId, new HashSet<>())); }
}