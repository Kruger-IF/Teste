package br.edu.poo.permissoes.domain.audit;
import br.edu.poo.permissoes.domain.user.IdUsuario;
import br.edu.poo.permissoes.domain.access.Acao;
import br.edu.poo.permissoes.domain.access.DecisaoAcesso;
import java.time.LocalDateTime;

public record RegistroAcesso(IdUsuario usuarioId, Acao acao, DecisaoAcesso decisao, LocalDateTime dataHora) {
    public String resumo() {
        return String.format("[%s] Usuario: %s | Acao: %s | Status: %s | Motivo: %s",
                dataHora, usuarioId.valor(), acao.valor(), decisao.resultado(), decisao.motivo().descricao());
    }
}