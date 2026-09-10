---
tags: [academico, ciencia-da-computacao, unimax, 2026-2, obsidian, tcc, redes, compiladores, enade]
aliases: [README, CS-Vault-2026, Central Acadêmica]
---

# 🎓 CS-Vault-2026 — Sistema Operacional Acadêmico & Central de Conhecimento
### Bacharelado em Ciência da Computação — Semestre 2026.2
**Centro Universitário Max Planck (UniMAX / UniEduK) • Turma: N13208A**  
**Autor:** Matheus Sousa dos Santos ([@Taiwansz](https://github.com/Taiwansz))

---

<p align="center">
  <img src="https://img.shields.io/badge/Obsidian-Cofre%20Acad%C3%Aamico-7C3AED?style=for-the-badge&logo=obsidian&logoColor=white" alt="Obsidian Vault" />
  <img src="https://img.shields.io/badge/Semestre-2026.2-blue?style=for-the-badge&logo=google-calendar&logoColor=white" alt="Semestre 2026.2" />
  <img src="https://img.shields.io/badge/Curso-Ci%C3%AAncia%20da%20Computa%C3%A7%C3%A3o-0D9488?style=for-the-badge&logo=target&logoColor=white" alt="Ciência da Computação" />
  <img src="https://img.shields.io/badge/Status-Ativo%20%26%20Sincronizado-success?style=for-the-badge&logo=github&logoColor=white" alt="Status" />
  <img src="https://img.shields.io/badge/Licen%C3%A7a-Acad%C3%AAmica-lightgrey?style=for-the-badge" alt="Licença" />
</p>

---

## 📌 Visão Geral do Repositório

O **CS-Vault-2026** é o cofre central de gestão do conhecimento, documentação técnica e acompanhamento acadêmico estruturado para o último ano da graduação em **Ciência da Computação**.

Projetado sob a metodologia **Zettelkasten** e otimizado para o **Obsidian**, o cofre funciona como um verdadeiro **Sistema Operacional Acadêmico**, integrando:
- Acompanhamento semanal de todas as disciplinas do semestre `2026.2`.
- Laboratórios práticos de **Redes Corporativas e Infraestrutura** com simulações no **Cisco Packet Tracer**, automação via IA e relatórios técnicos.
- Central de desenvolvimento e orientação do **Trabalho de Conclusão de Curso II (TCC II)** focado em *Compressão de Prompts em LLMs, Redução de Tokens e Sustentabilidade da IA*.
- Documento de Game Design (GDD) e prototipagem para o **Projeto Prático Integrado de Jogos Digitais (PPI)**.
- Módulo intensivo de preparação para o **ENADE** com simulados comentados e resolução de questões de concursos e provas anteriores.
- Dashboards dinâmicos com **Dataview**, painel de avaliações, controle de frequência e templates padronizados com metadados YAML.

---

## 🗺️ Arquitetura de Informação (Mapa do Cofre)

```
CS-Vault-2026/
├── 00 - Dashboard.md                             # Central de comando visual (Bento Grid & Dataview)
├── 00 - Guia do Cofre (Vault Guide).md           # Padrões de arquitetura, YAML e convenções
├── LEIA-ME - Como usar.md                        # Guia de primeiros passos no cofre
├── README.md                                     # Este documento mestre de apresentação
│
├── 01 - Disciplinas/                             # Pastas dedicadas a cada disciplina do semestre
│   ├── Compiladores/                             # Análise léxica, sintática, semântica e autômatos
│   │   ├── Aulas/                                # Anotações cronológicas e exercícios
│   │   ├── Materiais/                            # Slides e referências bibliográficas
│   │   └── Compiladores.md                       # Hub central da disciplina
│   │
│   ├── Estudos Avançados em Ciências da Computação/ # Redes Corporativas, Cisco IOS e HSRP
│   │   ├── Aulas/                                # Aulas organizadas por data e tópicos práticos
│   │   │   ├── 2026-08-05 - Aula 1 - Inaugural e Fundamentos/
│   │   │   ├── 2026-08-12 - Aula 2 - Switching Avançado/
│   │   │   ├── 2026-08-19 - Aula 3 - STP, RSTP e OSPF/
│   │   │   ├── 2026-09-09 - Aula 5 - HSRP e Alta Disponibilidade/ # Aula atual completa (.pkt, .pdf, .docx, prints)
│   │   │   └── Recursos e Ferramentas Interativas/ # Calculadora VLSM, simulador de terminal e guias
│   │   ├── Arquivos Auxiliares - Automação IA Packet Tracer/ # Scripts JS e bridge MCP de automação
│   │   ├── Materiais/                            # PDFs oficiais dos professores e vídeos gravados
│   │   └── Estudos Avançados em Ciências da Computação.md # Hub central da disciplina
│   │
│   ├── Interação Humano Computador e Sistemas Multimídia/ # UX/UI, Heurísticas de Nielsen e Design
│   ├── Projeto Prático Integrado - Desenvolvimento de Jogos Digitais/ # GDD, prototipagem e mecânicas
│   ├── Técnicas Avançadas de Jogos Digitais/     # Shaders, IA para jogos, arquitetura de engines
│   ├── Tópicos Especiais II/                     # Java avançado, design patterns e programação corporativa
│   └── Trabalho de Conclusão de Curso II/        # Hub do TCC II: Artigo, atas, cronograma e WebTCC
│       ├── Orientação/                           # Atas mensais de reunião com orientador
│       ├── Referências/                          # Fichamentos bibliográficos e papers
│       └── Trabalho de Conclusão de Curso II.md  # Painel geral do TCC II
│
├── 02 - Avaliações/                              # Calendário de provas, entregas e notas
│   ├── Avaliações 2026.2.md                      # Painel consolidado com prazos e pontuações
│   └── Painel Geral de Avaliações.md             # Tabela com consultas dinâmicas
│
├── 03 - Calendário/                              # Gestão de tempo acadêmico
│   ├── Calendário do Semestre.md                 # Datas letivas, feriados e períodos de provas
│   ├── Controle de Frequência.md                 # Faltômetro por disciplina
│   └── Quadro de Horários.md                     # Grade semanal de aulas (Segunda a Sexta)
│
├── 04 - Materiais Gerais/                        # Normas ABNT, manuais da faculdade e assets
│
├── 05 - ESTUDOS ENADE/                           # Preparação para o Exame Nacional de Desempenho
│   ├── Simulado 01 - Compiladores e Linguagens de Programação.md
│   └── Simulado 02 - Questões ENADE de Ciência da Computação.md
│
├── 99 - Templates/                               # Modelos padronizados de notas (YAML frontmatter)
│   ├── Template - Aula.md
│   ├── Template - Atividade Prática.md
│   ├── Template - Ata de Reunião com Orientador.md
│   └── Template - Fichamento Bibliográfico.md
│
└── Excalidraw/                                   # Diagramas de arquitetura e esboços conceituais
```

---

## 📚 Grade de Disciplinas (Semestre 2026.2)

| Dia | Disciplina | Professor | Hub da Disciplina | Foco Principal |
| :---: | :--- | :--- | :---: | :--- |
| **Seg** | Compiladores | Luiz Claudio Chiavini | [[Compiladores]] | Autômatos, Gramáticas Livres de Contexto e Análise Léxica/Sintática |
| **Ter** | Tópicos Especiais II | Luiz Claudio Chiavini | [[Tópicos Especiais II]] | Java Avançado, Orientação a Objetos, Design Patterns e Boas Práticas |
| **Qua** | Estudos Avançados em CC | Paulo Sérgio Granato | [[Estudos Avançados em Ciências da Computação]] | Redes Corporativas L2/L3, Cisco IOS, Switching, STP/RSTP, OSPF e HSRP |
| **Qua** | Trabalho de Conclusão de Curso II | Equipe TCC / Orientador | [[Trabalho de Conclusão de Curso II]] | Pesquisa experimental: *Prompt Compression & Sustentabilidade em LLMs* |
| **Qui** | Técnicas Avançadas de Jogos | Docente Especialista | [[Técnicas Avançadas de Jogos Digitais]] | Inteligência Artificial em Jogos, Matemática Vetorial, Física e Shaders |
| **Sex** | IHC e Sistemas Multimídia | Docente Especialista | [[Interação Humano Computador e Sistemas Multimídia]] | Experiência do Usuário (UX), Acessibilidade, Interfaces e Heurísticas |
| **Transv.** | Projeto Prático Integrado (PPI) | Coordenação de Jogos | [[Projeto Prático Integrado - Desenvolvimento de Jogos Digitais]] | Game Design Document (GDD), Prototipagem Jogável e Mecânicas de Gameplay |

---

## 🔬 Destaques Especiais do Cofre

### 1. Laboratório de Redes & Automação Packet Tracer
Localizado em `01 - Disciplinas/Estudos Avançados em Ciências da Computação/`:
* **Aulas Isoladas por Data:** Cada sessão prática possui pasta própria com:
  - Enunciado oficial em PDF fornecido pelo professor.
  - Relatório técnico de resolução em PDF.
  - Versão editável em Microsoft Word (`.docx`).
  - Arquivo de simulação executável do Cisco Packet Tracer (`.pkt`).
  - Pasta dedicada com as capturas de tela das evidências de teste (`Evidencias/`).
* **Automação IA e Packet Tracer MCP Bridge:** Integração do simulador Packet Tracer com assistentes de IA através de scripts JavaScript (`script_aula_5_hsrp.js`) e ponte local MCP para montagem automatizada de topologias e coleta de saídas CLI.
* **Ferramentas Interativas Embutidas:**
  - Calculadora de Sub-redes e VLSM.
  - Simulador interativo do Modelo OSI e Encapsulamento.
  - Terminal de testes simulado para Cisco IOS Catalyst 3560.
  - Árvore de decisão para diagnóstico e troubleshooting L2/L3.

### 2. Central do Trabalho de Conclusão de Curso II (TCC II)
Localizado em `01 - Disciplinas/Trabalho de Conclusão de Curso II/`:
* **Tema da Pesquisa:** *Compressão Semântica de Prompts em Modelos de Linguagem de Larga Escala (LLMs): Otimização de Janela de Contexto, Redução de Custo de Inferência e Sustentabilidade Computacional*.
* **Painel de Prazos WebTCC:** Acompanhamento dos registros obrigatórios de orientação mensais e do cronograma oficial para submissão à banca virtual e apresentação no **SIMTCC** (Simpósio de TCCs).
* **Fichamentos & Atas:** Registro formal de cada encontro de orientação, decisões metodológicas e métricas empíricas coletadas.

### 3. Simulados Preparatórios para o ENADE
Localizado em `05 - ESTUDOS ENADE/`:
* Cadernos de questões de provas anteriores comentadas item a item.
* Simulados focados em Fundamentos da Computação, Engenharia de Software, Algoritmos, Estruturas de Dados e Teoria da Computação.

---

## 🏷️ Padrão de Metadados (YAML Frontmatter)

Todas as notas do repositório seguem convenções padronizadas para viabilizar consultas automatizadas via **Dataview**:

```yaml
---
tipo: aula # aula | atividade | trabalho | avaliacao | tcc | gdd
disciplina: "Estudos Avançados em Ciências da Computação"
data: 2026-09-09
professor: "Paulo Sérgio Granato"
status: concluido # pendente | em_andamento | concluido
tags:
  - redes
  - hsrp
  - alta-disponibilidade
---
```

---

## 🛠️ Plugins Recomendados do Obsidian

Para desfrutar de toda a experiência e interatividade do cofre, recomenda-se habilitar os seguintes plugins da comunidade já pré-configurados em `.obsidian/`:

1. **Dataview:** Gera as tabelas e painéis dinâmicos de aulas, provas e tarefas no `00 - Dashboard.md`.
2. **Excalidraw:** Visualização e edição de diagramas conceituais e arquiteturais dentro das notas.
3. **Obsidian Linter:** Padronização automática de espaçamentos, títulos e consistência de markdown.
4. **Recent Files:** Acesso rápido ao histórico das notas editadas recentemente.

---

## 🚀 Como Utilizar este Repositório

### Clonar o Repositório
```bash
git clone https://github.com/Taiwansz/CS-Vault-2026.git
```

### Abrir no Obsidian
1. Abra o **Obsidian**.
2. Clique em **"Open folder as vault"** (Abrir pasta como cofre).
3. Selecione a pasta clonada `CS-Vault-2026`.
4. Ao abrir, habilite os plugins da comunidade quando solicitado para ativar os painéis do Dataview.
5. Inicie a navegação pela nota principal `00 - Dashboard.md`.

---

## 👤 Autor

* **Matheus Sousa dos Santos**  
  * *Graduando em Ciência da Computação (UniMAX — Turma N13208A)*  
  * **GitHub:** [@Taiwansz](https://github.com/Taiwansz)  
  * **Cofres Relacionados:** [Claude-PromptVault](https://github.com/Taiwansz/Claude-PromptVault)

---

<p align="center">
  <sub>Construído com base nas melhores práticas de Personal Knowledge Management (PKM) e Engenharia de Computação.</sub>
</p>
