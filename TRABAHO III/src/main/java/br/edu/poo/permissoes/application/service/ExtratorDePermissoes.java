package br.edu.poo.permissoes.application.service;
import br.edu.poo.permissoes.domain.role.*;
import br.edu.poo.permissoes.domain.permission.IdPermissao;
import br.edu.poo.permissoes.infrastructure.repository.PapelRepository;
import java.util.HashSet;
import java.util.Set;

public class ExtratorDePermissoes {
    private final PapelRepository papelRepository;
    
    public ExtratorDePermissoes(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }
    
    public Set<IdPermissao> extrair(ConjuntoDePapeis papeis) {
        Set<IdPermissao> consolidadas = new HashSet<>();
        for (IdPapel id : papeis.listar()) {
            Set<IdPermissao> permissoesDoPapel = papelRepository.listarPermissoesDoPapel(id);
            consolidadas.addAll(permissoesDoPapel);
        }
        return consolidadas;
    }
}