class Elemento {
    int chave;
    String nome;

    Elemento(int chave, String nome) {
        this.chave = chave;
        this.nome = nome;
    }
}

class ListaEstatica {
    int numElem;
    int max;
    Elemento[] lista;

    ListaEstatica(int max) {
        lista = new Elemento[max];
        numElem = 0;
        this.max = max;
    }

    int tamanho() {
        return numElem;
    }

    boolean insereFinalLista(Elemento l) {
        if (numElem == max)
            return false;
        lista[numElem] = l;
        numElem++;
        return true;
    }

    boolean insereNaPosicao(Elemento l, int pos) {
        if (numElem == max || pos < 0 || pos > numElem)
            return false;
        for (int i = numElem; i > pos; i--) {
            lista[i] = lista[i - 1];
        }
        lista[pos] = l;
        numElem++;
        return true;
    }

    boolean excluiPorChave(int chave) {
        int pos = -1;
        for (int i = 0; i < numElem; i++) {
            if (lista[i].chave == chave) {
                pos = i;
                break;
            }
        }
        if (pos == -1)
            return false;
        for (int i = pos; i < numElem - 1; i++) {
            lista[i] = lista[i + 1];
        }
        lista[numElem - 1] = null;
        numElem--;
        return true;
    }

    void imprime() {
        for (int i = 0; i < numElem; i++) {
            System.out.println("chave " + lista[i].chave + " nome " + lista[i].nome);
        }
    }

    void reinicializaLista() {
        numElem = 0;
    }

    public static void main(String args[]) {
        System.out.println("Lista Estatica para 5 elementos");
        ListaEstatica le = new ListaEstatica(5);
        System.out.println("Tamanho da lista " + le.tamanho());
        le.insereFinalLista(new Elemento(1, "Carlos"));
        le.insereFinalLista(new Elemento(2, "Alexandre"));
        le.imprime();
        System.out.println("Tamanho da lista " + le.tamanho());

        le.insereNaPosicao(new Elemento(3, "Maria"), 1);
        le.imprime();
        System.out.println("Tamanho da lista " + le.tamanho());

        le.excluiPorChave(2);
        le.imprime();
        System.out.println("Tamanho da lista " + le.tamanho());
        System.out.println("Reinicializando a lista");
        le.reinicializaLista();
        System.out.println("Tamanho da lista " + le.tamanho());

    }
}
