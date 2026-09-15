---
tipo: aula
disciplina: "Tópicos Especiais II"
data: 2026-09-15
assunto: "Persistência em Arquivos Texto, Manipulação de Strings e Formato CSV com Lutador e Gestão de Eventos"
professor: "Luiz Claudio Chiavini Oliveira Junior"
status: concluido
---

# 🥊 Aula 15/09 — Persistência em Arquivos e Manipulação de Fluxos de Dados em Java

> [!info] 📌 Informações da Aula
> - **Data:** 15/09/2026
> - **Disciplina:** [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Tópicos Especiais II]]
> - **Professor:** Luiz Claudio Chiavini Oliveira Junior
> - **Tópicos:** Fluxos de E/S (`FileWriter`, `BufferedWriter`, `FileReader`, `BufferedReader`), tratamento de exceções com `try-with-resources`, serialização plana delimitada (CSV) e interface interativa via `Scanner`.

---

## 🎯 Objetivo da Aula e Desafio Prático

A transição entre estruturas puramente voláteis em memória RAM (`ArrayList`) e sistemas corporativos reais requer a capacidade de gravar e restaurar o estado dos objetos em disco físico.

Nesta aula, implementamos um sistema de **Gestão de Eventos Esportivos de Lutas/Artes Marciais**, com cadastro de atletas, consulta e persistência contínua em formato textual delimitado (`lutadores.txt`).

```
  ┌────────────────┐         salvarDados()          ┌───────────────────────┐
  │ Lutador Object ├───────────────────────────────►│ lutadores.txt         │
  │ - nome         │  (nome + ";" + mod + ";" + v)  │                       │
  │ - modalidade   │◄───────────────────────────────┤ Charles;Jiu-Jitsu;34  │
  │ - vitoria      │          fromCsv()             │ Poatan;Kickboxing;12  │
  └────────────────┘                                └───────────────────────┘
          ▲
          │ ArrayList<Lutador>
  ┌───────┴────────┐
  │  GestaoEvento  │
  │ (Menu / CLI)   │
  └────────────────┘
```

---

## 🛠️ Fundamentos Técnicos Implementados

### 1. Escrita com `BufferedWriter` e `FileWriter`
Para otimizar o acesso ao disco e não realizar escritas caractere por caractere (que degradam performance I/O), encapsulamos o `FileWriter` dentro de um `BufferedWriter`:

```java
try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
    for (Lutador l : listaLutadores) {
        bw.write(l.salvarDados());
        bw.newLine();
    }
}
```

### 2. Padrão de Serialização CSV Customizado
A entidade `Lutador` encapsula a própria regra de representação externa:
- `salvarDados()`: produz uma linha no padrão `Nome;Modalidade;Vitorias`.
- `fromCsv(String linha)`: método estático de fábrica (*static factory*) que realiza o `split(";")` e faz o parse tipado dos dados.

### 3. Leitura e Reconstituição de Estado na Inicialização
Ao abrir o sistema, o método `carregarDados()` verifica a existência de `lutadores.txt` e remonta os objetos em memória antes de apresentar o menu principal ao operador.

---

## 💻 Código Fonte Organizado no Cofre

Todos os arquivos estão organizados na pasta de materiais:
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_1509_PersistenciaArquivos/Lutador.java|Lutador.java]] — Entidade de domínio com métodos de serialização CSV e parsing
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_1509_PersistenciaArquivos/GestaoEvento.java|GestaoEvento.java]] — Menu interativo e motor de persistência I/O
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_1509_PersistenciaArquivos/Main.java|Main.java]] — Ponto de entrada da aplicação
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_1509_PersistenciaArquivos/lutadores.txt|lutadores.txt]] — Base de dados em texto plano
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_1509_PersistenciaArquivos/README.md|README.md]] — Documentação e instruções de execução
