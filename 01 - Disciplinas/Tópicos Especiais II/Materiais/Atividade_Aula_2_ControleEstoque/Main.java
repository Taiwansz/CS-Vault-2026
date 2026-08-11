public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("    🏭 PROJETO 2 - SISTEMA DE CONTROLE DE ESTOQUE DE GALPÃO");
        System.out.println("==================================================================\n");

        // 1. Teste da classe Produto (estoque inicia obrigatoriamente com 0)
        System.out.println("--- 📦 1. CADASTRO E MOVIMENTAÇÃO DE PRODUTOS ---");
        Produto p1 = new Produto("Caixa de Ferramentas Industriais", 350.00);
        Produto p2 = new Produto("Palete de Madeira Tratada", 120.50);

        System.out.println(">> Estado Inicial (Estoque deve ser 0):");
        p1.exibirInformacoes();
        p2.exibirInformacoes();

        System.out.println("\n>> Realizando Entradas no Estoque:");
        p1.adicionarEstoque(50);
        p2.adicionarEstoque(120);

        System.out.println("\n>> Estado Atualizado:");
        p1.exibirInformacoes();
        p2.exibirInformacoes();

        System.out.println("\n>> Realizando Saída do Estoque:");
        p1.removerEstoque(15);
        p1.exibirInformacoes();

        // 2. Teste da hierarquia de Funcionários (Classe Abstrata e Subclasses)
        System.out.println("\n--- 👥 2. EQUIPE DO GALPÃO (CLASSES ABSTRATAS E HERANÇA) ---");
        FuncProducao operador = new FuncProducao("Carlos Eduardo", 3200.00, "Manhã");
        Gestor gestor = new Gestor("Ana Beatriz", 7800.00);

        System.out.println(">> Métodos Específicos:");
        operador.operar();
        gestor.atribuirTarefas();

        System.out.println("\n>> Demonstração do Polimorfismo com a Classe Abstrata Funcionario:");
        Funcionario[] equipe = { operador, gestor };

        for (Funcionario f : equipe) {
            System.out.println("\nColaborador: " + f.getNome() + " | Salário: R$ " + String.format("%.2f", f.getSalario()));
            f.trabalhar(); // Chamada ao método abstrato implementado em cada classe
        }

        System.out.println("\n==================================================================");
        System.out.println("                     FIM DA EXECUÇÃO DO PROJETO 2");
        System.out.println("==================================================================");
    }
}