package br.edu.poo.permissoes.application.service;
import br.edu.poo.permissoes.domain.user.*;
import br.edu.poo.permissoes.domain.role.IdPapel;
import br.edu.poo.permissoes.infrastructure.repository.UsuarioRepository;
import br.edu.poo.permissoes.shared.exception.DominioException;

public class UsuarioServicePadrao {
    private final UsuarioRepository usuarioRepo;
    private final AuditoriaServicePadrao auditoria;

    public UsuarioServicePadrao(UsuarioRepository usuarioRepo, AuditoriaServicePadrao auditoria) {
        this.usuarioRepo = usuarioRepo;
        this.auditoria = auditoria;
    }

    private Usuario buscarOuFalhar(IdUsuario id) {
        return usuarioRepo.buscarPorId(id).orElseThrow(() -> new DominioException("Usuario nao encontrado"));
    }

    public Usuario cadastrar(Usuario usuario) {
        usuarioRepo.salvar(usuario);
        auditoria.registrarAlteracao("Usuario cadastrado: " + usuario.obterId().valor());
        return usuario;
    }
    public void associarPapel(IdUsuario uId, IdPapel pId) {
        Usuario u = buscarOuFalhar(uId);
        u.atribuirPapel(pId);
        auditoria.registrarAlteracao("Papel " + pId.valor() + " associado ao usuario " + uId.valor());
    }
    public void removerPapel(IdUsuario uId, IdPapel pId) {
        Usuario u = buscarOuFalhar(uId);
        u.revogarPapel(pId);
        auditoria.registrarAlteracao("Papel " + pId.valor() + " removido do usuario " + uId.valor());
    }
    public void bloquear(IdUsuario uId) {
        Usuario u = buscarOuFalhar(uId);
        u.aplicarBloqueio();
        auditoria.registrarAlteracao("Usuario bloqueado: " + uId.valor());
    }
    public void desbloquear(IdUsuario uId) {
        Usuario u = buscarOuFalhar(uId);
        u.removerBloqueio();
        auditoria.registrarAlteracao("Usuario desbloqueado: " + uId.valor());
    }
}