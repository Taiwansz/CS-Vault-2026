import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GestaoEvento {
    private static final String NOME_ARQUIVO = "lutadores.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Lutador> listaLutadores = new ArrayList<>();
        int opcao = 0;

        // Carregar lutadores já cadastrados no arquivo persistido
        carregarDados(listaLutadores);

        System.out.println("=============================================");
        System.out.println("     CADASTRO DE LUTADORES DO EVENTO         ");
        System.out.println("=============================================");

        while (opcao != 3) {
            System.out.println("\nMenu de Opções:");
            System.out.println("1 - Cadastrar novo lutador");
            System.out.println("2 - Ver lutadores cadastrados");
            System.out.println("3 - Finalizar e salvar");
            System.out.print("Escolha a opção desejada: ");

            try {
                if (!scanner.hasNextInt()) {
                    System.out.println("Opção inválida! Digite um número.");
                    scanner.nextLine();
                    continue;
                }
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consome quebra de linha

                if (opcao == 1) {
                    System.out.print("Informe o nome do atleta: ");
                    String nome = scanner.nextLine();

                    System.out.print("Informe a modalidade: ");
                    String modalidade = scanner.nextLine();

                    System.out.print("Informe a quantidade de vitórias: ");
                    int vitoria = scanner.nextInt();
                    scanner.nextLine();

                    Lutador novoLutador = new Lutador(nome, modalidade, vitoria);
                    listaLutadores.add(novoLutador);
                    System.out.println("-> Atleta cadastrado com sucesso!");

                } else if (opcao == 2) {
                    System.out.println("\n--- LISTA DE ATLETAS DO EVENTO ---");
                    if (listaLutadores.isEmpty()) {
                        System.out.println("Nenhum atleta cadastrado no momento.");
                    } else {
                        for (int i = 0; i < listaLutadores.size(); i++) {
                            System.out.print("[" + (i + 1) + "] ");
                            listaLutadores.get(i).exibirDados();
                        }
                    }

                } else if (opcao == 3) {
                    salvarDados(listaLutadores);
                    System.out.println("Finalizando o sistema... Até logo!");

                } else {
                    System.out.println("Opção não encontrada. Tente novamente.");
                }

            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    /**
     * Salva a lista completa de lutadores no arquivo de texto formatado.
     */
    public static void salvarDados(ArrayList<Lutador> listaLutadores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Lutador l : listaLutadores) {
                bw.write(l.salvarDados());
                bw.newLine();
            }
            System.out.println("-> Dados salvos com sucesso no arquivo: " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no arquivo: " + e.getMessage());
        }
    }

    /**
     * Lê e carrega os lutadores existentes do arquivo de texto na inicialização.
     */
    public static void carregarDados(ArrayList<Lutador> listaLutadores) {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    Lutador l = Lutador.fromCsv(linha);
                    if (l != null) {
                        listaLutadores.add(l);
                    }
                }
            }
            System.out.println("[INFO] " + listaLutadores.size() + " atleta(s) carregado(s) de " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.out.println("[AVISO] Não foi possível ler arquivo existente: " + e.getMessage());
        }
    }
}
