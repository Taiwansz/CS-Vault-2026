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

class Funcionario : public Pessoa {
private:
  float salario;
  string setor;

public:
  Funcionario() { cout << "Eu sou construtor funcionarios" << endl; }
  ~Funcionario() { cout << "Eu sou destrutor funcionarios" << endl; }
  void setSalario(float salario) { this->salario = salario; }
  float getSalario() { return salario; }
  void setSetor(string setor) { this->setor = setor; }
  string getSetor() { return setor; }
};

int main() {
  Funcionario p;
  Funcionario *p2 = new Funcionario();
  p.setNome("Pedro Pe");
  p2->setNome("Romirdo");
  // p.nome = "credo"; // erro nome é provado
  cout << "Pessoa 1 " << p.getNome() << endl;
  cout << "Pessoa 2 " << p2->getNome() << endl;

  p.setRg("1.234.567-8");
  p.setPeso(80);
  p.setAltura(1.70);
  p.setSalario(15000.01);
  p.setSetor("TI");

  p2->setRg("1.111.111-1");
  p2->setPeso(111);
  p2->setAltura(1.71);
  p2->setSalario(1500.33);
  p2->setSetor("Secretaria");

  cout << "Objeto 1" << endl;
  cout << "Nome    " << p.getNome() << endl;
  cout << "RG      " << p.getRg() << endl;
  cout << "Altura  " << p.getAltura() << endl;
  cout << "Peso    " << p.getPeso() << endl;
  cout << "Salario " << p.getSalario() << endl;
  cout << "Setor   " << p.getSetor() << endl;
  cout << endl << "Objeto 2" << endl;
  cout << "Nome    " << p2->getNome() << endl;
  cout << "RG      " << p2->getRg() << endl;
  cout << "Altura  " << p2->getAltura() << endl;
  cout << "Peso    " << p2->getPeso() << endl;
  cout << "Salario " << p2->getSalario() << endl;
  cout << "Setor   " << p2->getSetor() << endl;
  delete (p2);
  return 0;
}
