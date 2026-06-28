package br.edu.poo.permissoes.domain.user;
public final class UsuarioComum extends Usuario {
    public UsuarioComum(Email email, Nome nome) { 
        super(new IdentificacaoUsuario(new IdUsuario(email.valor()), new Perfil(email, nome))); 
    }
}