---
tipo: guia
data: 2026-11-09
autor: Matheus Sousa dos Santos
status: ativo
---

# Guia Completo do Cofre — Arquitetura e Convencoes (Vault Guide)

> [!INFO] **Sistema Operacional Academico & Pessoal — Ciencia da Computacao 2026.2**  
> Este documento define a arquitetura de informacao, convencoes de metadados, estrutura de pastas, fluxos de trabalho e padroes do cofre no Obsidian.

---

## 1. Estrutura Canônica de Pastas

```text
Cofre Obsidian (CS-Vault-2026)
├── 00 - Dashboard.md                 <-- Central de Comando do Cofre
├── 00 - Guia do Cofre (Vault Guide).md <-- Este documento de convencoes
├── 01 - Disciplinas/                 <-- Pastas canônicas por disciplina
│   ├── Compiladores/
│   ├── Estudos Avançados em Ciências da Computação/
│   ├── Interação Humano Computador e Sistemas Multimídia/
│   ├── Projeto Prático Integrado - Desenvolvimento de Jogos Digitais/
│   ├── Trabalho de Conclusão de Curso II/  <-- Central do TCC II
│   ├── Técnicas Avançadas de Jogos Digitais/
│   └── Tópicos Especiais II/
├── 02 - Avaliações/                  <-- Painel de provas e notas
├── 03 - Calendário/                  <-- Quadro de horarios e faltometro
├── 04 - Materiais Gerais/            <-- Documentos e anexos institucionais
├── 05 - ESTUDOS ENADE/               <-- Simulados oficiais e banco de questoes
├── 99 - Templates/                   <-- Modelos padronizados de notas
└── Excalidraw/                       <-- Diagramas e mapas visuais
```

---

## 2. Padrao de Propriedades & Frontmatter (YAML)

Todas as notas de aula, atividades e orientacoes devem utilizar o cabecalho YAML padronizado:

```yaml
---
tipo: aula # aula | atividade | trabalho | avaliacao | tcc | gdd | simulado
disciplina: Topicos Especiais II
data: 2026-11-09
professor: Guilherme Weber
status: concluido # pendente | em_andamento | concluido
---
```

---

## 3. Fluxos de Trabalho Operacionais (Workflows)

### A. Criar uma Nova Aula / Anotacao
1. Crie a nota na subpasta `Aulas/` da respectiva disciplina (`01 - Disciplinas/<Nome>/Aulas/`).
2. Utilize a sintaxe de data ISO `AAAA-MM-DD - Tema da Aula.md`.
3. Aplique o template correspondente de `99 - Templates/`.

### B. Registrar Reunia de Orientacao ou Manuscrito do TCC II
1. Salve a ata na pasta `01 - Disciplinas/Trabalho de Conclusão de Curso II/02 - Orientação e WebTCC/`.
2. Nomeie o arquivo como `AAAA-MM-DD - Ata Reuniao TCC.md`.
3. Atualize a tabela de marcos criticos no Dashboard e no WebTCC.

### C. Consolidar Simulados do ENADE
1. Armazene a prova resolvida em `05 - ESTUDOS ENADE/`.
2. Mantenha as questoes objetivas com gabarito justificado e as discursivas calibradas em linguagem direta de estudante.
3. Atualize o `INDEX_ESTUDOS_ENADE.md` com a nova avaliacao.

---

## 4. Atalhos e Navegacao no Obsidian

- **Paginador / Busca Rapida:** `Ctrl + O`
- **Busca Global em Texto:** `Ctrl + Shift + F`
- **Visualizacao em Grafo:** `Ctrl + G`
- **Alternar Painel Lateral:** `Ctrl + Shift + E`
