---
tipo: orientacao-tcc
disciplina: Trabalho de Conclusão de Curso II
data: 2026-09-14
orientador: Luyz
---

# Orientação — Roadmap e Problematização (Prof. Luyz)

Após análise do tema central do TCC ("COMPRESSÃO DE PROMPTS, TOKENS E SUSTENTABILIDADE DA IA") e do cronograma oficial, as diretrizes do professor Luyz foram estruturadas no seguinte plano de ação estratégico, focado no Capítulo 10 (Resultados e Discussão):

## 1. Problematização e Argumentação
**Objetivo:** Deixar claro *o porquê* deste TCC.
- **Definir o Problema:** Por que otimizar prompts? (Custos das APIs comerciais, limite da janela de contexto, complexidade $O(n^2)$ da arquitetura Transformer, Sustentabilidade/Green AI).
- **Argumentação de Modelos:** Justificar a escolha dos modelos que serão utilizados nos testes baseando-se nas diferentes janelas de contexto e custos por token.

## 2. Roadmap Prático (Battle IA) - Foco no Cap. 10
**Objetivo:** Criar um ambiente de testes rigoroso para provar as hipóteses de compressão/engenharia de prompt.
- **Seleção de Modelos:** Escolher de 5 a 10 modelos de IA (ex: GPT-4o, Claude 3.5 Sonnet, Llama 3, Gemini 1.5, etc).
- **Técnicas de Prompting:** Selecionar as 3 técnicas diferentes de prompting que o Pinete (Felipe) comentou em aula (ex: Zero-shot, Few-shot, Chain-of-Thought ou técnicas com/sem LLMLingua).
- **Métricas Obrigatórias:** Em CADA teste, registrar obrigatoriamente:
  - Tamanho da Janela de Contexto do modelo.
  - Gasto de Tokens (Entrada/Saída).
- **Registro:** Tabular todos os resultados para criação de gráficos no TCC.

## 3. Aplicação Prática (App do Matheus)
**Objetivo:** Provar o valor da pesquisa em um cenário real.
- Aplicar as técnicas validadas no "Battle IA" diretamente no App desenvolvido pelo Matheus Sousa dos Santos.
- Mensurar a diferença de consumo de tokens e tempo de resposta na prática.

---

## ✅ Checklist de Execução e Prazos

### Ações Imediatas (Setembro)
- [ ] Registrar este 1º acompanhamento de orientação no WebTCC (Prazo: 19/09).
- [ ] Escrever o texto da Problematização e Argumentação (Integrar à Introdução/Justificativa).
- [ ] Definir a lista oficial dos 5 a 10 modelos que irão compor o "Battle IA".
- [ ] Formalizar quais serão as 3 técnicas de prompting (Pinete).

### Execução (Outubro)
- [ ] Rodar os testes do "Battle IA" e tabular "Gasto de Tokens" vs "Qualidade da Resposta".
- [ ] Integrar a melhor técnica no App do Matheus.
- [ ] Registrar o 2º acompanhamento no WebTCC (Prazo: 19/10).
- [ ] Consolidar os dados no **Capítulo 10: Resultados e Discussão**.

### Reta Final (Novembro)
- [ ] Fechar a **Conclusão** (Capítulo 11).
- [ ] **03/11 a 09/11:** Enviar PDF sem identificação para Banca Virtual (🚨 Crítico).
- [ ] **23/11 a 27/11:** Apresentação no SIMTCC.
- [ ] **24/11 a 27/11:** Entrega Final do PDF com nomes dos autores (Matheus, Felipe, Guilherme).
