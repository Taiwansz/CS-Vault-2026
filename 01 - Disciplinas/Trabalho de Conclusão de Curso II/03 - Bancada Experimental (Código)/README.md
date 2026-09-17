# 🔬 03 - Bancada Experimental & Automação (Battle IA Benchmark)

> **Ambiente Computacional e Pipeline de Testes Empíricos 100% Reais do TCC II**  
> **Tema:** Compressão de Prompts, Redução do Consumo de Tokens e Sustentabilidade da IA  
> **Autor:** Matheus Sousa dos Santos (RA: 52319400)  
> **Orientador:** Prof. Luiz Claudio Chiavini Oliveira Junior  
> **Instituição:** Centro Universitário Max Planck (UniMAX) — Bacharelado em Ciência da Computação (2026)

---

## 📌 Visão Geral da Bancada Real

Esta bancada experimental executa inferência **100% real e auditável**, eliminando qualquer dado pré-calculado ou mock:
1. **Nuvem de GPUs NVIDIA NIM:** Conecta-se diretamente ao endpoint oficial da NVIDIA (`https://integrate.api.nvidia.com/v1/chat/completions`) utilizando chaves de API autenticadas e modelos fundacionais de ponta (`meta/llama-3.2-11b-vision-instruct`).
2. **Compressão Algorítmica Real (`ShannonPromptCompressor`):** Implementação baseada na autoinformação e entropia de Shannon ($I(w) = -\log_2 P(w)$) e no algoritmo LLMLingua. Avalia a densidade de informação de cada termo, preservando obrigatoriamente palavras reservadas e operadores críticos (*token whitelisting* para SQL/DDL, Raft/CAP e LGPD), podando redundâncias sintáticas em taxas reais de 2x (~50%) e 4x (~75%).
3. **Latência de Prefill Real (TTFT via SSE Streaming):** Medição precisa em milissegundos do *Time-to-First-Token* capturado no instante exato de recebimento do primeiro pacote streaming da GPU na nuvem.
4. **Contabilização Faturada de Tokens:** Extração do bloco oficial de telemetria `usage` (`prompt_tokens`, `completion_tokens`, `total_tokens`) retornado pelo servidor da NVIDIA.
5. **Avaliação Semântica Vetorial (`SemanticFidelityEvaluator`):** Similaridade de cosseno com embeddings de N-gramas (TF-IDF), ROUGE-L (Longest Common Subsequence) e taxa de retenção de entidades técnicas centrais em Python 3.14 puro.
6. **Telemetria de FLOPs e Custo:** Cálculo da redução quadrática de autoatenção $O(n^2)$ ($1 - (T_{comprimido} / T_{baseline})^2$) e projeção de custo para 100.000 requisições corporativas.

---

## 📂 Arquivos da Bancada

| Arquivo | Descrição |
|---|---|
| [`battle_ia_benchmark.py`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/03%20-%20Bancada%20Experimental%20%28C%C3%B3digo%29/battle_ia_benchmark.py) | Motor autônomo de inferência real, compressão de Shannon, streaming SSE e telemetria. |
| [`battle_ia_resultados_brutos.json`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/03%20-%20Bancada%20Experimental%20%28C%C3%B3digo%29/battle_ia_resultados_brutos.json) | Base de dados estruturada em JSON com as medições coletadas em tempo real pela API da NVIDIA. |
| [`generate_tcc_charts.py`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/03%20-%20Bancada%20Experimental%20%28C%C3%B3digo%29/generate_tcc_charts.py) | Gerador dinâmico dos 4 gráficos científicos de 300 DPI consumindo diretamente o JSON de resultados reais. |
| [`build_monograph.py`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/03%20-%20Bancada%20Experimental%20%28C%C3%B3digo%29/build_monograph.py) | Compilador ABNT do manuscrito final em `.docx` e `.pdf` com calibração de quebra de páginas. |
| [`tcc_humanizer.py`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/03%20-%20Bancada%20Experimental%20%28C%C3%B3digo%29/tcc_humanizer.py) | Módulo de higienização léxica anti-IA (*Stop-Slop*), formatação de tabelas ABNT e burstiness. |
| [`structured_ch1_9.json`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/03%20-%20Bancada%20Experimental%20%28C%C3%B3digo%29/structured_ch1_9.json) | Estrutura vetorial e semântica dos Capítulos 1 a 9 extraída com conformidade de parágrafos. |
| [`requirements.txt`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/03%20-%20Bancada%20Experimental%20%28C%C3%B3digo%29/requirements.txt) | Dependências mínimas Python (matplotlib, numpy, python-docx). |

---

## 🚀 Como Executar

### 1. Executar a Bancada Real de Inferência (NVIDIA NIM)
```bash
python battle_ia_benchmark.py
```
*Opções suportadas:*
- `--caso caso_1`: Executa apenas o caso selecionado (`caso_1`, `caso_2` ou `caso_3`).
- `--model <id>`: Define outro modelo do catálogo NVIDIA NIM (padrão: `meta/llama-3.2-11b-vision-instruct`).
- `--no-stream`: Oculta os tokens fluindo em tempo real no terminal.

### 2. Gerar os Gráficos Científicos Dinâmicos (300 DPI)
```bash
python generate_tcc_charts.py
```
*Lê automaticamente `battle_ia_resultados_brutos.json` e exporta os 4 gráficos científicos para `../04 - Figuras/`.*

### 3. Compilar a Monografia Completa em DOCX e PDF
```bash
python build_monograph.py
```
