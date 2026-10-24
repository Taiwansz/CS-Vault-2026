# 04 - Figuras Cientificas (300 DPI)

> **Catalogo de Graficos e Recursos Visuais do Capitulo 10**  
> **Padrao:** Publicacao Cientifica ABNT / Resolucao 300 DPI  
> **Fonte de Dados:** `03 - Bancada Experimental (Codigo)/battle_ia_resultados_brutos.json` (`Meta Llama-3.2-11B-Vision-Instruct | NVIDIA NIM Cloud A100 SXM4`)

---

## Relacao de Figuras Integradas a Monografia

### 1. Figura 10.1 — Consumo Real de Tokens de Entrada por Tecnica e Caso
* **Arquivo:** [`figura_10_1_volume_tokens.png`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/04%20-%20Figuras/figura_10_1_volume_tokens.png)
* **Tipo:** Grafico de barras agrupadas com rotulos numericos exatos sobre as colunas.
* **Metricas Reais:** Caso 1 (340, 262, 206, 149 tokens), Caso 2 (434, 361, 267, 185 tokens) e Caso 3 (378, 305, 230, 171 tokens), demonstrando reducao de ate 57,4% (media de 56,2%) sob poda de 4x com *token whitelisting*.

### 2. Figura 10.2 — Latencia e Time-to-First-Token (TTFT em ms)
* **Arquivo:** [`figura_10_2_latencia_ttft.png`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/04%20-%20Figuras/figura_10_2_latencia_ttft.png)
* **Tipo:** Grafico de barras destacando a latencia inicial de prefill via SSE streaming em milissegundos.
* **Destaque:** Estabilizacao do TTFT sob compressao de 4x entre 538 ms e 637 ms nos tres casos, reduzindo em 23,0% frente ao baseline Few-shot (712 ms -> 548 ms) e 87,6% frente ao pico de contencao do Zero-shot (4.419 ms) no Caso 3.

### 3. Figura 10.3 — Curva de Retencao Semantica (BERTScore Proxy F1) vs Razao de Compressao
* **Arquivo:** [`figura_10_3_bertscore_f1.png`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/04%20-%20Figuras/figura_10_3_bertscore_f1.png)
* **Tipo:** Grafico de linhas com marcadores geometricos e linha de corte critica academica (`F1 = 0,80`).
* **Destaque:** Retencao de excelencia no Caso 2 (0,879 -> 0,862 -> 0,852), ganho de relacao sinal-ruido no Caso 3 (0,795 -> 0,804 -> 0,828) e retracao marginal no Caso 1 (0,811 -> 0,792 -> 0,791).

### 4. Figura 10.4 — Projecao de Custo Financeiro e Reducao de FLOPs O(n²)
* **Arquivo:** [`figura_10_4_custo_flops.png`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/04%20-%20Figuras/figura_10_4_custo_flops.png)
* **Tipo:** Grafico de eixo duplo (Dual Y-Axis): barras azuis para custo financeiro medio em lotes de 100k requisicoes (US$) e barras verdes para reducao percentual media de FLOPs da autoatencao.
* **Destaque:** Reducao do custo total de US$ 505,20 para US$ 440,50 (-56,2% no custo de entrada e -12,8% no custo global) e supressao media de 80,7% nos FLOPs de autoatencao do prefill.
