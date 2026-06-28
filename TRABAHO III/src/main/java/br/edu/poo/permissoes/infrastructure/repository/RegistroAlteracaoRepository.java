package br.edu.poo.permissoes.infrastructure.repository; 
import br.edu.poo.permissoes.domain.audit.RegistroAlteracao; 
import java.util.List; public interface RegistroAlteracaoRepository { 
    void salvar(RegistroAlteracao registro); 
    List<RegistroAlteracao> listarTodos(); 
}