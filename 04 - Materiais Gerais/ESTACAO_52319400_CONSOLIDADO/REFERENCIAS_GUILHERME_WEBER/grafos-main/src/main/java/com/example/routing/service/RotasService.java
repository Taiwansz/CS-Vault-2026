package com.example.routing.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.routing.algorithms.Dijkstra;
import com.example.routing.algorithms.EntregaComTempo;
import com.example.routing.algorithms.TSP;
import com.example.routing.algorithms.VRP;
import com.example.routing.models.Cliente;
import com.example.routing.models.Grafo;

@Service
public class RotasService {

    public void problema1_CaminhoMaisCurto() {
        Grafo mapa = new Grafo();

        // Construir o mapa
        mapa.adicionarRua("A", "B", 8);
        mapa.adicionarRua("A", "C", 5);
        mapa.adicionarRua("B", "D", 3);
        mapa.adicionarRua("C", "D", 4);
        mapa.adicionarRua("C", "E", 7);
        mapa.adicionarRua("D", "F", 6);
        mapa.adicionarRua("E", "F", 2);

        mapa.exibirGrafo();

        // Encontrar melhor rota
        String origem = "A";
        String destino = "F";
        Dijkstra.Resultado resultado = Dijkstra.encontrarMenorCaminho(mapa, origem, destino);
        Dijkstra.exibirResultado(origem, destino, resultado);
    }

    public void problema2_TSP() {
        String[] locais = {"CD", "C1", "C2", "C3", "C4", "C5"};
        double[][] distancias = {
            {0, 10, 15, 20, 25, 30}, // CD
            {10, 0, 35, 25, 30, 20}, // C1
            {15, 35, 0, 30, 15, 25}, // C2
            {20, 25, 30, 0, 15, 18}, // C3
            {25, 30, 15, 15, 0, 22}, // C4
            {30, 20, 25, 18, 22, 0} // C5
        };

        TSP tsp = new TSP(locais, distancias);
        tsp.comparar();
    }

    public void problema3_JanelasTempo() {
        EntregaComTempo entrega = new EntregaComTempo();
        LocalDateTime baseHora = LocalDateTime.now()
                .withHour(8)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        // Adicionar clientes
        Cliente c1 = new Cliente("C1", "Rua A, 123",
                baseHora.plusHours(1),
                baseHora.plusHours(2).plusMinutes(30),
                15);
        Cliente c2 = new Cliente("C2", "Av. B, 456",
                baseHora.plusMinutes(30),
                baseHora.plusHours(1).plusMinutes(30),
                20);
        Cliente c3 = new Cliente("C3", "Rua C, 789",
                baseHora.plusHours(2),
                baseHora.plusHours(3),
                10);

        entrega.adicionarCliente(c1);
        entrega.adicionarCliente(c2);
        entrega.adicionarCliente(c3);

        // Matriz de tempos
        String[] origens = {"CD", "C1", "C2", "C3"};
        int[][] tempos = {
            {0, 20, 15, 30}, // CD
            {20, 0, 25, 20}, // C1
            {15, 25, 0, 35}, // C2
            {30, 20, 35, 0} // C3
        };

        for (int i = 0; i < origens.length; i++) {
            for (int j = 0; j < origens.length; j++) {
                if (i != j) {
                    entrega.adicionarTempoViagem(origens[i], origens[j], tempos[i][j]);
                }
            }
        }

        // Testar ordem: C2 → C1 → C3
        List<String> ordem = Arrays.asList("C2", "C1", "C3");
        entrega.planejarRotaTemporal(ordem, baseHora);
    }

    public void problema4_VRP() {
        VRP vrp = new VRP(3, 100);

        // Adicionar clientes
        String[] clienteIds = {"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8"};
        int[] demandas = {30, 45, 25, 50, 35, 20, 40, 30};

        for (int i = 0; i < clienteIds.length; i++) {
            vrp.adicionarCliente(clienteIds[i], demandas[i]);
        }

        // Matriz de distâncias completa
        String[] locais = {"CD", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8"};
        double[][] distancias = {
            {0, 5, 8, 12, 7, 15, 10, 6, 9}, // CD
            {5, 0, 6, 10, 8, 14, 9, 7, 5}, // C1
            {8, 6, 0, 11, 9, 13, 12, 8, 7}, // C2
            {12, 10, 11, 0, 8, 10, 14, 13, 11}, // C3
            {7, 8, 9, 8, 0, 12, 11, 9, 8}, // C4
            {15, 14, 13, 10, 12, 0, 16, 15, 14}, // C5
            {10, 9, 12, 14, 11, 16, 0, 10, 11}, // C6
            {6, 7, 8, 13, 9, 15, 10, 0, 7}, // C7
            {9, 5, 7, 11, 8, 14, 11, 7, 0} // C8
        };

        // Adicionar todas as distâncias ao VRP
        for (int i = 0; i < locais.length; i++) {
            for (int j = 0; j < locais.length; j++) {
                if (i != j) {
                    vrp.adicionarDistancia(locais[i], locais[j], distancias[i][j]);
                }
            }
        }

        // Resolver
        vrp.resolverHeuristico();
    }

    public void executarTodosProblemas() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SISTEMA DE OTIMIZAÇÃO DE ROTAS DE ENTREGA");
        System.out.println("=".repeat(70));

        problema1_CaminhoMaisCurto();
        problema2_TSP();
        problema3_JanelasTempo();
        problema4_VRP();
    }

    public void estacoesMetro() {
        Grafo mapa = new Grafo();

        // Construir o mapa
        mapa.adicionarEstacao("AT", "AL", 10);
        mapa.adicionarEstacao("AT", "AJ", 45);
        mapa.adicionarEstacao("AT", "AS", 25);
        mapa.adicionarEstacao("AT", "VS", 25);
        mapa.adicionarEstacao("AL", "AT", 10);
        mapa.adicionarEstacao("AL", "AJ", 42);
        mapa.adicionarEstacao("AL", "AS", 3);
        mapa.adicionarEstacao("AL", "VS", 3);
        mapa.adicionarEstacao("AJ", "AT", 45);
        mapa.adicionarEstacao("AJ", "AL", 42);
        mapa.adicionarEstacao("AJ", "AS", 25);
        mapa.adicionarEstacao("AJ", "VS", 25);
        mapa.adicionarEstacao("AS", "AT", 25);
        mapa.adicionarEstacao("AS", "AL", 3);
        mapa.adicionarEstacao("AS", "AJ", 25);
        mapa.adicionarEstacao("AS", "VI", 81);
        mapa.adicionarEstacao("AS", "VB", 14);
        mapa.adicionarEstacao("AS", "VP", 26);
        mapa.adicionarEstacao("VI", "AS", 81);
        mapa.adicionarEstacao("VI", "VS", 80);
        mapa.adicionarEstacao("VI", "VB", 121);
        mapa.adicionarEstacao("VI", "VP", 10);
        mapa.adicionarEstacao("VS", "AT", 25);
        mapa.adicionarEstacao("VS", "AL", 3);
        mapa.adicionarEstacao("VS", "AJ", 25);
        mapa.adicionarEstacao("VS", "VI", 80);
        mapa.adicionarEstacao("VS", "AS", 12);
        mapa.adicionarEstacao("VS", "VB", 19);
        mapa.adicionarEstacao("VS", "VP", 26);
        mapa.adicionarEstacao("VB", "AS", 14);
        mapa.adicionarEstacao("VB", "VI", 121);
        mapa.adicionarEstacao("VB", "VS", 19);
        mapa.adicionarEstacao("VB", "VP", 23);
        mapa.adicionarEstacao("VB", "VI", 120);
        mapa.adicionarEstacao("VB", "VS", 18);
        mapa.adicionarEstacao("VB", "AJ", 25);
        mapa.adicionarEstacao("VP", "AS", 26);
        mapa.adicionarEstacao("VP", "VI", 10);
        mapa.adicionarEstacao("VP", "VS", 26);
        mapa.adicionarEstacao("VP", "VB", 23);
        mapa.adicionarEstacao("VP", "AL", 25);
        mapa.adicionarEstacao("VP", "AJ", 40);


        mapa.exibirGrafo();

        // Encontrar melhor rota
        String origem = "AL";
        String destino = "VI";
        Dijkstra.Resultado resultado = Dijkstra.encontrarMenorCaminho(mapa, origem, destino);
        Dijkstra.exibirResultado(origem, destino, resultado);
    }
}
