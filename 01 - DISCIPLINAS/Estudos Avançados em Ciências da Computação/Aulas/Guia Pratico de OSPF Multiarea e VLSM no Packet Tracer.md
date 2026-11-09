---
tipo: aula
disciplina: Estudos Avançados em Ciências da Computação
professor: Paulo Sergio
tema: "OSPF Multiarea, VLSM, Calculo de Mascaras e Troubleshooting Cisco"
status: concluido
---

> [!NOTE] Navegacao no Cofre
> **Painel Geral:** [[00 - Dashboard]] | **Indice de Aulas:** [[Índice Geral de Aulas]]
> **Disciplina:** [[Estudos Avançados em Ciências da Computação]] | **Nucleo ENADE:** [[06 - Redes de Computadores e Sistemas Distribuidos (Do Basico ao Avancado)]]

# Guia Pratico de OSPF Multiarea e VLSM no Packet Tracer

> [!INFO] **Visao Geral do Modulo**
> Roteamento dinamico avancado em redes corporativas de grande escala exige segmentacao hierarquica para conter a propagacao de LSAs (Link-State Advertisements) e diminuir a frequencia de recalculo da arvore de menor caminho atraves do algoritmo de Dijkstra (SPF — Shortest Path First).

---

## 1. Topologia OSPF Multiarea e Tipos de Roteadores

```text
 [ Area 1 ] [ Area 0 (Backbone) ] [ Area 2 ]
 (Escritorio Remoto) (Nucleo Corporativo) (Data Center Interno)
 ┌───────────────────┐ ┌───────────────────┐ ┌───────────────────┐
 │ Internal Router │ │ Backbone Router │ │ Internal Router │
 └─────────┬─────────┘ └─────────┬─────────┘ └─────────┬─────────┘
 │ │ │
 └───────────────[ ABR 1 ]─────────┴─────────[ ABR 2 ]────────────┘
```

### Classificacao dos Roteadores:
1. **Internal Router (IR):** Possui todas as suas interfaces vinculadas a uma unica area.
2. **Backbone Router (BR):** Possui pelo menos uma interface vinculada a **Area 0 (Backbone)**.
3. **Area Border Router (ABR):** Roteador de fronteira com interfaces em multiplas areas (uma delas obrigatoriamente a Area 0). Responsavel por resumir rotas inter-area gerando LSAs de Tipo 3.
4. **Autonomous System Boundary Router (ASBR):** Roteador que conecta o dominio OSPF a sistemas externos (BGP, rotas estaticas redistribuidas ou RIP), gerando LSAs de Tipo 5 (External).

---

## 2. Tipos Fundamentais de LSAs (Link-State Advertisements)

- **LSA Tipo 1 (Router LSA):** Gerado por todo roteador para descrever seus proprios links diretos. Nao ultrapassa os limites da propria area.
- **LSA Tipo 2 (Network LSA):** Gerado pelo DR (Designated Router) em redes de multiacesso broadcast (Ethernet) para listar todos os roteadores conectados ao mesmo segmento. Nao sai da area.
- **LSA Tipo 3 (Summary LSA):** Gerado pelos ABRs para anunciar redes aprendidas em uma area para as demais areas do sistema autonomo.
- **LSA Tipo 4 (ASBR Summary LSA):** Gerado pelo ABR para anunciar onde esta localizado o ASBR.
- **LSA Tipo 5 (AS External LSA):** Gerado pelo ASBR para anunciar rotas para redes externas ao OSPF.

---

## 3. Sub-redes e Mascaras de Tamanho Variavel (VLSM / CIDR)

A regra de ouro do dimensionamento de sub-redes:
$$\text{Hosts Uteis} = 2^{(32 - \text{prefixo})} - 2$$

| Prefixo CIDR | Mascara Decimal | Total de IPs | Hosts Validos | Casos de Uso Tipicos |
| :---: | :--- | :---: | :---: | :--- |
| **/30** | 255.255.255.252 | 4 | **2** | Links ponto a ponto entre roteadores (WAN). |
| **/29** | 255.255.255.248 | 8 | **6** | Segmentos de DMZ pequenos ou redundancia HSRP/VRRP. |
| **/28** | 255.255.255.240 | 16 | **14** | Servidores internos dedicados. |
| **/27** | 255.255.255.224 | 32 | **30** | Departamentos pequenos (diretoria, RH). |
| **/26** | 255.255.255.192 | 64 | **62** | Laboratorios ou setores medios. |
| **/25** | 255.255.255.128 | 128 | **126** | Andares de escritorio corporativo. |
| **/24** | 255.255.255.0 | 256 | **254** | Redes de usuarios gerais e Wi-Fi corporativo. |
