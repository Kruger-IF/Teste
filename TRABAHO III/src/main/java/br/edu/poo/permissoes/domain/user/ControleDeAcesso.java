package br.edu.poo.permissoes.domain.user;
import br.edu.poo.permissoes.domain.role.IdPapel;
import br.edu.poo.permissoes.domain.role.ConjuntoDePapeis;

public class ControleDeAcesso {
    private boolean bloqueado;
    private final ConjuntoDePapeis papeis;

    public ControleDeAcesso() {
        this.bloqueado = false;
        this.papeis = new ConjuntoDePapeis();
    }
    public boolean possuiBloqueio() { 
        return this.bloqueado; 
    }
    public void aplicarBloqueio() { 
        this.bloqueado = true; 
    }
    public void removerBloqueio() { 
        this.bloqueado = false; 
    }
    public void atribuirPapel(IdPapel papel) { 
        this.papeis.adicionar(papel); 
    }
    public void revogarPapel(IdPapel papel) { 
        this.papeis.remover(papel); 
    }
    public ConjuntoDePapeis obterPapeis() { 
        return this.papeis; 
    }
}