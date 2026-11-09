---
tipo: guia-enade
disciplina: "Redes de Computadores e Sistemas Distribuidos"
nivel: "Do Basico ao Avancado ENADE"
data: 2026-11-09
autor: "ThSyr Cognitive Sentinel"
---

# 06 — Redes de Computadores e Sistemas Distribuidos (Do Basico ao Nivel ENADE)

> [!INFO] **Objetivo deste Guia**
> Redes de Computadores e a disciplina com maior indice de reprovacao e erros no ENADE de Computacao. Este guia dissecara os modelos OSI e TCP/IP, o calculo matematico infalivel de mascaras VLSM, protocolos de roteamento (OSPF/RIP), controle de fluxo TCP e a arquitetura de Hashing Consistente em sistemas distribuidos.

---

## CAMADA 1: O BASICO (MODELOS OSI VS. TCP/IP E ENCAPSULAMENTO)

A comunicacao em rede e organizada em camadas hierarquicas, onde cada camada oferece servicos para a camada superior e consome servicos da camada inferior.

```text
Modelo OSI (7 Camadas)             Modelo TCP/IP (4 Camadas)       PDU (Unidade de Dados)
┌────────────────────────┐         ┌────────────────────────┐      ┌────────────────────┐
│ 7. Aplicacao           │         │                        │      │                    │
│ 6. Apresentacao        │ ──────> │ 4. Aplicacao           │ ───> │ Dados / Mensagem   │
│ 5. Sessao              │         │                        │      │                    │
├────────────────────────┤         ├────────────────────────┤      ├────────────────────┤
│ 4. Transporte          │ ──────> │ 3. Transporte          │ ───> │ Segmento (TCP/UDP) │
├────────────────────────┤         ├────────────────────────┤      ├────────────────────┤
│ 3. Rede                │ ──────> │ 2. Internet (Rede)     │ ───> │ Pacote (IP)        │
├────────────────────────┤         ├────────────────────────┤      ├────────────────────┤
│ 2. Enlace de Dados     │ ──────> │ 1. Acesso a Rede       │ ───> │ Quadro (Frame MAC) │
├────────────────────────┤         │    (Enlace + Fisica)   │      ├────────────────────┤
│ 1. Fisica              │ ──────> │                        │ ───> │ Bits (Sinais)      │
└────────────────────────┘         └────────────────────────┘      └────────────────────┘
```

- **Encapsulamento:** Ao descer a pilha para transmissao, cada camada adiciona seu proprio cabecalho (*Header*). Ao receber o sinal na maquina de destino, o processo inverso (*Desencapsulamento*) remove os cabecalhos camada por camada.

---

## CAMADA 2: CAMADA DE ENLACE E SWITCHING (STP E VLANS)

- **Endereco MAC (Media Access Control):** Identificador fisico de 48 bits (6 bytes em hexadecimal) gravado na placa de rede (ex: `00:1A:2B:3C:4D:5E`).
- **Switches:** Equipamentos de Camada 2 que aprendem os enderecos MAC de cada porta lendo o cabecalho dos quadros e armazenando-os na tabela CAM (*Content Addressable Memory*).
- **VLAN (Virtual LAN — IEEE 802.1Q):** Segmenta uma rede fisica em multiplos dominios de broadcast isolados. Para trafegar multiplas VLANs em um unico cabo entre switches, utiliza-se uma porta de **Tronco (Trunk)**, que insere uma etiqueta de 4 bytes (*Tag 802.1Q*) no quadro.

### Spanning Tree Protocol (STP — IEEE 802.1D / RSTP 802.1w):
- **O Problema:** Conectar switches em anel para redundancia fisica cria **Loops de Camada 2**. Como o cabecalho Ethernet nao possui campo TTL (*Time to Live*), os quadros circulam infinitamente, gerando uma **Tempestade de Broadcast (Broadcast Storm)** que satura a largura de banda e trava os switches.
- **A Solucao:** O protocolo STP elege um **Root Bridge (Ponte Raiz)** baseado no menor *Bridge ID (Prioridade + MAC)* e coloca portas redundantes estrategicas em estado de **Bloqueio (Blocking/Discarding)**, transformando o grafo ciclico em uma arvore aciclica sem loops.

---

## CAMADA 3: CAMADA DE REDE (CALCULO DE SUB-REDES VLSM E ROTEAMENTO)

### Formula Matematica Infalivel de Sub-redes:
Em um endereco IPv4 de 32 bits com prefixo `/P`:
1. **Bits de Host:** $H = 32 - P$.
2. **Total de Enderecos IP no Bloco:** $N = 2^H$.
3. **Hosts Uteis (Dispositivos reais):** $\text{Validos} = 2^H - 2$ (o primeiro IP e o **Endereco de Rede** e o ultimo e o **Broadcast**).

#### Exemplo Classico do ENADE:
*"Uma empresa precisa alocar uma sub-rede para 100 computadores na rede `192.168.10.0`."*
- Quantos bits de host precisamos?
  - $2^6 = 64$ (insuficiente para 100 hosts).
  - $2^7 = 128 \implies 128 - 2 = 126$ hosts validos. **Atende com precisao!**
- Prefixo da mascara: $32 - 7 = \mathbf{/25}$.
- Mascara em decimal: `255.255.255.128`.
- Bloco: Rede `192.168.10.0/25`, Primeiro host `192.168.10.1`, Ultimo host `192.168.10.126`, Broadcast `192.168.10.127`.

### Protocolos de Roteamento Dinamico:

| Protocolo | Categoria | Algoritmo Central | Metrica de Decisao | Escala |
| :--- | :--- | :--- | :--- | :--- |
| **RIPv2** | Vetor de Distancia (IGP) | Bellman-Ford | **Contagem de Saltos (Hop Count)**. Limite maximo de 15 saltos (16 = infinito/inalcancavel). | Redes pequenas |
| **OSPF** | Estado de Enlace (IGP) | Dijkstra (SPF) | **Custo inversamente proporcional a largura de banda** ($10^8 / \text{Banda em bps}$). | Redes corporativas e Data Centers |
| **BGP** | Vetor de Caminho (EGP) | Best-Path Selection | Atributos de politicas (AS-Path, Local Preference, MED). | O protocolo que roteia a **Internet** |

---

## CAMADA 4: CAMADA DE TRANSPORTE (TCP VS. UDP)

### 4.1 TCP (Transmission Control Protocol) — Confiabilidade Estrita
- Orientado a conexao, confiavel, garante entrega na ordem exata e sem duplicacao.
- **Handshake de 3 Vias (Three-Way Handshake) para Abertura:**
  1. Cliente envia `SYN` (Sequence Number inicial $X$).
  2. Servidor responde `SYN-ACK` (Seq $Y$, Acknowledgment $X+1$).
  3. Cliente confirma `ACK` (Ack $Y+1$). Conexao aberta!
- **Encerramento de Conexao (Four-Way Handshake):** `FIN` -> `ACK` -> `FIN` -> `ACK`.
- **Controle de Fluxo:** Mecanismo de **Janela Deslizante (Sliding Window)**, onde o receptor avisa no campo *Window Size* quanto espaco de buffer ele ainda tem livre para evitar que o transmissor o afogue.
- **Controle de Congestionamento:** Prevencao de colapso da rede atraves de algoritmos como **Slow Start** (dobro da janela a cada RTT ate o limiar *ssthresh*), **Congestion Avoidance** (crescimento aditivo linear) e **Fast Retransmit/Fast Recovery** (ao receber 3 ACKs duplicados, retransmite o segmento sem esperar pelo timeout).

### 4.2 UDP (User Datagram Protocol) — Velocidade Sem Sobrecarga
- Nao-orientado a conexao, nao-confiavel, cabecalho diminuto de apenas **8 bytes** (contra 20 a 60 bytes do TCP).
- Nao faz handshake, nao faz retransmissao e nao garante ordem.
- Usado em aplicacoes onde a baixa latencia e mais importante do que a perda eventual de um pacote: DNS (porta 53), DHCP, streaming de video/voz ao vivo (VoIP) e jogos multiplayer.

---

## CAMADA 5: SISTEMAS DISTRIBUIDOS E HASHING CONSISTENTE

### 5.1 O Teorema CAP (Eric Brewer)
Em qualquer sistema de dados distribuido conectado por rede, e **impossivel garantir simultaneamente as tres propriedades**:
1. **C — Consistencia Estrita:** Toda leitura recebe a gravacao mais recente ou um erro.
2. **A — Disponibilidade (Availability):** Toda requisicao nao-bloqueante recebe uma resposta sem garantia de ser a mais recente.
3. **P — Tolerancia a Particao (Partition Tolerance):** O sistema continua funcionando mesmo se pacotes forem perdidos ou a rede cair entre os servidores.

Como redes reais invariavelmente sofrem particoes ($P$), os arquitetos sao obrigados a escolher entre **CP** (Consistencia sobre Disponibilidade, como Bancos SQL e HBase) ou **AP** (Disponibilidade sobre Consistencia estrita, adotando Consistencia Eventual, como Cassandra e DynamoDB).

### 5.2 Hashing Consistente (Consistent Hashing)
- Em caches distribuidos tradicionais, calcular `servidor = hash(chave) % N` e desastroso quando um servidor falha ou e adicionado: quase **todas as chaves mudam de no**, esvaziando o cache e gerando sobrecarga em cascata.
- **A Mecanica do Hashing Consistente:**
  1. Mapeia servidores e chaves de dados no mesmo **anel circular uniforme** ($0$ a $2^b - 1$).
  2. Cada chave e alocada no primeiro servidor encontrado no sentido horario.
  3. Ao adicionar ou remover um servidor, **apenas as chaves do no vizinho imediato sao movidas (em media $K/N$ chaves)**. O resto do cluster permanece 100% estavel.
  4. Utiliza **Nos Virtuais (Virtual Nodes)** para evitar desbalanceamento de carga (hotspots).

---

## CAMADA 6: ARMADILHAS CLASSICAS DO ENADE

1. **Broadcast em Roteadores vs. Switches:** Switches comuns propagam broadcasts para todas as portas da mesma VLAN. **Roteadores (Camada 3) bloqueiam e descartam broadcasts por padrao**, delimitando o dominio de broadcast.
2. **Mascara /30:** Muito cobrada para conexoes entre dois roteadores. Tem 4 enderecos: 1 de rede, 2 para as interfaces dos roteadores e 1 de broadcast.
3. **Métrica do RIP:** O RIP escolhe o caminho com menos saltos, mesmo que seja por uma linha discada de 64 kbps, ignorando um caminho com 2 saltos em fibra optica de 10 Gbps. O OSPF escolhe pela velocidade real da banda.
