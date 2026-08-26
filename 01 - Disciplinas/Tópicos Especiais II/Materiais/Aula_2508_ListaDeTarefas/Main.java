import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Tarefa> listaDeTarefas = new ArrayList<>();

        System.out.println("---BEM VINDO MEU NOBRE---");
        int opcao = 0;
        while (opcao != 4) {
            System.out.println("1 - Adionar nova tarefa");
            System.out.println("2 - Listar tarefas");
            System.out.println("3 - Marcar tarefa como conluida");
            System.out.println("4 - Sair");
            System.out.println("Informe a opção desejada: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.println("Informe a descrição da sua tarefa: ");
                String descricao = scanner.nextLine();

//                Vamos instanciar nossa tarefa (obj tarefa)
                Tarefa novaTarefa = new Tarefa(descricao);

//                Vamos usar um add para colocar a informação no array
                listaDeTarefas.add(novaTarefa);
                System.out.println("Tarefa adicionada com sucesso!");
            } else if (opcao == 2) {
//            problema.... e se a lista estiver vazia?
                if (listaDeTarefas.isEmpty()) {
                    System.out.println("Lista de tarefas vazia");
                } else {
                    for (int i = 0; i < listaDeTarefas.size(); i++) {
                        Tarefa t = listaDeTarefas.get(i);
                        t.exibirTarefa(i + 1);
                    }

                }
            } else if (opcao == 3) {
//            mesmo problema... o sistema pode nao ter tarefas
                if (listaDeTarefas.isEmpty()) {
                    System.out.println("sem tarefas a concluir");
                } else {
                    System.out.println("Qual tarefa você quer concluir? ");
                    int numeroTarefa = scanner.nextInt();
                    int indiceReal = numeroTarefa - 1;

//                problema 2 - indice fora do cadastro
//                temos que garantir que o usuario só informe valores
//                existentes no arraylist
                    if (indiceReal >= 0 && indiceReal < listaDeTarefas.size()) {
                        Tarefa t = listaDeTarefas.get(indiceReal);
                        t.marcaComoConcluido();
                        System.out.println("Parabéns, não fez mais que a sua obrigação");
                    }

                }
            } else if (opcao == 4) {
                System.out.println("thau!");

            } else {
                System.out.println("opcao não encontradaa!");
            }
        }
        scanner.close();
    }
}
