#include <iostream>
using namespace std;

void imprimeFor(int n) {
  for (int i = n; i > 0; i--)
    cout << i << " ";
}

void imprime(int n) {
  if (n != 0) {
    cout << n << " ";
    imprime(n - 1);
  }
}

void imprime2(int n) {
  if (n != 0) {
    imprime2(n - 1);
    cout << n << " ";
  }
}

int main() {
  cout << "\nUsando for: ";
  imprimeFor(5);
  cout << "\n\nUsando reursao: ";
  imprime(5);
  cout << "\n\nUsando reursao(2): ";
  imprime2(5);
  return 0;
}