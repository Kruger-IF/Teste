package br.edu.poo.permissoes.domain.user; 

public record Perfil(Email email, Nome nome) { 
    public String formatar() { 
        return email.valor() + " (" + nome.valor() + ")"; 
    } 
}
