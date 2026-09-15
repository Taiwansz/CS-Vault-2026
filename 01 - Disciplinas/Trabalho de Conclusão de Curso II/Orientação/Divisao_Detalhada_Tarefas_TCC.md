---
tipo: planejamento-tcc
disciplina: Trabalho de Conclusão de Curso II
atualizado_em: 2026-09-14
status: ativo
---

# Plano de Ação: Detalhamento de Tarefas por Integrante

Este documento descreve minuciosamente *o que* cada membro do grupo deve fazer, *como* deve fazer e *qual o resultado esperado* (entregável) de cada etapa. O foco é a execução do "Battle IA" e a escrita do Capítulo 10.

---

## 🟨 Juan Pedro (RA: 52319411) — Research & Writer
**Foco:** Garantir que o trabalho tenha embasamento acadêmico e justificativa de negócio clara.

### 1. Escrever a Problematização e Argumentação (Capítulo 1 e 10)
- **O que fazer:** Escrever por que esse TCC é necessário. Você precisa pesquisar e citar artigos falando sobre o alto custo financeiro das APIs (OpenAI, Anthropic) e a barreira da Janela de Contexto (quando a IA "esquece" coisas antigas do prompt).
- **Abordagem Green AI:** Trazer o argumento da "pegada de carbono" (treinar e inferir LLMs gasta muita energia). Mostrar que a engenharia/compressão de prompt é uma atitude sustentável.
- **Entregável:** Textos finalizados para a Introdução e base argumentativa para a escolha dos modelos no Cap 10.

### 2. Redação do Capítulo 11 (Conclusão e Trabalhos Futuros)
- **O que fazer:** Após o fim dos testes, você deve escrever a conclusão baseando-se nos dados reais. Exemplo prático: *"Concluímos que a técnica X economiza Y% de tokens e reduz a latência, mantendo a precisão..."*
- **Entregável:** O Capítulo 11 escrito e finalizado, amarrando tudo o que foi provado no Battle IA.

---

## 🟦 Matheus Sousa (RA: 52319400) — Dev Core & API
**Foco:** Engenharia de software, automação dos testes e aplicação na prática.

### 1. Configurar Integração com os 5 a 10 Modelos
- **O que fazer:** Você vai criar o ambiente de testes (pode ser um script em Python, Node.js ou até um pipeline). Você precisará gerar/pegar as chaves de API para os modelos escolhidos (Ex: GPT-3.5, GPT-4, Claude 3, Llama, Gemini).
- **Entregável:** Um ambiente pronto que consiga receber um prompt genérico e disparar para todas as IAs simultaneamente.

### 2. Automatizar a Coleta de Dados (Battle IA)
- **O que fazer:** O script deve, ao bater na API de cada IA, extrair do JSON de retorno (payload) os metadados cruciais: **Tokens de Entrada** (Prompt), **Tokens de Saída** (Completion) e **Tempo de Resposta** (Latência).
- **Entregável:** Um arquivo JSON ou CSV contendo todos esses dados brutos extraídos das APIs após os testes.

### 3. Integração no App (Aplicação Prática)
- **O que fazer:** Pegar a técnica de prompt que "vencer" a batalha (a mais barata/eficiente) e implementar direto no código-fonte do App que você desenvolveu. 
- **Entregável:** O app rodando com o prompt otimizado e a demonstração empírica de que no software real também houve economia.

---

## 🟩 Felipe (Pinete) (RA: 52319337) — Prompt Engineering
**Foco:** Definir as regras do jogo. Garantir que as técnicas de IA aplicadas estão academicamente corretas.

### 1. Formalizar as 3 Técnicas de Prompting
- **O que fazer:** Você vai construir o *conteúdo* exato dos prompts que o Matheus vai disparar. Baseado na sua fala em aula, prepare 3 versões (ex: *Zero-shot* direto, *Few-shot* com exemplos, e uma técnica de *Chain-of-Thought* ou Compressão usando *LLMLingua*).
- **Entregável:** Um documento contendo as strings finais dos Prompts exatos que serão rodados no teste.

### 2. Analisar a Qualidade Semântica (BERTScore)
- **O que fazer:** Não basta gastar menos token, a IA tem que responder certo. Você ficará responsável por medir se a resposta da IA que usou o prompt comprimido/menor teve a mesma qualidade da resposta original. Vai usar a métrica BERTScore (ou BLEU/ROUGE) para não deixar a avaliação subjetiva.
- **Entregável:** Um laudo técnico comparando a "Qualidade vs Economia de Tokens".

### 3. Revisar o Capítulo 3 (Engenharia de Prompt)
- **O que fazer:** Atualizar o referencial teórico do TCC para garantir que as 3 técnicas usadas no Capítulo 10 estejam muito bem explicadas com base em autores e artigos no Capítulo 3.

---

## 🟥 Guilherme W. (RA: 52420339) — Data & Management
**Foco:** Tratar os dados, formatar visualmente e garantir que o grupo não perca prazos.

### 1. Tabulação e Geração de Gráficos (Capítulo 10)
- **O que fazer:** Pegar o CSV sujo/JSON que o Matheus gerar nos testes e transformar isso em gráficos acadêmicos. Gráfico de Barras comparando Modelos (Janela de Contexto vs Gasto de Token), Gráfico de Dispersão (Preço vs Qualidade do Felipe).
- **Entregável:** Todas as imagens/figuras prontas e formatadas para serem inseridas no TCC.

### 2. Escrita do Capítulo 10 (Resultados e Discussão)
- **O que fazer:** Inserir os gráficos gerados no Word, formatar segundo a ABNT e escrever o texto explicando o que cada gráfico significa. Você vai traduzir o experimento do Matheus e do Felipe em texto científico.

### 3. Gestão WebTCC e Formatação (PDF)
- **O que fazer:** Cuidar do calendário oficial. Garantir a postagem da 1ª Ata de Orientação (19/Set), e das futuras. Revisar todo o TCC (citações, margens) para gerar o **PDF cego** (sem o nome de vocês) que vai para a Banca Virtual de **03 a 09 de Novembro**.
- **Entregável:** TCC formatado, sem erros de ABNT e atas submetidas na plataforma WebTCC.
