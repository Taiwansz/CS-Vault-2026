---
tipo: ata-orientacao
disciplina: Trabalho de Conclusão de Curso II
codigo_disciplina: "1110020055"
turma: N13208A
periodo_oficial: 01/09/2026 a 19/09/2026
data_registro: 2026-09-16
orientador: Prof. Me. Luiz Claudio Chiavini Oliveira Junior
titulo_tcc: "COMPRESSÃO DE PROMPTS, TOKENS E SUSTENTABILIDADE DA IA: Engenharia de Prompt como Estratégia de Otimização de Respostas em Inteligências Artificiais"
status: pronto-para-submissao-webtcc
---

# 1º Registro de Acompanhamento de Orientação — TCC II (2026.2)

> **Documento Oficial de Prestação de Contas Acadêmicas para a Plataforma WebTCC**  
> Período de Referência: 01/09/2026 a 19/09/2026  
> Aluno: Matheus Sousa dos Santos (RA: 52319400) e Equipe  
> Orientador: Prof. Me. Luiz Claudio Chiavini Oliveira Junior (Luyz)

---

## 1. Arquivos Oficiais Gerados no Padrão ABNT (Entregáveis Anexos)

- 📄 **Documento Completo Editável (Word ABNT):**  
  [TCC_II_Versao_1_Acompanhamento_Matheus_Santos.docx](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/TCC_II_Versao_1_Acompanhamento_Matheus_Santos.docx)
- 📑 **Documento Oficial Compilado (PDF ABNT - 26 páginas):**  
  [TCC_II_Versao_1_Acompanhamento_Matheus_Santos.pdf](file:///C:/Users/stdma/Documents/faculdade-2026-2/01%20-%20Disciplinas/Trabalho%20de%20Conclus%C3%A3o%20de%20Curso%20II/TCC_II_Versao_1_Acompanhamento_Matheus_Santos.pdf)
- 📊 **Baseline do Semestre Anterior (TCC I):**  
  [[01 - Disciplinas/Trabalho de Conclusão de Curso II/Materiais/TCC_Parte_1_Entregue.pdf|TCC Parte 1 Entregue (PDF)]]

---

## 2. Texto Copia-e-Cola para Inserção no Formulário do WebTCC

> *Copie e cole o texto abaixo diretamente no campo de texto de acompanhamento da plataforma WebTCC:*

```text
RELATÓRIO DO 1º ACOMPANHAMENTO DE ORIENTAÇÃO — TCC II (2026.2)

Título do Trabalho: COMPRESSÃO DE PROMPTS, TOKENS E SUSTENTABILIDADE DA IA: Engenharia de Prompt como Estratégia de Otimização de Respostas em Inteligências Artificiais
Orientador: Prof. Me. Luiz Claudio Chiavini Oliveira Junior

1. ATIVIDADES DESENVOLVIDAS NO PERÍODO (01/09 a 19/09/2026):
No presente ciclo inicial de TCC II, consolidamos a transição da fundamentação puramente teórica para a esteira experimental de bancada científica (Benchmark Battle IA). As seguintes metas foram plenamente cumpridas:
a) Modelagem de três cenários densos de testes corporativos reais: (1) Raciocínio em Sistemas Distribuídos e Concorrência sob o teorema CAP; (2) Engenharia de Dados com geração de SQL sob esquemas DDL complexos (8 tabelas normalizadas e integridade referencial); e (3) Auditoria de Conformidade e Proteção de Dados com foco em LGPD.
b) Execução de testes empíricos de inferência confrontando as linhas de base convencionais (Zero-shot e Few-shot) com a técnica de compressão de prompts baseada em perplexidade (LLMLingua) em taxas nominais de 2x e 4x sobre modelos de fronteira (Llama-3-70B e Mistral-Large).
c) Quantificação da redução de complexidade no mecanismo de autoatenção O(n²), apurando economia de até 93,75% em operações matemáticas de produto interno e redução de até 81,9% no Time-to-First-Token (TTFT).
d) Avaliação sistemática de fidelidade semântica por meio do BERTScore contextual (RoBERTa-large), demonstrando que a preservação de significado (F1-score) sustentou-se acima do patamar crítico de 0,86 em todos os domínios avaliados.
e) Redação, estruturação e incorporação integral do Capítulo 10 ("Resultados e Discussão Experimental") e revisão do Capítulo 11 ("Conclusão e Trabalhos Futuros") na monografia oficial, agora totalizando 26 páginas formatadas estritamente sob as normas ABNT e diretrizes institucionais da UniMAX.

2. DIFICULDADES ENCONTRADAS E SOLUÇÕES ADOTADAS:
- Identificou-se que em taxas de compressão agressivas (> 5x) em tarefas de SQL, a poda cega de tokens removia identificadores de chaves estrangeiras. A solução adotada consistiu em formular diretrizes para listas de preservação obrigatória (whitelist tokens) e compressão segmentada (preservando o prompt de sistema e focando a compressão no payload de contexto documental).

3. PRÓXIMOS PASSOS (META PARA O 2º ACOMPANHAMENTO - OUTUBRO/2026):
- Ampliar os testes experimentais para pipelines integrados de Recuperação Aumentada por Geração (RAG).
- Refinar os comparativos com LLMLingua-2 (classificação supervisionada).
- Preparar a versão desidentificada preliminar para submissão à Banca Virtual (prazo fatal de 03 a 09 de novembro).
```

---

## 3. Síntese do Conteúdo Adicionado à Monografia (Capítulos 10 e 11)

### Capítulo 10: Resultados e Discussão Experimental (Benchmark Battle IA)
- **10.1 Metodologia Experimental e Configuração da Bancada:** Definição dos 3 datasets de entrada (Sistemas Distribuídos, SQL sobre DDL e LGPD), modelos de linguagem testados e arquitetura de streaming assíncrono.
- **10.2 Avaliação Quantitativa de Tokens:** Tabela 1 ABNT demonstrando redução média de 52,8% em 2x e 74,6% em 4x no consumo de tokens de entrada em relação ao baseline.
- **10.3 Latência de Inferência:** Tabela 2 ABNT evidenciando o impacto no prefill, com queda do TTFT de 2.150 ms para 390 ms no caso LGPD.
- **10.4 Fidelidade Semântica via BERTScore:** Tabela 3 ABNT com scores de Precisão, Recall e F1, todos mantidos entre 0,856 e 0,914.
- **10.5 Viabilidade Financeira e Green AI:** Tabela 4 ABNT com projeção para 100.000 requisições corporativas (economia de US$ 702,60 por lote de 100k chamadas sob 4x) e redução de 93,8% nos FLOPs de atenção.
- **10.6 Análise Crítica de Trade-offs:** Diretrizes para proteção de tokens rígidos em esquemas de banco de dados e arquitetura de compressão assimétrica em RAG.

### Capítulo 11: Conclusão e Trabalhos Futuros
- Confirmação empírica da hipótese de redundância linguística de Shannon.
- Posicionamento da técnica como ferramenta operacional de Green AI.
- Proposição de desdobramentos futuros em compressão adaptativa e quantização de modelos.
