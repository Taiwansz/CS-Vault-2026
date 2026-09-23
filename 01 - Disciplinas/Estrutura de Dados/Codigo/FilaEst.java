import javax.swing.JTextArea;

class Elemento {
    int chave;
    String nome;

    Elemento(int chave, String nome) {
        this.chave = chave;
        this.nome = nome;
    }
}

class FilaEstatica {
    int inicio;
    int numElem;
    int max;
    Elemento[] fila;

    FilaEstatica(int max) {
        fila = new Elemento[max];
        inicio = 0;
        numElem = 0;
        this.max = max;
    }

    int tamanho() {
        return numElem;
    }

    boolean inserirElemento(Elemento l) {
        if (numElem >= max)
            return false;
        int posicao = (inicio + numElem) % max;
        fila[posicao] = l;
        numElem++;
        return true;
    }

    Elemento excluirElemento() {
        if (numElem == 0)
            return null;
        Elemento l = fila[inicio];
        inicio = (inicio + 1) % max;
        numElem--;
        return l;

    }

    void imprime(JTextArea txtF) {
        txtF.append("\nListagem da Fila\n");
        int pos = inicio;
        for (int i = 0; i < numElem; i++) {
            // System.out.println("chave " + fila[i].chave + " nome " + fila[i].nome);
            txtF.append("chave " + fila[pos].chave + " nome " + fila[pos].nome + "\n");
            pos = (pos + 1) % max;
        }
        txtF.append("------------------------------------\n");
    }

    void reinicializaFila() {
        inicio = 0;
        numElem = 0;
    }
}
