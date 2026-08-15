---
tipo: guia-estudo
disciplina: Estudos Avançados em Ciências da Computação
tags:
  - cisco
  - ios
  - switching
  - vlan
  - trunk
  - etherchannel
  - lacp
  - cheat-sheet
  - comandos
---

# ⚡ Guia Rápido de Comandos: Cisco IOS (Switching & EtherChannel)

> [!info] 📌 Material de Consulta e Revisão para Provas
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Aula de Referência:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Aula 2 - Switching Avançado|Aula 2 — Switching Avançado]]
> - **Atividade Prática:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Resolução - Exercício Prático Aula 2 - TechSolutions|Resolução Exercício Aula 2 (TechSolutions)]]

---

## 1. 🏷️ Criação e Gerenciamento de VLANs

```ios
enable
configure terminal

! Criar VLAN com nome
vlan 10
 name ADMINISTRATIVO
exit

vlan 20
 name FINANCEIRO
exit

vlan 99
 name NATIVA
exit

! Comandos de Verificação
show vlan brief
show vlan id 10
```

---

## 2. 🔌 Portas de Acesso (Access Ports) e Voice VLAN

```ios
! Porta de Acesso Comum (Dados)
interface FastEthernet0/2
 switchport mode access
 switchport access vlan 10
 no shutdown
exit

! Porta Híbrida: Dados + Voice VLAN para Telefonia IP (Cisco 7960)
interface FastEthernet0/5
 switchport mode access
 switchport access vlan 10
 switchport voice vlan 40
 no shutdown
exit
```

---

## 3. 🌐 Enlaces de Tronco (Trunk 802.1Q)

```ios
interface GigabitEthernet0/1
 ! Em switches L3 (ex: 3560), defina o encapsulamento antes do modo trunk
 switchport trunk encapsulation dot1q
 switchport mode trunk
 switchport trunk native vlan 99
 switchport trunk allowed vlan 10,20,30,40,99
 no shutdown
exit

! Comandos de Verificação
show interfaces trunk
show interfaces GigabitEthernet0/1 switchport
```

---

## 4. 🚀 Agregação de Links: EtherChannel com LACP (IEEE 802.3ad)

### Modos LACP:
- `active`: Inicia ativamente a negociação enviando LACPDUs.
- `passive`: Apenas responde a LACPDUs recebidos.
- `on`: Força o agrupamento sem protocolo de negociação.

### Configuração no Switch 1 (LACP Ativo):
```ios
interface Port-channel 1
 switchport trunk encapsulation dot1q
 switchport mode trunk
 switchport trunk native vlan 99
 switchport trunk allowed vlan 10,20,30,40,99
 no shutdown
exit

interface range GigabitEthernet0/1 - 2
 switchport trunk encapsulation dot1q
 switchport mode trunk
 switchport trunk native vlan 99
 switchport trunk allowed vlan 10,20,30,40,99
 channel-group 1 mode active
 no shutdown
exit
```

### Configuração no Switch 2 (LACP Passivo):
```ios
interface Port-channel 1
 switchport trunk encapsulation dot1q
 switchport mode trunk
 switchport trunk native vlan 99
 switchport trunk allowed vlan 10,20,30,40,99
 no shutdown
exit

interface range GigabitEthernet0/1 - 2
 switchport trunk encapsulation dot1q
 switchport mode trunk
 switchport trunk native vlan 99
 switchport trunk allowed vlan 10,20,30,40,99
 channel-group 1 mode passive
 no shutdown
exit
```

---

## 5. 🔍 Tabela de Decodificação de Flags (`show etherchannel summary`)

| Flag do Port-Channel | Significado | Estado Esperado |
|---|---|---|
| **`S`** | Layer 2 (Camada 2) | ✅ Correto para switches de acesso/core L2 |
| **`R`** | Layer 3 (Roteado com IP) | ✅ Correto para uplinks de roteamento L3 |
| **`U`** | In Use (Em uso / Operacional) | ✅ Canal funcionando perfeitamente |
| **`D`** | Down (Inoperante) | 🚨 Erro de link ou portas desligadas |

| Flag da Porta Física | Significado | Diagnóstico |
|---|---|---|
| **`(P)`** | In port-channel | ✅ Porta física ativa e transmitindo tráfego no bundle |
| **`(s)`** | Suspended | 🚨 Inconsistência de parâmetros (VLAN nativa, speed ou duplex diferente) |
| **`(I)`** | Stand-alone / Individual | ⚠️ Porta não conseguiu negociar LACP (vizinho passivo ou sem LACP) |
| **`(D)`** | Down | 🔌 Cabo desconectado ou porta em shutdown |

---

## 6. 🛠️ Comandos Essenciais de Diagnóstico e Validação

```ios
! Visão geral rápida do Port-Channel
show etherchannel summary

! Detalhes da negociação com o switch parceiro
show lacp neighbor

! Identificador do sistema LACP (Prioridade + MAC)
show lacp sys-id

! Status e contadores da interface lógica
show interfaces Port-channel 1

! Tabela de endereços MAC aprendidos
show mac address-table dynamic
```
