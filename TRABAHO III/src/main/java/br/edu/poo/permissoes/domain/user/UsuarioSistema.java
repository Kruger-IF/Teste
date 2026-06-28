package br.edu.poo.permissoes.domain.user;
public final class UsuarioSistema extends Usuario {
    public UsuarioSistema(Email email, Nome nome) { 
        super(new IdentificacaoUsuario(new IdUsuario(email.valor()), new Perfil(email, nome))); 
    }
}