package com.example.routing.models;

import java.util.*;

public class Grafo {
    private Map<String, List<Aresta>> adjacencias;
    private Set<String> vertices;
    
    public static class Aresta {
        private String destino;
        private double peso;
        
        public Aresta(String destino, double peso) {
            this.destino = destino;
            this.peso = peso;
        }
        
        public String getDestino() { return destino; }
        public double getPeso() { return peso; }
    }
    
    public Grafo() {
        this.adjacencias = new HashMap<>();
        this.vertices = new HashSet<>();
    }
    
    public void adicionarRua(String origem, String destino, double distancia) {
        // Adiciona aresta bidirecional
        adjacencias.putIfAbsent(origem, new ArrayList<>());
        adjacencias.putIfAbsent(destino, new ArrayList<>());
        
        adjacencias.get(origem).add(new Aresta(destino, distancia));
        adjacencias.get(destino).add(new Aresta(origem, distancia));
        
        vertices.add(origem);
        vertices.add(destino);
    }

    public void adicionarEstacao(String origem, String destino, double distancia) {
        // Adiciona aresta bidirecional
        adjacencias.putIfAbsent(origem, new ArrayList<>());
        adjacencias.putIfAbsent(destino, new ArrayList<>());

        adjacencias.get(origem).add(new Aresta(destino, distancia));
        adjacencias.get(destino).add(new Aresta(origem, distancia));

        vertices.add(origem);
        vertices.add(destino);
    }
    
    public List<Aresta> getVizinhos(String vertice) {
        return adjacencias.getOrDefault(vertice, new ArrayList<>());
    }
    
    public Set<String> getVertices() {
        return vertices;
    }
    
    public void exibirGrafo() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ESTRUTURA DO GRAFO");
        System.out.println("=".repeat(60));
        
        for (String vertice : vertices) {
            System.out.print(vertice + " → ");
            List<Aresta> vizinhos = adjacencias.get(vertice);
            for (int i = 0; i < vizinhos.size(); i++) {
                Aresta a = vizinhos.get(i);
                System.out.print(a.getDestino() + "(" + a.getPeso() + "km)");
                if (i < vizinhos.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println("=".repeat(60) + "\n");
    }
}
