package com.example.routing.models;

import java.util.ArrayList;
import java.util.List;

public class Rota {
    private List<String> caminho;
    private double distanciaTotal;
    private double tempoTotal;
    
    public Rota() {
        this.caminho = new ArrayList<>();
        this.distanciaTotal = 0.0;
        this.tempoTotal = 0.0;
    }
    
    public void adicionarPonto(String ponto) {
        caminho.add(ponto);
    }
    
    public List<String> getCaminho() { return caminho; }
    public double getDistanciaTotal() { return distanciaTotal; }
    public double getTempoTotal() { return tempoTotal; }
    
    public void setDistanciaTotal(double distancia) { this.distanciaTotal = distancia; }
    public void setTempoTotal(double tempo) { this.tempoTotal = tempo; }
}
