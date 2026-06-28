package br.edu.poo.permissoes.application.service;

import br.edu.poo.permissoes.domain.access.Acao;
import br.edu.poo.permissoes.domain.access.DecisaoAcesso;
import br.edu.poo.permissoes.domain.audit.RegistroAcesso;
import br.edu.poo.permissoes.domain.audit.RegistroAlteracao;
import br.edu.poo.permissoes.domain.user.IdUsuario;
import java.util.List;

public interface AuditoriaService {
    void registrarAcesso(IdUsuario idUsuario, Acao acao, DecisaoAcesso decisao);
    void registrarAlteracao(String resumo);
    List<RegistroAcesso> listarTentativasAutorizadas();
    List<RegistroAcesso> listarTentativasNegadas();
    List<RegistroAlteracao> listarAlteracoes();
}