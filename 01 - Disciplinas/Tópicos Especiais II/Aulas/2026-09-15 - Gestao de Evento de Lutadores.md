---
tags: [topicos-especiais-ii, java, poo, persistencia, arquivos, colecoes, arraylist]
date: 2026-09-15
professor: Luiz Claudio Chiavini Oliveira Junior
status: concluido
tema: Gestao de Evento de Lutadores com Persistencia em Arquivo
dataview_type: aula
---

# 2026-09-15 - Gestao de Evento de Lutadores (Persistencia em Arquivo)

## Conteudo da Aula

Continuacao do tema de persistencia em arquivos, agora aplicado a um sistema de cadastro e gestao de atletas para um evento de luta. A aula combinou:

- `ArrayList<T>` para gerenciar colecao de objetos em memoria
- `FileWriter` + `BufferedWriter` para escrita em arquivo texto
- `Scanner` sobre `File` para leitura e reconstrucao de objetos
- Formato CSV simples (`nome;modalidade;vitorias`) como protocolo de serializacao manual
- Loop de menu interativo com tratamento de excecao via `try/catch`

## Classes Produzidas

### Lutador.java
Entidade simples com encapsulamento (`private`) e dois metodos de saida:
- `exibirDados()` — exibe no console
- `salvarDados()` — retorna string no formato CSV para persistencia

### GestaoEvento.java
Classe principal com `main()` e dois metodos estaticos de I/O:
- `escreverDados(ArrayList<Lutador>)` — serializa a lista em `lutadores.txt`
- `carregarDados(ArrayList<Lutador>)` — le o arquivo, reconstroi objetos e popula a lista

## Bugs Corrigidos na Auditoria Pos-Aula

| Bug | Arquivo | Linha | Descricao |
|---|---|---|---|
| Ordem de leitura invertida | GestaoEvento.java | 29-35 | `nome` era lido apos o prompt de `modalidade`. Corrigido: cada `print` imediatamente seguido do `scanner.nextLine()` correspondente. |
| `lista.add()` ausente | GestaoEvento.java | 91 | `carregarDados()` instanciava `Lutador` mas nao adicionava na lista — dados carregados eram descartados imediatamente. |
| `scanner.nextLine()` residual | GestaoEvento.java | 37 | Newline residual apos `nextInt()` consumia o proximo `nextLine()`. Adicionado consumo explicito. |

## Arquivos

- [[Lutador.java]]
- [[GestaoEvento.java]]

## Conexoes

- [[2026-09-15 - Persistência em Arquivos]] — aula anterior sobre o mesmo tema
- [[Tópicos Especiais II]]
