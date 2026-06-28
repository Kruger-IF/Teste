package br.edu.poo.permissoes.application.service;
import br.edu.poo.permissoes.domain.access.*;
import br.edu.poo.permissoes.domain.user.*;
import br.edu.poo.permissoes.domain.permission.IdPermissao;
import br.edu.poo.permissoes.infrastructure.repository.UsuarioRepository;
import br.edu.poo.permissoes.application.policy.PoliticaAcesso;
import br.edu.poo.permissoes.shared.exception.DominioException;
import java.util.Set;

public class AutorizacaoServicePadrao implements AutorizacaoService {
    private final UsuarioRepository usuarioRepository;
    private final ExtratorDePermissoes extrator;
    private final PoliticaAcesso politica;
    private final AuditoriaServicePadrao auditoria;

    public AutorizacaoServicePadrao(UsuarioRepository uRepo, ExtratorDePermissoes extrator, PoliticaAcesso politica, AuditoriaServicePadrao auditoria) {
        this.usuarioRepository = uRepo;
        this.extrator = extrator;
        this.politica = politica;
        this.auditoria = auditoria;
    }

    @Override
    public DecisaoAcesso verificarAcesso(IdUsuario idUsuario, Acao acao) {
        Usuario usuario = usuarioRepository.buscarPorId(idUsuario).orElseThrow(() -> new DominioException("Usuario inexistente"));
        Set<IdPermissao> permissoes = extrator.extrair(usuario.obterPapeis());
        
        DecisaoAcesso decisao = politica.avaliar(usuario, acao, permissoes);
        auditoria.registrarAcesso(idUsuario, acao, decisao);
        return decisao;
    }
}