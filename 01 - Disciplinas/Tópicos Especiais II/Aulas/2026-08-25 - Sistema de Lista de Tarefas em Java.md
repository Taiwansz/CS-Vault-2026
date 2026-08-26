---
tipo: aula
disciplina: "Tópicos Especiais II"
data: 2026-08-25
assunto: "Sistema de Lista de Tarefas em Java - ArrayList, Instanciação de Objetos e Encapsulamento"
professor: "Luiz Claudio Chiavini Oliveira Junior"
status: concluido
---

# 📋 Aula 25/08 — Sistema de Lista de Tarefas (ArrayList & POO em Java)

> [!info] 📌 Informações da Aula
> - **Data:** 25/08/2026
> - **Disciplina Hub:** [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Tópicos Especiais II]]
> - **Professor:** Luiz Claudio Chiavini Oliveira Junior
> - **Tópicos:** Manipulação de Coleções (`ArrayList`), Instanciação Dinâmica de Objetos, Encapsulamento, Métodos Mutadores (`marcaComoConcluido`) e Estrutura de Menu Interativo no Console.

---

## 🎯 Objetivo e Conceitos Abordados

Nesta aula foi desenvolvido um **Gerenciador de Tarefas (To-Do List)** interativo via linha de comando em Java. 

Os principais objetivos pedagógicos e técnicos incluíram:
1. **Modelagem de Entidade (`Tarefa`):** Encapsulamento de atributos (`descricao` e `concluido`) com visibilidade privada e exposição via getters/setters/métodos utilitários.
2. **Uso de `ArrayList<Tarefa>`:** Armazenamento dinâmico de objetos instanciados em tempo de execução sem limite fixo de tamanho.
3. **Validação de Limites de Array/Lista:** Tratamento de condições de lista vazia (`isEmpty()`) e validação de índices (`indiceReal >= 0 && indiceReal < listaDeTarefas.size()`) para evitar erros do tipo `IndexOutOfBoundsException`.
4. **Ciclo de Vida do Objeto:** Instanciação dinâmica através de `new Tarefa(descricao)` a partir da entrada do usuário (`Scanner`).

---

## ☕ Código Fonte Implementado

### 1. `Tarefa.java`

```java
public class Tarefa {
    // atributos da nossa classe tarefas
    private String descricao;
    private boolean concluido;

    public Tarefa(String descricao) {
        this.descricao = descricao;
        this.concluido = false;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    //"""""""SETTER""""""""""
    public void marcaComoConcluido(){
        this.concluido = true;
    }

    public void exibirTarefa(int indice){
        String status = this.concluido ? "[x]" : "[]";
        System.out.println(indice + " " + status + " " + this.descricao);
    }
}
```

---

### 2. `Main.java`

```java
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
```

---

## 🔗 Arquivos Locais no Cofre
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_2508_ListaDeTarefas/Tarefa.java|Tarefa.java]]
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_2508_ListaDeTarefas/Main.java|Main.java]]
