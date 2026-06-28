package br.edu.poo.permissoes.domain.role;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConjuntoDePapeis {
    private final Set<IdPapel> papeis = new HashSet<>();
    public void adicionar(IdPapel papel) { 
        this.papeis.add(papel); 
    }
    public void remover(IdPapel papel) { 
        this.papeis.remove(papel); 
    }
    public Set<IdPapel> listar() { 
        return Collections.unmodifiableSet(this.papeis); 
    }
}