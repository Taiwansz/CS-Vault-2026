package com.example.routing.algorithms;

import java.util.*;

public class VRP {
    private int numVeiculos;
    private int capacidade;
    private Map<String, ClienteVRP> clientes;
    private Map<String, Map<String, Double>> distancias;
    
    public static class ClienteVRP {
        String id;
        int demanda;
        
        public ClienteVRP(String id, int demanda) {
            this.id = id;
            this.demanda = demanda;
        }
        
        public String getId() { return id; }
        public int getDemanda() { return demanda; }
    }
    
    public static class RotaVeiculo {
        int veiculo;
        List<String> clientes;
        int carga;
        double distancia;
        
        public RotaVeiculo(int veiculo, List<String> clientes, int carga, double distancia) {
            this.veiculo = veiculo;
            this.clientes = clientes;
            this.carga = carga;
            this.distancia = distancia;
        }
    }
    
    public VRP(int numVeiculos, int capacidade) {
        this.numVeiculos = numVeiculos;
        this.capacidade = capacidade;
        this.clientes = new HashMap<>();
        this.distancias = new HashMap<>();
    }
    
    public void adicionarCliente(String id, int demanda) {
        clientes.put(id, new ClienteVRP(id, demanda));
    }
    
    public void adicionarDistancia(String origem, String destino, double dist) {
        distancias.putIfAbsent(origem, new HashMap<>());
        distancias.get(origem).put(destino, dist);
    }
    
    public List<RotaVeiculo> resolverHeuristico() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PROBLEMA 4: ROTEAMENTO DE VEÍCULOS (VRP)");
        System.out.println("=".repeat(70));
        System.out.printf("Veículos disponíveis: %d\n", numVeiculos);
        System.out.printf("Capacidade por veículo: %dkg\n\n", capacidade);
        
        List<RotaVeiculo> rotas = new ArrayList<>();
        List<String> clientesNaoAtendidos = new ArrayList<>(clientes.keySet());
        
        for (int v = 0; v < numVeiculos; v++) {
            if (clientesNaoAtendidos.isEmpty()) {
                break;
            }
            
            List<String> rota = new ArrayList<>();
            int cargaAtual = 0;
            
            while (!clientesNaoAtendidos.isEmpty()) {
                boolean clienteAdicionado = false;
                
                for (String clienteId : new ArrayList<>(clientesNaoAtendidos)) {
                    ClienteVRP cliente = clientes.get(clienteId);
                    
                    if (cargaAtual + cliente.getDemanda() <= capacidade) {
                        rota.add(clienteId);
                        cargaAtual += cliente.getDemanda();
                        clientesNaoAtendidos.remove(clienteId);
                        clienteAdicionado = true;
                        break;
                    }
                }
                
                if (!clienteAdicionado) {
                    break;
                }
            }
            
            if (!rota.isEmpty()) {
                double distanciaRota = calcularDistanciaRota(rota);
                rotas.add(new RotaVeiculo(v + 1, rota, cargaAtual, distanciaRota));
            }
        }
        
        exibirRotas(rotas);
        return rotas;
    }
    
    private double calcularDistanciaRota(List<String> rota) {
        if (rota.isEmpty()) {
            return 0;
        }
        
        double distancia = distancias.get("CD").get(rota.get(0));
        
        for (int i = 0; i < rota.size() - 1; i++) {
            distancia += distancias.get(rota.get(i)).get(rota.get(i + 1));
        }
        
        distancia += distancias.get(rota.get(rota.size() - 1)).get("CD");
        
        return distancia;
    }
    
    private void exibirRotas(List<RotaVeiculo> rotas) {
        double distanciaTotal = 0;
        
        for (RotaVeiculo rota : rotas) {
            System.out.printf("🚚 Veículo %d:\n", rota.veiculo);
            System.out.print("   Rota: CD → " + String.join(" → ", rota.clientes) + " → CD\n");
            System.out.printf("   📦 Carga: %dkg / %dkg (%.1f%%)\n", 
                            rota.carga, capacidade, 
                            (rota.carga * 100.0 / capacidade));
            System.out.printf("   📏 Distância: %.1fkm\n", rota.distancia);
            
            // Detalhar cada cliente
            for (String clienteId : rota.clientes) {
                ClienteVRP cliente = clientes.get(clienteId);
                System.out.printf("      • %s: %dkg\n", clienteId, cliente.getDemanda());
            }
            
            distanciaTotal += rota.distancia;
            System.out.println();
        }
        
        System.out.println("=".repeat(70));
        System.out.println("📊 RESUMO:");
        System.out.printf("   Total de veículos usados: %d\n", rotas.size());
        System.out.printf("   Distância total percorrida: %.1fkm\n", distanciaTotal);
        System.out.printf("   Média por veículo: %.1fkm\n", distanciaTotal / rotas.size());
        System.out.println("=".repeat(70) + "\n");
    }
}
