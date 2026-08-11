---
tipo: guia
data: 2026-08-11
autor: Matheus Sousa dos Santos
status: ativo
---

# 🗺️ Guia Completo do Cofre — Arquitetura e Convenções (Vault Guide)

> [!info] 🏛️ Sistema Operacional Acadêmico & Pessoal — Ciencia da Computação 2026.2
> Este documento define a arquitetura de informação, convenções de metadados, estrutura de pastas, fluxos de trabalho e padrões do cofre no Obsidian.

---

## 📂 1. Estrutura de Pastas (Information Architecture)

```
Cofre Obsidian (CS-Vault-2026)
├── 00 - Dashboard.md                 <-- Homepage / Central de Comando do Cofre
├── 00 - Guia do Cofre (Vault Guide).md <-- Este documento de convenções e guia
├── 01 - Disciplinas/                 <-- Pastas por disciplina do semestre 2026.2
│   ├── Compiladores/
│   ├── Estudos Avançados em Ciências da Computação/
│   ├── Interação Humano Computador e Sistemas Multimídia/
│   ├── Projeto Prático Integrado - Desenvolvimento de Jogos Digitais/
│   ├── Trabalho de Conclusão de Curso II/  <-- Central do TCC II
│   ├── Técnicas Avançadas de Jogos Digitais/
│   └── Tópicos Especiais II/
├── 02 - Avaliações/                  <-- Painel de provas, entregas e notas
├── 03 - Calendário/                  <-- Quadro de horários, faltômetro e calendário
├── 04 - Materiais Gerais/            <-- PDFs, arquivos globais e anexos
└── 99 - Templates/                   <-- Modelos padronizados de notas e ativades
```

---

## 🏷️ 2. Padrão de Propriedades & Frontmatter (YAML)

Todas as notas de aula, atividades e orientações devem utilizar o cabeçalho YAML padronizado:

### Exemplo para Aulas / Atividades:
```yaml
---
tipo: aula # aula | atividade | trabalho | avaliacao | tcc | gdd
disciplina: Tópicos Especiais II
data: 2026-08-11
professor: Luiz Claudio Chiavini Oliveira Junior
status: concluido # pendente | em_andamento | concluido
---
```

---

## 🚀 3. Fluxos de Trabalho (Workflows)

### A. Criar uma Nova Aula / Anotação
1. Pressione `Ctrl + N` ou crie a nota na pasta da disciplina correspondente (`01 - Disciplinas/<Nome>/Aulas/`).
2. Digite a sintaxe de inclusão do modelo de aula ou aplique o modelo **Template - Aula**.
3. Preencha a data e o resumo dos tópicos abordados.

### B. Registrar Reunião de Orientação do TCC II
1. Salve a ata na pasta `01 - Disciplinas/Trabalho de Conclusão de Curso II/Orientação/`.
2. Utilize o **Template - Ata de Reunião com Orientador**.
3. Atualize o progresso da tabela no **TCC II.md** e o registro mensal na plataforma WebTCC.

---

## ⌨️ 4. Atalhos e Consultas Dataview

- **Paginador / Busca Rápida:** `Ctrl + O`
- **Busca Global em Texto:** `Ctrl + Shift + F`
- **Visualização em Grafo:** `Ctrl + G`
- **Alternar Painel Lateral:** `Ctrl + Shift + E`
