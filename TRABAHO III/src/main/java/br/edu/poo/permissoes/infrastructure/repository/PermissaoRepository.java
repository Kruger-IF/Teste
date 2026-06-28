package br.edu.poo.permissoes.infrastructure.repository; 
import br.edu.poo.permissoes.domain.permission.*; 
import java.util.Optional; 
public interface PermissaoRepository { 
    void salvar(Permissao permissao); 
    Optional<Permissao> buscarPorId(IdPermissao id); 
}