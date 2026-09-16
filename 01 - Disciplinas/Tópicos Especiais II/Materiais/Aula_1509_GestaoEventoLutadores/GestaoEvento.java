import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class GestaoEvento {
    //vamos criar uma memoria para o nome do arquivo
    private static final String NOME_ARQUIVO = "lutadores.txt";

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ArrayList<Lutador> listaLutadores = new ArrayList<>();
        int opcao = 0;

        System.out.println("=======CADASTRO DE LUTADORES DO EVENTO=======");
        while (opcao !=3){
            System.out.println("Cadastrar novo lutador");
            System.out.println("Ver lutadores");
            System.out.println("Finalizar e salvar");
            System.out.println("Escolha a opção desejada");

            try{
                opcao = scanner.nextInt();
                scanner.nextLine();

                if(opcao == 1){
                    System.out.println("Informe o nome do atleta");
                    String nome = scanner.nextLine();
                    System.out.println("Informe a modalidade");
                    String modalidade = scanner.nextLine();
                    System.out.println("Informe a quantidade de vitórias");
                    int vitoria = scanner.nextInt();
                    scanner.nextLine(); // consumir newline residual
                    Lutador novoLutador = new Lutador(nome, modalidade, vitoria);
                    listaLutadores.add(novoLutador);
                    System.out.println("Atleta Cadastrado!");
                } else if (opcao == 2) {
                    if (listaLutadores.isEmpty()){
                        System.out.println("Não temos atletas");
                    } else{
                        for (Lutador i : listaLutadores){
                            i.exibirDados();
                        }
                    }

                } else if (opcao == 3) {
                    escreverDados(listaLutadores);
                    System.out.println("Finalizando Sistema");

                }else {
                    System.out.println("Opção não encontrada");
                }
            } catch (Exception e){
                System.out.println("Opção não encontrada");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    public static void escreverDados(ArrayList<Lutador> lista) {
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(NOME_ARQUIVO))){

            for(Lutador i : lista){
                escritor.write(i.salvarDados());
                escritor.newLine();
            }
        }catch (IOException e){
            System.out.println("Erro ao salvar: "+e.getMessage());

        }
    }

    public static void carregarDados(ArrayList<Lutador> lista) {
        File arquivo = new File(NOME_ARQUIVO);
        if(!arquivo.exists()){
            return;
        }

        try(Scanner leitorArquivo = new Scanner(arquivo)){
            while (leitorArquivo.hasNextLine()){
                String linha = leitorArquivo.nextLine();
                String[] infos = linha.split((";"));

                String nome = infos[0];
                String modalidade = infos[1];
                int vitoria = Integer.parseInt(infos[2]);

                Lutador lutadorDados = new Lutador(nome, modalidade, vitoria);
                lista.add(lutadorDados); // corrigido: estava instanciando sem adicionar
            }
        }catch (IOException e){
            System.out.println("ERRO ao carregar: " + e.getMessage());
        }
    }
}
