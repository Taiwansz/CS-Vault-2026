---
tipo: atividade
disciplina: Interação Humano Computador e Sistemas Multimídia
data: 2026-10-09
professor: Guilherme de Paula Bueno
status: concluido
tags:
  - faculdade
  - ihc
  - engenharia-de-software
  - don-norman
  - jakob-nielsen
  - figma
  - usabilidade
---

# Atividade Teórico-Prática de IHC — Fundamentos, Norman e Nielsen

> [!info] Dados Institucionais
> - **Instituição:** Centro Universitário Max Planck (UniMAX / UniEduK)
> - **Disciplina:** Interação Humano Computador e Sistemas Multimídia
> - **Professor:** Guilherme de Paula Bueno
> - **Data:** 09/10/2026
> - **Aluno:** Matheus Sousa dos Santos — **RA: 52319400**
> - **Status:** Concluído (Gabarito Analítico Completo)

---

## 1. Engenharia de Software vs. IHC (Qualidade de Uso)
- **Cenário:** Sistema transacional com 98% de cobertura de testes e zero exceções no backend, porém 40% dos usuários erram o destinatário de transferências por ambiguidade nos rótulos de confirmação.
- **Alternativa Correta:**
  > **O software cumpre requisitos funcionais, mas falha na qualidade de uso (ausência de bugs não garante usabilidade).**
- **Fundamentação Técnica:**
  A Engenharia de Software tradicional atesta a integridade do código, a conformidade funcional e a ausência de exceções em tempo de execução. Entretanto, a Interação Humano-Computador avalia a eficácia, eficiência e segurança cognitiva do operador humano no contexto da tarefa. Quando uma interface induz 40% de erro operacional sistêmico por deficiência semiótica de rótulos, o produto falha na qualidade de uso. Código sem bugs não equivale a software utilizável.

---

## 2. Psicologia de Norman: Significante e Affordance
- **Cenário:** Link crítico em interface web com texto cinza idêntico aos parágrafos, sem sublinhado, sombra ou borda.
- **Alternativa Correta:**
  > **Falta um significante para sinalizar a affordance de clique existente no elemento.**
- **Fundamentação Técnica:**
  Don Norman estabelece que *affordance* é a relação relacional entre as propriedades físicas/digitais de um artefato e as capacidades do agente (o elemento no DOM é clicável). Em interfaces gráficas planas, affordances não são puramente táteis, exigindo *significantes* (signifiers) — pistas perceptuais como sublinhados, variação cromática, sombras ou cursores — para indicar onde e como a ação deve ocorrer. Sem o significante, a affordance permanece oculta.

---

## 3. Modelos Mentais vs. Modelos de Implementação
- **Cenário:** Identificação da imposição do modelo de implementação sobre o modelo mental do usuário.
- **Alternativa Correta:**
  > **Um sistema acadêmico exigir o código relacional da tabela SQL para realizar a matrícula.**
- **Fundamentação Técnica:**
  O *modelo mental* representa a visão intuitiva que o usuário tem do domínio do problema (selecionar disciplinas pelo nome e horário). O *modelo de implementação* reflete a arquitetura técnica subjacente e o esquema relacional do banco de dados. Exigir chaves primárias ou códigos relacionais de SQL transfere o custo cognitivo da infraestrutura para o usuário final, violando o princípio do modelo manifesto (represented model) formulado por Alan Cooper.

---

## 4. Heurística de Nielsen H1: Visibilidade do Status do Sistema
- **Cenário:** Envio de arquivo de 100 MB congela a tela por 30s sem barra de progresso ou bloqueio de botão, levando a cliques repetidos.
- **Alternativa Correta:**
  > **Quebra do feedback contínuo, gerando incerteza no usuário e requisições concorrentes no servidor.**
- **Fundamentação Técnica:**
  A Heurística 1 prescreve que o sistema deve manter o usuário informado sobre o estado operacional em tempo razoável. A ausência de feedback de upload somada à falta de desativação do botão (ausência de debounce/idempotência na interface) cria vácuo de informação. O usuário assume falha, dispara múltiplos cliques (*rage clicks*) e submete requisições concorrentes idênticas que sobrecarregam o servidor.

---

## 5. Nielsen H5 vs. H9: Prevenção de Erros e Recuperação
- **Cenário:** Bloquear dias passados em um seletor de datas e exibir erros de conexão em linguagem humana orientada à solução.
- **Alternativa Correta:**
  > **Prevenção de erros e Recuperação clara de erros.**
- **Fundamentação Técnica:**
  Desabilitar dias passados no seletor impossibilita fisicamente o envio de dados anômalos antes que a ação ocorra, correspondendo à **Heurística 5 (Prevenção de Erros)**. Apresentar mensagens em linguagem clara, sem códigos crípticos de rede e com caminhos de resolução imediatos, corresponde à **Heurística 9 (Ajudar os usuários a reconhecer, diagnosticar e recuperar-se de erros)**.

---

## 6. Restrições de Norman (Constraints): Intertravamento Físico
- **Cenário:** Impedir a abertura da gaveta de um drive ótico enquanto o disco gira em alta rotação.
- **Alternativa Correta:**
  > **Física, pois atua na mecânica e geometria do próprio artefato.**
- **Fundamentação Técnica:**
  As restrições físicas (physical constraints) impõem limites diretamente no meio mecânico, elétrico ou geométrico, impedindo ações destrutivas ou incorretas antes que ocorram (conceito de *interlock*). Não demandam inferência lógica, convenção cultural ou compreensão semântica por parte do operador; a barreira é mecânica.

---

## 7. Heurística de Nielsen H6: Reconhecimento em vez de Memorização
- **Cenário:** Substituição de digitação manual de códigos por autocompletar e histórico dinâmico.
- **Alternativa Correta:**
  > **A memória operacional de curto prazo ser restrita, sendo o reconhecimento visual cognitivamente menos oneroso.**
- **Fundamentação Técnica:**
  A memória de trabalho de curto prazo humana possui capacidade restrita (7 ± 2 elementos). O esforço de recuperação espontânea (*recall*) demanda alta carga cognitiva. O reconhecimento visual (*recognition*) apoia-se em estímulos perceptuais visíveis, permitindo que o cérebro ative conexões prévias com mínimo consumo de energia cognitiva, alinhando-se à Heurística 6 de Nielsen.

---

## 8. Figma: Auto Layout e Mapeamento de Engenharia Frontend
- **Cenário:** Estruturação de botão com texto e contêiner usando Auto Layout com padding e alinhamento.
- **Alternativa Correta:**
  > **O modelo de caixas responsivo baseado em CSS Flexbox.**
- **Fundamentação Técnica:**
  O recurso de Auto Layout do Figma é uma transposição direta do modelo de caixas do CSS Flexbox. Propriedades como padding horizontal/vertical, gap entre itens (spacing), direção (vertical/horizontal) e alinhamento (start, center, space-between) mapeiam exatamente para `padding`, `gap`, `flex-direction`, `align-items` e `justify-content` na árvore DOM.

---

## 9. Avaliação Heurística vs. Testes com Usuários
- **Cenário:** Equipe afirma sistema totalmente validado por avaliação heurística conduzida apenas pelo desenvolvedor principal.
- **Alternativa Correta:**
  > **A inspeção analítica auxilia a achar erros evidentes, mas requer múltiplos avaliadores e não substitui testes com usuários no contexto real.**
- **Fundamentação Técnica:**
  A avaliação heurística é uma metodologia de inspeção analítica que depende de múltiplos avaliadores especialistas (a curva empírica de Nielsen aponta de 3 a 5 profissionais para cobrir cerca de 75-80% dos problemas). Um único avaliador captura menos de 35% dos erros. Ademais, o próprio desenvolvedor sofre de viés cognitivo e cegueira do modelo de implementação. Por fim, inspeções heurísticas identificam violações formais de boas práticas, mas não substituem o comportamento real do usuário em testes de usabilidade empíricos.
