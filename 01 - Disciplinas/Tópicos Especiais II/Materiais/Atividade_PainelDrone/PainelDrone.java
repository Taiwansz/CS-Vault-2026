import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class PainelDrone {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.forLanguageTag("pt-BR"));

        boolean altitudeValida = false;
        boolean dadosValidados = false;

        System.out.println("=== PAINEL DE CONTROLO DO DRONE ===");

        while (!dadosValidados) {
            try {
                if (!altitudeValida) {
                    System.out.println("Digite a altitude desejada (0 a 120m):");
                    System.out.print("> ");

                    int altitude = scanner.nextInt();

                    if (altitude < 0) {
                        throw new IllegalArgumentException(
                                "Altitude inválida: O valor mínimo é 0 metros.");
                    }

                    if (altitude > 120) {
                        throw new IllegalArgumentException(
                                "Altitude inválida: O limite máximo é 120 metros.");
                    }

                    altitudeValida = true;
                    System.out.println("Altitude aceite. Digite a velocidade (0 a 60 km/h):");
                } else {
                    System.out.println("Digite a velocidade (0 a 60 km/h):");
                }

                System.out.print("> ");
                double velocidade = scanner.nextDouble();

                if (velocidade < 0.0) {
                    throw new IllegalArgumentException(
                            "Velocidade inválida: O valor mínimo é 0 km/h.");
                }

                if (velocidade > 60.0) {
                    throw new IllegalArgumentException(
                            "Velocidade inválida: O limite máximo é 60 km/h.");
                }

                dadosValidados = true;
                System.out.println("[SUCESSO] Dados validados. Drone em rota!");

            } catch (InputMismatchException e) {
                System.out.println(
                        "[ALERTA CRÍTICO] Falha de comunicação: Digite apenas números! "
                                + "Pouso de emergência evitado.");
                System.out.println();
                scanner.nextLine();

            } catch (IllegalArgumentException e) {
                System.out.println("[ALERTA DE SEGURANÇA] " + e.getMessage());
                System.out.println();
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}
