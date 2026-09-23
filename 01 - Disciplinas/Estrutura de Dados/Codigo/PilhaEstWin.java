import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

class Elemento {
    int chave;
    String nome;

    Elemento(int chave, String nome) {
        this.chave = chave;
        this.nome = nome;
    }
}

class PilhaEstaticaWin {
    int topo;
    int max;
    Elemento[] pilha;
    JFrame janela;
    JPanel painel;

    PilhaEstaticaWin(int max) {
        pilha = new Elemento[max];
        topo = -1;
        this.max = max;
        janela = new JFrame("Lista Estatica 5 elementos");
        janela.setSize(400, 300);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.add(new JLabel("Pilha Estatica para 5 elementos"));
    }

    int tamanho() {
        return topo + 1;
    }

    boolean pushPilha(Elemento l) {
        if (topo >= max - 1)
            return false;
        pilha[++topo] = l;
        return true;
    }

    Elemento popPilha() {
        if (topo == -1)
            return null;
        return pilha[--topo];

    }

    void imprime() {
        for (int i = topo; i >= 0; i--) {
            // System.out.println("chave " + pilha[i].chave + " nome " + pilha[i].nome);
            painel.add(new JLabel("chave " + pilha[i].chave + " nome " + pilha[i].nome));
            painel.revalidate();
            painel.repaint();
        }
    }

    void reinicializaPilha() {
        topo = -1;
    }

    public static void main(String args[]) {

        // System.out.println("\nPilha Estatica para 5 elementos\n\n");
        PilhaEstaticaWin le = new PilhaEstaticaWin(5);

        // System.out.println("Tamanho da pilha " + le.tamanho());
        le.painel.add(new JLabel("Tamanho da pilha " + le.tamanho()));
        le.pushPilha(new Elemento(1, "Carlos"));
        le.pushPilha(new Elemento(2, "Alexandre"));
        le.imprime();
        // System.out.println("Tamanho da pilha " + le.tamanho());
        le.painel.add(new JLabel("Tamanho da pilha " + le.tamanho()));
        le.pushPilha(new Elemento(3, "Maria"));
        le.imprime();
        // System.out.println("Tamanho da pilha " + le.tamanho());

        // System.out.println("Excluido " + le.popPilha().chave);
        le.painel.add(new JLabel("Excluido " + le.popPilha().chave));
        le.imprime();
        // System.out.println("Tamanho da pilha " + le.tamanho());
        // System.out.println("Reinicializando a pilha");
        le.reinicializaPilha();
        // System.out.println("Tamanho da pilha " + le.tamanho());
        le.janela.add(le.painel);

        // Torna a janela visível
        le.janela.setVisible(true);
    }
}
