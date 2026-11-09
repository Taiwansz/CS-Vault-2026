---
tipo: dashboard
semestre: 2026.2
turma: N13208A
operador: Matheus Sousa dos Santos
---

# Painel Academico & Central de Comando — 2026.2

> [!INFO] **Centro Universitario Max Planck (UniMAX / UniEduK)**  
> **Bacharelado em Ciencia da Computacao** | **Turma:** N13208A | **Semestre:** 2026.2  
> **Focos Principais:** TCC II (Compressao de Prompts e Benchmarks LLM) & Estudos Avancados ENADE 2026

---

## 1. Navegacao Rapida (Bento Cards)

<div class="bento-grid">

<div class="bento-card">
<h3>Central do TCC II</h3>
<p>Monografia, experimentos de compressao de prompt, graficos e WebTCC.<br>
<a href="01 - Disciplinas/Trabalho de Conclusão de Curso II/Trabalho de Conclusão de Curso II.md">Acessar Hub do TCC II -></a></p>
</div>

<div class="bento-card">
<h3>Cofre de Estudos ENADE 2026</h3>
<p>Simulados 01 a 04, analises de questoes, discursivas e matriz do INEP.<br>
<a href="05 - ESTUDOS ENADE/INDEX_ESTUDOS_ENADE.md">Acessar Hub ENADE -></a></p>
</div>

<div class="bento-card">
<h3>Projeto Pratico de Jogos (PPI)</h3>
<p>Documento de Game Design (GDD), mecanicas e prototipagem.<br>
<a href="01 - Disciplinas/Projeto Prático Integrado - Desenvolvimento de Jogos Digitais/Projeto Prático Integrado - Desenvolvimento de Jogos Digitais.md">Acessar PPI Jogos -></a></p>
</div>

<div class="bento-card">
<h3>Controle de Frequencia & Horarios</h3>
<p>Faltometro do semestre e mapa de presenca semanal.<br>
<a href="03 - Calendário/Controle de Frequência.md">Abrir Faltometro -></a> | <a href="03 - Calendário/Quadro de Horários.md">Quadro de Horarios -></a></p>
</div>

</div>

---

## 2. Prazos e Marcos Criticos do TCC II (WebTCC 2026.2)

| Periodo / Data | Marco / Entrega | Responsavel | Status |
| :--- | :--- | :--- | :--- |
| **01/09 a 19/09** | 1º Registro Obrigatorio de Orientacao | Alunos / Orientador | Concluido |
| **01/10 a 19/10** | 2º Registro Obrigatorio de Orientacao | Alunos / Orientador | Concluido |
| **03/11 a 09/11** | Insercao do Trabalho para Banca Virtual (PDF sem nome) | Alunos | Entregue |
| **23/11 a 27/11** | Simposio de TCCs (SIMTCC) | Alunos / Banca | Preparacao de Banca |
| **24/11 a 27/11** | Insercao da Versao Final Pos-Banca (PDF com nome) | Alunos | Aguardando Banca |

---

## 3. Disciplinas do Semestre Ativo (Grade 2026.2)

- [[01 - Disciplinas/Compiladores/Compiladores|Compiladores (Segunda-feira)]] — Prof. Daniel
- [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Topicos Especiais II - Java POO e JDBC (Terca-feira)]] — Prof. Guilherme Weber
- [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avancados em CC - Redes Corporativas e Cisco (Quarta-feira)]] — Prof. Paulo Sergio
- [[01 - Disciplinas/Trabalho de Conclusão de Curso II/Trabalho de Conclusão de Curso II|Trabalho de Conclusao de Curso II (Quarta-feira)]] — Prof. Luyz
- [[01 - Disciplinas/Técnicas Avançadas de Jogos Digitais/Técnicas Avançadas de Jogos Digitais|Tecnicas Avancadas de Jogos Digitais (Quinta-feira)]] — Prof. Fabricio
- [[01 - Disciplinas/Interação Humano Computador e Sistemas Multimídia/Interação Humano Computador e Sistemas Multimídia|Interacao Humano Computador e Sistemas Multimidia (Sexta-feira)]] — Prof. Guilherme
- [[01 - Disciplinas/Projeto Prático Integrado - Desenvolvimento de Jogos Digitais/Projeto Prático Integrado - Desenvolvimento de Jogos Digitais|Projeto Pratico Integrado - Jogos Digitais (PPI)]]

```dataview
TABLE 
  codigo AS "Codigo",
  professor AS "Professor",
  horario AS "Horario",
  status AS "Status"
FROM "01 - Disciplinas"
WHERE tipo = "disciplina"
SORT horario ASC
```

---

## 4. Aulas e Atividades Recentes

```dataview
TABLE 
  disciplina AS "Disciplina",
  data AS "Data",
  status AS "Status"
FROM "01 - Disciplinas"
WHERE tipo = "aula" OR tipo = "atividade" OR tipo = "trabalho"
SORT data DESC
LIMIT 12
```

---

## 5. Avaliacoes & Provas Regimentais

```dataview
TABLE 
  disciplina AS "Disciplina",
  Avaliacao AS "Avaliacao",
  Data AS "Data da Prova",
  Status AS "Status"
FROM "02 - Avaliações"
```
