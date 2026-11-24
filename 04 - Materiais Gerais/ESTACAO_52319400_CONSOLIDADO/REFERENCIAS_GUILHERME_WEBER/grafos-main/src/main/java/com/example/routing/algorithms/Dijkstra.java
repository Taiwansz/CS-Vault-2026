package com.example.routing.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.example.routing.models.Grafo;
import com.example.routing.models.Grafo.Aresta;

public class Dijkstra {
    
    public static class Resultado {
        private double distancia;
        private List<String> caminho;
        
        public Resultado(double distancia, List<String> caminho) {
            this.distancia = distancia;
            this.caminho = caminho;
        }
        
        public double getDistancia() { return distancia; }
        public List<String> getCaminho() { return caminho; }
    }
    
    private static class No implements Comparable<No> {
        String vertice;
        double distancia;
        
        public No(String vertice, double distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }
        
        @Override
        public int compareTo(No outro) {
            return Double.compare(this.distancia, outro.distancia);
        }
    }
    
    public static Resultado encontrarMenorCaminho(Grafo grafo, String inicio, String fim) {
        Map<String, Double> distancias = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        PriorityQueue<No> fila = new PriorityQueue<>();
        
        // Inicializar distâncias
        for (String v : grafo.getVertices()) {
            distancias.put(v, Double.POSITIVE_INFINITY);
        }
        distancias.put(inicio, 0.0);
        
        fila.offer(new No(inicio, 0.0));
        
        while (!fila.isEmpty()) {
            No atual = fila.poll();
            String verticeAtual = atual.vertice;
            
            if (visitados.contains(verticeAtual)) {
                continue;
            }
            
            visitados.add(verticeAtual);
            
            // Se chegou no destino, pode parar
            if (verticeAtual.equals(fim)) {
                break;
            }
            
            // Explorar vizinhos
            for (Aresta aresta : grafo.getVizinhos(verticeAtual)) {
                String vizinho = aresta.getDestino();
                double novaDistancia = distancias.get(verticeAtual) + aresta.getPeso();
                
                if (novaDistancia < distancias.get(vizinho)) {
                    distancias.put(vizinho, novaDistancia);
                    anteriores.put(vizinho, verticeAtual);
                    fila.offer(new No(vizinho, novaDistancia));
                }
            }
        }
        
        // Reconstruir caminho
        List<String> caminho = reconstruirCaminho(anteriores, inicio, fim);
        
        return new Resultado(distancias.get(fim), caminho);
    }
    
    private static List<String> reconstruirCaminho(Map<String, String> anteriores, 
                                                    String inicio, String fim) {
        List<String> caminho = new ArrayList<>();
        String atual = fim;
        
        while (atual != null) {
            caminho.add(0, atual);
            atual = anteriores.get(atual);
        }
        
        return caminho;
    }
    
    public static void exibirResultado(String origem, String destino, Resultado resultado) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PROBLEMA 1: Trajeto Estações de Metrô - Caminho Mais Curto");
        System.out.println("=".repeat(60));
        System.out.println("\n📦 Objetivo de: " + origem + " → " + destino);
        System.out.println("🛣️  Melhor rota: " + String.join(" → ", resultado.getCaminho()));
        System.out.printf("📏 Tempo total: %.1f min\n", resultado.getDistancia());
        System.out.println("=".repeat(60) + "\n");
    }
}