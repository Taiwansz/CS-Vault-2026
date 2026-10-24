# 01 - Manuscrito Oficial (TCC II)

> **Repositorio Central dos Entregaveis em Word (.docx) e PDF (.pdf)**  
> **Tema:** COMPRESSAO DE PROMPTS, TOKENS E SUSTENTABILIDADE DA IA  
> **Autor:** Matheus Sousa dos Santos (RA: 52319400)  
> **Orientador:** Prof. Luiz Claudio Chiavini Oliveira Junior  
> **Instituicao:** Centro Universitario Max Planck (UniMAX) — Bacharelado em Ciencia da Computacao (2026)

---

## Arquivos Atuais (Versao Harmonizada — Outubro/2026)

| Arquivo | Formato | Paginas | Descricao / Status |
|---|---|---|---|
| [`TCC_II_Versao_1_Acompanhamento_Matheus_Santos.pdf`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/01%20-%20Manuscrito/TCC_II_Versao_1_Acompanhamento_Matheus_Santos.pdf) | PDF ABNT | 30 pags. | **Versao Oficial Harmonizada**: Contem os 11 capitulos completos, 4 Tabelas ABNT populadas dinamicamente do JSON real, 4 Figuras cientificas 300 DPI sem clipping, Sumario calibrado em 2 passadas e 21 referencias auditadas. |
| [`TCC_II_Versao_1_Acompanhamento_Matheus_Santos.docx`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/01%20-%20Manuscrito/TCC_II_Versao_1_Acompanhamento_Matheus_Santos.docx) | Word DOCX | 30 pags. | **Documento Fonte Editavel**: Formatado com margens 3/3/2/2 cm, Arial 12pt, entrelinhas 1,5, recuo 1,25 cm e estilos nativos. |
| [`TCC_II.pdf`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/01%20-%20Manuscrito/TCC_II.pdf) | PDF ABNT | 30 pags. | **Espelho Canonico Rapido**: Copia identica sincronizada com o PDF oficial. |

---

## Historico de Versoes do Manuscrito

* **Versao 1.1 (23/10/2026) — Harmonizacao Integral Pos-Auditoria (Prof. Luiz Chiavini):**
  * Unificacao estrita do modelo avaliado em todo o texto (Resumo, Abstract, Capitulo 10 e Conclusao): `Meta Llama-3.2-11B-Vision-Instruct` via `NVIDIA NIM Cloud (A100 SXM4)`;
  * Eliminacao de 100% de hardcoding nas Tabelas 1, 2, 3 e 4 do `build_monograph.py`, que agora leem diretamente `battle_ia_resultados_brutos.json`;
  * Correcao de escala vertical (`ylim`) e posicao da legenda na Figura 10.2 e ajuste de offset de rotulos na Figura 10.3;
  * Alinhamento do limiar critico do BERTScore Proxy F1 em `0,80` entre Capitulo 8, Tabela 3 e Figura 10.3.
* **Versao 1.0 (16/09/2026) — 1o Acompanhamento de Orientacao:**
  * Incorporacao do Capitulo 10 ("Resultados e Discussao Experimental") baseado no Benchmark Battle IA;
  * Inclusao de 4 figuras cientificas em alta resolucao e 4 tabelas de metricas;
  * Auditoria das 21 referencias bibliograficas sob a ABNT NBR 6023:2018.
