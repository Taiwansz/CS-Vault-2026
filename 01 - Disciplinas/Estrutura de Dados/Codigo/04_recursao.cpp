/***********************************************************************
 * Programa para calcular a somatória dos N primeiros números inteiros *
 * *********************************************************************/
#include <iostream>
using namespace std;

int somaInteiro(int n) {
  if (n == 0)
    return (0);
  else
    return (n + somaInteiro(n - 1));
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