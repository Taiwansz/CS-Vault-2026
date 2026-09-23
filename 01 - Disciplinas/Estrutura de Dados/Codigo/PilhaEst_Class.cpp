#include <iostream>
using namespace std;
#define MAX 3
typedef int TIPOCHAVE;

struct REGISTRO {
  TIPOCHAVE chave;
  // outros campos
};

class PilhaEstatica {
private:
  REGISTRO A[MAX];
  int topo;

public:
  void inicializaPilha() { topo = -1; }

  int tamanhoPilha() { return topo + 1; }

  void listarPilha() {
    cout << "Pilha [";
    int i;
    for (i = topo; i >= 0; i--) {
      cout << A[i].chave << " ";
    }
    cout << "]\n";
  }

  bool pushPilha(REGISTRO reg) {
    if (topo >= MAX - 1)
      return false;
    topo = topo + 1;
    A[topo] = reg;
    return true;
  }

  bool popPilha(REGISTRO *reg) {
    if (topo == -1)
      return false;
    *reg = A[topo];
    topo = topo - 1;
    return true;
  }

  void reinicializaPilha() { topo = -1; }
};
int main() {
  PilhaEstatica p1;
  REGISTRO reg1;
  p1.inicializaPilha();
  cout << "tamanho da pilha " << p1.tamanhoPilha() << endl;
  p1.listarPilha();
  reg1.chave = 10;
  if (p1.pushPilha(reg1))
    cout << "Elemento " << reg1.chave << " inserido com sucesso " << endl;
  else
    cout << "erro pilha cheia!!\n";
  reg1.chave = 20;
  if (p1.pushPilha(reg1))
    cout << "Elemento " << reg1.chave << " inserido com sucesso\n";
  else
    cout << "erro pilha cheia!!\n";
  reg1.chave = 30;
  if (p1.pushPilha(reg1))
    cout << "Elemento " << reg1.chave << " inserido com sucesso\n";
  else
    cout << "erro pilha cheia!!\n";
  p1.listarPilha();
  cout << "tamanho da pilha " << p1.tamanhoPilha() << endl;
  reg1.chave = 40;
  if (p1.pushPilha(reg1))
    cout << "Elemento " << reg1.chave << " inserido com sucesso\n";
  else
    cout << "erro pilha cheia!!\n";
  p1.listarPilha();
  cout << "tamanho da pilha " << p1.tamanhoPilha() << endl;
  if (p1.popPilha(&reg1)) {
    cout << "pop(" << reg1.chave << ")" << endl;
  }
  p1.listarPilha();
  p1.reinicializaPilha();
  cout << "tamanho da pilha " << p1.tamanhoPilha() << endl;
  p1.listarPilha();
  return 0;
}