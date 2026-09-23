/***********************************************************************
 * Programa para calcular a somatória dos N primeiros números inteiros *
 * *********************************************************************/
#include <iostream>
using namespace std;

int somaInteiro(int n) {
    int soma = 0;
    for (int i = 0; i <= n;i++)
        soma += i;
    return soma;
}

int main() {
  int i;
  cout << "\nPrograma para calcular a somatória dos \"N\" primeiros números "
          "inteiros\n\n";
  cout << "Digigte um número: ";
  cin >> i;
  cout << "somatória " << i << " = " << somaInteiro(i) << endl;
  return 0;
}