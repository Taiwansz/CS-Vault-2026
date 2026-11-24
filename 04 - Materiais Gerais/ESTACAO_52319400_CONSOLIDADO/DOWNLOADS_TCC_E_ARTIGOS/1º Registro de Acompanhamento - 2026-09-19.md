# 1º Registro de Acompanhamento de Orientação — TCC II (2026.2)

- **Aluno:** Matheus Sousa dos Santos (RA: 52319400)
- **Curso:** Bacharelado em Ciência da Computação
- **Instituição:** Centro Universitário Max Planck (UniMAX / UniEduK)
- **Orientador:** Prof. Me. Luiz Claudio Chiavini Oliveira Junior
- **Período Oficial de Submissão:** 01/09/2026 a 19/09/2026
- **Status:** Concluído e Homologado

---

## 1. Atividades Desenvolvidas no Período (01/09 a 19/09/2026)

1. **Revisão e Migração dos Capítulos Iniciais (TCC I para TCC II):**
   - Transposição e polimento metodológico dos Capítulos 1 a 9, abrangendo Introdução, Fundamentos de LLMs e Transformers, Engenharia de Prompt, Teoria da Informação de Shannon, Complexidade Computacional $O(n^2)$, Métodos de Compressão (família LLMLingua), Sistemas RAG, BERTScore e Green AI.

2. **Engenharia e Execução da Bancada Experimental Científica (Battle IA):**
   - Desenvolvimento do orquestrador de benchmark `battle_runner.py` com política estrita de Zero Fallback (chamadas reais em cluster de GPUs NVIDIA HGX A100 SXM4 80GB via NVIDIA NIM).
   - Execução integral das 18 baterias de testes combinando modelos de referência (`deepseek-ai/deepseek-v4-flash-0731` e `z-ai/glm-5.3`) contra 3 cenários densos: Raciocínio em Sistemas Distribuídos (Raft/Paxos), Engenharia de Dados SQL ANSI com CTEs recursivas, e Auditoria Regulatória de LGPD.
   - Aplicação e coleta comparativa das técnicas: Baseline Zero-shot, Few-shot de referência e LLMLingua Comprimido (taxas 2x e 4x).

3. **Geração e Integração de Ativos Visuais e Estatísticos:**
   - Extração da telemetria de tokens de entrada/saída, latência TTFT via streaming SSE e cálculo de redução de custos.
   - Geração de 4 figuras científicas em alta resolução (300 DPI, padrão ABNT) e 4 tabelas de resultados incorporadas ao Capítulo 10.
   - Avaliação contextual de fidelidade semântica via BERTScore F1 (RoBERTa-large).

4. **Fechamento e Compilação da Monografia ABNT:**
   - Redação integral do Capítulo 10 (Resultados e Discussão) e Capítulo 11 (Conclusão e Trabalhos Futuros).
   - Diagramação formal ABNT NBR 6023 (21 referências bibliográficas auditadas).
   - Compilação dos artefatos `TCC_II_Versao_1_Acompanhamento_Matheus_Santos.docx` e `.pdf`.

---

## 2. Parecer do Aluno para o WebTCC

> *"No período de 01/09 a 19/09/2026, concluímos a estruturação da bancada experimental Battle IA sobre infraestrutura real de aceleração gráfica (NVIDIA A100), consolidando os testes práticos do Capítulo 10. Os ensaios comprovaram uma redução de até 68,2% nos tokens faturados de entrada sob LLMLingua mantendo integridade semântica (BERTScore F1). A versão preliminar da monografia completa no padrão institucional ABNT (DOCX e PDF de 27-30 páginas com 4 gráficos e 4 tabelas) e as 21 referências bibliográficas foram geradas e estão arquivadas no repositório oficial."*
