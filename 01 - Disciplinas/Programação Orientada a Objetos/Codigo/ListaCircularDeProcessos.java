// ListaCircularDeProcessos.java
public class ListaCircularDeProcessos {

    private static class NoProcesso {
        int id;
        int prioridade;
        String descricao;
        NoProcesso proximo;
        NoProcesso(int id, int prioridade, String descricao) {
            this.id = id;
            this.prioridade = prioridade;
            this.descricao = descricao;
            this.proximo = null;
        }
        @Override
        public String toString() {
            return "(" + id + ", " + prioridade + ", \"" + descricao + "\")";
        }
    }

    private NoProcesso head = null;
    private NoProcesso tail = null;

    // inserir no fim mantendo circularidade
    public void inserirProcesso(int id, int prioridade, String descricao) {
        NoProcesso novo = new NoProcesso(id, prioridade, descricao);
        if (head == null) {
            head = tail = novo;
            novo.proximo = head;
        } else {
            tail.proximo = novo;
            tail = novo;
            tail.proximo = head;
        }
    }

    // remover por id, retorna true se removeu
    public boolean removerProcesso(int id) {
        if (head == null) return false;
        NoProcesso cur = head;
        NoProcesso prev = tail;
        boolean primeiraIter = true;
        do {
            if (cur.id == id) {
                if (cur == head && cur == tail) {
                    head = tail = null;
                } else {
                    prev.proximo = cur.proximo;
                    if (cur == head) head = cur.proximo;
                    if (cur == tail) tail = prev;
                }
                return true;
            }
            prev = cur;
            cur = cur.proximo;
            primeiraIter = false;
        } while (cur != head);
        return false;
    }

    // percorrer e imprimir (id, prioridade, descricao)
    public void percorrerEImprimir() {
        if (head == null) {
            System.out.println("[lista vazia]");
            return;
        }
        NoProcesso cur = head;
        do {
            System.out.println(cur.toString());
            cur = cur.proximo;
        } while (cur != head);
    }

    // ordenar por prioridade (menor = mais urgente) via Merge Sort
    public void ordenarPorPrioridade() {
        if (head == null || head.proximo == head) return;
        // quebrar circularidade
        tail.proximo = null;
        // aplicar merge sort (retorna cabeça da lista não circular)
        head = mergeSort(head);
        // recolocar circularidade e atualizar tail
        NoProcesso cur = head;
        while (cur.proximo != null) cur = cur.proximo;
        tail = cur;
        tail.proximo = head;
    }

    // mergeSort em lista simplesmente encadeada (head pode ser null)
    private NoProcesso mergeSort(NoProcesso h) {
        if (h == null || h.proximo == null) return h;
        NoProcesso mid = dividirLista(h);
        NoProcesso left = h;
        NoProcesso right = mid.proximo;
        mid.proximo = null; // separa
        left = mergeSort(left);
        right = mergeSort(right);
        return merge(left, right);
    }

    // encontra meio (retorna nó anterior ao início da segunda metade)
    private NoProcesso dividirLista(NoProcesso h) {
        NoProcesso slow = h;
        NoProcesso fast = h;
        NoProcesso prev = h;
        while (fast != null && fast.proximo != null) {
            prev = slow;
            slow = slow.proximo;
            fast = fast.proximo.proximo;
        }
        return prev;
    }

    // mescla duas listas ordenadas por prioridade asc
    private NoProcesso merge(NoProcesso a, NoProcesso b) {
        NoProcesso dummy = new NoProcesso(-1, -1, "");
        NoProcesso tailLocal = dummy;
        while (a != null && b != null) {
            if (a.prioridade <= b.prioridade) {
                tailLocal.proximo = a;
                a = a.proximo;
            } else {
                tailLocal.proximo = b;
                b = b.proximo;
            }
            tailLocal = tailLocal.proximo;
        }
        tailLocal.proximo = (a != null) ? a : b;
        return dummy.proximo;
    }

    // método auxiliar pra testar se necessário (não exigido)
    private boolean contemId(int id) {
        if (head == null) return false;
        NoProcesso cur = head;
        do {
            if (cur.id == id) return true;
            cur = cur.proximo;
        } while (cur != head);
        return false;
    }

    // demo simples conforme enunciado
    public static void main(String[] args) {
        ListaCircularDeProcessos lista = new ListaCircularDeProcessos();

        lista.inserirProcesso(1, 3, "executar calculadora");
        lista.inserirProcesso(2, 1, "executar navegador Chrome");
        lista.inserirProcesso(3, 5, "executar programa em java");
        lista.inserirProcesso(4, 2, "executar o Word");

        System.out.println("== Lista antes da ordenação ==");
        lista.percorrerEImprimir();

        lista.ordenarPorPrioridade();

        System.out.println("\\n== Lista depois da ordenação (por prioridade asc) ==");
        lista.percorrerEImprimir();

        System.out.println("\\n== Removendo processo id=2 ==");
        boolean rem = lista.removerProcesso(2);
        System.out.println("removido? " + rem);

        System.out.println("\\n== Lista após remoção ==");
        lista.percorrerEImprimir();
    }
}
