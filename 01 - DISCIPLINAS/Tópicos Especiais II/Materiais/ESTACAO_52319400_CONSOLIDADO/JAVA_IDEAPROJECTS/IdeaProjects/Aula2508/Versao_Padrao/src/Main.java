import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Tarefa> listaDeTarefas = new ArrayList<>();

        System.out.println("==========================================");
        println("  GESTOR DE TAREFAS - VERSÃO EXERCÍCIOS  ");
        System.out.println("==========================================");

        int opcao = 0;
        while (opcao != 7) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Adicionar nova tarefa");
            System.out.println("2 - Listar tarefas");
            System.out.println("3 - Marcar tarefa como concluída");
            System.out.println("4 - Remover tarefa");
            System.out.println("5 - Estatísticas");
            System.out.println("6 - Pesquisar tarefa");
            System.out.println("7 - Sair");
            System.out.print("Informe a opção desejada: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("\nInforme a descrição da sua tarefa: ");
                    String descricao = scanner.nextLine();
                    if (descricao.trim().isEmpty()) {
                        System.out.println("A descrição não pode ser vazia!");
                    } else {
                        Tarefa novaTarefa = new Tarefa(descricao);
                        listaDeTarefas.add(novaTarefa);
                        System.out.println("✅ Tarefa adicionada com sucesso!");
                    }
                    break;

                case 2:
                    System.out.println("\n--- LISTA DE TAREFAS ---");
                    if (listaDeTarefas.isEmpty()) {
                        System.out.println("Lista de tarefas vazia.");
                    } else {
                        for (int i = 0; i < listaDeTarefas.size(); i++) {
                            Tarefa t = listaDeTarefas.get(i);
                            t.exibirTarefa(i + 1);
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n--- CONCLUIR TAREFA ---");
                    if (listaDeTarefas.isEmpty()) {
                        System.out.println("Sem tarefas para concluir.");
                    } else {
                        System.out.print("Qual tarefa você quer concluir? (Informe o número): ");
                        try {
                            int numeroTarefa = Integer.parseInt(scanner.nextLine());
                            int indiceReal = numeroTarefa - 1;

                            if (indiceReal >= 0 && indiceReal < listaDeTarefas.size()) {
                                Tarefa t = listaDeTarefas.get(indiceReal);
                                if (t.isConcluido()) {
                                    System.out.println("Esta tarefa já estava concluída!");
                                } else {
                                    t.marcaComoConcluido();
                                    System.out.println("🎉 Tarefa concluída com sucesso!");
                                }
                            } else {
                                System.out.println("❌ Índice inválido! Tarefa não encontrada.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("❌ Entrada inválida! Digite um número inteiro.");
                        }
                    }
                    break;

                case 4:
                    // Exercício 1: Remover Tarefa (Gestão de Memória)
                    System.out.println("\n--- REMOVER TAREFA ---");
                    if (listaDeTarefas.isEmpty()) {
                        System.out.println("Sem tarefas para remover.");
                    } else {
                        System.out.print("Informe o número da tarefa que deseja apagar: ");
                        try {
                            int numeroRemover = Integer.parseInt(scanner.nextLine());
                            int indiceReal = numeroRemover - 1;

                            if (indiceReal >= 0 && indiceReal < listaDeTarefas.size()) {
                                Tarefa removida = listaDeTarefas.remove(indiceReal);
                                System.out.println("🗑️ Tarefa \"" + removida.getDescricao() + "\" removida com sucesso!");
                            } else {
                                System.out.println("❌ Índice inválido! Tarefa não encontrada.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("❌ Entrada inválida! Digite um número inteiro.");
                        }
                    }
                    break;

                case 5:
                    // Exercício 2: Painel de Estatísticas (Iteração e Contagem)
                    System.out.println("\n--- PAINEL DE ESTATÍSTICAS ---");
                    if (listaDeTarefas.isEmpty()) {
                        System.out.println("Nenhuma tarefa cadastrada até o momento.");
                    } else {
                        int total = listaDeTarefas.size();
                        int concluidas = 0;
                        int pendentes = 0;

                        for (int i = 0; i < listaDeTarefas.size(); i++) {
                            Tarefa t = listaDeTarefas.get(i);
                            if (t.isConcluido()) {
                                concluidas++;
                            } else {
                                pendentes++;
                            }
                        }

                        System.out.println("📊 Total: " + total + " | Concluídas: " + concluidas + " | Pendentes: " + pendentes);
                    }
                    break;

                case 6:
                    // Exercício 3: Pesquisar Tarefa (Filtro de Busca)
                    System.out.println("\n--- PESQUISAR TAREFA ---");
                    if (listaDeTarefas.isEmpty()) {
                        System.out.println("Nenhuma tarefa para pesquisar.");
                    } else {
                        System.out.print("Digite o termo ou palavra-chave para busca: ");
                        String termo = scanner.nextLine().trim();

                        if (termo.isEmpty()) {
                            System.out.println("Termo de busca não pode ser vazio.");
                        } else {
                            System.out.println("Resultados encontrados para \"" + termo + "\":");
                            boolean encontrou = false;
                            for (int i = 0; i < listaDeTarefas.size(); i++) {
                                Tarefa t = listaDeTarefas.get(i);
                                if (t.getDescricao().toLowerCase().contains(termo.toLowerCase())) {
                                    t.exibirTarefa(i + 1);
                                    encontrou = true;
                                }
                            }
                            if (!encontrou) {
                                System.out.println("Nenhuma tarefa encontrada com o termo informado.");
                            }
                        }
                    }
                    break;

                case 7:
                    System.out.println("\nSaindo do sistema... Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida! Escolha um número de 1 a 7.");
                    break;
            }
        }
        scanner.close();
    }

    private static void println(String msg) {
        System.out.println(msg);
    }
}
