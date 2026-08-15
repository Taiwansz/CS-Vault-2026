---
tipo: trabalho
disciplina: Estudos Avançados em Ciências da Computação
data: 2026-08-12
data-entrega: 2026-08-14
aluno: Matheus Sousa dos Santos
ra: "52319400"
status: concluido
tags:
  - redes
  - switching
  - vlan
  - voice-vlan
  - trunk
  - etherchannel
  - lacp
  - packet-tracer
  - techsolutions
---

# 📝 Resolução do Exercício Prático – Aula 2 (TechSolutions)

> [!info] 📌 Metadados da Atividade
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Aula de Referência:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Aula 2 - Switching Avançado|Aula 2 — Switching Avançado]]
> - **Aluno:** Matheus Sousa dos Santos | **RA:** `52319400`
> - **Tema:** Segmentação por VLANs, Voice VLAN, Trunks 802.1Q e EtherChannel LACP
> - **Status:** <span class="badge badge-success">🟢 Concluído e Validado</span>

---

## 📁 Anexos e Arquivos do Laboratório

> [!example] 🔗 Arquivos Disponíveis
> - 📄 **Enunciado Original do Professor:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/EXERCÍCIO PRÁTICO - aula 2 (Professor).pdf|EXERCÍCIO PRÁTICO - aula 2 (Professor).pdf]]
> - 📄 **Respostas Finais do Aluno (PDF):** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/RESPOSTAS_EXERCICIO_AULA_2 (Matheus Santos).pdf|RESPOSTAS_EXERCICIO_AULA_2 (Matheus Santos).pdf]]
> - 📝 **Arquivo Editável (DOCX):** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/RESPOSTAS_EXERCICIO_AULA_2.docx|RESPOSTAS_EXERCICIO_AULA_2.docx]]
> - 🔌 **Topologia no Cisco Packet Tracer (.PKT):** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/TECHSOLUTIONS_AULA_2_CONCLUIDO.pkt|TECHSOLUTIONS_AULA_2_CONCLUIDO.pkt]]
> - 🎨 **Diagrama no Excalidraw:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Topologia_TechSolutions_Switching.excalidraw|Topologia_TechSolutions_Switching.excalidraw]]
> - 🤖 **Guia de Automação com IA:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Arquivos Auxiliares - Automação IA Packet Tracer/README|Guia e Skill MCP Packet Tracer]]

---

## 1. Resumo da Topologia e Planejamento

A rede da **TechSolutions** foi implementada com 4 switches Cisco Catalyst 3560-24PS (L3/L2), 6 computadores e 2 telefones IP:

### 1.1 Tabela de VLANs
| VLAN | Nome | Sub-rede IPv4 | Finalidade |
|---|---|---|---|
| **10** | `ADMINISTRATIVO` | `192.168.10.0/24` | Computadores administrativos |
| **20** | `FINANCEIRO` | `192.168.20.0/24` | Computadores do setor financeiro |
| **30** | `TI` | `192.168.30.0/24` | Equipe de Tecnologia da Informação |
| **40** | `VOZ` | Voice VLAN | Telefones IP Cisco 7960 |
| **99** | `NATIVA` | Sem IP | VLAN nativa de segurança para os trunks |

### 1.2 Mapeamento de Portas e Endereçamento
- `PC-ADM-01` (SW-ACCESS-1 / Fa0/2 - VLAN 10): `192.168.10.11/24` | GW: `192.168.10.1`
- `PC-FIN-01` (SW-ACCESS-1 / Fa0/3 - VLAN 20): `192.168.20.11/24` | GW: `192.168.20.1`
- `PC-TI-01`  (SW-ACCESS-1 / Fa0/4 - VLAN 30): `192.168.30.11/24` | GW: `192.168.30.1`
- `IP-PHONE-01` (SW-ACCESS-1 / Fa0/5 - Access 10 + Voice 40)
- `PC-ADM-02` (SW-ACCESS-2 / Fa0/2 - VLAN 10): `192.168.10.12/24` | GW: `192.168.10.1`
- `PC-FIN-02` (SW-ACCESS-2 / Fa0/3 - VLAN 20): `192.168.20.12/24` | GW: `192.168.20.1`
- `PC-TI-02`  (SW-ACCESS-2 / Fa0/4 - VLAN 30): `192.168.30.12/24` | GW: `192.168.30.1`
- `IP-PHONE-02` (SW-ACCESS-2 / Fa0/5 - Access 10 + Voice 40)

### 1.3 EtherChannel LACP (Core)
- **Port-Channel 1**: Agrega as interfaces `GigabitEthernet0/1` e `GigabitEthernet0/2`.
- **SW-CORE-1**: `channel-group 1 mode active`
- **SW-CORE-2**: `channel-group 1 mode passive`
- **Trunk 802.1Q**: `switchport trunk encapsulation dot1q`, `switchport mode trunk`, `native vlan 99`, `allowed vlan 10,20,30,40,99`.

---

## 2. Respostas às Questões do Laboratório

### 13. Testes com PING
- **Teste A (`PC-ADM-01` $
ightarrow$ `PC-ADM-02` / `192.168.10.12`):** Funciona (0% perda). Mesma VLAN 10 comunicando em Camada 2 através do trunk e EtherChannel.
- **Teste B (`PC-FIN-01` $
ightarrow$ `PC-FIN-02` / `192.168.20.12`):** Funciona (0% perda). Mesma VLAN 20.
- **Teste C (`PC-TI-01` $
ightarrow$ `PC-TI-02` / `192.168.30.12`):** Funciona (0% perda). Mesma VLAN 30.
- **Teste D (`PC-ADM-01` $
ightarrow$ `PC-FIN-01` / `192.168.20.11`):** 100% perda (esperado). Hosts em sub-redes e domínios de broadcast distintos sem roteamento inter-VLAN configurado.

### 14. Teste de Alta Disponibilidade (Desconexão de 1 Cabo no EtherChannel)
- **a) A comunicação foi interrompida permanentemente?** Não, o tráfego continuou pelo link remanescente.
- **b) O Port-Channel continuou funcionando?** Sim, permaneceu no estado UP.
- **c) Quantas interfaces permaneceram ativas?** 1 interface física.
- **d) O que aconteceu com a largura de banda disponível?** Reduziu de 2 Gbps para 1 Gbps.
- **e) Por que o STP não bloqueou o enlace?** O STP enxerga o canal lógico (`Port-channel 1`) como uma única porta e não bloqueia membros físicos individuais do bundle.

### 15. Teste de Recuperação
- Ao reconectar o cabo físico, o protocolo LACP negocia via LACPDUs e reintegra a porta automaticamente ao bundle (marcada com flag `P` no `show etherchannel summary`).

### 16. Desafio de Troubleshooting
- Quando há inconsistência de configuração em uma porta do bundle, o switch suspende a porta individual (flag `s` ou `I`), mantendo as demais ativas. A correção consiste em igualar os parâmetros de trunk, native VLAN e velocidade.

### 17. Questões para Análise Técnica
1. **Organização por VLANs:** Reduz domínios de broadcast, aumenta segurança e segmenta o tráfego por departamento.
2. **Função do Trunk:** Transportar dados de múltiplas VLANs em um único cabo usando cabeçalho 802.1Q.
3. **VLAN 1 não recomendada como nativa:** Mitiga ataques de VLAN Hopping (Double Tagging) e separa tráfego de controle padrão de fábrica.
4. **Finalidade da Voice VLAN:** Isolar pacotes de telefonia IP e viabilizar políticas de QoS (baixa latência e prioridade).
5. **Problema de múltiplos links sem controle:** Loops de Camada 2, broadcast storms e corrupção da tabela MAC.
6. **Links independentes vs EtherChannel:** Links independentes são bloqueados pelo STP (50% de banda perdida). No EtherChannel, os links somam banda e operam juntos com failover transparente.
7. **Função do LACP:** Negociação e monitoramento automático da integridade do bundle (IEEE 802.3ad).
8. **LACP Active vs Passive:** Active inicia ativamente o envio de LACPDUs; Passive apenas escuta e responde.
9. **Ambos os lados Passive:** O EtherChannel não é formado por falta de iniciativa na negociação.
10. **Vantagem em caso de falha:** Redirecionamento quase instantâneo do tráfego sem recálculo de Spanning Tree.
11. **Consistência obrigatória:** Parâmetros divergentes causam suspensão da porta para evitar assimetria e loops.
12. **Por que VLANs diferentes não se comunicam:** Porque cada VLAN é uma rede IP independente e exige roteamento de Camada 3 (roteador ou SVI em switch L3).

### 18. Desafio Final (Ocorrência das 14h30)
- **Diagnóstico:** O Port-Channel 1 permaneceu UP com 1 membro ativo (1 Gbps) e 1 membro down. A rede não perdeu conectividade, apenas capacidade agregada. Não há necessidade de parada emergencial; a manutenção física do cabo/porta pode ser realizada em janela programada.
