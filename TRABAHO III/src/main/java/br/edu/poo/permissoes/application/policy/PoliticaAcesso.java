package br.edu.poo.permissoes.application.policy;
import br.edu.poo.permissoes.domain.user.Usuario;
import br.edu.poo.permissoes.domain.access.Acao;
import br.edu.poo.permissoes.domain.access.DecisaoAcesso;
import br.edu.poo.permissoes.domain.permission.IdPermissao;
import java.util.Set;

public interface PoliticaAcesso {
    DecisaoAcesso avaliar(Usuario usuario, Acao acao, Set<IdPermissao> permissoesEfetivas);
}