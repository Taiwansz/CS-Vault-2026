---
tipo: aula
disciplina: Estudos Avançados em Ciências da Computação
data: 2026-08-19
professor: Paulo Sergio Granato
status: assistida
tags:
  - redes
  - spanning-tree
  - stp
  - rstp
  - rapid-pvst
  - ospf
  - roteamento-dinamico
  - packet-tracer
---

# 📖 Aula 3 — Spanning Tree Protocol (STP, RSTP Rapid-PVST+) e Roteamento Dinâmico OSPF

> [!info] 📌 Informações da Aula
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Professor:** Paulo Sergio Granato
> - **Data:** 19/08/2026
> - **Tema:** Eliminação de Loops de Camada 2 com STP / RSTP (Rapid-PVST+), Eleição de Root Bridge, PortFast, BPDU Guard e Roteamento Dinâmico OSPF (Área 0)
> - **Status:** <span class="badge badge-success">🟢 Assistida e Documentada</span>

---

## 📁 Materiais e Resoluções da Aula
- 📄 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Aula 3 - STP-Avancado-Eliminando-Loops.pdf|Apresentação em PDF (Aula 3 - STP Avançado: Eliminando Loops)]]
- 🎥 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Aula 3 - STP-Avancado-Eliminando-Loops.mp4|Vídeo da Aula Teórica & Prática (MP4)]]
- 📄 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/EXERCÍCIO PRÁTICO - aula 3 (Professor).pdf|Enunciado Original em PDF (Exercício Prático - Aula 3)]]
- 📝 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-19 - Resolução - Exercício Prático Aula 3 - STP RSTP e OSPF|Resolução Completa do Exercício Prático da Aula 3]]
- 🤖 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Arquivos Auxiliares - Automação IA Packet Tracer/script_aula_3_stp_ospf.js|Script de Automação Packet Tracer (JS/MCP)]]
- 🧠 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Arquivos Auxiliares - Automação IA Packet Tracer/README|Guia do Protocolo MCP Packet Tracer]]

---

## 🎯 Objetivos de Aprendizagem
1. Compreender a anatomia e o perigo catastrófico de **Loops de Camada 2** (*Broadcast Storms* e *MAC Table Flapping*).
2. Dominar a evolução do protocolo Spanning Tree: do clássico **STP IEEE 802.1D** ao **RSTP IEEE 802.1w** e sua implementação proprietária **Rapid-PVST+** da Cisco.
3. Entender os critérios de eleição do **Root Bridge**, determinação de **Root Ports**, **Designated Ports** e portas de bloqueio (**Alternate/Discarding**).
4. Configurar mecanismos de otimização e proteção de borda: **PortFast** e **BPDU Guard**.
5. Projetar e implementar o roteamento dinâmico entre filiais utilizando **OSPFv2 (Open Shortest Path First) na Área 0 (Backbone)**.
6. Validar convergência e tempo de recuperação em cenários de falha física de links redundantes.

---

## 🧠 1. Fundamentos de Camada 2: O Desafio da Redundância e Loops

Em redes corporativas modernas, a redundância física é mandatória para evitar **Pontos Únicos de Falha (SPOF - Single Point of Failure)**. Conectar dois ou mais links entre switches garante que a rede continue operando se um cabo romper.

```
       [ SW1 ] ======================== [ SW2 ]
          │    (Links Físicos Duplos)      │
          │                                │
        [PC1]                            [PC2]
```

### Por que o Loop de Camada 2 é fatal?
Diferente dos pacotes IP de Camada 3 (que possuem o campo **TTL - Time to Live** decrescido a cada salto para descarte automático), o quadro Ethernet de Camada 2 **NÃO possui campo de TTL**.

Sem um mecanismo de controle:
1. **Tempestade de Broadcast (Broadcast Storm):** Um quadro de broadcast (como um *ARP Request* de "Quem tem o IP X?") enviado por um host é replicado por todas as portas do switch. O switch vizinho recebe nas duas portas e retransmite de volta. Em segundos, milhões de cópias circulam em loop, consumindo 100% da largura de banda e travando as CPUs dos switches.
2. **Flapping da Tabela de Endereços MAC:** O switch aprende portas com base no MAC de origem. Quando o mesmo quadro entra pela porta A e milissegundos depois pela porta B, a tabela CAM do switch fica oscilando loucamente, impedindo a comutação correta.
3. **Múltiplas Transmissões de Quadros Unicast:** O destinatário recebe múltiplas cópias desordenadas do mesmo frame, corrompendo fluxos de aplicação.

---

## 🌳 2. Spanning Tree Protocol (STP / RSTP Rapid-PVST+)

Para resolver o problema dos loops sem abrir mão da redundância física, foi criado o algoritmo **STA (Spanning Tree Algorithm)**, concebido pela cientista da computação Radia Perlman.

### 2.1 Comparativo: STP Clássico (802.1D) vs. RSTP (802.1w / Rapid-PVST+)

| Característica | STP Clássico (IEEE 802.1D) | RSTP (IEEE 802.1w / Rapid-PVST+) |
|---|---|---|
| **Tempo de Convergência** | **30 a 50 segundos** (Inaceitável para VoIP/Vídeo) | **Menos de 1 a 2 segundos** (Quase instantâneo) |
| **Mecanismo de Transição** | Timers passivos (Max Age, Forward Delay) | Negociação ativa (*Proposal/Agreement*) |
| **Estados das Portas** | *Blocking, Listening, Learning, Forwarding, Disabled* | *Discarding, Learning, Forwarding* |
| **Papéis das Portas** | *Root Port, Designated Port, Non-Designated* | *Root Port, Designated Port, Alternate Port, Backup Port* |
| **Padrão Cisco** | PVST+ (uma árvore por VLAN) | **Rapid-PVST+** (uma árvore rápida por VLAN) |

---

### 2.2 Critérios de Eleição do Spanning Tree (Passo a Passo)

O algoritmo STP elege a melhor árvore lógica através da troca de mensagens chamadas **BPDUs (Bridge Protocol Data Units)** a cada 2 segundos:

#### 1º Passo: Eleição do Root Bridge (O Líder da Rede)
O switch com o **menor Bridge ID (BID)** vence a eleição:
$$BID = \text{Prioridade (Múltiplo de 4096)} + \text{VLAN ID} + \text{MAC Address}$$
* Prioridade padrão Cisco: `32768`.
* Para forçar um switch a ser o líder principal (**Root Primary**):
  `spanning-tree vlan 1 priority 4096` (ou `spanning-tree vlan 1 root primary`).
* Para forçar o backup (**Root Secondary**):
  `spanning-tree vlan 1 priority 28672` (ou `spanning-tree vlan 1 root secondary`).

#### 2º Passo: Eleição da Root Port (RP) nos Switches Não-Root
Cada switch que não é o Root Bridge deve escolher **uma única porta** que possui o menor custo de caminho até o Root:
* Custo Gigabit Ethernet (1 Gbps) = `4`
* Custo Fast Ethernet (100 Mbps) = `19`

#### 3º Passo: Eleição das Designated Ports (DP)
Em cada enlace físico, a porta com o menor custo de caminho até o Root vira **Designated Port (Forwarding / Verde)**.
> 📌 **Regra de Ouro:** No Root Bridge, **TODAS as portas são Designated Ports (Verdes)**.

#### 4º Passo: Bloqueio da Porta Redundante (Alternate Port / Discarding)
A porta que perder nos critérios de desempate (mesmo custo $\rightarrow$ menor Bridge ID vizinho $\rightarrow$ menor Port ID vizinho) é colocada no papel de **Alternate Port** e estado **Discarding (Bolinha Laranja)**.
* No nosso laboratório, como `Gi0/1` tem menor ID que `Gi0/2`, a porta **`Gi0/2` do SW2 e do SW4** foi a escolhida para ser bloqueada!

---

## ⚡ 3. Recursos de Segurança e Otimização: PortFast e BPDU Guard

Em ambientes de produção, portas conectadas a computadores, servidores e impressoras (*Edge Ports*) não precisam esperar o tempo de negociação do STP.

### 3.1 PortFast
```ios
interface FastEthernet0/1
 spanning-tree portfast
```
* **O que faz:** Faz a porta transitar imediatamente para o estado *Forwarding* assim que o cabo é conectado, sem passar pelas etapas de negociação.
* **Benefício:** Evita atrasos na obtenção de IP via DHCP e acelera o boot dos PCs.

### 3.2 BPDU Guard
```ios
interface FastEthernet0/1
 spanning-tree bpduguard enable
```
* **O que faz:** Monitora a porta de borda. Se qualquer equipamento que gere BPDUs (como um switch clandestino conectado por um usuário malicioso) for plugado nessa porta, ela é desativada imediatamente no modo `err-disabled`.
* **Segurança:** Impede que um invasor tome o controle da topologia STP ou gere loops externos.

---

## 🌐 4. Roteamento Dinâmico com OSPFv2 (Área 0)

O **OSPF (Open Shortest Path First)** é um protocolo de gateway interior (IGP) do tipo **Link-State (Estado de Enlace)** padronizado pela IETF (RFC 2328).

```
       [ Rede A: 192.168.10.0/24 ]
                   │
                [ R1 ] (10.0.0.1/30)
                   │  (Área 0 - Backbone)
                [ R2 ] (10.0.0.2/30)
                   │
       [ Rede B: 192.168.20.0/24 ]
```

### Principais Características do OSPF:
1. **Algoritmo SPF (Shortest Path First):** Utiliza o algoritmo de Dijkstra para calcular a árvore de menor caminho até cada rede destino.
2. **Métrica Baseada em Custo:** Calculada pela fórmula $Custo = \frac{10^8}{\text{Largura de Banda em bps}}$. Links Gigabit têm menor custo que FastEthernet e Seriais.
3. **Convergência Rápida & Sem Loops L3:** Roteadores mantêm um mapa topológico completo idêntico em toda a área (**LSDB - Link State Database**).
4. **Hierarquia por Áreas:** Todas as redes comunicam-se através da **Área 0 (Backbone)**.

### Configuração Básica no Cisco IOS:
```ios
router ospf 1
 router-id 1.1.1.1
 network 192.168.10.0 0.0.0.255 area 0
 network 10.0.0.0 0.0.0.3 area 0
```
* `router-id`: Identificador único de 32 bits do roteador no processo OSPF.
* `network <ip_rede> <wildcard_mask> area <id>`: Anuncia a rede e ativa o protocolo na interface correspondente.

---

## 🧪 5. Comandos Úteis de Diagnóstico e Cheat-Sheet

| Objetivo | Comando Cisco IOS |
|---|---|
| Ver status do Spanning Tree da VLAN | `show spanning-tree` ou `show spanning-tree vlan 1` |
| Ver resumo de portas e papéis STP | `show spanning-tree summary` |
| Ver quem é o Root Bridge atual | `show spanning-tree root` |
| Ver detalhes de uma interface específica | `show spanning-tree interface GigabitEthernet 0/2` |
| Ver vizinhos OSPF e estado da adjacência | `show ip ospf neighbor` (deve estar em `FULL/BDR` ou `FULL/DR`) |
| Ver apenas rotas aprendidas via OSPF | `show ip route ospf` (indicadas pela letra `O`) |
| Ver parâmetros gerais do protocolo OSPF | `show ip protocols` |
| Salvar as configurações na NVRAM | `copy running-config startup-config` ou `write memory` |

---

## 🤖 6. Automação do Laboratório com IA e Servidor MCP

Nesta aula, a montagem e validação da topologia foi realizada via integração programática utilizando o **Packet Tracer MCP Server** (`packet-tracer-mcp`):
1. **Ponte HTTP Bridge (:54321):** Comunicação bidirecional autenticada com a extensão do Script Engine do Cisco Packet Tracer.
2. **Deploy Programático:** Criação automatizada de 16 dispositivos, 15 conexões cabeadas, endereçamento IP e injeção de parâmetros IOS.
3. **Auditoria em Tempo Real:** Verificação e validação de conformidade de portas, bloqueio STP e despachos de pings automatizados.
