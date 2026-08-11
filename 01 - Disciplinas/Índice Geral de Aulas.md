---
tipo: indice
data: 2026-08-11
---

# 📚 Índice Geral de Aulas & Anotações (2026.2)

> [!info] 📌 Visão Consolidada
> Registro cronológico automático de todas as aulas, conteúdos e atividades salvas nas disciplinas do semestre.

---

```dataview
TABLE 
  disciplina AS "Disciplina",
  professor AS "Professor",
  data AS "Data",
  status AS "Status"
FROM "01 - Disciplinas"
WHERE tipo = "aula" OR tipo = "atividade"
SORT data DESC
```
