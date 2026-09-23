#include <iomanip>
#include <iostream>
using namespace std;
int main() {
  int idade;
  float salario;
  double altura;
  char tamanho;
  string nome;
  cout << "Qual a idade: ";
  cin >> idade;
  cout << "Qual o nome : ";
  cin >> nome;
  cout << "Qual o tamanho do uniforme (p,m,g,x) : ";
  cin >> tamanho;
  cout << "Qual a pretencao salarial : ";
  cin >> salario;
  cout << "Qual a sua altura : ";
  cin >> altura;

  cout << endl << endl << "Listagem" << endl;
  cout << "Nome...............: " << nome << endl;
  cout << "Idade..............: " << idade << endl;
  cout << "Altura.............: " << altura << endl;
  cout << "Pretencao salarial : " << salario << endl;
  cout << "Tamanho do uniforme: " << tamanho << endl;
  return 0;
}
