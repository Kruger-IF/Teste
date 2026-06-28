package br.edu.poo.permissoes.application.service;

import br.edu.poo.permissoes.domain.permission.Permissao;
import br.edu.poo.permissoes.infrastructure.repository.PermissaoRepository;

public class PermissaoServicePadrao {
    // Calisthenics: Máximo de 2 atributos por classe
    private final PermissaoRepository permissaoRepository;
    private final AuditoriaService auditoria;

    public PermissaoServicePadrao(PermissaoRepository permissaoRepository, AuditoriaService auditoria) {
        this.permissaoRepository = permissaoRepository;
        this.auditoria = auditoria;
    }

    public Permissao cadastrar(Permissao permissao) {
        this.permissaoRepository.salvar(permissao);
        this.auditoria.registrarAlteracao("Permissao cadastrada no sistema: " + permissao.id().valor());
        return permissao;
    }
}