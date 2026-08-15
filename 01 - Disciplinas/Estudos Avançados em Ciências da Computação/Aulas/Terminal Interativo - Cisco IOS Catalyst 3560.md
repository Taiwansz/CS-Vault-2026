---
tipo: ferramenta-interativa
disciplina: Estudos Avançados em Ciências da Computação
tags:
  - cisco
  - terminal
  - simulador
  - ios
  - switching
  - comandos
---

# 💻 Terminal Interativo: Cisco IOS (Catalyst 3560)

> [!info] 🕹️ Simulador de Console Cisco IOS
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Atividade:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Resolução - Exercício Prático Aula 2 - TechSolutions|Resolução Exercício Aula 2 (TechSolutions)]]
> - **Guia de Comandos:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/Guia Rápido - Comandos Cisco IOS Switching e LACP|Guia Rápido de Comandos Cisco IOS]]
> - **Arquivo HTML Fonte:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Terminal_Cisco_IOS.html|Terminal_Cisco_IOS.html]]

---

## 🖥️ Console de Simulação

<iframe src="../Materiais/Terminal_Cisco_IOS.html" width="100%" height="650px" style="border: 1px solid #30363d; border-radius: 8px; box-shadow: 0 4px 16px rgba(0,0,0,0.4);" frameborder="0"></iframe>

---

### 📋 Comandos Suportados no Simulador:
- `show vlan brief` — Exibe tabela de VLANs 10, 20, 30, 40 e 99.
- `show etherchannel summary` — Exibe o status do bundle LACP Port-Channel 1 (`Po1(SU) Gi0/1(P) Gi0/2(P)`).
- `show interfaces trunk` — Exibe troncos 802.1Q e a VLAN nativa 99.
- `show lacp neighbor` — Detalha os parâmetros LACP trocados entre os switches de core.
- `ping 192.168.10.12` — Testa ping na mesma VLAN 10 (Retorna 100% sucesso).
- `ping 192.168.20.11` — Testa ping inter-VLAN (Retorna 100% perda - bloqueio esperado em L2).
- `show ip interface brief`, `show mac address-table`, `show run`, `help`, `clear`.
