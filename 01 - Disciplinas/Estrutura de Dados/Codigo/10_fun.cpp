#include <iostream>
using namespace std;

int main() {
  int v1, v2, maior;
  cout << "Digite um numero: ";
  cin >> v1;
  cout << "Digite outro numero: ";
  cin >> v2;
  if (v1 > v2) {
    maior = v1;
  } else {
    maior = v2;
  }
  cout << "O maior numero " << maior << endl;
  return 0;
}