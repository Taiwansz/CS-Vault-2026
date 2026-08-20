---
tipo: trabalho
disciplina: Estudos Avançados em Ciências da Computação
data: 2026-08-19
alunos:
  - nome: Matheus Sousa dos Santos
    ra: "52319400"
  - nome: Felipe Guarnieri Pinete
    ra: "52319337"
  - nome: Guilherme Gustavo Weber
    ra: "52420339"
status: concluido
tags:
  - redes
  - spanning-tree
  - stp
  - rstp
  - rapid-pvst
  - ospf
  - roteamento-dinamico
  - packet-tracer
  - automacao-mcp
---

# 📝 Resolução do Exercício Prático – Aula 3 (STP/RSTP + OSPF)

> [!info] 📌 Metadados da Atividade
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Aula de Referência:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-19 - Aula 3 - Spanning Tree Protocol (STP, RSTP) e OSPF|Aula 3 — Spanning Tree (STP/RSTP) e Roteamento Dinâmico OSPF]]
> - **Integrantes do Grupo:**
>   - 👤 **Matheus Sousa dos Santos** | **RA:** `52319400`
>   - 👤 **Felipe Guarnieri Pinete** | **RA:** `52319337`
>   - 👤 **Guilherme Gustavo Weber** | **RA:** `52420339`
> - **Tema:** Eliminação de Loops L2 com RSTP (Rapid-PVST+), Eleição de Root Bridge, PortFast, BPDU Guard, e Interligação WAN via OSPF Área 0.
> - **Status:** <span class="badge badge-success">🟢 Concluído, Auditado e Validado</span>

---

## 📁 Anexos e Arquivos do Laboratório

> [!example] 🔗 Arquivos Disponíveis no Cofre
> - 📄 **Apresentação em PDF:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Aula 3 - STP-Avancado-Eliminando-Loops.pdf|Aula 3 - STP-Avancado-Eliminando-Loops.pdf]]
> - 🎥 **Vídeo da Aula Teórica & Prática:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Aula 3 - STP-Avancado-Eliminando-Loops.mp4|Aula 3 - STP-Avancado-Eliminando-Loops.mp4]]
> - 📄 **Enunciado Original do Professor:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/EXERCÍCIO PRÁTICO - aula 3 (Professor).pdf|EXERCÍCIO PRÁTICO - aula 3 (Professor).pdf]]
> - 📖 **Nota Teórica Completa da Aula 3:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-19 - Aula 3 - Spanning Tree Protocol (STP, RSTP) e OSPF|Aula 3 — Teoria e Prática (STP/RSTP + OSPF)]]
> - 🤖 **Script de Automação JS (Script Engine / MCP):** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Arquivos Auxiliares - Automação IA Packet Tracer/script_aula_3_stp_ospf.js|script_aula_3_stp_ospf.js]]
> - 🧠 **Guia e Protocolo MCP Packet Tracer:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Arquivos Auxiliares - Automação IA Packet Tracer/README|Guia MCP Packet Tracer]]

---

## 1. Visão Geral da Topologia e Planejamento

O objetivo deste laboratório foi projetar, implantar e validar uma infraestrutura de rede corporativa dividida em duas filiais interconectadas via enlace WAN de longa distância:
1. **Rede A (Administrativo):** Sub-rede `192.168.10.0/24`, com 5 computadores (`PC1` a `PC5`), comutada por dois switches 2960 (`SW1` e `SW2`) com links redundantes em gigabit e protegida por **RSTP** (com `SW1` como Root Primary).
2. **Rede B (Operacional):** Sub-rede `192.168.20.0/24`, com 5 computadores (`PC6` a `PC10`), comutada por dois switches 2960 (`SW3` e `SW4`) com links redundantes em gigabit e protegida por **RSTP** (com `SW3` como Root Primary).
3. **Núcleo de Roteamento WAN:** Dois roteadores Cisco 2911 (`R1` e `R2`) interligados por um link ponto a ponto `/30` (`10.0.0.0/30`), executando o protocolo de roteamento dinâmico **OSPF (Process ID 1, Área 0 Backbone)**.

---

## 2. Diagrama Arquitetural da Rede

```
                         REDE A - ADMINISTRATIVO (192.168.10.0/24)
   [PC1]  [PC2]  [PC3]                                        [PC4]  [PC5]
   .11    .12    .13                                          .14    .15
     │      │      │                                            │      │
     └──────┼──────┘                                            └──────┘
            │ (Fa0/1..3)                                               │ (Fa0/1..2)
         [SW1] ================================================ [SW2]
     (Root Primary)          Links Redundantes Gi0/1 e Gi0/2       (Root Secundário)
     Prioridade: 4096                                              Prioridade: 28672
            │ (Fa0/24)                                             [Gi0/2 Bloqueada]
            │
          [R1] (LAN: 192.168.10.1 / Gi0/0)
            │
            │  LINK WAN PONTO A PONTO (10.0.0.0/30) - OSPF ÁREA 0
            │  R1 (Gi0/1: 10.0.0.1/30) <---> R2 (Gi0/1: 10.0.0.2/30)
            │
          [R2] (LAN: 192.168.20.1 / Gi0/0)
            │
            │ (Fa0/24)
         [SW3] ================================================ [SW4]
     (Root Primary)          Links Redundantes Gi0/1 e Gi0/2       (Root Secundário)
     Prioridade: 4096                                              Prioridade: 28672
            │ (Fa0/1..3)                                           [Gi0/2 Bloqueada]
     ┌──────┼──────┐                                            ┌──────┘
     │      │      │                                            │      │
   [PC6]  [PC7]  [PC8]                                        [PC9]  [PC10]
   .11    .12    .13                                          .14    .15
                         REDE B - OPERACIONAL (192.168.20.0/24)
```

---

## 3. Tabela Completa de Endereçamento e Dimensionamento

| Segmento | Dispositivo | Interface Fisiológica | Endereço IP / Máscara | Gateway Padrão | Função no Cenário |
|---|---|---|---|---|---|
| **Rede A** | **R1** | `GigabitEthernet0/0` | `192.168.10.1/24` | - | Default Gateway da Rede A |
| | **R1** | `GigabitEthernet0/1` | `10.0.0.1/30` | - | Link WAN Ponto a Ponto (R1 $\leftrightarrow$ R2) |
| | **SW1** | `VLAN 1` / `Fa0/24` | - | - | Switch Distribuição / **Root Primary (Pri: 4096)** |
| | **SW2** | `VLAN 1` / `Gi0/1,2` | - | - | Switch Acesso / **Root Secondary (Pri: 28672)** |
| | **PC1** | `FastEthernet0` | `192.168.10.11/24` | `192.168.10.1` | Host Admin (SW1: Fa0/1) |
| | **PC2** | `FastEthernet0` | `192.168.10.12/24` | `192.168.10.1` | Host Admin (SW1: Fa0/2) |
| | **PC3** | `FastEthernet0` | `192.168.10.13/24` | `192.168.10.1` | Host Admin (SW1: Fa0/3) |
| | **PC4** | `FastEthernet0` | `192.168.10.14/24` | `192.168.10.1` | Host Admin (SW2: Fa0/1) |
| | **PC5** | `FastEthernet0` | `192.168.10.15/24` | `192.168.10.1` | Host Admin (SW2: Fa0/2) |
| **Rede B** | **R2** | `GigabitEthernet0/0` | `192.168.20.1/24` | - | Default Gateway da Rede B |
| | **R2** | `GigabitEthernet0/1` | `10.0.0.2/30` | - | Link WAN Ponto a Ponto (R2 $\leftrightarrow$ R1) |
| | **SW3** | `VLAN 1` / `Fa0/24` | - | - | Switch Distribuição / **Root Primary (Pri: 4096)** |
| | **SW4** | `VLAN 1` / `Gi0/1,2` | - | - | Switch Acesso / **Root Secondary (Pri: 28672)** |
| | **PC6** | `FastEthernet0` | `192.168.20.11/24` | `192.168.20.1` | Host Operacional (SW3: Fa0/1) |
| | **PC7** | `FastEthernet0` | `192.168.20.12/24` | `192.168.20.1` | Host Operacional (SW3: Fa0/2) |
| | **PC8** | `FastEthernet0` | `192.168.20.13/24` | `192.168.20.1` | Host Operacional (SW3: Fa0/3) |
| | **PC9** | `FastEthernet0` | `192.168.20.14/24` | `192.168.20.1` | Host Operacional (SW4: Fa0/1) |
| | **PC10** | `FastEthernet0` | `192.168.20.15/24` | `192.168.20.1` | Host Operacional (SW4: Fa0/2) |

---

## 4. Fundamentos Teóricos: Como as Tecnologias Funcionam

### 4.1 O Problema do Loop em Camada 2
Em redes locais Ethernet, quando existem enlaces redundantes entre switches (como os dois cabos interligando `SW1-SW2` e `SW3-SW4`), cria-se um **loop físico**. Diferente do cabeçalho IP na Camada 3 (que possui o campo **TTL - Time to Live** para descartar pacotes infinitos), o cabeçalho do quadro Ethernet (Camada 2) **não possui TTL**.
* **Consequência sem STP:** Um único pacote de broadcast (ex: ARP Request ou DHCP Discover) seria replicado indefinidamente em círculos entre os switches, gerando:
  1. **Broadcast Storm (Tempestade de Broadcast):** Consumo de 100% da banda e da CPU dos switches em frações de segundo.
  2. **Instabilidade na Tabela de MAC (MAC Table Flapping):** O switch recebe o mesmo endereço MAC de origem por portas diferentes a cada milissegundo, corrompendo a tabela CAM.
  3. **Múltiplas Cópias de Quadros:** Hosts recebem cópias duplicadas do mesmo pacote.

### 4.2 Spanning Tree Protocol (STP / RSTP Rapid-PVST+)
Para permitir a redundância física sem sofrer loops lógicos, o algoritmo **STA (Spanning Tree Algorithm)** cria uma topologia em árvore livre de loops:
1. **Eleição do Root Bridge:** O switch com o menor **Bridge ID** (formado por `Prioridade + MAC Address`) é eleito líder da rede. Forçamos `SW1` e `SW3` com **Prioridade 4096** (`root primary`) e `SW2` e `SW4` com **Prioridade 28672** (`root secondary`).
2. **Definição dos Papéis das Portas:**
   - **Root Port (RP):** A porta com menor custo de caminho até o Root Bridge no switch não-root (no SW2/SW4 é a `Gi0/1`). Estado: **Forwarding (Verde)**.
   - **Designated Port (DP):** A melhor porta de cada segmento para encaminhar tráfego. No Root Bridge (`SW1` e `SW3`), **todas as portas são Designated**. Estado: **Forwarding (Verde)**.
   - **Alternate / Backup Port (AP/BP):** A porta redundante que perdeu o desempate de menor Port ID. No `SW2` e no `SW4`, a porta `Gi0/2` é colocada em estado **Discarding/Blocking (Bolinha Laranja)**.
3. **Por que Rapid-PVST+ (IEEE 802.1w)?**
   - O STP tradicional (802.1D) leva de **30 a 50 segundos** para convergir após uma falha (passando por Listening e Learning).
   - O **RSTP (Rapid-PVST+)** introduz o mecanismo de negociação ativa *Proposal/Agreement*, reduzindo a convergência para **menos de 1 a 2 segundos**, sem interrupção perceptível aos usuários.

### 4.3 PortFast e BPDU Guard (Segurança e Agilidade na Borda)
* **`spanning-tree portfast`:** Configurado exclusivamente nas portas conectadas aos computadores (`PC1` a `PC10`). Faz a porta pular os estados de transição e entrar imediatamente em **Forwarding** (evitando timeouts de DHCP e inicialização rápida do host).
* **`spanning-tree bpduguard enable`:** Proteção essencial. Se um usuário acidentalmente conectar outro switch ou roteador em uma porta de PC com PortFast, a porta receberá um pacote BPDU e será desativada imediatamente no modo `err-disabled`, blindando a rede contra ataques de invasão de topologia STP.
* **Regra de Ouro:** PortFast **NUNCA** deve ser ativado em portas entre switches (`Gi0/1`, `Gi0/2`) ou uplinks com roteadores (`Fa0/24`).

### 4.4 Roteamento Dinâmico OSPF (Open Shortest Path First)
* Protocolo de roteamento do tipo **Link-State (Estado de Enlace)** baseado no algoritmo **SPF de Dijkstra**.
* Opera no **Process ID 1** e pertence à **Área 0 (Backbone)**.
* **Formação de Adjacência:** `R1` e `R2` trocam pacotes *Hello* a cada 10 segundos pelo link ponto a ponto `10.0.0.0/30`. Ao estabelecerem adjacência *FULL*, compartilham suas **LSAs (Link State Advertisements)**.
* O OSPF converte a métrica de custo baseada em largura de banda ($Cost = \frac{10^8}{Bandwidth}$) e constrói a tabela de rotas para que qualquer PC da Rede A alcance qualquer PC da Rede B de forma totalmente dinâmica e tolerante a falhas.

---

## 5. Configurações IOS Aplicadas

### 5.1 Switches da Rede A (`SW1` e `SW2`)

```ios
! ==========================================
! SW1 - Root Bridge Primário da Rede A
! ==========================================
enable
configure terminal
hostname SW1
spanning-tree mode rapid-pvst
spanning-tree vlan 1 priority 4096
!
! Portas de acesso dos computadores (PortFast + BPDU Guard)
interface range FastEthernet0/1 - 3
 description Conexao-PCs-PC1-PC3
 spanning-tree portfast
 spanning-tree bpduguard enable
 exit
!
! Uplinks e Links Redundantes (PortFast desativado)
interface range GigabitEthernet0/1 - 2
 description Link-Redundante-SW2
 no spanning-tree portfast
 exit
interface FastEthernet0/24
 description Uplink-Router-R1
 no spanning-tree portfast
end
write memory
```

```ios
! ==========================================
! SW2 - Root Secundário da Rede A (Bloqueio em Gi0/2)
! ==========================================
enable
configure terminal
hostname SW2
spanning-tree mode rapid-pvst
spanning-tree vlan 1 priority 28672
!
! Portas de acesso dos computadores
interface range FastEthernet0/1 - 2
 description Conexao-PCs-PC4-PC5
 spanning-tree portfast
 spanning-tree bpduguard enable
 exit
!
! Links Redundantes
interface range GigabitEthernet0/1 - 2
 description Link-Redundante-SW1
 no spanning-tree portfast
end
write memory
```

---

### 5.2 Switches da Rede B (`SW3` e `SW4`)

```ios
! ==========================================
! SW3 - Root Bridge Primário da Rede B
! ==========================================
enable
configure terminal
hostname SW3
spanning-tree mode rapid-pvst
spanning-tree vlan 1 priority 4096
!
! Portas de acesso dos computadores
interface range FastEthernet0/1 - 3
 description Conexao-PCs-PC6-PC8
 spanning-tree portfast
 spanning-tree bpduguard enable
 exit
!
! Uplinks e Links Redundantes
interface range GigabitEthernet0/1 - 2
 description Link-Redundante-SW4
 no spanning-tree portfast
 exit
interface FastEthernet0/24
 description Uplink-Router-R2
 no spanning-tree portfast
end
write memory
```

```ios
! ==========================================
! SW4 - Root Secundário da Rede B (Bloqueio em Gi0/2)
! ==========================================
enable
configure terminal
hostname SW4
spanning-tree mode rapid-pvst
spanning-tree vlan 1 priority 28672
!
! Portas de acesso dos computadores
interface range FastEthernet0/1 - 2
 description Conexao-PCs-PC9-PC10
 spanning-tree portfast
 spanning-tree bpduguard enable
 exit
!
! Links Redundantes
interface range GigabitEthernet0/1 - 2
 description Link-Redundante-SW3
 no spanning-tree portfast
end
write memory
```

---

### 5.3 Roteadores Centrais (`R1` e `R2`)

```ios
! ==========================================
! R1 - Roteador Central (Rede A / WAN)
! ==========================================
enable
configure terminal
hostname R1
!
interface GigabitEthernet0/0
 description Gateway-Rede-A-Administrativo
 ip address 192.168.10.1 255.255.255.0
 no shutdown
 exit
!
interface GigabitEthernet0/1
 description Link-WAN-to-R2
 ip address 10.0.0.1 255.255.255.252
 no shutdown
 exit
!
router ospf 1
 router-id 1.1.1.1
 network 192.168.10.0 0.0.0.255 area 0
 network 10.0.0.0 0.0.0.3 area 0
end
write memory
```

```ios
! ==========================================
! R2 - Roteador Central (Rede B / WAN)
! ==========================================
enable
configure terminal
hostname R2
!
interface GigabitEthernet0/0
 description Gateway-Rede-B-Operacional
 ip address 192.168.20.1 255.255.255.0
 no shutdown
 exit
!
interface GigabitEthernet0/1
 description Link-WAN-to-R1
 ip address 10.0.0.2 255.255.255.252
 no shutdown
 exit
!
router ospf 1
 router-id 2.2.2.2
 network 192.168.20.0 0.0.0.255 area 0
 network 10.0.0.0 0.0.0.3 area 0
end
write memory
```

---

## 6. Validações e Testes Finais

### 6.1 Bateria de Testes de Conectividade (Pings)

| Origem | Destino | Tipo de Rota | Status do Ping | Análise do Caminho |
|---|---|---|---|---|
| `PC1` (`192.168.10.11`) | `PC5` (`192.168.10.15`) | Intra-Rede A (L2) | **🟢 Sucesso (100%)** | `PC1` $\rightarrow$ `SW1` $\rightarrow$ `Gi0/1` $\rightarrow$ `SW2` $\rightarrow$ `PC5` |
| `PC1` (`192.168.10.11`) | `PC6` (`192.168.20.11`) | Inter-Redes (L3 OSPF) | **🟢 Sucesso (100%)** | `PC1` $\rightarrow$ `SW1` $\rightarrow$ `R1` $\rightarrow$ `WAN` $\rightarrow$ `R2` $\rightarrow$ `SW3` $\rightarrow$ `PC6` |
| `PC1` (`192.168.10.11`) | `PC10` (`192.168.20.15`) | Inter-Redes Extremo | **🟢 Sucesso (100%)** | `PC1` $\rightarrow$ `SW1` $\rightarrow$ `R1` $\rightarrow$ `R2` $\rightarrow$ `SW3` $\rightarrow$ `SW4` $\rightarrow$ `PC10` |
| `PC3` (`192.168.10.13`) | `PC8` (`192.168.20.13`) | Inter-Redes (L3 OSPF) | **🟢 Sucesso (100%)** | `PC3` $\rightarrow$ `SW1` $\rightarrow$ `R1` $\rightarrow$ `R2` $\rightarrow$ `SW3` $\rightarrow$ `PC8` |
| `PC5` (`192.168.10.15`) | `PC10` (`192.168.20.15`) | Inter-Redes Cruzado | **🟢 Sucesso (100%)** | `PC5` $\rightarrow$ `SW2` $\rightarrow$ `SW1` $\rightarrow$ `R1` $\rightarrow$ `R2` $\rightarrow$ `SW3` $\rightarrow$ `SW4` $\rightarrow$ `PC10` |

### 6.2 Teste de Falha e Failover do Spanning Tree (RSTP)
1. **Cenário Inicial:** O link principal `Gi0/1` entre `SW1` e `SW2` estava ativo (*Forwarding*) e o link `Gi0/2` estava bloqueado (*Discarding - bolinha laranja no SW2*).
2. **Execução da Falha:** Desconectou-se propositalmente o cabo `Gi0/1` entre `SW1` e `SW2` durante um ping contínuo (`ping -t 192.168.10.15`).
3. **Resultado e Observações:**
   * A porta `Gi0/2` do `SW2` detectou a perda de BPDUs no link ativo e transitou imediatamente de *Discarding* para *Forwarding* (**ficou verde**).
   * **Tempo de Convergência:** O restabelecimento ocorreu em **menos de 1,5 segundos**, com perda de apenas 1 pacote ICMP, comprovando a eficácia e agilidade do **Rapid-PVST+** frente ao STP legado de 50 segundos.

---

## 7. Comandos de Diagnóstico e Auditoria no Cisco IOS

```text
! 1. Verificar o estado do Spanning Tree no Switch:
SW1# show spanning-tree
SW1# show spanning-tree summary

! 2. Verificar detalhes da porta bloqueada no SW2:
SW2# show spanning-tree interface GigabitEthernet 0/2

! 3. Verificar a vizinhança OSPF nos Roteadores:
R1# show ip ospf neighbor

! 4. Verificar a tabela de rotas aprendidas via OSPF:
R1# show ip route ospf
R1# show ip protocols
```
