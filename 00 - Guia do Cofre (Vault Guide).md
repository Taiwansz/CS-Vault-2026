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

## 1. Centrais de Comando do Grafo

- **Central de Operacoes:** [[00 - Dashboard]]
- **Catalogo Geral de Aulas:** [[Índice Geral de Aulas]]
- **Hub Mestre de Estudos ENADE:** [[INDEX_ESTUDOS_ENADE]]
- **Painel de Exames e Notas:** [[Painel Geral de Avaliações]]
- **Calendario e Presenca:** [[Calendário do Semestre]] | [[Controle de Frequência]] | [[Quadro de Horários]]
- **Diretrizes de Uso:** [[LEIA-ME - Como usar]] | [[SESSION_STATE]]

---

## 2. Estrutura Canonica de Pastas

```text
Cofre Obsidian (CS-Vault-2026)
|-- 00 - Dashboard.md <-- Central de Comando do Cofre
|-- 00 - Guia do Cofre (Vault Guide).md <-- Este documento de convencoes
|-- 01 - DISCIPLINAS/ <-- Pastas canonicas por disciplina
| |-- Compiladores/
| |-- Estudos Avançados em Ciências da Computação/
| |-- Interação Humano Computador e Sistemas Multimídia/
| |-- Projeto Prático Integrado - Desenvolvimento de Jogos Digitais/
| |-- Trabalho de Conclusão de Curso II/ <-- Central do TCC II
| |-- Técnicas Avançadas de Jogos Digitais/
| `-- Tópicos Especiais II/
|-- 02 - Avaliações/ <-- Painel de provas e notas
|-- 03 - Calendário/ <-- Quadro de horarios e faltometro
|-- 04 - Materiais Gerais/ <-- Documentos e anexos institucionais
|-- 05 - ESTUDOS ENADE/ <-- 8 Guias completos e cadernos de simulados
|-- 99 - Templates/ <-- Modelos padronizados de notas
`-- Excalidraw/ <-- Diagramas e topologias visuais
```

---

## 3. Modelos Padronizados (Templates)

Para garantir consistencia visual e rastreabilidade nos grafos, todas as novas notas devem instanciar os modelos homologados:

- [[Template - Aula]]: Para aulas regulares e anotacoes teoricas semanais.
- [[Template - Trabalho]]: Para especificacao de atividades e projetos entregues.
- [[Template - Avaliação]]: Para registro de provas parciais, substitutivas e exames.
- [[Template - Laboratório de Redes e Packet Tracer]]: Para cenarios de rede e comandos Cisco.
- [[Template - Ata de Reunião com Orientador]]: Para alinhamentos formais do TCC II.
- [[Template - Fichamento Bibliográfico]]: Para leitura e sintese de artigos cientificos.
- [[Template - GDD (Game Design Document)]]: Para concepcao de jogos digitais.
- [[Template - Registro de Bug e Playtest]]: Para controle de qualidade de software e jogos.

---

## 4. Padrao de Propriedades & Frontmatter (YAML)

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

## 5. Fluxos de Trabalho Operacionais (Workflows)

### A. Criar uma Nova Aula / Anotacao
1. Crie a nota na subpasta `Aulas/` da respectiva disciplina (`01 - DISCIPLINAS/<Nome>/Aulas/`).
2. Utilize a sintaxe de data ISO `AAAA-MM-DD - Tema da Aula.md`.
3. Aplique o [[Template - Aula]].
4. Inclua o bloco de navegacao apontando para [[00 - Dashboard]], [[Índice Geral de Aulas]] e a disciplina.

### B. Registrar Reuniao de Orientacao ou Manuscrito do TCC II
1. Salve a ata na pasta `01 - DISCIPLINAS/Trabalho de Conclusão de Curso II/02 - Orientação e WebTCC/`.
2. Utilize o [[Template - Ata de Reunião com Orientador]].
3. Atualize o [[Trabalho de Conclusão de Curso II]] e a tabela no [[00 - Dashboard]].

### C. Consolidar Simulados e Guias do ENADE
1. Armazene a prova resolvida em `05 - ESTUDOS ENADE/`.
2. Mantenha os links bidirecionais vinculados ao [[INDEX_ESTUDOS_ENADE]] e aos guias de materia em [[INDEX_ESTUDOS_ENADE]].
