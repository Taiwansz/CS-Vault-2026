package com.example.routing.models;

import java.time.LocalDateTime;

public class Cliente {
    private final String id;
    private final String endereco;
    private final LocalDateTime janelaInicio;
    private final LocalDateTime janelaFim;
    private final int tempoServico;
    
    public Cliente(String id, String endereco, LocalDateTime janelaInicio, 
                   LocalDateTime janelaFim, int tempoServico) {
        this.id = id;
        this.endereco = endereco;
        this.janelaInicio = janelaInicio;
        this.janelaFim = janelaFim;
        this.tempoServico = tempoServico;
    }
    
    public String getId() { return id; }
    public String getEndereco() { return endereco; }
    public LocalDateTime getJanelaInicio() { return janelaInicio; }
    public LocalDateTime getJanelaFim() { return janelaFim; }
    public int getTempoServico() { return tempoServico; }
    
    @Override
    public String toString() {
        return String.format("%s - %s", id, endereco);
    }
}