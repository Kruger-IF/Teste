package br.edu.poo.permissoes.infrastructure.repository; 
import br.edu.poo.permissoes.domain.audit.RegistroAcesso; 
import java.util.*; 
public class RegistroAcessoRepositoryEmMemoria implements RegistroAcessoRepository { 
        private final List<RegistroAcesso> banco = new ArrayList<>(); 
        @Override public void salvar(RegistroAcesso r) { 
            banco.add(r); 
        } 
        @Override public List<RegistroAcesso> listarTodos() { 
        return Collections.unmodifiableList(banco); 
    } 
}
