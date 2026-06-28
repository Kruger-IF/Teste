package br.edu.poo.permissoes.infrastructure.repository; 
import br.edu.poo.permissoes.domain.audit.RegistroAlteracao; 
import java.util.*; 
public class RegistroAlteracaoRepositoryEmMemoria implements RegistroAlteracaoRepository { 
    private final List<RegistroAlteracao> banco = new ArrayList<>(); 
    @Override public void salvar(RegistroAlteracao r) { banco.add(r); 
    } 
    @Override public List<RegistroAlteracao> listarTodos() { 
        return Collections.unmodifiableList(banco); 
    } 
}