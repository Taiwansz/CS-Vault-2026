package com.example.routing.algorithms;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.*;

import com.example.routing.models.Cliente;

public class EntregaComTempo {
    private Map<String, Cliente> clientes;
    private Map<String, Map<String, Integer>> matrizTempo; // tempo em minutos
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    
    public static class InfoEntrega {
        String cliente;
        String endereco;
        LocalDateTime horaChegada;
        String janela;
        int tempoEspera;
        LocalDateTime horaInicioServico;
        LocalDateTime horaSaida;
        String status;
        
        public InfoEntrega(String cliente, String endereco, LocalDateTime horaChegada,
                          String janela, int tempoEspera, LocalDateTime horaInicioServico,
                          LocalDateTime horaSaida, String status) {
            this.cliente = cliente;
            this.endereco = endereco;
            this.horaChegada = horaChegada;
            this.janela = janela;
            this.tempoEspera = tempoEspera;
            this.horaInicioServico = horaInicioServico;
            this.horaSaida = horaSaida;
            this.status = status;
        }
    }
    
    public EntregaComTempo() {
        this.clientes = new HashMap<>();
        this.matrizTempo = new HashMap<>();
    }
    
    public void adicionarCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }
    
    public void adicionarTempoViagem(String origem, String destino, int minutos) {
        matrizTempo.putIfAbsent(origem, new HashMap<>());
        matrizTempo.get(origem).put(destino, minutos);
    }
    
    public boolean planejarRotaTemporal(List<String> ordemVisita, LocalDateTime horaInicio) {
        LocalDateTime horaAtual = horaInicio;
        List<InfoEntrega> rotaDetalhada = new ArrayList<>();
        boolean viavel = true;
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PROBLEMA 3: PLANEJAMENTO DE ROTA COM JANELAS DE TEMPO");
        System.out.println("=".repeat(70));
        System.out.println("Início: " + horaInicio.format(formatter) + "\n");
        
        String localAtual = "CD"; // Centro de Distribuição
        
        for (int i = 0; i < ordemVisita.size(); i++) {
            String clienteId = ordemVisita.get(i);
            Cliente cliente = clientes.get(clienteId);
            
            // Tempo de viagem
            int tempoViagem = matrizTempo.get(localAtual).get(clienteId);
            LocalDateTime horaChegada = horaAtual.plusMinutes(tempoViagem);
            
            LocalDateTime horaInicioServico;
            int tempoEspera;
            
            // Verificar janela
            if (horaChegada.isBefore(cliente.getJanelaInicio())) {
                // Chegou cedo - esperar
                horaInicioServico = cliente.getJanelaInicio();
                tempoEspera = (int) Duration.between(horaChegada, horaInicioServico).toMinutes();
            } else if (horaChegada.isAfter(cliente.getJanelaFim())) {
                // Perdeu a janela!
                viavel = false;
                horaInicioServico = horaChegada;
                tempoEspera = 0;
            } else {
                // No horário
                horaInicioServico = horaChegada;
                tempoEspera = 0;
            }
            
            LocalDateTime horaSaida = horaInicioServico.plusMinutes(cliente.getTempoServico());
            
            boolean dentroJanela = !horaChegada.isBefore(cliente.getJanelaInicio()) && 
                                  !horaChegada.isAfter(cliente.getJanelaFim());
            String status = dentroJanela ? "✅" : "❌";
            
            String janela = cliente.getJanelaInicio().format(formatter) + "-" + 
                           cliente.getJanelaFim().format(formatter);
            
            InfoEntrega info = new InfoEntrega(
                clienteId, cliente.getEndereco(), horaChegada, janela,
                tempoEspera, horaInicioServico, horaSaida, status
            );
            
            rotaDetalhada.add(info);
            
            // Exibir informações
            System.out.printf("%s Entrega %d: %s - %s\n", status, i + 1, 
                            clienteId, cliente.getEndereco());
            System.out.println("   🚗 Viagem: " + tempoViagem + " min");
            System.out.println("   ⏰ Chegada: " + horaChegada.format(formatter));
            System.out.println("   🕐 Janela permitida: " + janela);
            
            if (tempoEspera > 0) {
                System.out.println("   ⏳ Espera: " + tempoEspera + " min");
            }
            
            System.out.printf("   📦 Serviço: %d min (%s - %s)\n", 
                            cliente.getTempoServico(),
                            horaInicioServico.format(formatter),
                            horaSaida.format(formatter));
            System.out.println();
            
            horaAtual = horaSaida;
            localAtual = clienteId;
        }
        
        // Retorno ao CD
        int tempoRetorno = matrizTempo.get(localAtual).get("CD");
        LocalDateTime horaRetorno = horaAtual.plusMinutes(tempoRetorno);
        
        System.out.println("🏁 Retorno ao CD: " + horaRetorno.format(formatter));
        
        long tempoTotal = Duration.between(horaInicio, horaRetorno).toMinutes();
        System.out.println("⏱️  Tempo total: " + tempoTotal + " minutos");
        System.out.println("=".repeat(70) + "\n");
        
        if (!viavel) {
            System.out.println("⚠️  ATENÇÃO: Algumas entregas estão fora da janela de tempo!");
        }
        
        return viavel;
    }
}
