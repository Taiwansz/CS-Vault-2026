#include <iomanip>
#include <iostream>
using namespace std;
int main() {
  int idade;
  float salario;
  double altura;
  char tamanho;
  string nome;
  idade = 53;
  salario = 1.12345678901234567890;
  altura = 9.12345678901234567890;
  tamanho = 'x';
  nome = "Carlos Miglinski";
  cout << fixed << setprecision(10);
  cout << endl << endl << "Listagem" << endl;
  cout << "Nome...............: " << nome << endl;
  cout << "Idade..............: " << idade << endl;
  cout << "Altura.............: " << altura << endl;
  cout << "Pretencao salarial : " << salario << endl;
  cout << "Tamanho do uniforme: " << tamanho << endl;
  return 0;
}