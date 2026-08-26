---
tipo: aula
disciplina: "Tópicos Especiais II"
data: 2026-08-25
assunto: "Sistema de Lista de Tarefas em Java (Exercícios 1, 2 e 3 + Versão Ultra Mega Blaster GUI)"
professor: "Luiz Claudio Chiavini Oliveira Junior"
status: concluido
---

# 📋 Aula 25/08 — Gestor de Tarefas Completo & Desafios de Fixação

> [!info] 📌 Informações da Aula e Desafios
> - **Data:** 25/08/2026
> - **Disciplina Hub:** [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Tópicos Especiais II]]
> - **Professor:** Luiz Claudio Chiavini Oliveira Junior
> - **Desafios Implementados:**
>   - **Exercício 1:** Remover Tarefa (Gestão de Memória com `.remove(indiceReal)` e validações)
>   - **Exercício 2:** Painel de Estatísticas (Iteração, contagem de total, concluídas e pendentes)
>   - **Exercício 3:** Pesquisar Tarefa (Filtro de busca por substring com `.contains()`)

---

## 🎯 Estrutura das Soluções Desenvolvidas

Para atender plenamente a atividade, o projeto foi organizado em **duas versões distintas**:

1. **📁 Versão Padrão (`Versao_Padrao`):**
   - Atende estritamente aos 3 exercícios solicitados de forma limpa, direta e bem estruturada via console interativo Java.
   - Trata exceções de entrada (`NumberFormatException`), previne `IndexOutOfBoundsException` ao remover/concluir e exibe o painel estatístico no formato exato requerido.

2. **🚀 Versão ULTRA MEGA BLASTER (`Versao_UltraMegaBlaster`):**
   - **Interface Gráfica Nativa Swing:** Dashboard moderno com tabela interativa, barra de progresso visual, checkbox de conclusão rápida e visualização por categorias e prioridades.
   - **Pesquisa em Tempo Real (Exercício 3):** Filtragem instantânea conforme o usuário digita na barra de busca.
   - **Gestão de Memória Avançada (Exercício 1):** Sistema de **Desfazer (Undo)** com lixeira temporária e botão para limpeza em lote de tarefas concluídas.
   - **Estatísticas Dinâmicas (Exercício 2):** Métricas atualizadas em tempo real com gráfico de porcentagem, contagem por prioridade alta e exportação de relatórios em Markdown.
   - **Persistência de Dados Automática:** Salva e carrega automaticamente as tarefas em arquivo local CSV/dados.
   - **Suporte Dual (GUI & CLI):** Pode ser executado com interface gráfica ou em modo terminal interativo (`--cli`).

---

## ☕ 1. Código Fonte — Versão Padrão (Exercícios 1, 2 e 3)

### `Main.java` (Versão Padrão)
- 📄 Local do Arquivo: `Versao_Padrao/src/Main.java`

```java
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Tarefa> listaDeTarefas = new ArrayList<>();

        int opcao = 0;
        while (opcao != 7) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Adicionar nova tarefa");
            System.out.println("2 - Listar tarefas");
            System.out.println("3 - Marcar tarefa como concluída");
            System.out.println("4 - Remover tarefa (Exercício 1)");
            System.out.println("5 - Estatísticas (Exercício 2)");
            System.out.println("6 - Pesquisar tarefa (Exercício 3)");
            System.out.println("7 - Sair");
            System.out.print("Informe a opção desejada: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    System.out.print("Informe a descrição: ");
                    String desc = scanner.nextLine();
                    listaDeTarefas.add(new Tarefa(desc));
                    System.out.println("✅ Tarefa adicionada!");
                }
                case 2 -> {
                    for (int i = 0; i < listaDeTarefas.size(); i++) {
                        listaDeTarefas.get(i).exibirTarefa(i + 1);
                    }
                }
                case 3 -> {
                    System.out.print("Número da tarefa para concluir: ");
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < listaDeTarefas.size()) {
                        listaDeTarefas.get(idx).marcaComoConcluido();
                    }
                }
                case 4 -> { // EXERCÍCIO 1: REMOVER TAREFA
                    System.out.print("Número da tarefa para remover: ");
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < listaDeTarefas.size()) {
                        Tarefa removida = listaDeTarefas.remove(idx);
                        System.out.println("🗑️ Tarefa \"" + removida.getDescricao() + "\" removida!");
                    }
                }
                case 5 -> { // EXERCÍCIO 2: ESTATÍSTICAS
                    int total = listaDeTarefas.size(), concluidas = 0, pendentes = 0;
                    for (Tarefa t : listaDeTarefas) {
                        if (t.isConcluido()) concluidas++; else pendentes++;
                    }
                    System.out.println("📊 Total: " + total + " | Concluídas: " + concluidas + " | Pendentes: " + pendentes);
                }
                case 6 -> { // EXERCÍCIO 3: PESQUISAR TAREFA
                    System.out.print("Digite o termo de busca: ");
                    String termo = scanner.nextLine().toLowerCase();
                    for (int i = 0; i < listaDeTarefas.size(); i++) {
                        if (listaDeTarefas.get(i).getDescricao().toLowerCase().contains(termo)) {
                            listaDeTarefas.get(i).exibirTarefa(i + 1);
                        }
                    }
                }
            }
        }
    }
}
```

---

## ⚡ 2. Arquivos de Código no Cofre
- 📁 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_2508_Versao_Padrao/src/Main.java|Versão Padrão — Main.java]]
- 📁 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_2508_Versao_Padrao/src/Tarefa.java|Versão Padrão — Tarefa.java]]
- 🚀 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_2508_Versao_UltraMegaBlaster/src/Main.java|Versão Ultra Mega Blaster — App Principal]]
- 🚀 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_2508_Versao_UltraMegaBlaster/src/view/MainFrame.java|Versão Ultra Mega Blaster — GUI (Swing)]]
- 🚀 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_2508_Versao_UltraMegaBlaster/src/service/GerenciadorTarefasService.java|Versão Ultra Mega Blaster — Serviço & Regras de Negócio]]
