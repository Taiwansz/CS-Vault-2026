package com.example.routing.utils;

import com.example.routing.models.Grafo;
import com.example.routing.models.Grafo.Aresta;
import com.example.routing.models.Rota;

public class GrafoVisualizer {
    
    public void imprimirGrafo(Grafo grafo) {
        System.out.println("=== Estrutura do Grafo ===");
        for (String vertice : grafo.getVertices()) {
            System.out.print(vertice + " -> ");
            for (Aresta aresta : grafo.getVizinhos(vertice)) {
                System.out.print(aresta.getDestino() + "(" + aresta.getPeso() + ") ");
            }
            System.out.println();
        }
    }
    
    public void imprimirRota(Rota rota) {
        System.out.println("=== Rota ===");
        System.out.println("Caminho: " + String.join(" -> ", rota.getCaminho()));
        System.out.println("Distância Total: " + rota.getDistanciaTotal() + " km");
        System.out.println("Tempo Total: " + rota.getTempoTotal() + " horas");
    }
}
