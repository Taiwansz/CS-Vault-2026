---
disciplina: Interação Humano Computador e Sistemas Multimídia
professor: Guilherme P. Bueno
data: 2026-08-21
tags:
  - faculdade
  - ihc
  - figma
  - auto-layout
  - don-norman
  - nielsen
  - revisao-p1
---

# Aula 03 — Prototipação e Engenharia de Telas com Figma

> Síntese técnica do material "Aula 3 - Introdução ao Figma" ministrado pelo Prof. Guilherme P. Bueno.

---

## 1. O Paradoxo do Código
- **Cenário:** O backend possui arquitetura impecável, pipelines assíncronos e latência menor que 2ms (`Latency: <2ms`). Porém, se o usuário não souber onde clicar em menos de **200 milissegundos**, o sistema falha.
- **Tese:** *Uma falha de interface é, em última análise, uma falha completa de sistema.*

---

## 2. O Sistema Operacional do Cérebro (Don Norman na Prática)

Na engenharia de componentes interativos (ex: um botão de ação), 4 pilares de Don Norman operam simultaneamente:

1. **Affordance (Afordância)**:
   - Características físicas ou visuais que comunicam a possibilidade de ação.
   - *No Figma:* Formato 3D, cantos arredondados (`border-radius: 8px`) e sombra projetada (`Drop Shadow Y:4, Blur:10`). Comunicam visualmente que o elemento é pressionável/clicável.
2. **Significante (Signifier)**:
   - Sinal explícito que indica *onde* e *qual* ação deve ser realizada.
   - *No Figma:* Texto claro e centralizado (ex: `"Confirmar Envio"`).
3. **Restrição (Constraint)**:
   - Bloqueio lógico ou físico que impede o usuário de cometer erros catastróficos.
   - *No Figma:* Botão desabilitado em cinza (`#CBD5E1`) sem sombra, comunicando a impossibilidade física de clique enquanto formulários estiverem incompletos.
4. **Feedback**:
   - Retorno sensorial imediato sobre o estado da aplicação.
   - *No Figma:* Mudança de cor no hover/active (`#10B981` -> `#059669`) e spinners de carregamento em requisições assíncronas.

---

## 3. O Try/Catch da Interface (Jakob Nielsen)

Mapeamento das heurísticas de Nielsen como tratamento de exceções de frontend:

| Heurística | Conceito de Interface | Implementação no Figma / Código |
| :--- | :--- | :--- |
| **H1: Visibilidade do Status** | O usuário deve sempre saber o que está acontecendo. | Spinners de loading, barras de progresso com delay simulado (`100ms - 1500ms`). |
| **H5: Prevenção de Erros** | Impedir que o erro ocorra antes que o usuário envie dados. | Bloqueio físico de caracteres inválidos no input (`User_Name@##$`), botões desabilitados. |
| **H6: Reconhecimento vs. Memorização** | Reduzir a carga na memória de trabalho do usuário. | Ícones visuais associados a rótulos de texto (Pastas, Engrenagens, Perfil). |
| **H9: Recuperação de Erros** | Mensagens claras de diagnóstico com linguagem humana. | Borda suave vermelha com mensagem instrutiva: *"E-mail incompleto. Use o formato: nome@unifaj.edu.br"* em vez de códigos de status como `HTTP 500`. |

---

## 4. Matriz de Tradução: Design no Figma <-> Frontend Code

O Figma não é uma ferramenta de desenho artístico; é um compilador visual que traduz propriedades vetoriais diretamente em especificações CSS:

| Ferramenta no Figma | Atalho | Equivalente no Código Frontend | Papel Arquitetural |
| :--- | :--- | :--- | :--- |
| **Retângulo / Círculo** | `R` / `O` | Geometria estática (`<svg>`, `<canvas>`) | Formas puras, vetores gráficos decorativos. |
| **Frame** | `F` | Contêiner inteligente (`<div>`, `<section>`, `<main>`) | Dispositivos e janelas com regras de recorte (`clip content`), grids e aninhamento. **Um frame NÃO é um retângulo.** |
| **Auto Layout** | `Shift + A` | CSS Flexbox (`display: flex`) | Posicionamento dinâmico adaptativo que responde ao crescimento de texto. |
| **Componentes** | `Ctrl + Alt + K` | Componentes React/Vue/Svelte | Elementos mestres reutilizáveis com herança de propriedades e variantes. |

---

## 5. Auto Layout como CSS Flexbox
- **Princípio:** Não desenhar caixas de coordenadas absolutas (`x: 120px, y: 340px`). Programar regras espaciais relacionais.
- **Mapeamento:**
  - Direção de fluxo (Horizontal / Vertical) -> `flex-direction: row / column`
  - Distância entre itens (`Gap`) -> `gap: 10px`
  - Espaçamento interno (`Padding`) -> `padding: 12px 24px`
  - Redimensionamento (`Hug Contents` / `Fill Container`) -> `width: fit-content` / `flex-grow: 1; width: 100%`

---

## 6. O Protótipo Interativo (3 Estados, 1 Fluxo)
- **Necessidade:** Usuários clicam repetidas vezes em botões de checkout se a interface não responder visualmente em menos de 100ms.
- **Fluxo do Protótipo na aba Prototype:**
  1. `Tela 01 (Formulário: Input)`
  2. Gatilho: `On click` -> Ação: `Navigate to` -> Animação: `Smart Animate (300ms)`
  3. `Tela 02 (Processando... Loading Spinner)`
  4. Gatilho: `After delay (1500ms)` -> Ação: `Navigate to` -> Animação: `Dissolve`
  5. `Tela 03 (Confirmado com Sucesso Checkmark)`
- **Handoff:** Uso da aba Inspect para extrair propriedades CSS (`box-shadow`, `border-radius`, `fill`) e código SVG vetorial limpo.
