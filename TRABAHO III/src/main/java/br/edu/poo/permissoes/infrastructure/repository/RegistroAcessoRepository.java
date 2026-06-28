package br.edu.poo.permissoes.infrastructure.repository; 
import br.edu.poo.permissoes.domain.audit.RegistroAcesso; 
import java.util.List; public interface RegistroAcessoRepository { 
    void salvar(RegistroAcesso registro); 
    List<RegistroAcesso> listarTodos(); 
}