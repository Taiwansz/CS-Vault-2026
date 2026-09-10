---
tipo: aula
disciplina: Estudos Avançados em Ciências da Computação
data: 2026-09-09
professor: Paulo Sergio Granato
status: assistida
tags:
  - redes
  - hsrp
  - fhrp
  - alta-disponibilidade
  - redundancia
  - failover
  - gateway-virtual
  - cisco-ios
  - packet-tracer
---

# 📖 Aula 5 — Redundância de Gateway e Alta Disponibilidade com HSRP (Hot Standby Router Protocol)

> [!info] 📌 Informações da Aula
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Professor:** Paulo Sergio Granato
> - **Data:** 09/09/2026
> - **Tema:** Protocolos de Redundância de Primeiro Salto (FHRP), Arquitetura e Operação do HSRP, Eleição de Active e Standby, Mecanismos de Preempção (*Preempt*) e Validação de Failover em Cenários Corporativos.
> - **Status:** <span class="badge badge-success">🟢 Assistida e Documentada</span>

---

## 📁 Materiais e Atividades Práticas da Aula 5

> [!example] 🔗 Recursos Disponíveis no Cofre
> - 📄 **Enunciado do Exercício do Professor (PDF):** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/EXERCÍCIO PRÁTICO - aula 5.pdf|EXERCÍCIO PRÁTICO - aula 5.pdf]]
> - 📝 **Resolução Detalhada do Exercício Prático (com 15 Questões):** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-09-09 - Resolução - Exercício Prático Aula 5 - HSRP e Alta Disponibilidade|Resolução Completa da Aula 5 (HSRP)]]
> - 🔌 **Laboratório Concluído Cisco Packet Tracer (PKT):** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/GRUPO_03_HSRP_ALTA_DISPONIBILIDADE.pkt|GRUPO_03_HSRP_ALTA_DISPONIBILIDADE.pkt]]
> - 🤖 **Script de Automação da Topologia (Script Engine / MCP):** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Arquivos Auxiliares - Automação IA Packet Tracer/script_aula_5_hsrp.js|script_aula_5_hsrp.js]]

---

## 1. O Problema do Gateway Único: Ponto Único de Falha (SPOF)

Em arquiteturas convencionais de redes locais (LAN), cada host é configurado estaticamente ou via DHCP com um único endereço de **Gateway Padrão (*Default Gateway*)**. 

Caso esse roteador sofra uma pane de hardware, desligamento, travamento de software ou falha de link de uplink:
* Todos os computadores da sub-rede continuam se comunicando entre si na camada 2 (via switch).
* **Nenhum host consegue enviar pacotes para fora da sua sub-rede local**, paralisando o acesso à Internet, servidores de filiais ou banco de dados em nuvem.
* Isso caracteriza um clássico **Single Point of Failure (SPOF)**.

A solução é utilizar uma tecnologia do grupo **FHRP (*First Hop Redundancy Protocols*)**, que permite colocar dois ou mais roteadores físicos atuando como se fossem um único roteador lógico.

---

## 2. A Família FHRP: HSRP, VRRP e GLBP

| Protocolo | Desenvolvedor / Padronização | Características Principais | Modelo Operacional |
|---|---|---|---|
| **HSRP** (*Hot Standby Router Protocol*) | Cisco (Proprietário) | Amplamente utilizado em equipamentos Cisco Catalyst e ISR. Cria um IP e MAC virtual único. | 1 Active, 1 Standby, demais Listeners |
| **VRRP** (*Virtual Router Redundancy Protocol*) | IETF (RFC 3768 / RFC 5798 - Aberto) | Padrão da indústria multivendor (Juniper, Huawei, Linux, Mikrotik). Permite usar o IP físico do Master como VIP. | 1 Master, demais Backups |
| **GLBP** (*Gateway Load Balancing Protocol*) | Cisco (Proprietário) | Além da redundância, fornece balanceamento de carga real ativo-ativo através de múltiplos MACs virtuais (AVF). | 1 AVG (Active Virtual Gateway), até 4 AVF |

---

## 3. Funcionamento Interno do HSRP

### 3.1. Gateway Virtual e MAC Virtual
No HSRP (Versão 1), os roteadores compartilham:
* **IP Virtual:** Configurado pelos administradores (ex: `192.168.10.1`).
* **MAC Address Virtual:** Formato padronizado: `0000.0c07.acXX`, onde `XX` é o número do grupo HSRP em hexadecimal.
  * *Exemplo:* Grupo HSRP 1 $\rightarrow$ MAC Virtual `0000.0c07.ac01`.

### 3.2. Temporizadores e Mensagens de Keepalive (Hello)
* **Hello Timer:** A cada **3 segundos**, o roteador Active envia pacotes *Hello* via multicast (`224.0.0.2`, porta UDP 1985).
* **Hold Timer:** **10 segundos** (aproximadamente $3 \times \text{Hello}$). Se o Standby passar 10 segundos sem ouvir o Hello do Active, ele assume que o Active falhou e promove-se a Active.

### 3.3. Máquina de Estados do HSRP
Um roteador percorre os seguintes estados ao iniciar o HSRP em uma interface:
1. **Initial:** Interface inativa ou configuração inicial.
2. **Learn:** O roteador ainda não conhece o IP virtual e aguarda pacotes de outros nós.
3. **Listen:** Escuta mensagens Hello, mas não é Active nem Standby.
4. **Speak:** Envia e recebe mensagens Hello periódicas, participando ativamente da eleição.
5. **Standby:** Candidato imediato a assumir o papel ativo caso o Active falhe. Envia Hellos.
6. **Active:** Roteador atualmente encarregado de encaminhar pacotes enviados ao IP/MAC virtual. Responde às requisições ARP para o IP virtual.

### 3.4. Eleição e Prioridade
* A prioridade varia de **0 a 255** (padrão de fábrica: **100**).
* O roteador com a **maior prioridade** vence a eleição para **Active**.
* Em caso de empate de prioridade, o critério de desempate é o **maior IP físico** na interface.

### 3.5. O Mecanismo de Preempção (*Preempt*)
* Por padrão na Cisco, a preempção vem **desabilitada**.
* Se um roteador principal com prioridade 110 cair e o backup de prioridade 90 assumir, quando o principal retornar ele **não reassume** o papel ativo a menos que tenha o comando **`standby [grupo] preempt`** ativado!
* Com `preempt`, o roteador de prioridade superior reassume seu posto assim que restabelece comunicação.

---

## 4. Síntese dos Comandos Cisco IOS (HSRP)

```cisco
! Configuração Básica na Interface LAN
interface GigabitEthernet0/0
 ip address 192.168.10.2 255.255.255.0
 no shutdown
 standby 1 ip 192.168.10.1
 standby 1 priority 110
 standby 1 preempt
! Ajuste de Temporizadores (Opcional - convergência rápida)
 standby 1 timers 1 3
```

### Comandos de Diagnóstico e Verificação:
```cisco
show standby brief       ! Tabela consolidada com Grupo, Prioridade, Estado, IPs e Preempt
show standby             ! Estatísticas completas, timers, contadores de transição de estado
debug standby events     ! Monitoramento em tempo real das eleições e mensagens Hello
debug standby packets    ! Inspeção detalhada de cada pacote de controle recebido/enviado
```

---

## 5. Relação com a Atividade Prática de Hoje
A prática de hoje colocou essa teoria em ação com 2 roteadores Cisco 2911 (`R1-PRINCIPAL` e `R2-BACKUP`), validando o failover com desligamento simulado da interface e verificação de continuidade do ping sem qualquer reconfiguração nos clientes.
Consulte a documentação completa em: [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-09-09 - Resolução - Exercício Prático Aula 5 - HSRP e Alta Disponibilidade|Resolução do Exercício Prático - Aula 5]].