#include <iostream>
using namespace std;

typedef int TIPOCHAVE;

struct REGISTRO {
  TIPOCHAVE chave;
};

class FilaEstatica {
private:
  REGISTRO *A;
  int inicio;
  int nroElem;
  int max;

public:
  FilaEstatica(int max) {
    // void inicializarFila() {
    inicio = 0;
    nroElem = 0;
    this->max = max;
    A = new REGISTRO[max];
  }

  int tamanhoFila() { return nroElem; }

  void listarFila() {
    cout << "Fila: [ ";
    int i = inicio;
    int temp;
    for (temp = 0; temp < nroElem; temp++) {
      cout << A[i].chave << " ";
      i = (i + 1) % max; // se o i passar do máximo, i volta a zero
    }
    cout << "]" << endl;
  }

  bool inserirElemFila(REGISTRO reg) {
    if (nroElem >= max)
      return false;
    int posicao = (inicio + nroElem) % max;
    A[posicao] = reg;
    nroElem++;
    return true;
  }

  bool excluirElementoFila(REGISTRO *reg) {
    if (nroElem == 0)
      return false;
    *reg = A[inicio];
    inicio = (inicio + 1) % max;
    nroElem--;
    return true;
  }

  void reinicializaFila() {
    inicio = 0;
    nroElem = 0;
  }
};
int main() {
  FilaEstatica *f = new FilaEstatica(3);
  REGISTRO r1;
  cout << "Tamanho da fila " << f->tamanhoFila() << endl;
  r1.chave = 10;
  if (f->inserirElemFila(r1))
    cout << "Elemento " << r1.chave << " inserido com sucesso\n";
  else
    cout << "Erro, fila cheia\n";
  r1.chave = 20;
  if (f->inserirElemFila(r1))
    cout << "Elemento " << r1.chave << " inserido com sucesso\n";
  else
    cout << "Erro, fila cheia\n";

  r1.chave = 30;
  if (f->inserirElemFila(r1))
    cout << "Elemento " << r1.chave << " inserido com sucesso\n";
  else
    cout << "Erro, fila cheia\n";
  r1.chave = 40;
  if (f->inserirElemFila(r1))
    cout << "Elemento " << r1.chave << " inserido com sucesso\n";
  else
    cout << "Erro, fila cheia\n";
  f->listarFila();
  cout << "Tamanho da fila " << f->tamanhoFila() << endl;
  f->excluirElementoFila(&r1);
  f->listarFila();

  f->reinicializaFila();
  f->listarFila();
  return 0;
}