#include <iostream>
using namespace std;
#define MAX 5

typedef int TIPOCHAVE;

struct REGISTRO {
  TIPOCHAVE chave;
  // outros campos
};

class ListaEstica {
private:
  REGISTRO A[MAX];
  int nroElem;

public:
  void inicializaLista() { nroElem = 0; }

  int tamanho() { return nroElem; }

  void imprimirLista() {
    int i;
    cout << "Lista [ ";
    for (i = 0; i < nroElem; i++)
      cout << A[i].chave << " ";
    cout << " ]" << endl;
  }

  int buscaSequencial(TIPOCHAVE ch) {
    int i = 0;
    while (i < nroElem) {
      if (ch == A[i].chave)
        return i;
      else
        i++;
    }
    return -1;
  }

  bool inserirPosicaoLista(REGISTRO reg, int i) {
    int j;
    if ((nroElem == MAX) || (i < 0) || (i > nroElem))
      return false;
    for (j = nroElem; j > i; j--)
      A[j] = A[j - 1];
    A[i] = reg;
    nroElem++;
    return true;
  }

  bool inserirFinalLista(REGISTRO reg) {
    if (nroElem == MAX)
      return false;
    A[nroElem] = reg;
    nroElem++; // = nroElem + 1;
    return true;
  }

  bool excluiElementoLista(TIPOCHAVE ch) {
    int pos, j;
    pos = buscaSequencial(ch);
    if (pos == -1)
      return false;
    for (j = pos; j < nroElem; j++)
      A[j] = A[j + 1];
    nroElem--;
    return true;
  }

  void reinicializaLista() { nroElem = 0; }
};
int main() {
  ListaEstica l1;
  REGISTRO r1;
  l1.inicializaLista();
  r1.chave = 21;
  l1.inserirFinalLista(r1);
  r1.chave = 9;
  l1.inserirFinalLista(r1);
  r1.chave = 55;
  l1.inserirFinalLista(r1);
  l1.imprimirLista();
  r1.chave = 3;
  l1.inserirPosicaoLista(r1, 2);
  l1.imprimirLista();
  l1.excluiElementoLista(3);
  l1.imprimirLista();
  l1.reinicializaLista();
  l1.imprimirLista();
  return 0;
  return 0;
}
