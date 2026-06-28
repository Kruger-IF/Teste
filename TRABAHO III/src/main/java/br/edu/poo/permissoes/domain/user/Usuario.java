package br.edu.poo.permissoes.domain.user;
import br.edu.poo.permissoes.domain.role.ConjuntoDePapeis;
import br.edu.poo.permissoes.domain.role.IdPapel;

public abstract class Usuario {
    private final IdentificacaoUsuario identificacao;
    private final ControleDeAcesso controle;

    protected Usuario(IdentificacaoUsuario identificacao) {
        this.identificacao = identificacao;
        this.controle = new ControleDeAcesso();
    }
    public IdUsuario obterId() { 
        return this.identificacao.id(); 
    }
    public String obterDescricao() { 
        return this.identificacao.perfil().formatar(); 
    }
    public boolean possuiBloqueio() { 
        return this.controle.possuiBloqueio(); 
    }
    public void aplicarBloqueio() { 
        this.controle.aplicarBloqueio(); 
    }
    public void removerBloqueio() { 
        this.controle.removerBloqueio(); 
    }
    public void atribuirPapel(IdPapel papel) { 
        this.controle.atribuirPapel(papel); 
    }
    public void revogarPapel(IdPapel papel) { 
        this.controle.revogarPapel(papel); 
    }
    public ConjuntoDePapeis obterPapeis() { 
        return this.controle.obterPapeis(); 
    }
}