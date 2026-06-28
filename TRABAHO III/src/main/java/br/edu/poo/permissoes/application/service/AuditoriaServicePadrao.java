package br.edu.poo.permissoes.application.service;
import br.edu.poo.permissoes.domain.audit.*;
import br.edu.poo.permissoes.domain.access.*;
import br.edu.poo.permissoes.domain.user.IdUsuario;
import br.edu.poo.permissoes.infrastructure.repository.*;
import java.time.LocalDateTime;
import java.util.List;

public class AuditoriaServicePadrao implements AuditoriaService {
    private final RegistroAcessoRepository acessoRepo;
    private final RegistroAlteracaoRepository alteracaoRepo;

    public AuditoriaServicePadrao(RegistroAcessoRepository acessoRepo, RegistroAlteracaoRepository alteracaoRepo) {
        this.acessoRepo = acessoRepo;
        this.alteracaoRepo = alteracaoRepo;
    }

    public void registrarAcesso(IdUsuario uId, Acao acao, DecisaoAcesso decisao) {
        acessoRepo.salvar(new RegistroAcesso(uId, acao, decisao, LocalDateTime.now()));
    }
    public void registrarAlteracao(String resumo) {
        alteracaoRepo.salvar(new RegistroAlteracao(resumo, LocalDateTime.now()));
    }
    public List<RegistroAcesso> listarTentativasAutorizadas() {
        return acessoRepo.listarTodos().stream().filter(r -> r.decisao().isAutorizado()).toList();
    }
    public List<RegistroAcesso> listarTentativasNegadas() {
        return acessoRepo.listarTodos().stream().filter(r -> !r.decisao().isAutorizado()).toList();
    }
    public List<RegistroAlteracao> listarAlteracoes() {
        return alteracaoRepo.listarTodos();
    }
}