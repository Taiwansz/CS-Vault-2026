# 🔬 03 - Bancada Experimental & Automação (Battle IA Benchmark)

> **Ambiente Computacional e Pipeline de Testes Empíricos do TCC II**  
> **Tema:** Compressão de Prompts, Redução do Consumo de Tokens e Sustentabilidade da IA  
> **Autor:** Matheus Sousa dos Santos (RA: 52319400)  
> **Orientador:** Prof. Luiz Claudio Chiavini Oliveira Junior  
> **Instituição:** Centro Universitário Max Planck (UniMAX) — Bacharelado em Ciência da Computação (2026)

---

## 📌 Visão Geral da Bancada

Esta pasta consolida o código-fonte, dados brutos e scripts de geração científica que suportam os experimentos apresentados no **Capítulo 10 ("Resultados e Discussão Experimental")** da monografia de TCC II.

Diferentemente de avaliações subjetivas de preferência humana (como o *LMSYS Chatbot Arena*), a esteira **Battle IA** foi desenvolvida para coletar métricas físicas, matemáticas e operacionais determinísticas de inferência:
1. **Volume de Tokens de Entrada:** Contagem exata antes e após a poda por perplexidade;
2. **Latência de Prefill (TTFT - Time-to-First-Token):** Medição em milissegundos da resposta inicial sob streaming assíncrono;
3. **Fidelidade Semântica Contextual (BERTScore):** Cálculo vetorial via modelo pré-treinado *RoBERTa-large* avaliando Precisão, Cobertura (Recall) e F1-score;
4. **Complexidade Algorítmica $O(n^2)$:** Quantificação da redução de operações matriciais (FLOPs) no mecanismo de autoatenção do Transformer;
5. **Impacto Financeiro:** Modelagem de custos operacionais em escala corporativa (100.000 requisições) em APIs comerciais.

---

## 📂 Arquivos da Bancada

| Arquivo | Descrição |
|---|---|
| `battle_ia_benchmark.py` | Script principal da esteira automatizada de inferência e cálculo de telemetria dos 3 casos de uso. |
| `battle_ia_resultados_brutos.json` | Base de dados estruturada em JSON com as medições coletadas pela esteira. |
| `generate_tcc_charts.py` | Gerador dos 4 gráficos científicos em resolução de 300 DPI salvos em `../04 - Figuras/`. |
| `build_monograph.py` | Compilador ABNT do manuscrito final em `.docx` e `.pdf` com calibração dinâmica de páginas. |
| `tcc_humanizer.py` | Módulo de higienização léxica anti-IA (*Stop-Slop*), formatação de tabelas ABNT e burstiness. |
| `structured_ch1_9.json` | Estrutura vetorial e semântica dos Capítulos 1 a 9 extraída com conformidade de parágrafos. |
| `requirements.txt` | Lista de dependências Python necessárias para execução do ambiente. |

---

## 🚀 Como Executar

### 1. Instalação das Dependências
```bash
pip install -r requirements.txt
```

### 2. Executar a Bancada de Testes de Inferência (Battle IA)
```bash
python battle_ia_benchmark.py
```
*Gera o arquivo `battle_ia_resultados_brutos.json` contendo as métricas de tokens, latência, BERTScore e custos.*

### 3. Gerar os Gráficos Científicos em Alta Resolução (300 DPI)
```bash
python generate_tcc_charts.py
```
*Gera os arquivos `.png` científicos na pasta `../04 - Figuras/`.*

### 4. Compilar a Monografia Completa em Word e PDF
```bash
python build_monograph.py
```
*Compila em duas passadas, mapeando o sumário com tabulação ABNT militar e salvando em `../01 - Manuscrito/`.*
