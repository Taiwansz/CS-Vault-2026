# Aula 15/09/2026 — Persistência em Arquivos e Gestão de Eventos

Exercício prático de Programação Orientada a Objetos em Java ministrado pelo Prof. Luiz Claudio Chiavini Oliveira Junior.

## Objetivos e Conceitos Abordados
- **Streams de Arquivos:** `FileWriter` e `BufferedWriter` para gravação eficiente em disco.
- **Leitura e Desserialização:** `FileReader` e `BufferedReader` com conversão de linhas de texto CSV delimitadas por `;` em instâncias de objetos `Lutador`.
- **Coleções Dinâmicas:** Gerenciamento da lista em memória via `ArrayList<Lutador>`.
- **Tratamento de Exceções:** Blocos `try-catch` e `try-with-resources` para fechamento seguro de canais de E/S (`IOException`).
- **Interface Console Interativa:** Menu em loop `while` com leitura validada via `Scanner`.

## Arquivos
- `Lutador.java`: Entidade de domínio com métodos de serialização/desserialização CSV (`salvarDados()` e `fromCsv()`).
- `GestaoEvento.java`: Controlador central da aplicação com menu interativo e rotinas de leitura/escrita de arquivo.
- `Main.java`: Ponto de entrada do sistema.
- `lutadores.txt`: Arquivo de texto persistente contendo os registros salvos.

## Compilação e Execução
```bash
javac *.java
java Main
```
