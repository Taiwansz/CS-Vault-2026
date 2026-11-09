---
tipo: indice
data: 2026-11-09
operador: Matheus Sousa dos Santos
---

# Indice Geral de Aulas & Anotacoes (2026.2)

> [!NOTE] Navegacao Geral
> **Central do Cofre:** [[00 - Dashboard]] | **Guia de Convencoes:** [[00 - Guia do Cofre (Vault Guide)]] | **Hub ENADE:** [[INDEX_ESTUDOS_ENADE]]

---

## 1. Visao Consolidada por Dataview

```dataview
TABLE 
  disciplina AS "Disciplina",
  professor AS "Professor",
  data AS "Data",
  status AS "Status"
FROM "01 - Disciplinas"
WHERE tipo = "aula" OR tipo = "atividade" OR tipo = "trabalho"
SORT data DESC
```

---

## 2. Mapa Estruturado de Conexoes (Grafo Completo de Aulas)

### [[Banco de Dados e Aplicações]] (2 registros)
> Nucleo ENADE Vinculado: [[05 - Banco de Dados, SQL Avancado e Modelagem (Do Basico ao Avancado)]]
- [[TEMPLATE - Documento de Análise de Requisitos]]
- [[template documentacao]]

### [[Compiladores]] (7 registros)
> Nucleo ENADE Vinculado: [[03 - Teoria da Computacao, Automatos e Compiladores (Do Basico ao Avancado)]]
- [[2026-08-03 - Aula Inaugural - Introdução e Ementa]]
- [[2026-10-05 - Metodologia de Problemas, Compiladores e Engenharia de Bootstrap]]
- [[2026-10-26 - LLVM, Representacao Intermediaria e WebAssembly]]
- [[Guia Completo de Analise Lexica e Automatos Finitos]]
- [[Guia de Analise Semantica e Geracao de Codigo Intermediario 3AC]]
- [[Guia de Analise Sintatica LL(1) e LR(1) com Tabelas de Parsing]]
- [[Guia de Estudos - Bootstrapping, AST e Seguranca de Compiladores]]

### [[Computação Gráfica - Modelagem 3D]] (1 registros)
> Nucleo ENADE Vinculado: [[01 - Algoritmos, Estruturas de Dados e Paradigmas (Do Basico ao Avancado)]]
- [[Pipeline Grafico e Shading na Pratica]]

### [[Estudos Avançados em Ciências da Computação]] (18 registros)
> Nucleo ENADE Vinculado: [[06 - Redes de Computadores e Sistemas Distribuidos (Do Basico ao Avancado)]]
- [[Topologia_TechSolutions_Switching.excalidraw]]
- [[SKILL]]
- [[Guia Pratico de OSPF Multiarea e VLSM no Packet Tracer]]
- [[2026-08-05 - Aula Inaugural e Aula 1]]
- [[2026-08-12 - Aula 2 - Switching Avançado]]
- [[2026-08-12 - Resolução - Exercício Prático Aula 2 - TechSolutions]]
- [[TRABALHO_PRATICO_EXERCICIO_AULA_2]]
- [[2026-08-19 - Aula 3 - Spanning Tree Protocol (STP, RSTP) e OSPF]]
- [[2026-08-19 - Resolução - Exercício Prático Aula 3 - STP RSTP e OSPF]]
- [[2026-09-09 - Aula 5 - Redundância de Gateway e Alta Disponibilidade com HSRP]]
- [[2026-09-09 - Guia Passo a Passo - Captura de Evidências e Prints do HSRP]]
- [[2026-09-09 - Resolução - Exercício Prático Aula 5 - HSRP e Alta Disponibilidade]]
- [[Calculadora Interativa - Sub-redes e VLSM]]
- [[Guia Mestre - Sub-redes e IPv4 do Zero Absoluto]]
- [[Guia Rápido - Comandos Cisco IOS Switching e LACP]]
- [[Simulador Interativo - Modelo OSI e Encapsulamento]]
- [[Terminal Interativo - Cisco IOS Catalyst 3560]]
- [[Árvore de Decisão - Troubleshooting de Redes L2 e L3]]

### [[Interação Humano Computador e Sistemas Multimídia]] (13 registros)
> Nucleo ENADE Vinculado: [[08 - Formacao Geral, Etica, LGPD e Sociedade Digital (Do Basico ao Avancado)]]
- [[07.08]]
- [[2026-08-07 - Atividade IHC - Analise de Interfaces (UniEduK vs Apple)]]
- [[2026-09-18 - Atividade IHC - Redesenho de Interface e Diagnostico de Usabilidade (Figma)]]
- [[2026-10-09 - Atividade IHC - Fundamentos de Engenharia de Software vs IHC, Norman e Nielsen]]
- [[Aula 01 - Introducao a IHC e Sistemas Multimidia]]
- [[Aula 03 - Prototipacao e Engenharia de Telas com Figma]]
- [[Aula 04 - Fatores Humanos, Cognicao, Gestalt e Carga Mental]]
- [[Guia das 10 Heuristicas de Usabilidade de Nielsen e Avaliacao Heuristica]]
- [[Guia de Acessibilidade Digital WCAG 2.2 e Design Inclusivo]]
- [[Classroom]]
- [[Conteúdo PIR]]
- [[Notas]]
- [[Provas]]

### [[Projeto Prático Integrado - Desenvolvimento de Jogos Digitais]] (3 registros)
> Nucleo ENADE Vinculado: [[07 - Engenharia de Software, SOLID e Padroes de Projeto (Do Basico ao Avancado)]]
- [[Documento de Game Design (GDD)]]
- [[Kanban - PPI Jogos]]
- [[Materiais]]

### [[Segurança da Informação]] (2 registros)
> Nucleo ENADE Vinculado: [[08 - Formacao Geral, Etica, LGPD e Sociedade Digital (Do Basico ao Avancado)]]
- [[Criptografia Moderna AES RSA e Curvas Elipticas]]
- [[OWASP Top 10 e Modelagem de Ameacas STRIDE]]

### [[Trabalho de Conclusão de Curso II]] (8 registros)
> Nucleo ENADE Vinculado: [[08 - Formacao Geral, Etica, LGPD e Sociedade Digital (Do Basico ao Avancado)]]
- [[1º Registro de Acompanhamento - 2026-09-19]]
- [[2026-09-14 - Orientacao Luyz - Roadmap e Problematizacao]]
- [[Ata - Reunião Exemplo]]
- [[Divisao_Detalhada_Tarefas_TCC]]
- [[battle_ia_report]]
- [[semantic_report]]
- [[Bibliografia Oficial Auditada (21 Referências)]]
- [[Fichamento - Exemplo]]

### [[Técnicas Avançadas de Jogos Digitais]] (2 registros)
> Nucleo ENADE Vinculado: [[01 - Algoritmos, Estruturas de Dados e Paradigmas (Do Basico ao Avancado)]]
- [[Arquitetura ECS vs OOP em Game Engines]]
- [[Shaders PBR e Otimizacao de Renderizacao em Tempo Real]]

### [[Tópicos Especiais II]] (14 registros)
> Nucleo ENADE Vinculado: [[01 - Algoritmos, Estruturas de Dados e Paradigmas (Do Basico ao Avancado)]]
- [[2026-08-04 - Aula Inaugural - POO e Diagramas]]
- [[2026-08-25 - Sistema de Lista de Tarefas em Java]]
- [[2026-09-01 - Automação Industrial e Polimorfismo]]
- [[2026-09-15 - Gestao de Evento de Lutadores]]
- [[2026-09-15 - Persistência em Arquivos e Gestão de Eventos]]
- [[2026-09-22 - Tratamento de Excecoes em Java]]
- [[2026-09-29 - Banco de Dados Relacional e SQL]]
- [[2026-10-06 - Modelagem Dimensional, Star Schema e Telemetria Operacional ANAC]]
- [[2026-10-20 - Integracao Java e Banco de Dados com JDBC]]
- [[2026-10-27 - ArenaPlay Sports Gestao e Scout - CRUD Full JDBC e SQL]]
- [[Atividade 01 - Conta Bancaria em Java]]
- [[Atividade 02 - Controle de Estoque e Classes Abstratas]]
- [[Atividade 03 - Simulador de Batalha em Javaland]]
- [[GUIA - Tratamento de Excecoes em Java]]
