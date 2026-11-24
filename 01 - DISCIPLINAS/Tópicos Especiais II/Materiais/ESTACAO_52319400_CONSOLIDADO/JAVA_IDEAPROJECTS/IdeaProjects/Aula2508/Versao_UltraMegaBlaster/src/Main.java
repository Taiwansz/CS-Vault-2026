import service.GerenciadorTarefasService;
import view.ConsoleView;
import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GerenciadorTarefasService service = new GerenciadorTarefasService();

        boolean modoCli = false;
        for (String arg : args) {
            if ("--cli".equalsIgnoreCase(arg) || "-c".equalsIgnoreCase(arg)) {
                modoCli = true;
                break;
            }
        }

        if (modoCli) {
            ConsoleView consoleView = new ConsoleView(service);
            consoleView.iniciar();
        } else {
            SwingUtilities.invokeLater(() -> {
                try {
                    MainFrame frame = new MainFrame(service);
                    frame.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Falha ao inicializar interface gráfica: " + e.getMessage());
                    System.out.println("Iniciando em modo Console CLI...");
                    ConsoleView consoleView = new ConsoleView(service);
                    consoleView.iniciar();
                }
            });
        }
    }
}
