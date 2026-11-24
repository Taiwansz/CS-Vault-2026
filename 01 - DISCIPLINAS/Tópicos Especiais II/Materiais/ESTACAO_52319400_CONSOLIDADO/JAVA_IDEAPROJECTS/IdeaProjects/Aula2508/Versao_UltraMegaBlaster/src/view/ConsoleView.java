package view;

import model.Categoria;
import model.Prioridade;
import model.Tarefa;
import service.GerenciadorTarefasService;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private final GerenciadorTarefasService service;
    private final Scanner scanner;

    public ConsoleView(GerenciadorTarefasService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=========================================================");
        System.out.println(" 🚀 GESTOR DE TAREFAS ULTRA MEGA BLASTER (CLI EDITION) ");
        System.out.println("=========================================================");

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            try {
                System.out.print("👉 Escolha uma opção: ");
                opcao = Integer.parseInt(scanner.nextLine().trim());
                executarOpcao(opcao);
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida! Por favor, digite um número inteiro.");
            } catch (Exception e) {
                System.out.println("⚠️ Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n---------------- MENU DE NAVEGAÇÃO ----------------");
        System.out.println("1. ➕ Adicionar Nova Tarefa (Completa)");
        System.out.println("2. 📋 Listar Todas as Tarefas");
        System.out.println("3. ✅ Alternar Status (Concluir / Pendente)");
        System.out.println("4. 🗑️ Remover Tarefa por Número (Exercício 1)");
        System.out.println("5. 📊 Painel de Estatísticas & Progresso (Exercício 2)");
        System.out.println("6. 🔍 Pesquisar por Palavra-Chave (Exercício 3)");
        System.out.println("7. 🧹 Limpar Tarefas Concluídas (Lixeira em Lote)");
        System.out.println("8. ↩️ Desfazer Última Remoção");
        System.out.println("0. 🚪 Sair");
        System.out.println("---------------------------------------------------");
    }

    private void executarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> adicionarTarefa();
            case 2 -> listarTarefas();
            case 3 -> alternarStatus();
            case 4 -> removerTarefa();
            case 5 -> exibirEstatisticas();
            case 6 -> pesquisarTarefas();
            case 7 -> limparConcluidas();
            case 8 -> desfazerRemocao();
            case 0 -> System.out.println("👋 Encerrando o sistema. Suas tarefas foram salvas automaticamente!");
            default -> System.out.println("❌ Opção não reconhecida! Escolha uma opção do menu.");
        }
    }

    private void adicionarTarefa() {
        System.out.println("\n--- ➕ ADICIONAR TAREFA COMPLETA ---");
        System.out.print("Descrição da tarefa: ");
        String desc = scanner.nextLine().trim();
        if (desc.isEmpty()) {
            System.out.println("❌ A descrição não pode ser vazia!");
            return;
        }

        System.out.println("\nSelecione a Categoria:");
        Categoria[] categorias = Categoria.values();
        for (int i = 0; i < categorias.length; i++) {
            System.out.printf("%d. %s\n", i + 1, categorias[i].getDescricaoFormatada());
        }
        System.out.print("Escolha (1-" + categorias.length + "): ");
        int catIndex = lerInteiroValido(1, categorias.length) - 1;
        Categoria categoria = categorias[catIndex];

        System.out.println("\nSelecione a Prioridade:");
        Prioridade[] prioridades = Prioridade.values();
        for (int i = 0; i < prioridades.length; i++) {
            System.out.printf("%d. %s\n", i + 1, prioridades[i].getDescricaoFormatada());
        }
        System.out.print("Escolha (1-" + prioridades.length + "): ");
        int prioIndex = lerInteiroValido(1, prioridades.length) - 1;
        Prioridade prioridade = prioridades[prioIndex];

        System.out.print("Observações (opcional): ");
        String obs = scanner.nextLine().trim();

        Tarefa t = service.adicionar(desc, categoria, prioridade, obs);
        System.out.println("✅ Tarefa [" + t.getId() + "] adicionada com sucesso!");
    }

    private void listarTarefas() {
        System.out.println("\n--- 📋 LISTA COMPLETA DE TAREFAS ---");
        List<Tarefa> lista = service.getTodas();
        if (lista.isEmpty()) {
            System.out.println("📭 Nenhuma tarefa cadastrada no momento.");
            return;
        }

        exibirTabelaTarefas(lista);
    }

    private void alternarStatus() {
        List<Tarefa> lista = service.getTodas();
        if (lista.isEmpty()) {
            System.out.println("📭 Nenhuma tarefa para alterar status.");
            return;
        }
        listarTarefas();
        System.out.print("Digite o número da tarefa para alternar o status: ");
        int num = lerInteiroValido(1, lista.size());
        Tarefa t = lista.get(num - 1);
        service.alternarStatus(t.getId());
        System.out.println("🔄 Status da tarefa \"" + t.getDescricao() + "\" alterado com sucesso!");
    }

    private void removerTarefa() {
        // Exercício 1: Remover Tarefa
        List<Tarefa> lista = service.getTodas();
        if (lista.isEmpty()) {
            System.out.println("📭 Nenhuma tarefa para remover.");
            return;
        }
        listarTarefas();
        System.out.print("Digite o número da tarefa que deseja remover (1 a " + lista.size() + "): ");
        int num = lerInteiroValido(1, lista.size());
        int indiceReal = num - 1;
        
        Tarefa removida = lista.get(indiceReal);
        boolean ok = service.removerPorIndice(indiceReal);
        if (ok) {
            System.out.println("🗑️ Tarefa \"" + removida.getDescricao() + "\" removida com sucesso!");
            System.out.println("💡 Dica: Escolha a opção 8 se desejar desfazer esta ação.");
        } else {
            System.out.println("❌ Falha ao remover tarefa.");
        }
    }

    private void exibirEstatisticas() {
        // Exercício 2: Painel de Estatísticas
        System.out.println("\n--- 📊 PAINEL DE ESTATÍSTICAS & PROGESSO ---");
        GerenciadorTarefasService.Estatisticas stats = service.getEstatisticas();
        
        System.out.println("• Total de Tarefas: " + stats.total());
        System.out.println("• Tarefas Concluídas: " + stats.concluidas());
        System.out.println("• Tarefas Pendentes: " + stats.pendentes());
        System.out.println("• Pendentes de Alta Prioridade: " + stats.altaPrioridadePendentes());
        System.out.printf("• Progresso Geral: %.1f%%\n\n", stats.porcentagemConclusao());
        
        // Exemplo formatado conforme solicitado no Exercício 2:
        System.out.println("📌 Formato Padrão: Total: " + stats.total() + " | Concluídas: " + stats.concluidas() + " | Pendentes: " + stats.pendentes());
    }

    private void pesquisarTarefas() {
        // Exercício 3: Pesquisar Tarefa
        System.out.println("\n--- 🔍 PESQUISAR TAREFA ---");
        System.out.print("Digite o termo ou palavra-chave para busca: ");
        String termo = scanner.nextLine().trim();

        if (termo.isEmpty()) {
            System.out.println("⚠️ O termo de busca não pode ser vazio.");
            return;
        }

        List<Tarefa> encontradas = service.pesquisar(termo);
        if (encontradas.isEmpty()) {
            System.out.println("❌ Nenhuma tarefa encontrada para o termo \"" + termo + "\".");
        } else {
            System.out.println("✅ " + encontradas.size() + " tarefa(s) encontrada(s):");
            exibirTabelaTarefas(encontradas);
        }
    }

    private void limparConcluidas() {
        int qtd = service.limparConcluidas();
        System.out.println("🧹 " + qtd + " tarefa(s) concluída(s) foram movidas para a lixeira.");
    }

    private void desfazerRemocao() {
        if (service.desfazerUltimaRemocao()) {
            System.out.println("↩️ Última tarefa removida foi restaurada com sucesso!");
        } else {
            System.out.println("⚠️ Não há tarefas na lixeira para desfazer.");
        }
    }

    private void exibirTabelaTarefas(List<Tarefa> tarefas) {
        System.out.printf("%-4s | %-8s | %-6s | %-28s | %-12s | %-10s\n", "Nº", "ID", "Status", "Descrição", "Categoria", "Prioridade");
        System.out.println("---------------------------------------------------------------------------------------");
        for (int i = 0; i < tarefas.size(); i++) {
            Tarefa t = tarefas.get(i);
            String status = t.isConcluido() ? "[X] Concluída" : "[ ] Pendente";
            String descTruncada = t.getDescricao().length() > 27 ? t.getDescricao().substring(0, 24) + "..." : t.getDescricao();
            System.out.printf("%-4d | %-8s | %-6s | %-28s | %-12s | %-10s\n",
                    (i + 1), t.getId(), status, descTruncada, t.getCategoria().name(), t.getPrioridade().name());
        }
    }

    private int lerInteiroValido(int min, int max) {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) {
                    return val;
                }
                System.out.print("❌ Valor fora do intervalo (" + min + "-" + max + "). Digite novamente: ");
            } catch (NumberFormatException e) {
                System.out.print("❌ Por favor, digite um número inteiro válido: ");
            }
        }
    }
}
