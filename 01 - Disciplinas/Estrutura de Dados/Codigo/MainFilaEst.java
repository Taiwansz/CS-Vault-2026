import javax.swing.*;
import java.awt.*;

public class MainFilaEst {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Fila Estatica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        JTextArea textArea = new JTextArea();
        Font font = new Font("Arial", Font.PLAIN, 18);
        textArea.setFont(font);

        FilaEstatica f = new FilaEstatica(5);

        textArea.append("Tamanho da fila " + f.tamanho() + "\n");
        f.inserirElemento(new Elemento(1, "Carlos"));
        f.inserirElemento(new Elemento(2, "Alexandre"));
        f.imprime(textArea);
        textArea.append("\nTamanho da fila " + f.tamanho() + "\n");
        f.inserirElemento(new Elemento(3, "Maria"));
        f.imprime(textArea);
        textArea.append("\nTamanho da fila " + f.tamanho() + "\n");
        textArea.append("\nExcluido " + f.excluirElemento().chave);
        f.imprime(textArea);
        textArea.append("\nTamanho da fila " + f.tamanho() + "\n");
        textArea.append("\nReinicializando a Fila");
        f.reinicializaFila();
        textArea.append("\nTamanho da fila " + f.tamanho() + "\n");
        frame.add(new JScrollPane(textArea));
        frame.setVisible(true);
    }
}
