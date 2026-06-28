package br.edu.poo.permissoes.infrastructure.repository;
import br.edu.poo.permissoes.domain.user.*;
import java.util.Optional;
public interface UsuarioRepository {
    void salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(IdUsuario id);
}