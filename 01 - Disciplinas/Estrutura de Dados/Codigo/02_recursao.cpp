/**************************************************************************
 * Calcular fatorial usando recursão
 * 5! = 5 * 4 * 3 * 2 * 1
 * 0! = 1
 * ************************************************************************/

#include <iostream>
using namespace std;

int fat(int n) {
  if (n <= 0)
    return 1;
  else
    return (n * fat(n - 1));
}

int main() {
  cout << "\nFatorial de 0 = " << fat(0) << "\n";
  cout << "\nFatorial de 1 = " << fat(1) << "\n";
  cout << "\nFatorial de 3 = " << fat(3) << "\n";
  int numero;
  cout << "Fatorial de : ";
  cin >> numero;
  cout << "\nFatorial de " << numero << " = " << fat(numero) << "\n";
  return 0;
}