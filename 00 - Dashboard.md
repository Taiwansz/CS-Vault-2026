---
tipo: dashboard
semestre: 2026.2
turma: N13208A
---

# 🎓 Painel Acadêmico & Central de Comando — 2026.2

> [!info] 🏛️ Centro Universitário Max Planck (UniMAX / UniEduK)
> **Bacharelado em Ciência da Computação** | **Turma:** N13208A | **Semestre:** 2026.2  
> 🎯 **Focos Principais:** TCC II (Compressão de Prompts em LLMs) & Projeto Prático de Jogos (PPI)

---

## ⚡ Navegação Rápida (Bento Cards)

<div class="bento-grid">

<div class="bento-card">
<h3>🎓 Central do TCC II</h3>
<p>Tema: <i>Compressão de Prompts, Tokens e Sustentabilidade da IA</i>.<br>
<a href="01 - Disciplinas/Trabalho de Conclusão de Curso II/Trabalho de Conclusão de Curso II.md">Acessar Hub do TCC II ➔</a></p>
</div>

<div class="bento-card">
<h3>🎮 Projeto Prático de Jogos</h3>
<p>Documento de Game Design (GDD), Kanban e prototipagem em Unity/Godot.<br>
<a href="01 - Disciplinas/Projeto Prático Integrado - Desenvolvimento de Jogos Digitais/Projeto Prático Integrado - Desenvolvimento de Jogos Digitais.md">Acessar PPI Jogos ➔</a></p>
</div>

<div class="bento-card">
<h3>📊 Controle de Frequência</h3>
<p>Monitoramento de presença e faltômetro do semestre.<br>
<a href="03 - Calendário/Controle de Frequência.md">Abrir Faltômetro ➔</a></p>
</div>

<div class="bento-card">
<h3>🗺️ Guia do Cofre (Vault Guide)</h3>
<p>Convenções de pastas, modelos, metadados e atalhos.<br>
<a href="00 - Guia do Cofre (Vault Guide).md">Ver Guia do Cofre ➔</a></p>
</div>

</div>

---

## 🚨 Prazos e Marcos Críticos do TCC II (WebTCC 2026.2)

| Período / Data | Marco / Entrega | Responsável | Status |
|---|---|---|---|
| **01/09 a 19/09** | 1º Registro Obrigatório de Orientação | Alunos / Orientador | ⏳ Pendente |
| **01/10 a 19/10** | 2º Registro Obrigatório de Orientação | Alunos / Orientador | ⏳ Pendente |
| **03/11 a 09/11** | 🚨 **Inserção do Trabalho para Banca Virtual (PDF sem nome)** | **Alunos** | 🚨 **Crítico** |
| **23/11 a 27/11** | 🎓 **Simpósio de TCCs (SIMTCC)** | Alunos / Banca | 🎓 **Apresentação** |
| **24/11 a 27/11** | 🚨 **Inserção do Trabalho Final (PDF com nome)** | **Alunos** | 🚨 **Entrega Final** |

---

## 📚 Disciplinas e Grade Horária do Semestre

```dataview
TABLE 
  codigo AS "Código",
  professor AS "Professor",
  horario AS "Horário",
  status AS "Status"
FROM "01 - Disciplinas"
WHERE tipo = "disciplina"
SORT horario ASC
```

---

## 📝 Aulas e Atividades Recentes

```dataview
TABLE 
  disciplina AS "Disciplina",
  data AS "Data",
  status AS "Status"
FROM "01 - Disciplinas"
WHERE tipo = "aula" OR tipo = "atividade"
SORT data DESC
LIMIT 10
```

---

## 📅 Próximas Avaliações & Provas

```dataview
TABLE 
  disciplina AS "Disciplina",
  Avaliação AS "Avaliação",
  Data AS "Data da Prova",
  Status AS "Status"
FROM "02 - Avaliações"
```

---

> [!tip] 💡 Bloco de Anotações Rápidas
> Digite aqui ideias ou rascunhos antes de organizá-los nas pastas definitivas.
