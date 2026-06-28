package br.edu.poo.permissoes.domain.user;
public final class Administrador extends Usuario {
    public Administrador(Email email, Nome nome) { 
        super(new IdentificacaoUsuario(new IdUsuario(email.valor()), new Perfil(email, nome))); 
    }
}