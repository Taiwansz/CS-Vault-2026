#include <iostream>
using namespace std;

class Pessoa {
private:
  string nome;
  string rg;
  float peso;
  float altura;

public:
  Pessoa() {
    cout << "Eu sou o contrutor" << endl;
    rg = "0.000.000-0";
  }
  ~Pessoa() { cout << "Eu sou o destrutor" << endl; }
  void setNome(string nome) { this->nome = nome; }
  string getNome() { return nome; }
  void setRg(string rg) { this->rg = rg; }
  string getRg() { return rg; }
  void setAltura(float altura) { this->altura = altura; }
  float getAltura() { return altura; }
  void setPeso(float peso) { this->peso = peso; }
  float getPeso() { return peso; }
};

int main() {
  Pessoa p;
  Pessoa *p2 = new Pessoa();
  p.setNome("Pedro Pe");
  p2->setNome("Romirdo");
  // p.nome = "credo"; // erro nome é provado
  cout << "Pessoa 1 " << p.getNome() << endl;
  cout << "Pessoa 2 " << p2->getNome() << endl;

  p.setRg("1.234.567-8");
  p.setPeso(80);
  p.setAltura(1.70);

  p2->setRg("1.111.111-1");
  p2->setPeso(111);
  p2->setAltura(1.71);

  cout << "Objeto 1" << endl;
  cout << "Nome   " << p.getNome() << endl;
  cout << "RG     " << p.getRg() << endl;
  cout << "Altura " << p.getAltura() << endl;
  cout << "Peso   " << p.getPeso() << endl;
  cout << endl << "Objeto 2" << endl;
  cout << "Nome   " << p2->getNome() << endl;
  cout << "RG     " << p2->getRg() << endl;
  cout << "Altura " << p2->getAltura() << endl;
  cout << "Peso   " << p2->getPeso() << endl;
  delete (p2);
  return 0;
}
