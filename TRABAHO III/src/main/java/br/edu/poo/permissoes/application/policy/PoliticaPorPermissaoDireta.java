package br.edu.poo.permissoes.application.policy;
import br.edu.poo.permissoes.domain.user.Usuario;
import br.edu.poo.permissoes.domain.access.*;
import br.edu.poo.permissoes.domain.permission.IdPermissao;
import java.util.Set;

public class PoliticaPorPermissaoDireta implements PoliticaAcesso {
    @Override
    public DecisaoAcesso avaliar(Usuario usuario, Acao acao, Set<IdPermissao> permissoesEfetivas) {
        if (usuario.possuiBloqueio()) {
            return DecisaoAcesso.negado(new Motivo("Usuario bloqueado"));
        }
        
        IdPermissao permissaoExigida = new IdPermissao(acao.valor());
        if (permissoesEfetivas.contains(permissaoExigida)) {
            return DecisaoAcesso.autorizado(new Motivo("Permissao encontrada nos papeis do usuario"));
        }
        
        return DecisaoAcesso.negado(new Motivo("Usuario nao possui a permissao exigida"));
    }
}