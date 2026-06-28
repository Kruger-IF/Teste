package br.edu.poo.permissoes.app;

import br.edu.poo.permissoes.application.policy.*;
import br.edu.poo.permissoes.application.service.*;
import br.edu.poo.permissoes.domain.access.*;
import br.edu.poo.permissoes.domain.permission.*;
import br.edu.poo.permissoes.domain.role.*;
import br.edu.poo.permissoes.domain.user.*;
import br.edu.poo.permissoes.infrastructure.repository.*;
import br.edu.poo.permissoes.shared.exception.DominioException;

public class Main {
    public static void main(String[] args) {
        new Main().executar();
    }

    private void executar() {
        System.out.println("=== SISTEMA DE CONTROLE DE PERMISSOES E ACESSOS ===");

        // Injeção de dependências (DIP)
        UsuarioRepository usuarioRepo = new UsuarioRepositoryEmMemoria();
        PapelRepository papelRepo = new PapelRepositoryEmMemoria();
        PermissaoRepository permissaoRepo = new PermissaoRepositoryEmMemoria();
        RegistroAcessoRepository acessoRepo = new RegistroAcessoRepositoryEmMemoria();
        RegistroAlteracaoRepository alteracaoRepo = new RegistroAlteracaoRepositoryEmMemoria();

        AuditoriaServicePadrao auditoria = new AuditoriaServicePadrao(acessoRepo, alteracaoRepo);
        ExtratorDePermissoes extrator = new ExtratorDePermissoes(papelRepo);
        PoliticaAcesso politica = new PoliticaPorPermissaoDireta();
        
        AutorizacaoService autorizacao = new AutorizacaoServicePadrao(usuarioRepo, extrator, politica, auditoria);
        UsuarioServicePadrao usuarioService = new UsuarioServicePadrao(usuarioRepo, auditoria);

        // Criação de Dados e Cenários Básicos
        Papel admin = new Papel(new IdPapel("ADMIN"));
        papelRepo.salvar(admin);
        
        Permissao userCreate = new Permissao(new IdPermissao("USER_CREATE"));
        permissaoRepo.salvar(userCreate);
        papelRepo.associarPermissao(admin.id(), userCreate.id());

        Usuario administrador = usuarioService.cadastrar(new Administrador(new Email("admin@empresa.com"), new Nome("Chefe")));
        usuarioService.associarPapel(administrador.obterId(), admin.id());
        
        Usuario bloqueado = usuarioService.cadastrar(new UsuarioComum(new Email("block@empresa.com"), new Nome("Ruim")));
        usuarioService.bloquear(bloqueado.obterId());

        // Execução de Cenários do console
        executarCenario("Usuario autorizado", () -> {
            DecisaoAcesso d = autorizacao.verificarAcesso(administrador.obterId(), new Acao("USER_CREATE"));
            System.out.println("Resultado: " + d.resultado() + " | Motivo: " + d.motivo().descricao());
        });

        executarCenario("Usuario bloqueado", () -> {
            DecisaoAcesso d = autorizacao.verificarAcesso(bloqueado.obterId(), new Acao("USER_CREATE"));
            System.out.println("Resultado: " + d.resultado() + " | Motivo: " + d.motivo().descricao());
        });
        
        executarCenario("Historico Auditoria", () -> {
            auditoria.listarTentativasAutorizadas().forEach(r -> System.out.println("OK: " + r.resumo()));
            auditoria.listarTentativasNegadas().forEach(r -> System.out.println("FALHOU: " + r.resumo()));
        });
    }

    private void executarCenario(String titulo, Runnable acao) {
        System.out.println("\n--- " + titulo + " ---");
        try { acao.run(); } catch (DominioException e) { System.out.println("Regra de Negocio: " + e.getMessage()); }
    }
}