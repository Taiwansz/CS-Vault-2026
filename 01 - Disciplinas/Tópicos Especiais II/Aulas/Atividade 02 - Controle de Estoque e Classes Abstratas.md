---
tipo: atividade
disciplina: Tópicos Especiais II
data: 2026-08-11
professor: Luiz Claudio Chiavini Oliveira Junior
status: concluido
---

# 📦 Atividade 02 — Projeto 2: Controle de Estoque e Classes Abstratas em Java

> [!success] 🚀 Status: Concluído e Implementado
> - **Disciplina:** Tópicos Especiais II
> - **Professor:** Luiz Claudio Chiavini Oliveira Junior
> - **Data:** 11/08/2026
> - **Foco:** Abstração, Classes Abstratas, Herança, Encapsulamento e Polimorfismo no Java.

---

## 🎯 Requisitos da Atividade

### 1. Classe `Produto`
- **Atributos:**
  - `nome` (String)
  - `preco` (double)
  - `quantidadeEmEstoque` (int)
- **Regra do Construtor:** Deve obrigatoriamente receber `nome` e `preco`, mas a `quantidadeEmEstoque` **sempre deve ser inicializada com zero (`0`)**.
- **Métodos:** Getters, Setters, `adicionarEstoque(int)`, `removerEstoque(int)` e `exibirInformacoes()`.

### 2. Classe Abstrata `Funcionario` (Superclasse)
- **Princípio Obrigatório:** Utilização de **Classes Abstratas**.
- **Atributos:**
  - `nome` (String)
  - `salario` (double)
- **Método Abstrato:** `trabalhar()` — exige que cada subclasse implemente sua própria regra de trabalho.

### 3. Classe `FuncProducao` (Subclasse)
- Extende `Funcionario`.
- **Atributo Adicional:** `turno` (String).
- **Métodos:**
  - `operar()` — simula o manuseio de máquinas/equipamentos no galpão.
  - `@Override trabalhar()` — define o trabalho específico da produção.

### 4. Classe `Gestor` (Subclasse)
- Extende `Funcionario`.
- **Métodos:**
  - `atribuirTarefas()` — simula a distribuição de tarefas para a equipe.
  - `@Override trabalhar()` — define o trabalho específico de gestão e auditoria.

### 5. Classe `Main`
- Demonstração do cadastro de produtos com estoque zerado por padrão.
- Teste de entradas e saídas de mercadorias no galpão.
- Demonstração de polimorfismo utilizando a superclasse abstrata `Funcionario`.

---

## 🔗 Arquivos de Código-Fonte no Cofre

- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_2_ControleEstoque/Produto.java|Produto.java]]
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_2_ControleEstoque/Funcionario.java|Funcionario.java]]
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_2_ControleEstoque/FuncProducao.java|FuncProducao.java]]
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_2_ControleEstoque/Gestor.java|Gestor.java]]
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_2_ControleEstoque/Main.java|Main.java]]
