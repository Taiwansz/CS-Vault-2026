#include <iostream>
using namespace std;

void imprime(int n) {
  if (n > 0) {
    cout << n << ", ";
    imprime(n - 1);
  }
}

int main() {
  cout << "Imprime de 5 ate 1 usando recursao" << endl;
  imprime(5);
  return 0;
}