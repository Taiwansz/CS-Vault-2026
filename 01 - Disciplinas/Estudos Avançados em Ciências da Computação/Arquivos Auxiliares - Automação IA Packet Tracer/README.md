---
tipo: guia-auxiliar
disciplina: Estudos Avançados em Ciências da Computação
tags:
  - ia
  - mcp
  - packet-tracer
  - automacao
  - script-engine
---

# 🤖 Guia de Automação do Cisco Packet Tracer com Inteligência Artificial

> [!info] 📌 Módulo Auxiliar de Automação
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Atividade Prática:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Resolução - Exercício Prático Aula 2 - TechSolutions|Resolução Exercício Aula 2 (TechSolutions)]]
> - **Tecnologia:** Protocolo MCP (Model Context Protocol) + Cisco Packet Tracer Script Engine

---

## 📦 Arquivos do Módulo

1. 🧩 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Arquivos Auxiliares - Automação IA Packet Tracer/V5.2.pts|V5.2.pts]]: Módulo de extensão compilado para o Cisco Packet Tracer (Script Engine + HTTP/File Bridge).
2. 🧠 [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Arquivos Auxiliares - Automação IA Packet Tracer/SKILL.md|SKILL.md]]: Definição da *Skill* de IA com o catálogo exato de dispositivos, cabos, comandos suportados e regras de validação.


---

## 🚀 Como Configurar no Cisco Packet Tracer (Passo a Passo)

### Passo 1: Carregar o Script Module no Packet Tracer
1. Abra o **Cisco Packet Tracer** (versão 8.x ou 9.x).
2. No menu superior, clique em:
   👉 **`Extensions`** $ightarrow$ **`Scripting`** $ightarrow$ **`Configure PT Script Modules...`**
3. Na janela que abrir, clique no botão **`Add...`**.
4. Selecione o arquivo **`V5.2.pts`** localizado nesta pasta.
5. Clique em **Abrir** e confirme com **OK**.

### Passo 2: Abrir a Interface da Extensão (MCP BUILDER)
1. No menu superior do Packet Tracer, clique em:
   👉 **`Extensions`** $ightarrow$ **`MCP BUILDER`**
2. Uma janela intitulada **PT-MCP Control Center** será aberta.
3. **Não precisa colar nada no editor da janela!** A extensão funciona de forma autônoma como uma ponte (*Bridge*) ouvindo requisições na porta local ou via sistema de arquivos.

---

## 🤖 Como a IA se Conecta e Constrói a Rede

1. **Deixe o Packet Tracer aberto** com a janela do MCP BUILDER aberta ou minimizada.
2. No seu ambiente de IA (Claude Code, Antigravity, Cursor etc.), tendo o servidor MCP `packet-tracer` ativo e a `SKILL.md` carregada, basta fazer o pedido em linguagem natural:
   > *"Crie uma topologia com 2 switches Core em EtherChannel LACP, 2 switches de acesso, 6 PCs separados nas VLANs 10, 20 e 30, e aplique os endereços IP."*
3. A IA executará os passos:
   - **Planejamento e Validação:** Dimensiona portas, cabos e endereçamento.
   - **Deploy em Tempo Real:** Insere switches, roteadores e computadores no canvas lógico.
   - **Configuração IOS:** Aplica VLANs, Trunks 802.1Q, EtherChannel e Port-Security.
   - **Verificação:** Executa pings e lê comandos `show` diretamente do simulador.

---

## 🛠️ Dicas e Resolução de Problemas

* **Status "NOT connected" no servidor MCP:** Se a IA reportar que o bridge não respondeu, verifique se o Cisco Packet Tracer está aberto e se o módulo `V5.2.pts` foi adicionado em *Configure PT Script Modules*.
* **Modais travados no Packet Tracer:** Se ocorrer algum aviso modal de erro na tela do Packet Tracer, feche-o clicando em OK para liberar a execução dos comandos em lote.
* **Salvamento do Arquivo:** O projeto pode ser salvo a qualquer momento pela IA ou manualmente usando `Ctrl + S` no Packet Tracer.
