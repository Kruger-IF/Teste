package br.edu.poo.permissoes.domain.audit; 
import java.time.LocalDateTime; 
public record RegistroAlteracao(String resumo, LocalDateTime dataHora) {
    
}