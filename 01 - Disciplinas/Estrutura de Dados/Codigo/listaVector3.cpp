using namespace std;
#include <algorithm>
#include <iostream>
#include <vector>
struct REGISTRO {
  int chave;
  string nome;
  // outros campos
};
class listaVector {
private:
  vector<REGISTRO> lista;

public:
  void reinicializaLista() { lista.clear(); }
  void insereNaLista(REGISTRO reg) { lista.push_back(reg); }

  void insereNaPosicao(REGISTRO reg, int pos) {
    lista.insert(lista.begin() + pos, reg);
  }

  void imprime() {
    cout << "Lista [ ";
    for (const auto &elemento : lista) {
      cout << "(" << elemento.chave << "-" << elemento.nome << ") ";
    }
    cout << "] " << endl;
    cout << endl;
  }

  bool apaga(int chave) {
    int i = 0;
    bool achou = false;
    for (const auto &elemento : lista) {
      if (elemento.chave == chave) {
        achou = true;
        break;
      }
      i++;
    }
    if (achou)
      lista.erase(lista.begin() + i);
    return achou;
  }
};
int main() {
  REGISTRO reg;
  reg.chave = 21;
  reg.nome = "Carlos";
  listaVector *lista = new listaVector();
  lista->insereNaLista(reg);
  reg.chave = 9;
  reg.nome = "Pedro";
  lista->insereNaLista(reg);

  reg.chave = 55;
  reg.nome = "Ana";
  lista->insereNaLista(reg);
  cout << "Vetor apos as insercoes no final da fila:" << endl;
  lista->imprime();
  reg.chave = 3;
  reg.nome = "Ivo";
  lista->insereNaPosicao(reg, 1);
  cout << "Vetor apos as insercao na 2 posicao :" << endl;
  lista->imprime();
  if (lista->apaga(3))
    cout << "Consegui apagar chave 3" << endl;
  else
    cout << "Nao Consegui apagar chave 3" << endl;
  lista->imprime();
  cout << "Reinicializando a lista" << endl;
  lista->reinicializaLista();
  lista->imprime();
  return 0;
}
