using namespace std;
#include <algorithm>
#include <iostream>
#include <vector>
struct REGISTRO {
  int chave;
  string nome;
  // outros campos
};

int main() {
  vector<REGISTRO> lista;
  REGISTRO reg;
  reg.chave = 21;
  reg.nome = "Carlos";
  lista.push_back(reg);

  reg.chave = 9;
  reg.nome = "Pedro";
  lista.push_back(reg);

  reg.chave = 55;
  reg.nome = "Ana";
  lista.push_back(reg);

  reg.chave = 3;
  reg.nome = "Ivo";
  lista.insert(lista.begin() + 1, reg);
  cout << "Vetor após as inserções:" << endl;
  cout << "Lista [ ";
  for (const auto &elemento : lista) {
    cout << "(" << elemento.chave << "-" << elemento.nome << ") ";
  }
  cout << "] " << endl;
  cout << endl;

  // delete
  reg.chave = 21;
  int i = 0;
  for (const auto &elemento : lista) {
    if (elemento.chave == reg.chave)
      break;
    i++;
  }

  lista.erase(lista.begin() + i);
  cout << "Vetor após a exclusão do número 3:" << endl;
  cout << "Lista [ ";
  for (const auto &elemento : lista) {
    cout << "(" << elemento.chave << "-" << elemento.nome << ") ";
  }
  cout << "] " << endl;
  cout << endl;

  lista.clear();
  cout << "Vetor após a clear() :" << endl;
  cout << "Lista [ ";
  for (const auto &elemento : lista) {
    cout << "(" << elemento.chave << "-" << elemento.nome << ") ";
  }
  cout << "] " << endl;
  cout << endl;

  return 0;
}
