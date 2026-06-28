package br.edu.poo.permissoes.application.service; 
import br.edu.poo.permissoes.domain.access.*; 
import br.edu.poo.permissoes.domain.user.IdUsuario; 
public interface AutorizacaoService { 
    DecisaoAcesso verificarAcesso(IdUsuario idUsuario, Acao acao); 
}