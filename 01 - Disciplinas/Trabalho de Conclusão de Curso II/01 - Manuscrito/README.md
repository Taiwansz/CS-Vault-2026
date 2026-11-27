> [!NOTE] Navegacao no Cofre
> **Central TCC II:** [[Trabalho de Conclusão de Curso II]] | **Painel Geral:** [[00 - Dashboard]]

# 01 - Manuscrito Oficial (TCC II)

> **Repositorio Central dos Entregaveis em Word (.docx) e PDF (.pdf)**  
> **Tema:** COMPRESSAO DE PROMPTS, TOKENS E SUSTENTABILIDADE DA IA  
> **Autores:** Matheus Sousa dos Santos (RA: 52319400), Felipe Guarnieri Pinete (RA: 52319337), Guilherme Gustavo Weber (RA: 52420339) e Juan Pedro Souza de Oliveira (RA: 52319411)  
> **Orientador:** Prof. Luiz Claudio Chiavini Oliveira Junior  
> **Instituicao:** Centro Universitario Max Planck (UniMAX) — Bacharelado em Ciencia da Computacao (2026)  
> **Repositorio Oficial de Avaliacao e Artigo:** https://github.com/Taiwansz/TCC-CIENCIAS-DA-COMPUTACAO-2026

---

## Arquivos Atuais (Entrega Final Identificada — 27/11/2026)

| Arquivo | Formato | Paginas | Descricao / Status |
|---|---|---|---|
| [`TCC_II_Entrega_Final_Identificada_2026.pdf`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/01%20-%20Manuscrito/TCC_II_Entrega_Final_Identificada_2026.pdf) | PDF ABNT | 37 pags. (ABNT 5–36) | **Entrega Final Oficial WebTCC (24/11 a 27/11)**: Capa + Folha de Rosto com identificacao completa dos 4 autores, RAs e orientador, paginacao ABNT NBR 14724 no cabecalho a partir da Introducao, 12 quesitos da Banca Virtual saneados, Quadro 1 comparativo, 4 Tabelas dinamicas, 4 Figuras 300 DPI, 27 referencias NBR 6023:2018 e metadados identificados. |
| [`TCC_II_Entrega_Final_Identificada_2026.docx`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/01%20-%20Manuscrito/TCC_II_Entrega_Final_Identificada_2026.docx) | Word DOCX | 37 pags. | **Fonte Editavel da Entrega Final**: Compilado via `build_monograph_final_identificada.py` em duas passadas via Word COM. |
| [`TCC_II_Versao_2_Pos_Banca_EXTRA.pdf`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/01%20-%20Manuscrito/TCC_II_Versao_2_Pos_Banca_EXTRA.pdf) | PDF ABNT | 36 pags. | Espelho pos-banca anonimizado (isolado em 23/11/2026). |
| [`TCC_II_Versao_1_Acompanhamento_Matheus_Santos.pdf`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/01%20-%20Manuscrito/TCC_II_Versao_1_Acompanhamento_Matheus_Santos.pdf) | PDF ABNT | 30 pags. | **Versao Historica Pre-Banca**: Preservada intocada (11 capitulos, 21 referencias). |
| [`TCC_II.pdf`](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/01%20-%20Manuscrito/TCC_II.pdf) | PDF ABNT | 30 pags. | **Espelho Historico Pre-Banca**: Preservado intocado. |

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
