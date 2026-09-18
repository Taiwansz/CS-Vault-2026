import java.util.InputMismatchException;
import java.util.Scanner;

public class PainelDrone {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int altitude = 0;
        double velocidade = 0.0;
        boolean altitudeValida = false;
        boolean dadosValidados = false;

        System.out.println("=== PAINEL DE CONTROLO DO DRONE ===");

        while (!dadosValidados) {
            try {
                if (!altitudeValida) {
                    System.out.println("Digite a altitude desejada (0 a 120m):");
                    System.out.print("> ");
                    altitude = scanner.nextInt();

                    if (altitude < 0 || altitude > 120) {
                        if (altitude > 120) {
                            throw new IllegalArgumentException("Altitude inválida: O limite máximo é 120 metros.");
                        } else {
                            throw new IllegalArgumentException("Altitude inválida: A altitude não pode ser negativa.");
                        }
                    }

                    altitudeValida = true;
                    System.out.println("Altitude aceite. Digite a velocidade (0 a 60 km/h):");
                } else {
                    System.out.println("Digite a velocidade (0 a 60 km/h):");
                }

                System.out.print("> ");
                velocidade = scanner.nextDouble();

                if (velocidade < 0.0 || velocidade > 60.0) {
                    if (velocidade > 60.0) {
                        throw new IllegalArgumentException("Velocidade inválida: O limite máximo é 60 km/h.");
                    } else {
                        throw new IllegalArgumentException("Velocidade inválida: A velocidade não pode ser negativa.");
                    }
                }

                dadosValidados = true;
                System.out.println("[SUCESSO] Dados validados. Drone em rota!");

            } catch (InputMismatchException e) {
                System.out.println("[ALERTA CRÍTICO] Falha de comunicação: Digite apenas números! Pouso de emergência evitado.");
                System.out.println();
                scanner.nextLine(); // limpa o buffer
            } catch (IllegalArgumentException e) {
                System.out.println("[ALERTA DE SEGURANÇA] " + e.getMessage());
                System.out.println();
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}
