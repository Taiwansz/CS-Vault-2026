---
tipo: aula
disciplina: Interação Humano Computador e Sistemas Multimídia
professor: Guilherme
tema: "Acessibilidade Digital, WCAG 2.2, WAI-ARIA e Design Inclusivo"
status: concluido
---

> [!NOTE] Navegacao no Cofre
> **Painel Geral:** [[00 - Dashboard]] | **Indice de Aulas:** [[Índice Geral de Aulas]]
> **Disciplina:** [[Interação Humano Computador e Sistemas Multimídia]] | **Nucleo ENADE:** [[08 - Formacao Geral, Etica, LGPD e Sociedade Digital (Do Basico ao Avancado)]]

# Guia de Acessibilidade Digital WCAG 2.2 e Design Inclusivo

> [!INFO] **Visao Geral do Modulo**
> A acessibilidade web (e-Accessibility) assegura que pessoas com deficiencia (visual, auditiva, motora ou cognitiva) possam perceber, compreender, navegar e interagir eficazmente com a tecnologia digital. A norma internacional e a **WCAG 2.2** (Web Content Accessibility Guidelines) do W3C.

---

## 1. Os 4 Principios Fundamentais (POUR)

1. **Perceptivel (Perceivable):** As informacoes e os componentes da interface devem ser apresentados de maneira que os usuarios possam percebe-los (nao podem ser invisiveis a todos os sentidos).
 - Alternativas em texto para midias nao-textuais (`alt` descritivo em imagens).
 - Legendas e audiodescricao em videos.
 - Contraste minimo de cores: **4.5:1** para texto normal e **3:1** para texto grande (Nivel AA).

2. **Operavel (Operable):** Os componentes de interface e a navegacao devem ser operaveis atraves de multiplos dispositivos de entrada.
 - Acessibilidade total via teclado (`Tab`, `Shift+Tab`, `Enter`, `Espaco`).
 - Foco visual evidente (`:focus-visible` destacado sem `outline: none` irresponsavel).
 - Tempo suficiente para leitura e interacao (evitar timeouts rigidos).
 - Evitar animacoes com piscamento frequente (> 3 flashes/segundo) para prevencao de convulsoes.

3. **Compreensivel (Understandable):** As informacoes e a operacao da interface do usuario devem ser compreensiveis.
 - Idioma da pagina explicitado (`<html lang="pt-BR">`).
 - Comportamento previsivel (mudancas de contexto nao devem ocorrer ao focar elementos).
 - Assistencia de entrada: prevencao e identificacao automatica de erros em formularios.

4. **Robusto (Robust):** O conteudo deve ser robusto o suficiente para ser interpretado confiavelmente por uma ampla variedade de agentes de usuario, incluindo tecnologias assistivas (leitores de tela como NVDA, JAWS e TalkBack).
 - HTML semantico estrito (`<nav>`, `<main>`, `<article>`, `<button>` em vez de `<div onclick>`).
 - Uso correto de papeis e estados WAI-ARIA (`aria-expanded`, `aria-label`, `aria-live`).

---

## 2. Niveis de Conformidade da WCAG

- **Nivel A:** Requisitos minimos essenciais. Sem eles, o acesso e impossivel para grupos inteiros de pessoas.
- **Nivel AA (Padrao Global de Compliance e Legislacao):** Elimina as maiores barreiras de usabilidade para a maioria das pessoas. E o nivel exigido pela legislacao brasileira (LBI — Lei Brasileira de Inclusao, Lei nº 13.146/2015) e europeia.
- **Nivel AAA:** O mais alto nivel de acessibilidade especializada (ex: contraste ultra-alto 7:1 e traducao integral em lingua de sinais).
