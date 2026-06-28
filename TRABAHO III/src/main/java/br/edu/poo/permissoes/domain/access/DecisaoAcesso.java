package br.edu.poo.permissoes.domain.access;
public record DecisaoAcesso(String resultado, Motivo motivo) {
    public static DecisaoAcesso autorizado(Motivo motivo) { 
        return new DecisaoAcesso("AUTORIZADO", motivo); 
    }
    public static DecisaoAcesso negado(Motivo motivo) { 
        return new DecisaoAcesso("NEGADO", motivo); 
    }
    public boolean isAutorizado() { 
        return "AUTORIZADO".equals(resultado); 
    }
}