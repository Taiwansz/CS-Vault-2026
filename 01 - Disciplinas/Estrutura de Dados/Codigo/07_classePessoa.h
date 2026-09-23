#ifndef _CLASSEPESSOA_H_
#define _CLASSEPESSOA_H_
#include <iostream>
using namespace std;
class Pessoa {
private:
  string nome;
  string rg;
  float peso;
  float altura;

public:
  Pessoa();
  ~Pessoa();
  void setNome(string);
  string getNome();
  void setRg(string);
  string getRg();
  void setAltura(float);
  float getAltura();
  void setPeso(float);
  float getPeso();
};
#endif