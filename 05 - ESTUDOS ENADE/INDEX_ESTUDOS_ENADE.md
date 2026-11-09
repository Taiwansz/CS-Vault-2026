---
tipo: indice-mestre
categoria: "Estudos ENADE"
data_atualizacao: 2026-11-09
operador: "Matheus Sousa dos Santos (Taiwansz)"
curso: "Ciência da Computação (UniEduK / UniMAX)"
---

# 📚 COFRE MESTRE DE ESTUDOS ENADE — CIÊNCIA DA COMPUTAÇÃO (2026)

> [!IMPORTANT] **Diretriz de Preparação para o ENADE**
> O acervo a seguir consolida todos os simulados, provas anteriores, guias de aprofundamento e autópsias analíticas realizadas no curso de Ciência da Computação. O material cobre integralmente os dois pilares da matriz do INEP: **Formação Geral** e **Componente Específico**.

---

## 🗺️ Mapa de Simulados e Avaliações Canônicas

| Documento | Data | Foco / Disciplinas | Questões | Status |
| :--- | :---: | :--- | :---: | :---: |
| **[[Simulado 04 - Ciencia da Computacao Completo 20 Questoes 2026-11-09]]** | 09/11/2026 | Formação Geral (C2PA, DMA, Right to Repair, LGPD) + Algoritmos (Mochila DP/Guloso, Tries, Grafos Tarjan/Dijkstra, Segment Tree), Java Moderno (Virtual Threads, Records, Sealed, Metaspace) e SOLID. | 20 (3 Discursivas + 17 Objetivas) | ✅ 100% Anotado e Gabaritado |
| **[[Simulado_03_Mapeamento_Completo_e_Plano_de_Estudos_ENADE_2026]]** | 16/09/2026 | Redes de Computadores Avançadas (OSPF Multiarea, VLSM, RIPv2, TCP/IP, Segurança de Redes) + Conhecimentos Gerais. | 40 questões oficiais | ✅ Mapeamento Completo e Autópsia |
| **[[Simulado 02 - Questões ENADE de Ciência da Computação]]** | 18/08/2026 | Análise de Algoritmos (Merge Sort, Teorema Mestre), GoF, Banco de Dados (2FN), Sistemas Operacionais (Memória Virtual), Teoria da Computação (Chomsky). | 5 questões | ✅ Resolvido com Didática Passo a Passo |
| **[[Simulado 01 - Compiladores e Linguagens de Programação]]** | Agosto/2026 | Análise Léxica, Sintática (LL/LR), ASTs, Tabela de Símbolos e Geração de Código Intermediário. | 5 questões | ✅ Resolvido e Anotado |
| **[[Guia de Estudos - Bootstrapping, AST e Ataque de Ken Thompson (ENADE 2026)]]** | Setembro/2026 | Compiladores, Vetores de Ataque em Compilação, Trusting Trust e Segurança de Código. | Teoria + Exercícios | ✅ Guia Especializado |
| **[[SIMULADO ENADE.pdf]]** | 2026 | Caderno oficial de questões do Simulado Geral de 40 questões da UniMAX. | 40 questões | 📄 Documento PDF Original |

---

## 🎯 Matriz Temática de Competências (INEP / ENADE)

### 1. Algoritmos e Teoria da Computação
- **Complexidade Assintótica:** Limite inferior $\Omega(n \log n)$ vs. ordenação linear $O(n)$ sem comparação ([[Counting Sort]], [[Radix Sort]]).
- **Paradigmas:** Algoritmos Gulosos (Mochila Fracionária) vs. Programação Dinâmica (Mochila 0/1 com complexidade pseudo-polinomial).
- **Teoria de Grafos:** Componentes Fortemente Conexos ([[Tarjan]], [[Kosaraju-Sharir]]) em tempo linear $O(|V| + |E|)$, Caminhos Mínimos ([[Dijkstra]] com Binary Heap e Fibonacci Heap).
- **Estruturas de Dados Avançadas:** [[Trie]] para prefixos em tempo $O(m)$, [[Segment Tree]] e [[Fenwick Tree (BIT)]] para consultas e atualizações de intervalos em $O(\log n)$.

### 2. Linguagens de Programação e Java Moderno
- **Concorrência e Threads:** Virtual Threads (Projeto Loom — Java 21) com modelo M:N vs. Platform Threads clássicas (1:1). Diagnóstico e mitigação de **Thread Pinning** (bloqueios em blocos `synchronized`).
- **Arquitetura da JVM e Memória:** Estrutura do **Metaspace** (memória nativa do host) substituindo o antigo PermGen; riscos de OOM em geração dinâmica de classes.
- **Tipagem e Design Moderno:** `record` para dados imutáveis transparentes; `sealed classes` combinadas com pattern matching exhaustivo no `switch`; uso canônico e funcional de `Optional<T>`.
- **Coleções Concorrentes:** Escalabilidade com `ConcurrentHashMap` (locks granulares em buckets com CAS) versus `Collections.synchronizedMap` (lock global).

### 3. Engenharia de Software e Arquitetura
- **Princípios SOLID:** Destaque para o **Princípio da Inversão de Dependência (DIP)** e Injeção de Dependências (módulos de alto nível dependem de interfaces, não de classes concretas de banco de dados).
- **Sistemas Distribuídos:** **Hashing Consistente** em anéis de hash ($0$ a $2^b - 1$) para particionamento elástico de dados com migração mínima de chaves.

### 4. Formação Geral, Sociedade e Regulação Tecnológica
- **Ética e IA:** Deepfakes, integridade do voto e padrões criptográficos de proveniência de mídia (**C2PA** com assinaturas digitais na captura); vulnerabilidade do "furo analógico" (*analog hole*).
- **Cibersegurança e Legislação:** Responsabilidade civil e administrativa sob a **LGPD** (Art. 6º, VII - Segurança) e Marco Civil da Internet. Negligência grave por supressão de modelagem de ameaças e pentests.
- **Sustentabilidade:** Direito de Reparar (**Right to Repair**) e ODS 12 (combate à obsolescência programada e ao lixo eletrônico).
- **Regulação Antitruste:** Lei dos Mercados Digitais (**DMA Europeu**) contra o favorecimento próprio (*self-preferencing*) de Big Techs.
