---
tipo: aula
disciplina: Estudos Avançados em Ciências da Computação
data: 2026-08-12
professor: Paulo Sergio Granato
status: assistida
---

# 📖 Aula 2 — Switching Avançado: Voice VLAN, Trunking 802.1Q e EtherChannel (LACP/PAgP)

> [!info] 📌 Informações da Aula
> - **Disciplina:** Estudos Avançados em Ciências da Computação (Eletiva 8: Redes Corporativas)
> - **Professor:** Paulo Sergio Granato (Especialista em Redes de Computadores)
> - **Data:** 12/08/2026
> - **Foco Técnico:** Segmentação de Redes Corporativas, QoS com Voice VLAN, Troncos 802.1Q, Agregação de Links com **EtherChannel (LACP IEEE 802.3ad)**, Algoritmos de Balanceamento de Carga e Diagnóstico CLI Cisco.

---

## 📁 Materiais da Aula
- 📄 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Aula 2 - Switching Avançado.pdf|Apresentação em PDF (Aula 2 - Switching Avançado)]]
- 🎥 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Aula_2__Switching_Avançado.mp4|Vídeo da Aula Teórica & Prática (MP4)]]
- 📝 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Resolução - Exercício Prático Aula 2 - TechSolutions|Resolução Completa do Exercício Prático (TechSolutions)]]
- 🔌 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/TECHSOLUTIONS_AULA_2_CONCLUIDO.pkt|Laboratório Concluído Cisco Packet Tracer (PKT)]]
- 🤖 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Arquivos Auxiliares - Automação IA Packet Tracer/README|Guia e Skill: Automação do Packet Tracer com IA]]


---

## 🎯 Objetivos da Aula
1. Entender a evolução do switching corporativo da segmentação por porta à agregação de alta disponibilidade.
2. Dominar a anatomia de links de tronco **IEEE 802.1Q** e mitigar ataques de **VLAN Hopping** reconfigurando a **VLAN Nativa**.
3. Implementar **Voice VLAN** para garantias de QoS (latência < 150ms e jitter controlado) em ramais VoIP.
4. Compreender a limitação do **Spanning Tree Protocol (STP)** em desperdiçar banda redundante e adotar a solução de agregação lógica **EtherChannel**.
5. Configurar e verificar bundles **LACP (IEEE 802.3ad / 802.1AX)** em switches Cisco em modos `Active` e `Passive`.
6. Dominar o monitoramento e diagnóstico avançado no Cisco IOS (`show etherchannel summary`, `show lacp neighbor`).

---

## 🧠 1. Revisão e Aprofundamento: Segmentação Lógica com VLANs

A segmentação lógica por **VLANs (Virtual Local Area Networks)** divide uma infraestrutura física de comutação em múltiplos domínios de broadcast independentes.

```
       [ Switch Físico ]
        /      |      \
   (VLAN 10) (VLAN 20) (VLAN 30)
   Vendas     RH       Financeiro
```

### Principais Fundamentos:
- **Domínio de Colisão vs. Broadcast:** Switches modernos isolam domínios de colisão porta a porta. As VLANs expandem esse conceito isolando **domínios de broadcast** inteiros na Camada 2.
- **Desempenho & Eficiência:** Limita quadros de broadcast (ARP, DHCP, NetBIOS) ao grupo pertencente, evitando a degradação de performance por *Broadcast Storms*.
- **Isolamento de Segurança:** O tráfego de uma VLAN **nunca** alcança portas de outra VLAN sem passar por um dispositivo de roteamento de Camada 3 (Router ou Switch L3/MLS).

---

## 🔌 2. Trunking e o Padrão IEEE 802.1Q

Para transportar dados de múltiplas VLANs entre switches através de uma única conexão física, utilizamos um **Link de Tronco (Trunk Link)** multiplexado via **IEEE 802.1Q**.

### 🏷️ Anatomia do Quadro IEEE 802.1Q (Tagging)
O padrão insere um campo cabeçalho de **4 bytes** no quadro Ethernet padrão:
- **TPID (Tag Protocol Identifier):** Valor fixo `0x8100` indicando quadro com tag.
- **Priority / PCP (3 bits):** Marcação de CoS para QoS (Class of Service).
- **DEI (1 bit):** Drop Eligible Indicator.
- **VLAN ID / VID (12 bits):** Suporta até **4094 VLANs** válidas.

```
+-------------------+--------------------+-------------------+----------------+
| Dst/Src MAC (12B) | Tag 802.1Q (4B)    | EtherType (2B)    | Payload + FCS  |
+-------------------+--------------------+-------------------+----------------+
                    | TPID | CoS | VID   |
                    |0x8100| 0-7 | 1-4094|
```

### 🛡️ O Conceito de VLAN Nativa e Segurança
A **VLAN Nativa** é a VLAN cujo tráfego trafega **sem tag (untagged)** pelo trunk. Por padrão no Cisco IOS, a **VLAN 1** é a VLAN nativa e de gerenciamento.

> [!CAUTION] 🚨 Risco de Segurança: VLAN Hopping
> Atacantes podem explorar trunks com VLAN Nativa 1 e portas em modo *Dynamic Auto/Desirable* para realizar injeção de tags duplas (*Double Tagging*), acessando redes privadas sem passar pelo roteador.
> 
> **Boas Práticas de Hardening Cisco:**
> 1. Altere a VLAN nativa para uma VLAN dedicada, sem usuários e não utilizada (ex.: `VLAN 99` ou `VLAN 999`).
> 2. Remova a `VLAN 1` de todos os trunks (`switchport trunk allowed vlan remove 1`).
> 3. Desative a negociação automática DTP (`switchport nonegotiate`).

---

## 🎙️ 3. Voice VLAN (VLAN de Voz) & QoS em Telefonia IP

Telefones IP possuem um switch interno de 3 portas: uma porta de uplink conectada ao switch de acesso, uma porta interna para o hardware de voz e uma porta PC para o computador do usuário.

```
[ Switch de Acesso ] ──( Trunk / Access + Voice )──> [ Telefone IP ] ──( Untagged )──> [ PC do Usuário ]
                                                          │
                                                    ( Voice VLAN )
```

### Configuração de Porta Dupla
A porta de acesso do switch é configurada para aceitar tráfego de dados (sem tag) para a estação de trabalho e tráfego com tag 802.1Q referente à **VLAN de Voz**.

### Requisitos de Qualidade de Serviço (QoS)
- **Latência (Delay):** $< 150\text{ ms}$ fim a fim.
- **Jitter (Variação de Atraso):** $< 30\text{ ms}$.
- **Perda de Pacotes (Packet Loss):** $< 1\%$.
- **Marcação CoS/DSCP:** Marcação CoS 5 / DSCP **EF (Expedited Forwarding)** para prioridade estrita nas filas de saída do switch.

### Exemplo de Configuração Cisco CLI (Voice VLAN):
```cisco
SW-Acesso(config)# interface GigabitEthernet 0/10
SW-Acesso(config-if)# description Conexao PC + Telefone IP Cisco
SW-Acesso(config-if)# switchport mode access
SW-Acesso(config-if)# switchport access vlan 10
SW-Acesso(config-if)# switchport voice vlan 150
SW-Acesso(config-if)# spanning-tree portfast
```

---

## ⚡ 4. O Problema da Redundância e a Solução EtherChannel

### O Dilema do Spanning Tree Protocol (STP)
A redundância física de cabos entre switches é essencial para alta disponibilidade. Contudo, em redes de Camada 2, links redundantes formam loops físicos que causam *Broadcast Storms* e instabilidade na tabela MAC.
O **STP (IEEE 802.1D / 802.1w)** resolve os loops **bloqueando** logicamente os links redundantes, o que resulta em desperdício de banda cara.

```
      [ STP Padrão ]                       [ EtherChannel ]
   ( Link Bloqueado )                    ( Links Agregados )
SW-1 ────────────── SW-2             SW-1 ══════════════════ SW-2
  │      (X)         │                 ║   Port-Channel 1   ║
  └──────────────────┘                 ╚════════════════════╝
  Banda Reduzida / 1 Link              Banda Total Somada (8x)
```

### O que é o EtherChannel?
O **EtherChannel** (ou *Link Aggregation*) agrupa de **2 a 8 interfaces físicas paralelas** (FastEthernet, GigabitEthernet ou 10GigabitEthernet) em um único link lógico chamado **Port-Channel**.

### Principais Vantagens:
1. **Multiplicação de Banda:** Soma a largura de banda de todos os links físicos ativos (até $8 \times 1\text{ Gbps} = 8\text{ Gbps}$).
2. **Alta Disponibilidade e Resiliência:** Em caso de rompimento de um cabo físico, o tráfego é redistribuído instantaneamente entre os membros restantes sem recálculo do STP.
3. **Visibilidade Única para o STP:** O STP enxerga o `Port-Channel` como uma única interface lógica, mantendo todos os cabos físicos operacionais sem bloquear portas.

---

## ⚙️ 5. Protocolos de Agregação: LACP vs. PAgP vs. Estático

Existem três maneiras de formar bundles EtherChannel em switches Cisco:

| Característica | IEEE 802.3ad / 802.1AX (LACP) | Cisco PAgP | Modo Estático (`On`) |
| :--- | :--- | :--- | :--- |
| **Tipo de Padrão** | Padrão Aberto da Indústria | Proprietário Cisco | Sem Protocolo / Forçado |
| **Interoperabilidade** | Cisco, Juniper, HP, Aruba, Linux, etc. | Apenas equipamentos Cisco | Qualquer dispositivo |
| **Modos de Operação** | `Active` / `Passive` | `Desirable` / `Auto` | `On` |
| **Segurança & Detecção** | Altíssima (Troca contínua de PDUs) | Alta (Mensagens PAgP) | Nenhuma (Risco de Loops) |
| **Máximo de Links** | 16 (8 ativos + 8 hot-standby) | 8 ativos | 8 ativos |

### Modos LACP em Detalhe:
- **`Active` (Ativo):** Inicia ativamente a negociação enviando quadros LACP PDU pela interface.
- **`Passive` (Passivo):** Apenas responde a negociações LACP iniciadas pelo switch remoto.

```
Switch A (Active)   <─── LACP PDU ───>   Switch B (Active)   ===> 🟢 Port-Channel UP
Switch A (Active)   <─── LACP PDU ───>   Switch B (Passive)  ===> 🟢 Port-Channel UP
Switch A (Passive)  <─── Sem PDU  ───>   Switch B (Passive)  ===> 🔴 Port-Channel DOWN
```

> [!RECOMMENDATION] 💡 Recomendação em Produção
> Em ambientes corporativos de alta criticidade, utilize sempre **LACP em modo `Active`** em ambas as pontas. Evite o modo estático `On`, pois erros de conexão física podem causar loops devastadores sem que o switch identifique a falha.

---

## 💻 6. Guia Prático de Configuração e Comandos Cisco CLI

### ⚠️ Requisitos Obrigatórios de Homogeneidade
Para que as interfaces físicas entrem com sucesso no bundle `Port-Channel`, todas as portas membros devem possuir obrigatoriamente:
- Mesma velocidade e modo duplex (ex.: 1 Gbps Full-Duplex).
- Mesmo modo switchport (Trunk ou Access).
- Mesma VLAN Nativa e mesma lista de VLANs permitidas (`allowed vlan`).

---

### 📝 Script de Configuração Completo (Switch Core & Switch Acesso)

#### Passos no **SW-Core-1** (LACP Active):
```cisco
! 1. Selecionar o intervalo de portas físicas membros
SW-Core-1(config)# interface range GigabitEthernet 0/1 - 2
SW-Core-1(config-if-range)# description Bundle LACP para SW-Acesso-1
SW-Core-1(config-if-range)# channel-group 1 mode active
SW-Core-1(config-if-range)# exit

! 2. Configurar a interface lógica Port-Channel criada automaticamente
SW-Core-1(config)# interface port-channel 1
SW-Core-1(config-if)# description Trunk Logico Agregado LACP
SW-Core-1(config-if)# switchport mode trunk
SW-Core-1(config-if)# switchport trunk native vlan 99
SW-Core-1(config-if)# switchport trunk allowed vlan 10,20,30,99
SW-Core-1(config-if)# exit
```

#### Passos no **SW-Acesso-1** (LACP Active ou Passive):
```cisco
! Configuração espelhada no switch remoto
SW-Acesso-1(config)# interface range GigabitEthernet 0/1 - 2
SW-Acesso-1(config-if-range)# channel-group 1 mode active
SW-Acesso-1(config-if-range)# exit

SW-Acesso-1(config)# interface port-channel 1
SW-Acesso-1(config-if)# switchport mode trunk
SW-Acesso-1(config-if)# switchport trunk native vlan 99
SW-Acesso-1(config-if)# switchport trunk allowed vlan 10,20,30,99
SW-Acesso-1(config-if)# exit
```

---

### ⚖️ Algoritmos de Balanceamento de Carga (Load-Balancing Hash)
O EtherChannel não realiza balanceamento por pacotes (o que causaria desordenamento de pacotes TCP), mas sim **por fluxo de dados** via cálculo de Hash.

```cisco
! Configurar o método de balanceamento de carga no switch
SW-Core-1(config)# port-channel load-balance src-dst-ip
```

- **`src-mac` / `dst-mac`:** Adequado para tráfego em redes de Camada 2 puras.
- **`src-dst-ip`:** Recomendado para comunicação entre múltiplos clientes e servidores através de roteadores.
- **`src-dst-port`:** Ideal para tráfego concentrado entre poucos endereços IP com múltiplos serviços (HTTP, SSH, Banco de Dados).

---

## 🔍 7. Monitoramento, Diagnóstico e Comandos de Verificação

### 1️⃣ `show etherchannel summary`
É o comando principal de diagnóstico no Cisco IOS.

```
SW-Core-1# show etherchannel summary
Group  Port-channel  Protocol    Ports
------+-------------+-----------+-----------------------------------------------
1      Po1(SU)         LACP      Gi0/1(P)   Gi0/2(P)
```

> [!IMPORTANT] 📌 Significado das Flags Importantes:
> - **Flags de Port-Channel:**
>   - `S`: Layer 2 (Switchport).
>   - `U`: In use (Operacional / UP).
>   - `D`: Down (Desativado / Com erro).
> - **Flags de Portas Físicas:**
>   - `P`: **Bundled in port-channel** (Membro ativo operando com sucesso).
>   - `I`: **Standalone** (Porta inativa ou inconsistente, operando como link individual).
>   - `w`: Waiting to be aggregated.

---

### 2️⃣ `show lacp neighbor`
Exibe os detalhes do switch vizinho negociado via LACP.

```cisco
SW-Core-1# show lacp neighbor
Flags:  S - Device is requesting Slow PACDs 
        F - Device is requesting Fast PACDs
        A - Device is in Active mode       P - Device is in Passive mode

Channel group 1 neighbors determine:
Port      Flags   State    Partner ID         Partner Port
Gi0/1     SA      bndl     32768.aabb.cc00.0100  0x2
Gi0/2     SA      bndl     32768.aabb.cc00.0100  0x3
```

---

### 3️⃣ `show interfaces port-channel 1`
Permite visualizar a velocidade agregada e os contadores de quadros/erros do bundle.

```cisco
SW-Core-1# show interfaces port-channel 1
Port-channel1 is up, line protocol is up (connected)
  Hardware is EtherChannel, address is aabb.cc00.0100
  BW 2000000 Kbit/sec, DLY 10 usec, 
  Encapsulation ARPA, loopback not set
```
*(Demonstra banda lógica agregada de 2 Gbps referente a 2 links GigabitEthernet).*

---

## 🛡️ 8. Boas Práticas, Segurança e Resiliência em Camada 2

1. **PortFast + BPDU Guard em Portas de Borda:**
   Aplique em portas que conectam hosts finais. O `PortFast` coloca a porta imediatamente em *Forwarding* e o `BPDU Guard` desativa a porta (`err-disabled`) se um switch não autorizado for conectado.
   ```cisco
   SW-Acesso(config)# interface range FastEthernet 0/1 - 24
   SW-Acesso(config-if-range)# spanning-tree portfast
   SW-Acesso(config-if-range)# spanning-tree bpduguard enable
   ```
2. **Min-Links (Proteção Contra Degradação Silenciosa):**
   Exige um número mínimo de membros ativos no bundle. Se o número de links cair abaixo do limite especificado, o Port-Channel é desativado para forçar a rota de backup do STP ou HSRP.
3. **LACP 1:1 Redundancy (Hot-Standby):**
   Designa um membro ativo e um link reserva (*standby*). Caso o link ativo caia, o reserva entra em ação sem necessidade de recálculo de topologia.

---

## 🚀 9. Tendências Tecnológicas em Switching

- **Cisco SD-Access (Software-Defined Access):** Abstrai VLANs e EtherChannels tradicionais através de tecidos de superposição (*Overlay Fabrics* com LISP/VXLAN) gerenciados pelo controlador centralizado Cisco DNA Center.
- **NFV (Network Functions Virtualization):** Execução de funções de firewall, IPS e roteamento virtualizados diretamente no hardware de switches de distribuição.
- **Switching com Inteligência Artificial:** Algoritmos de aprendizado de máquina integrados para análise de telemetria em tempo real, predição de congestionamento e ajuste automático de QoS.

---

## 🧪 10. Roteiro do Laboratório Prático (Cisco Packet Tracer)

```
       +-----------------------+                    +-----------------------+
       |      SW-Core-1        |====================|      SW-Core-2        |
       |  (LACP Mode Active)   |  Po1 (Gi0/1-2)     |  (LACP Mode Active)   |
       +-----------------------+                    +-----------------------+
            ║                                            ║
            ║ Po2 (Gi0/3-4)                              ║ Po3 (Gi0/3-4)
            ║ LACP Active                                ║ LACP Active
            ▼                                            ▼
       +-----------------------+                    +-----------------------+
       |     SW-Acesso-1       |                    |     SW-Acesso-2       |
       | (LACP Mode Passive)   |                    | (LACP Mode Passive)   |
       +-----------------------+                    +-----------------------+
```

### Checklist do Aluno no Laboratório:
- [x] Criar as VLANs `10` (Vendas), `20` (RH), `30` (Financeiro) e `99` (Gerência/Nativa) em todos os switches.
- [x] Configurar os grupos EtherChannel `1`, `2` e `3` usando LACP (`channel-group mode active/passive`).
- [x] Configurar as interfaces `port-channel` como troncos 802.1Q com VLAN Nativa `99`.
- [x] Executar `show etherchannel summary` e validar a flag `(SU)` no Port-channel e `(P)` nas portas membros.
- [x] Simular a desconexão de um cabo físico do bundle e comprovar via teste contínuo (`ping -t`) que a comunicação não é interrompida.
