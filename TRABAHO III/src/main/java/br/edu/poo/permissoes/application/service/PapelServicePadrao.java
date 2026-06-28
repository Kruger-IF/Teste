package br.edu.poo.permissoes.application.service;

import br.edu.poo.permissoes.domain.permission.IdPermissao;
import br.edu.poo.permissoes.domain.role.IdPapel;
import br.edu.poo.permissoes.domain.role.Papel;
import br.edu.poo.permissoes.infrastructure.repository.PapelRepository;

public class PapelServicePadrao {
    // Calisthenics: Máximo de 2 atributos por classe
    private final PapelRepository papelRepository;
    private final AuditoriaService auditoria;

    public PapelServicePadrao(PapelRepository papelRepository, AuditoriaService auditoria) {
        this.papelRepository = papelRepository;
        this.auditoria = auditoria;
    }

    public Papel cadastrar(Papel papel) {
        this.papelRepository.salvar(papel);
        this.auditoria.registrarAlteracao("Papel cadastrado: " + papel.id().valor());
        return papel;
    }

    public void associarPermissao(IdPapel idPapel, IdPermissao idPermissao) {
        this.papelRepository.associarPermissao(idPapel, idPermissao);
        this.auditoria.registrarAlteracao("Permissao " + idPermissao.valor() + " vinculada ao papel " + idPapel.valor());
    }

    public void removerPermissao(IdPapel idPapel, IdPermissao idPermissao) {
        this.papelRepository.removerPermissao(idPapel, idPermissao);
        this.auditoria.registrarAlteracao("Permissao " + idPermissao.valor() + " removida do papel " + idPapel.valor());
    }
}