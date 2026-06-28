package br.edu.poo.permissoes.infrastructure.repository; 
import br.edu.poo.permissoes.domain.permission.*; 
import java.util.*; 
public class PermissaoRepositoryEmMemoria implements PermissaoRepository { 
    private final Map<IdPermissao, Permissao> banco = new HashMap<>(); 
    @Override public void salvar(Permissao p) { 
        banco.put(p.id(), p); 
    } 
    @Override public Optional<Permissao> buscarPorId(IdPermissao id) { 
        return Optional.ofNullable(banco.get(id)); 
    } 
}