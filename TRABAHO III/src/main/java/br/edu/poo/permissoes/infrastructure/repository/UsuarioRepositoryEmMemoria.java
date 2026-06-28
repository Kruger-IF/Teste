package br.edu.poo.permissoes.infrastructure.repository;
import br.edu.poo.permissoes.domain.user.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
public class UsuarioRepositoryEmMemoria implements UsuarioRepository {
    private final Map<IdUsuario, Usuario> banco = new HashMap<>();
    @Override public void salvar(Usuario u) { banco.put(u.obterId(), u); 
    }
    @Override public Optional<Usuario> buscarPorId(IdUsuario id) { 
        return Optional.ofNullable(banco.get(id)); 
    }
}