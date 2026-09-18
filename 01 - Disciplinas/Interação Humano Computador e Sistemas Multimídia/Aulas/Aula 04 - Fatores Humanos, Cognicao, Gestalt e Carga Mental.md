---
disciplina: Interação Humano Computador e Sistemas Multimídia
professor: Guilherme P. Bueno
data: 2026-09-04
tags:
  - faculdade
  - ihc
  - cognicao
  - gestalt
  - carga-mental
  - revisao-p1
---

# Aula 04 — Fatores Humanos em Computação: Cognição, Gestalt e Carga Mental

> Base teórica e matemática de Fatores Humanos, Psicologia da Percepção e Engenharia de Telas ministrada pelo Prof. Guilherme P. Bueno.

---

## 1. O Mito do "Menos Cliques" e a Densidade Cognitiva

- **A Falácia da Eficiência Física:** A crença ingênua de que agrupar todos os 70 campos em uma única tela economiza tempo do usuário é um erro primário de engenharia de software.
- **Sistemas Legados (70+ campos):** Exposição massiva simultânea gera paralisia analítica, fadiga visual severa e elevação exponencial na taxa de erros.
- **Fluxos Modernos (Wizards em 3 a 5 Etapas):** A fragmentação da tarefa em etapas lógicas reduz a sobrecarga mental e acelera o tempo total de conclusão, mesmo exigindo mais cliques motores.
- *Conclusão de IHC:* **O esforço cognitivo para decodificar telas poluídas supera de longe o micro-custo motor de avançar etapas estruturadas.**

---

## 2. O Modelo do Processador Humano (MHP)
*Proposto por Stuart Card, Thomas Moran e Allen Newell (1983).*

O cérebro opera em 3 subsistemas sequenciais com latências mensuráveis:
1. **Subsistema Perceptivo:**
   - Sensores visuais (olhos) e auditivos (ouvidos).
   - Latência de processamento: **100 ms a 200 ms**.
2. **Subsistema Cognitivo:**
   - Memória de trabalho + memória de longo prazo.
   - Tempo de ciclo para tomada de decisão: **~70 ms por ciclo**.
3. **Subsistema Motor:**
   - Execução do movimento físico (dedos, mãos, clique, digitação).
   - Tempo de movimento físico: **~70 ms por ciclo**.

---

## 3. A Arquitetura da Memória Humana (O Gargalo Crítico)

| Camada | Equivalente Computacional | Duração / Retenção | Capacidade |
| :--- | :--- | :--- | :--- |
| **Memória Sensorial** | Buffer Transitório I/O | `< 500 ms` | Curtíssima, decai instantaneamente. |
| **Memória de Trabalho** | **RAM Biológica (Gargalo)** | **10 a 20 segundos** | **Ultra-limitada (5 a 9 itens)**. Altamente volátil. |
| **Memória de Longo Prazo** | Disco Rígido / Storage | Ilimitada / Permanente | Recuperação semântica indexada por esquemas. |

---

## 4. A Lei de Miller (1956) e Chunking
- **George Miller (1956):** A capacidade de processamento imediato do cérebro humano é restrita a **$7 \pm 2$ unidades discretas** de informação.
- Ultrapassar esse limiar força o descarte forçado de dados vitais da memória de trabalho, elevando exponencialmente a taxa de erro.
- **Chunking (Agrupamento Cognitivo):** Técnica de agrupar itens isolados em blocos conceituais dotados de significado:
  - *Sem Chunking:* `11987654321` (11 itens contínuos, estresse cognitivo, alta chance de erro).
  - *Com Chunking:* `(11) 98765-4321` (3 blocos compreensíveis: DDD, prefixo, sufixo).
  - *Aplicação:* Máscaras de CPF, blocos de 4 dígitos em cartões de crédito e agrupamento de campos em formulários.

---

## 5. Teoria da Carga Cognitiva (John Sweller)

$$\text{Carga Total} = \text{Carga Intrínseca} + \text{Carga Pertinente} + \text{Carga Estranha}$$

1. **Carga Intrínseca (Complexidade Natural):** O esforço mental inevitável exigido pela própria natureza da tarefa (ex: calcular um imposto ou configurar uma rede).
2. **Carga Pertinente (Esforço Útil):** Processamento benéfico dedicado à aprendizagem, assimilação de modelos conceituais e automatização de esquemas mentais.
3. **Carga Estranha (Esforço Inútil / Design Ruim):** Ruído desnecessário gerado por layouts caóticos, cores berrantes, falta de alinhamento e mensagens de erro indecifráveis.
- *Meta de IHC:* O cérebro possui capacidade fixa. Devemos **reduzir a carga estranha a zero** para que os recursos mentais fiquem disponíveis para o processamento pertinente e a resolução da tarefa intrínseca.

---

## 6. As Leis da Gestalt Aplicadas a Engenharia de Software

Fundamentadas por Max Wertheimer, Kurt Koffka e Wolfgang Köhler. *"O todo é diferente da soma de suas partes."*

1. **Lei da Proximidade:**
   - Elementos espacialmente próximos são agrupados automaticamente pelo cérebro como pertencentes à mesma unidade lógica.
   - *No Figma/CSS:* O espaçamento entre um label e seu input deve ser estritamente menor do que o espaçamento para o próximo bloco (`gap: 4px` entre label e input vs. `margin-bottom: 24px` entre campos).
2. **Lei da Região Comum (Stephen Palmer, 1992):**
   - Elementos contidos dentro de um limite visual fechado (borda, container, background diferente) são interpretados como uma categoria única, superando até a proximidade espacial.
   - *No Figma/CSS:* Cards com `padding`, `border` e `background` segmentando métricas em dashboards.
3. **Lei da Semelhança (Similarity):**
   - Elementos com atributos visuais compartilhados (cor, formato, tamanho) sinalizam a mesma função no sistema.
   - *Alerta de Usabilidade:* Evitar a **Falsa Afordância** (ex: estilizar um texto estático com azul sublinhado que induz o usuário a acreditar que se trata de um link clicável).
4. **Figura-Fundo:**
   - O cérebro distingue o plano focal do contexto circundante.
   - *Aplicação:* Modais e caixas de diálogo sobrepondo um overlay escuro semi-transparente (*backdrop* de 50% de opacidade) para focar a decisão.
5. **Continuidade:**
   - O olhar segue trajetórias contínuas e fluxos visuais previsíveis.
   - *Aplicação:* Carrosséis horizontais onde o último card fica parcialmente cortado na borda da tela, induzindo o gesto de rolagem (*swipe*).
6. **Fechamento:**
   - O cérebro preenche lacunas e completa mentalmente formas inacabadas.
   - *Aplicação:* Ícones minimalistas de contorno aberto e telas de *Skeleton Screens* durante o carregamento assíncrono de feeds.

---

## 7. Estudo de Caso: O Painel Hospitalar
- **Interface Original Caótica:** Dados vitais de pacientes distribuídos sem hierarquia visual nem agrupamento por Gestalt. Tempo médio de diagnóstico sob estresse: **14 segundos**.
- **Refatoração com Gestalt (Cards + Proximidade):** Uso de Região Comum para delimitar cada leito de UTI e espaçamentos consistentes entre batimento, saturação e pressão. Tempo de diagnóstico reduzido para **1,8 segundo** (queda de 87% no tempo de resposta).
