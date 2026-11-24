package com.example.routing.algorithms;


import java.util.*;

public class TSP {
    private String[] locais;
    private double[][] distancias;
    private int n;
    
    public static class ResultadoTSP {
        private List<Integer> rota;
        private double distanciaTotal;
        
        public ResultadoTSP(List<Integer> rota, double distanciaTotal) {
            this.rota = rota;
            this.distanciaTotal = distanciaTotal;
        }
        
        public List<Integer> getRota() { return rota; }
        public double getDistanciaTotal() { return distanciaTotal; }
    }
    
    public TSP(String[] locais, double[][] distancias) {
        this.locais = locais;
        this.distancias = distancias;
        this.n = locais.length;
    }
    
    public double calcularDistanciaRota(List<Integer> rota) {
        double distancia = 0;
        for (int i = 0; i < rota.size() - 1; i++) {
            distancia += distancias[rota.get(i)][rota.get(i + 1)];
        }
        // Voltar ao início
        distancia += distancias[rota.get(rota.size() - 1)][rota.get(0)];
        return distancia;
    }
    
    public ResultadoTSP vizinhoMaisProximo() {
        return vizinhoMaisProximo(0);
    }
    
    public ResultadoTSP vizinhoMaisProximo(int inicio) {
        List<Integer> visitados = new ArrayList<>();
        Set<Integer> naoVisitados = new HashSet<>();
        
        visitados.add(inicio);
        for (int i = 0; i < n; i++) {
            if (i != inicio) {
                naoVisitados.add(i);
            }
        }
        
        int atual = inicio;
        
        while (!naoVisitados.isEmpty()) {
            // Encontrar o mais próximo não visitado
            int proximo = -1;
            double menorDistancia = Double.POSITIVE_INFINITY;
            
            for (int candidato : naoVisitados) {
                if (distancias[atual][candidato] < menorDistancia) {
                    menorDistancia = distancias[atual][candidato];
                    proximo = candidato;
                }
            }
            
            visitados.add(proximo);
            naoVisitados.remove(proximo);
            atual = proximo;
        }
        
        double distanciaTotal = calcularDistanciaRota(visitados);
        return new ResultadoTSP(visitados, distanciaTotal);
    }
    
    public ResultadoTSP forcaBruta() {
        if (n > 10) {
            System.out.println("⚠️  Muitos pontos para força bruta!");
            return null;
        }
        
        // Fixar o ponto inicial (0) e permutar os demais
        List<Integer> outros = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            outros.add(i);
        }
        
        List<Integer> melhorRota = null;
        double melhorDistancia = Double.POSITIVE_INFINITY;
        
        List<List<Integer>> permutacoes = gerarPermutacoes(outros);
        
        for (List<Integer> perm : permutacoes) {
            List<Integer> rota = new ArrayList<>();
            rota.add(0);
            rota.addAll(perm);
            
            double distancia = calcularDistanciaRota(rota);
            
            if (distancia < melhorDistancia) {
                melhorDistancia = distancia;
                melhorRota = new ArrayList<>(rota);
            }
        }
        
        return new ResultadoTSP(melhorRota, melhorDistancia);
    }
    
    private List<List<Integer>> gerarPermutacoes(List<Integer> lista) {
        List<List<Integer>> resultado = new ArrayList<>();
        permutarRecursivo(lista, 0, resultado);
        return resultado;
    }
    
    private void permutarRecursivo(List<Integer> lista, int inicio, 
                                   List<List<Integer>> resultado) {
        if (inicio == lista.size() - 1) {
            resultado.add(new ArrayList<>(lista));
            return;
        }
        
        for (int i = inicio; i < lista.size(); i++) {
            Collections.swap(lista, inicio, i);
            permutarRecursivo(lista, inicio + 1, resultado);
            Collections.swap(lista, inicio, i); // backtrack
        }
    }
    
    public void comparar() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PROBLEMA 2: Múltiplas Entregas - TSP");
        System.out.println("=".repeat(60));
        System.out.println("COMPARAÇÃO DE MÉTODOS");
        System.out.println("=".repeat(60));
        
        // Vizinho mais próximo
        ResultadoTSP resultadoVMP = vizinhoMaisProximo();
        System.out.println("\n🔍 Vizinho Mais Próximo:");
        System.out.print("   Rota: " + traduzirRota(resultadoVMP.getRota()));
        System.out.println(" → " + locais[0]);
        System.out.printf("   Distância: %.1f km\n", resultadoVMP.getDistanciaTotal());
        
        // Força bruta (se viável)
        if (n <= 8) {
            ResultadoTSP resultadoFB = forcaBruta();
            System.out.println("\n✅ Solução Ótima (Força Bruta):");
            System.out.print("   Rota: " + traduzirRota(resultadoFB.getRota()));
            System.out.println(" → " + locais[0]);
            System.out.printf("   Distância: %.1f km\n", resultadoFB.getDistanciaTotal());
            
            double diferenca = resultadoVMP.getDistanciaTotal() - resultadoFB.getDistanciaTotal();
            double percentual = (resultadoVMP.getDistanciaTotal() / resultadoFB.getDistanciaTotal() - 1) * 100;
            System.out.printf("\n📊 Diferença: %.2f km (%.1f%% pior)\n", diferenca, percentual);
        } else {
            System.out.printf("\n⚠️  Força bruta inviável para %d pontos\n", n);
        }
        
        System.out.println("=".repeat(60) + "\n");
    }
    
    private String traduzirRota(List<Integer> rota) {
        List<String> nomes = new ArrayList<>();
        for (int i : rota) {
            nomes.add(locais[i]);
        }
        return String.join(" → ", nomes);
    }
}