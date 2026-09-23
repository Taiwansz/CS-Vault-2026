class Elemento {
    int chave;
    String nome;

    Elemento(int chave, String nome) {
        this.chave = chave;
        this.nome = nome;
    }
}

class PilhaEstatica {
    int topo;
    int max;
    Elemento[] pilha;

    PilhaEstatica(int max) {
        pilha = new Elemento[max];
        topo = -1;
        this.max = max;
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
            System.out.println("chave " + pilha[i].chave + " nome " + pilha[i].nome);
        }
    }

    void reinicializaPilha() {
        topo = -1;
    }

    public static void main(String args[]) {
        System.out.println("\nPilha Estatica para 5 elementos\n\n");
        PilhaEstatica le = new PilhaEstatica(5);
        System.out.println("Tamanho da pilha " + le.tamanho());
        le.pushPilha(new Elemento(1, "Carlos"));
        le.pushPilha(new Elemento(2, "Alexandre"));
        le.imprime();
        System.out.println("Tamanho da lista " + le.tamanho());

        le.pushPilha(new Elemento(3, "Maria"));
        le.imprime();
        System.out.println("Tamanho da lista " + le.tamanho());

        System.out.println("Excluido " + le.popPilha().chave);
        le.imprime();
        System.out.println("Tamanho da lista " + le.tamanho());
        System.out.println("Reinicializando a lista");
        le.reinicializaPilha();
        System.out.println("Tamanho da lista " + le.tamanho());

    }
}
